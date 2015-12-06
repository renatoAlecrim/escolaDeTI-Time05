module.controller("EstadiaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', 'ServiceFuncoes', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {
        $scope.busca = {};
        $scope.placeHolder = "Buscar estadia";
        $scope.ent = $rootScope.ent = "estadia";
        $scope.campoPrincipal = 'nomefamilia';
        $scope.isNovaEstadia = true;
        var diasSemana = new Array("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-Feira", "Sábado");

        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
            if (campo == null || campo == "") {
                campo = $scope.campoPrincipal;
            }
            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
            atualizaScope;
        };

        $scope.chamarModalEstadia = function (idestadia) {
            jQuery('#modalEstadia').modal('show', {backdrop: 'static'});
            $scope.carregarEstadiaModal(idestadia);
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

        $scope.print = function (divNome) {
            var printContents = document.getElementById(divNome).innerHTML;
            document.body.innerHTML = printContents;
            $location.path("/Estadia/listar");
            location.reload(true);
            window.print();
        };

        function novaEstadia() {
            $scope.estadia = {
                idestadia: "",
                dataentrada: "",
                datasaida: "",
                observacoesentrada: "",
                observacoessaida: "",
                familia: "",
                representante: "",
                membros: []
            };
            $scope.isNovaEstadia = true;
        }

        $scope.saidaEstadiaNova = function () {
//            console.log("asdasd");
            $scope.saida = {
                idestadia: "",
                datasaida: "",
                observacoessaida: ""
            };
        };

        $scope.novaEstadia = function () {
            novaEstadia();
        };

        $scope.carregarEstadia = function () {
            if ($location.path() === "/Estadia/nova") {
                novaEstadia();
            } else {
                $http.get("/estadia/obj/" + $routeParams.id)
                        .success(function (data) {
                            novaEstadia();
                            $scope.carregarMembros(data.familia.idfamilia);
                            getSelects($routeParams.id);
                            $scope.estadia.idestadia = $routeParams.id;
                            var d = new Date(data.dataentrada);
                            $scope.estadia.dataentrada = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                            d = (data.datasaida) ? new Date(data.datasaida) : "";
//                            console.log(data.datasaida);
                            $scope.estadia.datasaida = (data.datasaida) ? new Date(d.getTime() + (d.getTimezoneOffset() * 60000)) : "";
//                            console.log($scope.estadia.datasaida);
                            $scope.estadia.observacoesentrada = data.observacoesentrada;
                            $scope.estadia.observacoessaida = data.observacoessaida;
//                            console.log(data.familia);
                            $scope.estadia.familia = {
                                "nomefamilia": data.familia.nomefamilia,
                                "quantidade": data.familia.membros.length,
                                "telefone": (data.familia.telefone)?data.familia.telefone.telefone:"",
                                "idrepresentante": data.familia.representante.codigoassindi,
                                "nome": data.familia.representante.nome,
                                "idfamilia": data.familia.idfamilia
                            };
//                            console.log($scope.estadia.familia);
                            $scope.estadia.representante.telefone = (data.representante.telefone) ? data.representante.telefone.telefone : "";

                            $scope.isNovaEstadia = false;
//                            console.log("Editar:");
//                            console.log($scope.estadia);
                        })
                        .error(deuErro);
            }
        };

        $scope.getNomeFamilia = function (familia) {
            $http.get("/familia/obj/" + familia.idfamilia).success(function (data) {
                $scope.nomeFamilia = data.nomefamilia;
            }).error(deuErro);
        };

        $scope.carregarEstadiaModal = function (idEstadia) {
//            console.log(idEstadia);
            $timeout(function () {
                $http.get("/estadia/obj/" + idEstadia)
                        .success(function (data) {
                            novaEstadia();
//                            $timeout(function () {
                            $scope.getNomeFamilia(data.familia);
//                            }, 500); 
//                            console.log(data);
                            $scope.estadia.idestadia = idEstadia;
                            $scope.dataentradaModal = $scope.dateToSoData(data.dataentrada);

                            var d = new Date(data.dataentrada);
                            $scope.estadia.dataentrada = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                            $scope.diaSemana = diasSemana[$scope.estadia.dataentrada.getDay()];
                            d = (data.datasaida) ? new Date(data.datasaida) : "";

                            var ds = new Date(data.datasaida);
                            $scope.estadia.datasaida = new Date(ds.getTime() + (ds.getTimezoneOffset() * 60000));
                            if (data.datasaida !== null) {
                                $scope.dataSaidaModal = $scope.dateToSoData(data.datasaida);
                                $scope.diaSemanaSaida = "(" + diasSemana[$scope.estadia.datasaida.getDay()] + ")";
                                $scope.cor = "success";
                                $scope.status = " concluída";
                            } else {
                                $scope.dataSaidaModal = "Não informado";
                                $scope.diaSemanaSaida = "";
                                $scope.cor = "warning";
                                $scope.status = " em andamento";
                            }

                            if (data.observacoesentrada.length > 0) {
                                $scope.estadia.observacoesentrada = data.observacoesentrada;
                            } else {
                                $scope.estadia.observacoesentrada = "Não informado";
                            }

                            if (data.observacoessaida.length > 0) {
                                $scope.estadia.observacoessaida = data.observacoessaida;
                            } else {
                                $scope.estadia.observacoessaida = "Não informado";
                            }

                            $scope.estadia.familia = data.familia.idfamilia;
                            $scope.carregarMembros($scope.estadia.familia);
                            getSelects(idEstadia);
                            $scope.estadia.representante.telefone = (data.representante.telefone)?data.representante.telefone.telefone:"";
//                            console.log("es");
//                            console.log($scope.estadia);
                        })
                        .error(deuErro);
            }, 100);
        };

        function getSelects(id) {
            $http.get("/estadia/getRepresentante/" + id).success(function (data) {
                $scope.estadia.representante = data;
            }).error(deuErro);

            $http.get("/estadia/getMembros/" + id).success(function (data) {
                $scope.estadia.membros = data;
            }).error(deuErro);

        }

        $scope.salvarDataSaida = function () {
            $scope.saida.idestadia = $routeParams.id;
//            console.log($scope.saida);
            $http.post("/estadia/saida", $scope.saida)
                    .success(function () {
                        toastr.success("Atualizado com sucesso!");
                    })
                    .error(deuErroSalvar);
            $timeout(function () {
                $scope.atualizarListagens($scope.busca.numregistros, $scope.pagina, $scope.campoAtual, '', '', $scope.ent, '');
            }, 100);
        };

        $scope.atualizarEstadia = function () {
            $http.get("/estadia")
                    .success(function (data) {
                        $scope.estadia = data;
                    })
                    .error(deuErro);
        };

        $scope.editarEstadia = function (idestadia) {
            $location.path("/Estadia/editar/" + idestadia);
        };

        $scope.dateToData = function (valor) {

            var date = new Date(valor);
            date = new Date(date.getTime() + (date.getTimezoneOffset() * 60000));
            var dia = (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
            var data = dia + "/" + (((date.getMonth() + 1) < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + date.getFullYear();
            return (valor != null) ? data : "";
        };


        $scope.salvarEstadia = function () {
            var estadiaCorreto = {
                idestadia: $scope.estadia.idestadia,
                dataentrada: $scope.estadia.dataentrada,
                datasaida: $scope.estadia.datasaida,
                observacoesentrada: $scope.estadia.observacoesentrada,
                observacoessaida: $scope.estadia.observacoessaida,
                familia: $scope.estadia.familia,
                representante: $scope.estadia.representante,
                membros: $scope.estadia.membros
            };
            if ($scope.isNovaEstadia) {
//                console.log($scope.estadia);
//                console.log($scope.estadia.datasaida);
                $http.post("/estadia", estadiaCorreto)
                        .success(function () {
                            $location.path("/Estadia/listar");
                            toastr.success("Estadia inserido com sucesso!");
                        })
                        .error(deuErroSalvar);
            } else {
                $http.put("/estadia", estadiaCorreto)
                        .success(function () {
                            $location.path("/Estadia/listar");
                            toastr.success("Estadia inserido com sucesso!");
                        })
                        .error(deuErroSalvar);
            }
        };

//        $scope.deletarEstadia = function (estadia) {
//            $http.delete("/estadia/" + estadia.idestadia)
//                    .success(function () {
//                        toastr.success("Estadia " + estadia.idestadia + " excluído com sucesso.");
//                        $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal, '', '', $rootScope.ent, false);
//                    }).error(deuErroDeletar);
//        };

        $scope.carregarFamilias = function () {
            $http.get("/familia")
                    .success(function (data) {
                        $scope.familia = data;
                    }).error(deuErro);
        };

        $scope.statusEstadia = function (datasaida) {
            if (datasaida === null) {
                return "warning";
            } else {
                return "success";
            }
            ;
        };


        $scope.carregarMembros = function (id) {
            if (id !== undefined) {
                $http.get("/familia/membrosPorFamilia/" + id)
                        .success(function (data) {
                            $scope.itens = data;
                        }).error(deuErro);
            }
        };

        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }

        $scope.dateToSoData = function (valor) {
            var data = "";
            if (valor != null && valor != "" && valor != undefined) {
                data = ServiceFuncoes.dateToData(valor);
            }
            return data;
        };


        $scope.getIdEstadia = function (idestadia) {
            $routeParams.id = idestadia;
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar a estadia, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, erro ao tentar salvar a estadia, verifique!");
        }

    }]);