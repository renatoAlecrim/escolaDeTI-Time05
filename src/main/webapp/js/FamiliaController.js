module.controller("FamiliaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar família";
        $scope.ent = $rootScope.ent = "familia";
        $scope.campoPrincipal = 'nomefamilia';
        $rootScope.tipoOrdem = 'asc';

        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
            if (campo == null || campo == "") {
                campo = $scope.campoPrincipal;
            }
            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
            atualizaScope;
            //console.log($scope.dadosRecebidos);
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

        function novaFamilia() {
            $scope.familia = {
                "nomefamilia": "",
                "representante": "",
                "telefone": "",
                "membros": []
            };
            $scope.isNovaFamilia = true;
        }
        function getMembrosFamilia(idrep) {
            $http.get("/familia/membrosPorFamilia/" + $routeParams.id)
                    .success(function (data) {
//                        console.log(data);
                        $scope.familia.membros = data;
//                        return data;
                    })
                    .error(deuErro);
            $http.get("/indigena/" + idrep)
                    .success(function (data) {
//                        console.log(data);
                        $scope.familia.representante = data;
//                        return data;
                    })
                    .error(deuErro);

        }

        $scope.todosIndios = function () {
            $http.get("/familia/indios")
                    .success(function (data) {
                        //console.log(data);
                        $scope.indios = data;
                    })
                    .error(deuErro);
        };

        $scope.carregarFamilia = function () {
            if ($location.path() === "/Familia/nova") {
                novaFamilia();
            }
            else {
                $timeout(function () {
                    $http.get("/familia/obj/" + $routeParams.id)
                            .success(function (data) {
                                var dados = data;
//                                console.log(data);
                                dados.telefone = (data.telefone) ? data.telefone.telefone : "";
                                dados.representante.nometerra = data.representante.terraIndigena.nometerra;

                                $scope.familia = dados;
                                getMembrosFamilia(data.representante.codigoassindi);
                                $scope.isNovaFamilia = false;
                            })
                            .error(deuErro);
                }, 100);
            }
        };

        $scope.salvarFamilia = function () {
            var familiaCompleta = {
                nomefamilia: $scope.familia.nomefamilia,
                representante: $scope.familia.representante,
                telefone: {
                    telefone: $scope.familia.telefone
                },
                membros: $scope.familia.membros
            };

            if ($scope.isNovaFamilia) {
                $http.post("/familia", familiaCompleta)
                        .success(function () {
                            toastr.success("Família " + familiaCompleta.nomefamilia + " cadastrada com sucesso!");
                            $location.path("/Familia/listar");
                        })
                        .error(erroCadastraFamilia);
            }
            else {
                familiaCompleta.idfamilia = $routeParams.id;
                $http.put("/familia", familiaCompleta)
                        .success(function () {
                            toastr.success("Família " + familiaCompleta.nomefamilia + " atualizada com sucesso!");
                            $location.path("/Familia/listar");
                        })
                        .error(deuErro);
            }
        };

        $scope.editarFamilia = function (familia) {
            $location.path("/Familia/editar/" + familia.idfamilia);
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

        function erroCadastraFamilia() {
            toastr.error("Não foi possível cadastrar a família, verifique os dados fornecidos. ", "Erro");
        }

        function erroExcluiFamilia() {
            toastr.error("Não foi possível excluir a família, verifique se a mesma não foi inserida em alguma estadia.", "Erro");
        }

    }]);