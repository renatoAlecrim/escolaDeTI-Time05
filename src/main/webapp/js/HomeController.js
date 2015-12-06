module.controller("HomeController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http) {

        var yy;
        var calendarArray = [];
        var monthOffset = [6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5];
        var monthArray = [["JAN", "Janeiro"], ["FEV", "Fevereiro"], ["MAR", "Março"], ["ABR", "Abril"], ["MAI", "Maio"], ["JUN", "Junho"], ["JUL", "Julho"], ["AGO", "Agosto"], ["SET", "Setembro"], ["OUT", "Outubro"], ["NOV", "Novembro"], ["DEZ", "Dezembro"]];
        var letrasArray = ["Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"];
        var subLetras = ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"];
        var dayArray = ["1", "2", "3", "4", "5", "6", "7"];

        function novoEvento() {
            $scope.eventos = {
                descricao: "",
                horainicial: "",
                horafinal: "",
                datainicial: "",
                datafinal: "",
                visualizarnocalendario: ""
            };
        }

        function getEventosDoCalendario() {
            novoEvento();
            $http.get("/eventos/carregaCalendario").success(function (data) {
                $scope.eventos = data;
//                console.log($scope.eventos);
            }).error(erroNoEvento);
        }

//        function cadastrarevento() {
//            var dia = new Date(parseInt($(this).attr('time')));
//            console.log("Cadastrar Evento do dia: " + dia.getDate());
//            
//        }

        $(document).ready(function () {
            getEventosDoCalendario();
            setTimeout(function () {
                $(document).on('click', '.calendar-day.have-events', activateDay);
                $(document).on('click', '.calendar-day.no-have-events', insereEvento);
                $(document).on('click', '.specific-day', activatecalendar);
                $(document).on('click', '.calendar-month-view-arrow', offsetcalendar);
//                $(document).on('click', '.calendar-day', cadastrarevento);
                $(window).resize(calendarScale);
                calendarSet();
                calendarScale();
            }, 100);
        });

        function calendarScale() {
            $(".calendar").each(function () {
                if ($(this).width() < 400 && !$(this).hasClass('small')) {
                    $(this).addClass('small');
                } else if ($(this).width() > 400 && $(this).hasClass('small')) {
                    $(this).removeClass('small');
                }
            })
        }

        function offsetcalendar() {
            var cm = parseInt($(".calendar").attr('offset'));
            if ($(this).data('dir') === "left") {
                calendarSetMonth(cm - 1);
            } else if ($(this).data('dir') === "right") {
                calendarSetMonth(cm + 1);
            }

        }

        function orderBy(deli, array) {
            var p = array.slice();
            var o = p.length;
            var y, t;
            var temparray = [];
            for (var u = 0; u < o; u++) {
                for (var uu = 0; uu < p.length; uu++) {
                    if (uu === 0) {
                        t = uu;
                        y = p[uu];
                    }
                    else if (parseInt(p[uu][deli].replace(':', '')) < parseInt(y[deli].replace(':', ''))) {
                        y = p[uu];
                        t = uu;
                    }
                }
                temparray.push(y);
                p.splice(t, 1);
            }
            return temparray;
        }

        function calendarSet() {
            $(".calendar").append('<div class="calendar-month-view"><div class="calendar-month-view-arrow" data-dir="left"><i class="fa fa-chevron-left"></i></div><p></p><div class="calendar-month-view-arrow" data-dir="right"><i class="fa fa-chevron-right"></i></div><div class="letrasDay"></div></div><div class="calendar-holder"><div class="calendar-grid"></div><div class="calendar-specific"><div class="specific-day"><div class="specific-day-info" i="day"></div><div class="specific-day-info" i="month"></div></div><div class="specific-day-scheme"></div></div></div>');

            if ($(this).data("color") === undefined) {
                $(this).data("color", "red");
            }

            var tempdayarray = [];
            for (var i = 0; i < $scope.eventos.length; i++) {
                if ($scope.eventos[i].visualizarnocalendario) {
                    var periodo = getPeriodo($scope.eventos[i].datainicial, $scope.eventos[i].datafinal);
                    for (var j = 0; j <= periodo; j++) {
                        var tempeventarray = [];
                        var date = new Date($scope.eventos[i].datainicial);
                        date.setDate(date.getDate() + (j + 1));

                        var mes;
                        if ((date.getMonth() + 1) < 10) {
                            mes = '0' + (date.getMonth() + 1);
                        } else {
                            mes = (date.getMonth() + 1);
                        }

                        var dia;
                        if (date.getDate() < 10) {
                            dia = '0' + date.getDate();
                        } else {
                            dia = date.getDate();
                        }

                        var strdate = date.getFullYear() + '' + mes + '' + dia;
                        tempeventarray["datainicial"] = strdate; //$scope.eventos[i].datainicial.replaceAllCaracteresEspecial('-', '');
                        tempeventarray["datafinal"] = $scope.eventos[i].datafinal.replaceAllCaracteresEspecial('-', '');
                        tempeventarray["horainicial"] = $scope.eventos[i].horainicial.substring(0, 2) + ":" + $scope.eventos[i].horainicial.substring(3, 5);
                        tempeventarray["horafinal"] = $scope.eventos[i].horafinal.substring(0, 2) + ":" + $scope.eventos[i].horafinal.substring(3, 5);
                        tempeventarray["descricao"] = $scope.eventos[i].descricao;
                        tempeventarray["tipoevento"] = $scope.eventos[i].tipoevento;
                        tempdayarray.push(tempeventarray);
                    }
                }
            }

            calendarArray = [];
            for (var i = 0; i < tempdayarray.length; i++) {
                var tempdayevent = [];
                if (calendarArray[tempdayarray[i].datainicial] === undefined) {
                    for (var j = 0; j < tempdayarray.length; j++) {
                        var tempevent = [];
                        if (tempdayarray[i].datainicial === tempdayarray[j].datainicial) {
                            tempevent["name"] = tempdayarray[j].descricao;
                            tempevent["timestart"] = tempdayarray[j].horainicial;
                            tempevent["timeend"] = tempdayarray[j].horafinal;
                            tempevent["tipo"] = tempdayarray[j].tipoevento;
//                            tempevent["datestart"] = tempdayarray[j].datainicial.substring(0, 2) + "/" + tempdayarray[j].datainicial.substring(3, 5) + "/" + tempdayarray[j].datainicial.substring(6, 10);
//                            tempevent["dateend"] = tempdayarray[j].datafinal.substring(0, 2) + "/" + tempdayarray[j].datafinal.substring(3, 5) + "/" + tempdayarray[j].datafinal.substring(6, 10);
                            tempdayevent.push(tempevent);
                        }
                    }
                    calendarArray[tempdayarray[i].datainicial] = tempdayevent;
                }
            }

            $(".calendar [data-role=day]").remove();
            calendarSetMonth();
        }

        function getPeriodo(datainicialevento, datafinalevento) {
            var datainicial = new Date(datainicialevento);
            var datafinal = new Date(datafinalevento);
            var milisegundos = datafinal - datainicial;
            var segundos = milisegundos / 1000;
            var minutos = segundos / 60;
            var horas = minutos / 60;
            return horas / 24;
        }

        String.prototype.replaceAllCaracteresEspecial = function (de, para) {
            var str = this;
            var pos = str.indexOf(de);
            while (pos > -1) {
                str = str.replace(de, para);
                pos = str.indexOf(de);
            }
            return (str);
        }

        function insereEvento() {
            var di = new Date(parseInt($(this).attr('time')));
            var strtime = $(this).attr('strtime');
            var d = new Object();
            d.data = di.getFullYear() + "-" + (di.getMonth() + 1) + "-" + di.getDate();
            window.location.href = '#/Eventos/novo/' + d.data;
        }

        function activateDay() {
            $(this).parents('.calendar').addClass('spec-day');
            var di = new Date(parseInt($(this).attr('time')));
            var strtime = $(this).attr('strtime');
            var d = new Object();
            d.day = di.getDate();
            d.month = di.getMonth();
            d.data = di.getFullYear() + "-" + (d.month + 1) + "-" + d.day;
            d.events = calendarArray[strtime];
            d.week = di.getDay() + 1;
            if (d.week == 0) {
                d.week = 6;
            }
            else {
                d.week--;
            }
            d.tocalendar = tocalendar;
            d.tocalendar();
        }
        var tocalendar = function () {
            $(".specific-day-info[i=day]").html(this.day + ' <span>' + monthArray[this.month][0] + '</span>');
            $(".specific-day-info[i=month]").html("<div class='hidden-sm hidden-xs'>" + letrasArray[this.week] + "</div>");
            $(".specific-day-info[i=month]").append("<div class='hidden-md hidden-lg'>" + subLetras[this.week] + "</div>");
            if (this.events !== undefined) {
                var ev = orderBy('timestart', this.events);
                for (var o = 0; o < ev.length; o++) {
                    $(".specific-day-scheme").append('<div class="specific-day-scheme-event border-'+ ev[o]['tipo'] +'"><p>' + ev[o]['name'] + '</p><p data-role="dur">' + ev[o]['timestart'] + ' - ' + ev[o]['timeend'] + '</p></div>'); //<p data-role="loc">' + ev[o]['datestart'] + ev[o]['dateend'] + '</p></div>'); // Monta os eventos do dia clicado.
                }
            }
            $(".specific-day-scheme").append('<br><p><a class="btn btn-success" href="#/Eventos/novo/' + this.data + '">Adicionar novo evento</a></p>');
        }
        function activatecalendar() {
            $(this).parents('.calendar').removeClass('spec-day');
            $(".specific-day-scheme").html('');
        }

        function calendarSetMonth(offset) {
            $(".calendar-grid").html('');
            $(".letrasDay").html('');
            var d = new Date();
            var c = new Date();
            var e = new Date();
            var p = d;
            if (offset !== undefined) {
                d = monthChange(p, offset);
                e = monthChange(p, offset);
                $(".calendar").attr('offset', offset);
            } else {
                $(".calendar").attr('offset', 0);
            }
            $(".calendar .calendar-month-view p").html('<span>' + d.getFullYear() + ' <i class="fa fa-calendar"></i> ' + monthArray[d.getMonth()][1] + '</span>');
            d.setDate(1);
            if (dayArray[d.getDay()] === 1) {
                d.setDate(d.getDate() - 7);
            } else {
                d.setDate(d.getDate() - dayArray[d.getDay()] + 1);
            }
            for (var i = 0; i < 7; i++) {
                var dias_Semana = $('<div class="hidden-sm hidden-xs">' + letrasArray[i] + '</div>');
                $(".letrasDay").append(dias_Semana);
            }
            for (var i = 0; i < 7; i++) {
                var dias_subSemana = $('<div class="hidden-md hidden-lg">' + subLetras[i] + '</div>');
                $(".letrasDay").append(dias_subSemana);
            }

            for (var i = 0; i < 42; i++) {
                d.setDate(d.getDate() + i);
                var cal_day = $('<div class="calendar-day"><div class="date-holder">' + d.getDate() + '</div></div>');
                if (d.getMonth() !== e.getMonth()) {
                    cal_day.addClass('other-month');
                }

                if (d.getDate() == c.getDate() && d.getMonth() == c.getMonth() && d.getFullYear() == c.getFullYear()) {
                    cal_day.addClass('this-day');
                }

                if (d.getDay() == 6 || d.getDay() == 0) {
                    cal_day.addClass('fds');
                }

                var mes;
                if ((d.getMonth() + 1) < 10) {
                    mes = '0' + (d.getMonth() + 1);
                } else {
                    mes = (d.getMonth() + 1);
                }

                var dia;
                if (d.getDate() < 10) {
                    dia = '0' + d.getDate();
                } else {
                    dia = d.getDate();
                }

                var strtime = d.getFullYear() + '' + mes + '' + dia;
                if (calendarArray[strtime] !== undefined) {
                    cal_day.addClass('have-events');
                }
                else {
                    cal_day.addClass('no-have-events');
                }
                var cal_day_eventholder = $('<div class="event-notif-holder"></div>');
                
                if (calendarArray[strtime] != undefined) {
                    for (var u = 0; u < 5 && u < calendarArray[strtime].length; u++) {
                        //console.log(calendarArray[strtime]);
                        cal_day_eventholder.append('<div class="' + calendarArray[strtime][u].tipo + '"></div>');
                    }
                }
                cal_day.attr('strtime', strtime);
                cal_day.attr('time', d.getTime());
                cal_day.prepend(cal_day_eventholder);

                $(".calendar-grid").append(cal_day);
                d.setDate(d.getDate() - i);
            }
        }

        function monthChange(d, o) {
            var dim = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            var day = d.getDate();
            var month = o !== undefined ? d.getMonth() + o : d.getMonth();
            var year = d.getFullYear();
            var hours = d.getHours();
            var minutes = d.getMinutes();
            var seconds = d.getSeconds();
            while (month > 11) {
                month = month - 12;
                year++;
            }
            while (month < 0) {
                month = month + 12;
                year--;
            }
            if (dim[month] < day) {
                day = dim[month];
            }
            return new Date(year, month, day, hours, minutes, seconds);
        }

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function erroNoEvento() {
            toastr.error("Erro ao carregar os eventos.");
        }


    }]);