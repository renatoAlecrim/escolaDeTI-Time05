module.controller("PessoaJuridicaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar pessoa";
        $scope.ent = $rootScope.ent = "pessoa/juridica";
        $scope.campoPrincipal = 'nome';
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

        function novaPessoaJuridica() {
            $scope.juridica = {
                nome: "",
                telefone: "",
                telefonesecundario: "",
                email: {
                    email: ""
                },
                logradouro: "",
                numero: "",
                bairro: "",
                complemento: "",
                cep: "",
                codigoibge: "",
                codigoestado: "",
                cnpj: {
                    cnpj: ""
                },
                tipoPessoa: "JURIDICA"
            };
            $scope.isNovaJuridica = true;
        }

        $scope.salvar = function () {
            if ($scope.isNovaJuridica) {
                $http.post("/pessoa/juridica", $scope.juridica)
                        .success(function () {
                            toastr.success("Empresa cadastrada com sucesso!");
                            if ($location.path() === "/Juridica/nova") {
                                $location.path("/Pessoa/listar");
                            }
                            else {
                                $scope.listarJuridicas();
                                fechaModal('cadJuridica');
                            }
                        })
                        .error(deuErro);
            }
            else {
                $scope.juridica.idpessoa = $routeParams.id;
                $http.put("/pessoa/juridica", $scope.juridica)
                        .success(function () {
                            toastr.success("Pessoa atualizado com sucesso!");
                            $location.path("/Pessoa/listar");
                        })
                        .error(deuErro);
            }
        };

        $scope.editar = function (aId) {
            $location.path("/Juridica/editar/" + aId);
        };

        $scope.carregar = function () {
            if ($location.path() == "/Juridica/nova" || $location.path() == "/Visita/nova" || $location.path() == "/Visita/editar/" + $routeParams.id) {
                novaPessoaJuridica();
            }
            else {
                $timeout(function () {
                    $http.get("/pessoa/juridica/obj/" + $routeParams.id)
                            .success(function (data) {
                                novaPessoaJuridica();
                                $scope.juridica.idpessoa = $routeParams.id;
                                $scope.juridica.nome = data.nome;
                                $scope.juridica.cnpj = (data.cnpj != null) ? data.cnpj : "";
                                $scope.juridica.email.email = data.email.email;
                                $scope.juridica.cep = data.endereco.cep;
                                if (data.endereco != null && data.endereco.cidade != null && data.endereco.cidade.estado != null) {
                                    $scope.juridica.codigoestado = data.endereco.cidade.estado.codigoestado;
                                    $scope.juridica.codigoibge = data.endereco.cidade.codigoIBGE;
                                } else {
                                    $scope.juridica.codigoestado = "";
                                    $scope.juridica.codigoibge = "";
                                }
                                $scope.listarCidades();
                                $scope.juridica.logradouro = data.endereco.logradouro;
                                $scope.juridica.numero = data.endereco.numero;
                                $scope.juridica.complemento = data.endereco.complemento;
                                $scope.juridica.bairro = data.endereco.bairro;
                                $scope.juridica.telefone = (data.telefone)?data.telefone:"";
                                $scope.juridica.telefonesecundario = (data.telefonesecundario)?data.telefonesecundario:"";
                                $scope.juridica.tipoPessoa = "JURÍDICA";
                                $scope.juridica.imgSrc = data.imgSrc;

                                $scope.isNovaJuridica = false;
                            }).error(deuErro);
                }, 100);
            }
        };

        $scope.resetJur = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novaPessoaJuridica();
        };

        $scope.listarEstados = function () {
            $http.get("/uf/listar").success(function (data) {
                $scope.estados = data.listaDeRegistros;
            }).error(deuErro);
        };

        $scope.listarCidades = function () {
            if ($scope.juridica.codigoestado !== "" && $scope.juridica.codigoestado !== undefined) {
                $http.get("/cidade/listarPorCodigoEstado/" + $scope.juridica.codigoestado).success(function (data) {
                    $scope.cidades = data;
                }).error(deuErro);
            }
        };

        $scope.cidadeSelecionada = function (codigo) {
            if (codigo == $scope.juridica.codigoibge) {
                return true;
            } else {
                return false;
            }
        };

//-----------------AKI-------------------------------

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        $scope.carregaScript = function (nScript) {
            $timeout(function () {
                var script = document.createElement('script');
                script.src = nScript + ".js";
                document.getElementsByTagName('head')[0].appendChild(script);
            }, 100);
        };

    }]);



