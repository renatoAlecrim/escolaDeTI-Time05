module.controller("EventosController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar Eventos";
        $scope.ent = $rootScope.ent = "eventos";
        $scope.campoPrincipal = 'descricao';

        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
            if (campo == null || campo == "") {
                campo = $scope.campoPrincipal;
            }
            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
            atualizaScope;
        };

        function atualizaScope() {
            $scope = $rootScope;
        }

        $rootScope.atualizarListagens = $scope.atualizarListagens;

        $scope.registrosPadrao = function () {
            $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
            $rootScope.numregistros = $scope.busca.numregistros;
        };

        $scope.fazPesquisa = function (registros, string) {
            $rootScope.string = string;
            $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
        };

        function novoEvento() {
            $scope.evento = {
                descricao: "",
                datainicial: "",
                datafinal: "",
                horainicial: "",
                horafinal: "",
                visualizarnocalendario: "true"                
            };
            $scope.isNovoEvento = true;            
        }

        $scope.novoEvento = function () {
            novoEvento();
            //$("#visualizarnocalendario").val('true');
        };

        $scope.carregarEvento = function () {
            if ($location.path().substring(0, 13) === "/Eventos/novo") {
                novoEvento();
                if($routeParams.data){
                    var d = new Date($routeParams.data);
                    var dataCerta = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                    $scope.evento.datainicial = dataCerta;
                    $scope.evento.datafinal = dataCerta;
                }
            }
            else {
                $http.get("/evento/" + $routeParams.id)
                        .success(function (data) {
                            $scope.evento = data[0];
                            $scope.isNovoEvento = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.atualizarEvento = function () {
            $http.get("/evento")
                    .success(function (data) {
                        $scope.evento = data;
                    })
                    .error(deuErro);
        };

        $scope.editarEvento = function (evento) {
            $location.path("/Eventos/editar/" + evento.idevento);
        };

        $scope.taNoCalendario = function (valor) {
            if (valor) 
                return "SIM";
            else
                return "NÃO";
        }

        $scope.dateToData = function (valor) {
            var date = new Date(valor);
            date = new Date(date.getTime() + (date.getTimezoneOffset() * 60000));
            var dia = (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
            var data = dia + "/" + (((date.getMonth() + 1) < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + date.getFullYear();
            return (valor != null) ? data : "";
        };
        
        function setVisualizar(value){
            $scope.evento.visualizarnocalendario = value;
        }


        $scope.salvarEvento = function () {

            var hInicial = new Date($scope.evento.horainicial);
            hInicial = hInicial.getHours() + ":" + hInicial.getMinutes() + ":00"; 
            var hFinal = new Date($scope.evento.horafinal);
            hFinal = hFinal.getHours() + ":" + hFinal.getMinutes() + ":00"; 
            var dataInicio = dataToDate($scope.evento.datainicial);
            var dataFinal = dataToDate($scope.evento.datafinal);
            var eventoCorreto = {
                descricao: $scope.evento.descricao,
                horainicial: hInicial,
                horafinal: hFinal,
                datainicial: dataInicio + "T00:00:00-03",
                datafinal: dataFinal + "T00:00:00-03",
                visualizarnocalendario: $scope.evento.visualizarnocalendario
            };

            if(new Date (dataInicio) <= new Date (dataFinal)){
              $http.post("/eventos", eventoCorreto)
                    .success(function () {
                        $location.path("/Eventos/listar");
                        toastr.success("Evento inserido com sucesso!");
                    })
                    .error(deuErroSalvar);
           }
           else{
               toastr.warning("Datas Invalidas");
           }

        };

        $scope.confirmaExclusao = function(entidade, nomeEntidade, nomeRegistro, id) {
            jQuery('#apagarModal').modal('show', {backdrop: 'static'});
            $scope.dadosExclusao = {};
            $scope.dadosExclusao.entidade = entidade;
            $scope.dadosExclusao.nomeEntidade = nomeEntidade;
            $scope.dadosExclusao.nomeRegistro = nomeRegistro;
            $scope.dadosExclusao.id = id;
        };
        
        $scope.excluiRegistro = function () {
            ServiceFuncoes.excluiRegistro($scope.dadosExclusao.entidade, $scope.dadosExclusao.nomeEntidade, $scope.dadosExclusao.nomeRegistro, $scope.dadosExclusao.id);
            $timeout(function() { 
                $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoAtual, '', '', $rootScope.ent, false);
            },100);
        };
        
        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar o evento, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, erro ao tentar salvar o evento, verifique!");
        }

    }]);