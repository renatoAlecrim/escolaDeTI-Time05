module.controller("ConvenioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

    $scope.busca = {};
    $scope.placeHolder = "Buscar convênio";
    $scope.ent = $rootScope.ent = "convenio";
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

    function novoConvenio() {
        $scope.convenio = {
            nome: ""
            };
            $scope.isNovoConvenio = true;
        }

        $scope.novoConvenio = function () {
            novoConvenio();
        };
        $scope.carregarConvenio = function () {
            if ($location.path() === "/Convenio/novo") {
                novoConvenio();
            }
            else {
                $http.get("/convenio/" + $routeParams.id)
                        .success(function (data) {
//                            console.log(data);
                            $scope.convenio = data;
                            $scope.isNovoConvenio = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.atualizarConvenios = function () {
            $http.get("/convenio")
                    .success(function (data) {
                        $scope.convenios = data;
                    })
                    .error(deuErro);
        };

        $scope.editarConvenio = function (convenio) {
            $location.path("/Convenio/editar/" + convenio.idconvenio);
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

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar o convênio, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, convênio já cadastrado!");
        }

        function deuErroDeletar() {
            toastr.error("Atenção, erro ao tentar deletar o convênio, verifique!");
        }
        $scope.salvarConvenio = function (flag) {
            if (flag == "modal")
                $scope.isNovoConvenio = true;
            if ($scope.isNovoConvenio) {
                $http.post("/convenio", $scope.convenio)
                        .success(function () {
                            if (flag == "cad")
                                $location.path("/Convenio/listar");
                            else {
                                novoConvenio();
                                $scope.getConvenios();
                            }
                            toastr.success("Convênio inserido com sucesso!");
                        })
                        .error(function (err){
                            toastr.error(err.message);
                        });
            }
            else {
                $http.put("/convenio/", $scope.convenio)
                        .success(function () {
                            $location.path("/Convenio/listar");
                            toastr.success("Convênio atualizado com sucesso!");
                        })
                        .error(deuErroAtualizacao);
            }
        };
    }]);
