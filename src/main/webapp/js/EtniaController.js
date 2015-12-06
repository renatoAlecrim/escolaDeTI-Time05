module.controller("EtniaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {
    
    $scope.busca = {};
    $scope.placeHolder = "Buscar etnia";
    $scope.ent = $rootScope.ent = "etnia";
    $scope.campoPrincipal = 'descricao';
    $rootScope.tipoOrdem = 'asc';
        
    $scope.atualizarListagens = function(qtdePorPag, pag, campo, string, troca, paro){
        if (campo == null || campo == "") { campo = $scope.campoPrincipal; }
        $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
        atualizaScope;
    };
    
    function atualizaScope() {
        $scope = $rootScope;
    }
    
    $rootScope.atualizarListagens = $scope.atualizarListagens;
    
    $scope.registrosPadrao = function() {
        $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
        $rootScope.numregistros = $scope.busca.numregistros;
    };
    
    $scope.fazPesquisa = function(registros, string){
        $rootScope.string = string;
        $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
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
    
    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.", "Erro");
    }

    function novaEtnia() {
        $scope.etnia = {
            descricao: ""
            };
            $scope.isNovaEtnia = true;
        }

        $scope.novaEtnia = function () {
            novaEtnia();
        };

        $scope.carregarEtnia = function () {
            if ($location.path() === "/Etnia/nova") {
                novaEtnia();
            }
            else {
                $http.get("/etnia/" + $routeParams.id)
                        .success(function (data) {
//                            console.log(data);
                            $scope.etnia = data;
                            $scope.isNovaEtnia = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.todasEtnias = function () {
            $http.get("/etnia")
                    .success(function (data) {
                        $scope.etnias = data;
//                console.log($scope.etnias);
                    })
                    .error(deuErro);
        };

        $scope.editarEtnia = function (etnia) {
            $location.path("/Etnia/editar/" + etnia.idetnia);
        };
        $scope.atualizarEtnias = function (reg, pag, campo, order, string, paro) {
            if (reg == null || reg == "") {
                reg = 10;
            }
            if (pag == null || pag == "") {
                pag = 1;
            }
            if (campo == null || campo == "") {
                campo = "descricao";
            }
            if (order != "asc" && order != "desc") {
                order = "asc";
            }
            if (string == null) {
                string = "";
            }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
            $http.get("/etnia/listar/" + reg + "/" + pag + "/" + campo + "/" + order + "/" + string)
                    .success(function (data) {
                        $scope.etnias = data;
//                        console.log(data);
//                        console.log("/etnia/listar/" + pag + "/" + campo + "/" + order + "/" + string);

                        if (!paro) {
                            atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false);
                        }

                    })
                    .error(deuErro);
        };

        $scope.trocaOrdem = function (string) {
            if ($scope.tipoOrdem == true) {
                $scope.tipoOrdem = false;
                var ordem = "asc";
            }
            else {
                $scope.tipoOrdem = true;
                var ordem = "desc";
            }
            $scope.atualizarEtnias("", "", ordem, string, true);
        };

        function atualizaPaginacao(qtde, pag, campo, order, string, paro) {
            $('#paginacao').bootpag({
                total: qtde,
                page: pag,
                maxVisible: 5
            }).on('page', function (event, num) {
                paro = true;
                $scope.atualizarEtnias(10, num, campo, order, string, paro);

            });
        }


        $scope.salvarEtnia = function (flag) {
            if (flag == "modal")
                $scope.isNovaEtnia = true;
//                            console.log($scope.etnia);
            if ($scope.isNovaEtnia) {
                $http.post("/etnia", $scope.etnia)
                        .success(function () {
//                            console.log(flag);
                            if (flag == "cad")
                                $location.path("/Etnia/listar");
                            else {
                                novaEtnia();
                                $scope.getEtnias();
                            }
                            toastr.success("Etnia inserida com sucesso!");
                        })
                        .error(function(err){
                            toastr.error(err.message);
                        });
            }
            else {
                $http.put("/etnia/", $scope.etnia)
                        .success(function () {
//                            console.log(flag);
//                            if (flag == "cad")
                            $location.path("/Etnia/listar");
                            toastr.success("Etnia atualizada com sucesso!");
                        })
                        .error(function(err){
                            toastr.error(err.message);
                        });
            }

        };
    }]);