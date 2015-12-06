module.controller("VisitaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', 'ServiceFuncoes', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar visita";
        $scope.ent = $rootScope.ent = "visita";
        $scope.campoPrincipal = 'datavisita';
        $rootScope.tipoOrdem = 'desc';

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

        $scope.print = function (divNome) {
            var printContents = document.getElementById(divNome).innerHTML;
            document.body.innerHTML = printContents;
            $location.path("/Visita/listar");
            location.reload(true);
            window.print();
        };

        $scope.statusVisita = function (data, visitarealizada) {
            var hoje = new Date();
            var coisa;
            if (hoje < new Date(data)) {
                coisa = "warning";
                if (visitarealizada) {
                    coisa = "success";
                }
            }
            else {
                if (visitarealizada) {
                    coisa = "success";
                }
                else {
                    coisa = "danger";
                }
            }
            return coisa;
        };

        $scope.reset = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novaVisita();
        };

        function novaVisita() {
            $scope.visita = {
                datavisita: "",
                horavisita: "",
                horaentrada: "",
                horasaida: "",
                observacao: "",
                quantidadedevisitantes: "",
                seriecurso: "",
                visitarealizada: "",
                telefonevisita: "",
                pessoaresponsavel: {idpessoa: ""},
                entidade: {idpessoa: ""}
            };
            $scope.isNovaVisita = true;
        }
        ;
        $scope.novaVisita = function () {
            novaVisita();
        };

        $scope.fichaVisita = function (codigovisita) {
            jQuery('#modalVisita').modal('show', {backdrop: 'static'});
            $scope.carregarVisita(codigovisita);
        };

        var diasSemana = new Array("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-Feira", "Sábado");

        function timeToHora(valor) {
            var x = new Date(valor.getTime());
            var hora = x.getHours();
            var min = x.getMinutes();
            if (hora < 10) {
                hora = "0" + hora;
            }
            if (min < 10) {
                min = "0" + min;
            }
            return hora + ":" + min;
        }

        $scope.carregarVisita = function (id) {
            if ($location.path() === "/Visita/nova") {
                novaVisita();
            }
            else {
                $timeout(function () {
                    var busca;
                    if (id) {
                        busca = "/visita/obj/" + id;
                    }
                    else {
                        busca = "/visita/obj/" + $routeParams.id;
                    }
                    $http.get(busca)
                            .success(function (data) {
                                $scope.datavisita = $scope.dateToData(data.datavisita);
                                var dados = data;
                                var d = new Date(data.datavisita);
                                dados.datavisita = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                                dados.horavisita = new Date(1970, 01, 01, data.horavisita.substring(0, 2), data.horavisita.substring(3, 5), 00, 00);
                                $scope.horaVisita = timeToHora(dados.horavisita);
                                $scope.diaSemana = diasSemana[dados.datavisita.getDay()];

                                if (dados.horaentrada) {
                                    dados.horaentrada = new Date(1970, 01, 01, data.horaentrada.substring(0, 2), data.horaentrada.substring(3, 5), 00, 00);
                                    $scope.horaEntrada = timeToHora(dados.horaentrada);
                                }
                                else {
                                    $scope.horaEntrada = "Não informada";
                                }
                                if (dados.horasaida) {
                                    dados.horasaida = new Date(1970, 01, 01, data.horasaida.substring(0, 2), data.horasaida.substring(3, 5), 00, 00);
                                    $scope.horaSaida = timeToHora(dados.horasaida);
                                }
                                else {
                                    $scope.horaSaida = "Não informada";
                                }
                                if (!dados.entidade) {
                                    $scope.dadosEntidade = 'Não informado';
                                }
                                else {
                                    $scope.dadosEntidade = dados.entidade.nome + ' (' + dados.entidade.endereco.cidade.descricao + ' - ' + dados.entidade.endereco.cidade.estado.sigla + ')';
                                }
                                
                                if (dados.entidade) {
                                    if (dados.entidade.telefone.telefone) {
                                        $scope.dadosEntidadeTelefone = dados.entidade.telefone.telefone;
                                        if (dados.entidade.telefonesecundario.telefone) {
                                            $scope.dadosEntidadeTelefone += ' / ' + dados.entidade.telefonesecundario.telefone;
                                        }
                                    }
                                }
                                
                                if (!dados.seriecurso) {
                                    $scope.seriecurso = 'Série / curso não informado';
                                }
                                else {
                                    $scope.seriecurso = dados.seriecurso;
                                }

                                if (!dados.observacao) {
                                    $scope.observacao = 'Não informado';
                                }
                                else {
                                    $scope.observacao = dados.observacao;
                                }

                                if (!dados.telefonevisita) {
                                    $scope.telefonevisita = 'Não informado';
                                }
                                else {
                                    $scope.telefonevisita = dados.telefonevisita;
                                }

                                var hoje = new Date();
                                if (hoje < new Date(data.datavisita)) {
                                    $scope.cor = "warning";
                                    $scope.status = "a ser realizada";
                                    if (dados.visitarealizada) {
                                        $scope.cor = "success";
                                        $scope.status = "já realizada";
                                    }
                                }
                                else {
                                    if (dados.visitarealizada) {
                                        $scope.cor = "success";
                                        $scope.status = "já realizada";
                                    }
                                    else {
                                        $scope.cor = "danger";
                                        $scope.status = "não foi realizada";
                                    }
                                }

                                $scope.visita = dados;
                                $scope.isNovaVisita = false;
                                $scope.pegarFone('fisica');
                                $scope.pegarFone('juridica');
                            })
                            .error(deuErro);
                }, 100);
            }
        };

        $scope.editarVisita = function (visita) {
            $location.path("/Visita/editar/" + visita.idvisita);
        };

        $scope.confirmaExclusao = function (entidade, nomeEntidade, nomeRegistro, id) {
            jQuery('#apagarModal').modal('show', {backdrop: 'static'});
            $scope.dadosExclusao = {};
            $scope.dadosExclusao.entidade = entidade;
            $scope.dadosExclusao.nomeEntidade = nomeEntidade;
            $scope.dadosExclusao.nomeRegistro = nomeRegistro;
            $scope.dadosExclusao.id = id;
        };

        $scope.excluiRegistro = function () {
            ServiceFuncoes.excluiRegistro($scope.dadosExclusao.entidade, $scope.dadosExclusao.nomeEntidade, $scope.dadosExclusao.nomeRegistro, $scope.dadosExclusao.id);
            $timeout(function () {
                $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoAtual, '', '', $rootScope.ent, false);
            }, 100);
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar a visita, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, erro ao tentar salvar a visita, verifique!");
        }

        $scope.salvarVisita = function () {
//            console.log($scope.visita);
            var visitaCompleta = $scope.visita;

            var dVisita = $scope.visita.datavisita;
            var dVisitaOK = dVisita.getFullYear() + "-" + (dVisita.getMonth() + 1) + '-' + dVisita.getDate();

            var hVisita = $scope.visita.horavisita;
            var hVisitaOK = hVisita.getHours() + ":" + hVisita.getMinutes() + ":00";

            if ($scope.visita.horaentrada) {
                var hEntrada = $scope.visita.horaentrada;
                var hEntradaOK = hEntrada.getHours() + ":" + hEntrada.getMinutes() + ":00";
                visitaCompleta.horaentrada = hEntradaOK;
            }
            else {
                visitaCompleta.horaentrada = null;
            }

            if ($scope.visita.horasaida) {
                var hSaida = $scope.visita.horasaida;
                var hSaidaOK = hSaida.getHours() + ":" + hSaida.getMinutes() + ":00";
                visitaCompleta.horasaida = hSaidaOK;
            }
            else {
                visitaCompleta.horasaida = null;
            }

            visitaCompleta.datavisita = dVisitaOK + "T00:00:00-03";
            visitaCompleta.horavisita = hVisitaOK;

            if (hSaidaOK) {
                visitaCompleta.visitarealizada = true;
            }
            else {
                visitaCompleta.visitarealizada = false;
            }

            if ($scope.isNovaVisita) {
                //console.log(visitaCompleta)
//                console.log($scope.visita);
                $http.post("/visita", visitaCompleta)
                        .success(function () {
                            $location.path("/Visita/listar");
                            novaVisita();
                            toastr.success("Visita inserida com sucesso!");
                        })
                        .error(deuErroSalvar);
            }
            else {
                $http.put("/visita", $scope.visita)
                        .success(function () {
                            $location.path("/Visita/listar");
                            toastr.success("Visita atualizada com sucesso!");
                        })
                        .error(deuErroAtualizacao);
            }
        };

        $scope.listarFisicas = function () {
            $http.get('/pessoa/fisica/getFisicas')
                    .success(function (data) {
                        $scope.fisicas = data;
                    })
                    .error(deuErro);
        };

        $scope.listarJuridicas = function () {
            $http.get('/pessoa/juridica')
                    .success(function (data) {
                        $scope.juridicas = data;
                    })
                    .error(deuErro);
        };

        $scope.pegarFone = function (tipo) {
            if ($scope.visita.pessoaresponsavel.idpessoa) {
                if (tipo == 'fisica') {
                    $http.get("/pessoa/fisica/obj/" + $scope.visita.pessoaresponsavel.idpessoa)
                            .success(function (data) {
                                $scope.telefoneFisica = (data.telefone) ? data.telefone.telefone : "";
                            })
                            .error(deuErro);
                }
            }
            else {
                $scope.telefoneFisica = "";
            }
            if ($scope.visita.entidade.idpessoa) {
                if (tipo == 'juridica') {
                    $http.get("/pessoa/juridica/obj/" + $scope.visita.entidade.idpessoa)
                            .success(function (data) {
                                $scope.telefoneJuridica = (data.telefone) ? data.telefone.telefone : "";
                            })
                            .error(deuErro);
                }
            }
            else {
                $scope.telefoneJuridica = "";
            }


        };

        $scope.abrirModal = function (id) {
            $routeParams.id = id;
            jQuery('#saidaModal').modal('show', {backdrop: 'static'});
            $scope.carregarVisita();
        };

        $scope.registarSaida = function () {
            $scope.salvarVisita();
            $timeout(function () {
                $scope.atualizarListagens(10, $rootScope.pagina, $scope.campoAtual, '', '', $rootScope.ent, false);
            }, 100);
        };

        $scope.dateToData = function (valor) {
            var data = "";
            if (valor != null && valor != "" && valor != undefined) {
                data = ServiceFuncoes.dateToData(valor);
            }
            return data;
        };
    }]);