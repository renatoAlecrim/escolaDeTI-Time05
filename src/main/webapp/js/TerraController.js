module.controller("TerraController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

    $scope.busca = {};
    $scope.placeHolder = "Buscar terra indígena";
    $scope.ent = $rootScope.ent = "terraIndigena";
    $scope.campoPrincipal = 'nomeTerra';
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
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function novaTerra() {
        $scope.terra = {
            nometerra: "",
            cidade: ""
        };
        $scope.isNovaTerra = true;
    }
    
    $scope.novaTerra = function() {
        novaTerra();
    };
    
    $scope.todasCidades = function(){
        $http.get("/cidade")
            .success(function(data){
                //console.log(data);
                $scope.cidades = data;
            })
            .error(deuErro);
    };
    
    $scope.carregarTerra = function(){
        if($location.path() === "/TerraIndigena/nova"){
            novaTerra();
            }
            else {
                $http.get("/terraIndigena/obj/" + $routeParams.id)
                        .success(function (data) {
                            $scope.terra = data;
                            $scope.terra.cidade = {
                                codigoibge: data.cidade.codigoIBGE,
                                descricao: data.cidade.descricao,
                                codigoestado: data.cidade.estado.codigoestado,
                                estados: data.cidade.estado.descricao,
                                sigla: data.cidade.estado.sigla
                            };
                            $scope.isNovaTerra = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.todasTerras = function () {
            $http.get("/terraIndigena")
                    .success(function (data) {
                        $scope.terras = data;
//                        console.log($scope.terras);
                    })
                    .error(deuErro);
        };

        $scope.editarTerra = function (terra) {
            $location.path("/TerraIndigena/editar/" + terra.idterraindigena);
        };

        $scope.salvarTerra = function (flag) {
            if ($scope.terra.cidade == "") {
                toastr.error("A cidade não foi preenchida corretamente.", "Atenção");
            }
            else {
//                console.log($scope.terra);
                var terraCompleta = {
                    nometerra: $scope.terra.nometerra,
                    cidade: {
                        codigoIBGE: $scope.terra.cidade.codigoibge,
                        descricao: $scope.terra.cidade.nomecidade,
                        estado: {
                            codigoestado: $scope.terra.cidade.codigoestado,
                            descricao: $scope.terra.cidade.descricao,
                            sigla: $scope.terra.cidade.sigla
                        }
                    }
                };
//                console.log(terraCompleta);
                if (flag == "modal")
                    $scope.isNovaTerra = true;
                if ($scope.isNovaTerra) {
                    $http.post("/terraIndigena", terraCompleta)
                            .success(function () {
                                if (flag == "cad")
                                    $location.path("/TerraIndigena/listar");
                                else{
                                    novaTerra();
                                    $scope.getTerras();
                                }
                                toastr.success("Terra indígena inserida com sucesso!");
                            })
                            .error(function (err){
//                                console.log(err);
                                toastr.error(err.message);
                            });
                }
                else {
                    terraCompleta.idterraindigena = $scope.terra.idterraindigena;
                    $http.put("/terraIndigena", terraCompleta)
                            .success(function () {
                                $location.path("/TerraIndigena/listar");
                                toastr.success("Terra indígena atualizada com sucesso!");
                            })
                            .error(deuErro);
                }
            }
        };

        $scope.atualizarTerras = function (pag, campo, order, string, paro) {
            if (pag == null || pag == "") {
                pag = 1;
            }
            if (campo == null || campo == "") {
                campo = "nometerra";
            }
            if (order != "asc" && order != "desc") {
                order = "asc";
            }
            if (string == null) {
                string = "";
            }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
            $http.get("/terraIndigena/listar/" + pag + "/" + campo + "/" + order + "/" + string)
                    .success(function (data) {
                        $scope.terras = data;
                        if (!paro) {
                            atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false);
                        }
                    })
                    .error(deuErro);
        };

        $scope.trocaOrdem = function (campo, string) {
            var ordem;
            if ($scope.tipoOrdem == true) {
                $scope.tipoOrdem = false;
                ordem = "asc";
            }
            else {
                $scope.tipoOrdem = true;
                ordem = "desc";
            }
            $scope.campoAtual = campo;
            $scope.atualizarTerras("", campo, ordem, string, true);
        };

        function atualizaPaginacao(qtde, pag, campo, order, string, paro) {
            $('#paginacao').bootpag({
                total: qtde,
                page: pag,
                maxVisible: 5
            }).on('page', function (event, num) {
                paro = true;
                $scope.atualizarTerras(num, campo, order, string, paro);
            });
        }

    }]);