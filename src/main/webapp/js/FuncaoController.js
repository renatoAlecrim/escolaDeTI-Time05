module.controller("FuncaoController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar função";
        $scope.ent = $rootScope.ent = "funcao";
        $scope.campoPrincipal = 'descricao';
        $rootScope.tipoOrdem = 'asc';

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

        function novaFuncao() {
            $scope.funcao = {
                descricao: ""
            };
            $scope.isNovaFuncao = true;
        }

        function novaFuncao() {
            $scope.funcao = {
                descricao: ""
            };
            $scope.isNovaFuncao = true;
        }

        $scope.novaFuncao = function () {
            novaFuncao();
        }

        $scope.carregarFuncao = function () {
            $scope.itensAcesso.itens;
            if ($location.path() === "/Funcao/nova") {
                novaFuncao();
            }
            else {
                $http.get("/funcao/" + $routeParams.id)
                        .success(function (data) {
                            $scope.funcao = data;
                            $scope.isNovaFuncao = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.todasFuncoes = function () {
            $http.get("/funcao")
                    .success(function (data) {
                        $scope.funcoes = data;
                    })
                    .error(deuErro);
        };

        $scope.atualizarFuncoes = function () {
            $http.get("/funcao")
                    .success(function (data) {
                        $scope.funcoes = data;
                    })
                    .error(deuErro);
        };

        $scope.editarFuncao = function (funcao) {
            $location.path("/Funcao/editar/" + funcao.idfuncao);
//            console.log(funcao.idfuncao);
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

        $scope.salvarFuncao = function (flag) {


            $http.get("/funcao/verificarDescricao/" + $scope.funcao.descricao)
                    .success(function (data) {
//                        console.log(data);
                        if (data == false) {
                            toastr.error("Já existe uma função cadastrada com esse nome!");
                            $scope.funcao.descricao = "";
                            if (!$scope.isNovaFuncao) {
                                $location.path("/Funcao/listar");
                                toastr.info("Não foram feitas modificações!");
                            }
                        }
                        else {
                            if (flag == "modal")
                                $scope.isNovaFuncao = true;
                            if ($scope.isNovaFuncao) {
                                $http.post("/funcao", $scope.funcao)
                                        .success(function () {
                                            if (flag == "cad")
                                                $location.path("/Funcao/listar");
                                            else {
                                                novaFuncao();
                                                $scope.getFuncoes();
                                            }
                                            toastr.success("Funcao inserida com sucesso!");
                                        })
                                        .error(deuErro);
                            }
                            else {
                                $http.put("/funcao/", $scope.funcao)
                                        .success(function () {
                                            $location.path("/Funcao/listar");
                                            toastr.success("Funcao atualizada com sucesso!");
                                        })
                                        .error(deuErro);
                            }
                        }
                    }
                    ).error(deuErro);
        };
        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }
        function deuErroDeletar() {
            toastr.error("Funções cadastradas em pessoas não podem ser apagadas.","Tente novamente.");
        }
    }]);
