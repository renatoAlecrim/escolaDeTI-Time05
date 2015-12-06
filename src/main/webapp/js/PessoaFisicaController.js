module.controller("PessoaFisicaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar pessoa";
        $scope.ent = $rootScope.ent = "pessoa/fisica";
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

        function novaPessoaFisica() {
            $scope.fisica = {
                nome: "",
                telefone: "",
                telefonesecundario: "",
                email: {
                    email: ""
                },
                idfuncao: "",
                logradouro: "",
                numero: "",
                bairro: "",
                complemento: "",
                cep: "",
                codigoibge: "",
                codigoestado: "",
                datanasc: "",
                cpf: {
                    cpf: ""
                },
                genero: "",
                tipo: "",
                imgSrc: "/fotos/default.png"
            };
            $scope.isNovo = true;
        }

        $scope.salvar = function () {
            if ($scope.isNovo) {
                $http.post("/pessoa/fisica", $scope.fisica)
                        .success(function () {
                            toastr.success("Usuário cadastrado com sucesso!");
                            if ($location.path() === "/Fisica/nova") {
                                $location.path("/Pessoa/listar");
                            }
                            else {
                                $scope.listarFisicas();
                                fechaModal('cadFisica');
                            }
                        })
                        .error(deuErro);
            }
            else {
                $scope.fisica.idpessoa = $routeParams.id;
//                console.log($scope.fisica);
                $http.put("/pessoa/fisica", $scope.fisica)
                        .success(function () {
                            toastr.success("Pessoa atualizado com sucesso!");
                            $location.path("/Pessoa/listar");
                        })
                        .error(deuErro);
            }
        };

        $scope.print = function (divNome) {
            var printContents = document.getElementById(divNome).innerHTML;
            document.body.innerHTML = printContents;
            $location.path("/Pessoa/listar");
            location.reload(true);
            window.print();
        };

        $scope.editar = function (aId, genero) {
//            console.log(genero);
            if (genero == undefined || genero == null)
                $location.path("/Juridica/editar/" + aId);
            else
                $location.path("/Fisica/editar/" + aId);
        };

        $scope.juridica = {};

        $scope.carregar = function (tipo, idPessoa) {
            if (!tipo) {
                $timeout(function () {
                    var busca = "/pessoa/juridica/obj/" + idPessoa;
                    $http.get(busca)
                            .success(function (data) {
                                $scope.juridica.nome = data.nome;
                                $scope.juridica.cnpj = (data.cnpj != null) ? data.cnpj : "";
                                $scope.juridica.email = data.email.email;
                                $scope.juridica.cep = data.endereco.cep;
                                if (data.endereco != null && data.endereco.cidade != null && data.endereco.cidade.estado != null) {
                                    $scope.juridica.cidade = data.endereco.cidade.descricao + " - " + data.endereco.cidade.estado.sigla;

                                } else {
                                    $scope.juridica.codigoestado = "";
                                    $scope.juridica.codigoibge = "";
                                    $scope.juridica.cidade = "Cidade não informada."
                                }
                                $scope.juridica.logradouro = data.endereco.logradouro;
                                $scope.juridica.numero = data.endereco.numero;
                                $scope.juridica.complemento = data.endereco.complemento;
                                $scope.juridica.bairro = data.endereco.bairro;
                                $scope.juridica.telefone = (data.telefone)?data.telefone:"";
                                $scope.juridica.telefonesecundario = (data.telefonesecundario)?data.telefonesecundario:"";
                                $scope.juridica.telefones = $scope.juridica.telefone.telefone;
                                if (data.telefonesecundario.telefone) {
                                    $scope.juridica.telefones += " / " + data.telefonesecundario.telefone;
                                }
                                $scope.juridica.tipoPessoa = "JURÍDICA";
                                $scope.juridica.imgSrc = data.imgSrc;

                                $scope.isNovaJuridica = false;
                            }).error(deuErro);
                }, 100);
            }
            else {
                if ($location.path() == "/Fisica/nova" || $location.path() == "/Visita/nova" || $location.path() == "/Visita/editar/" + $routeParams.id) {
                    novaPessoaFisica();
                }
                else {
                    $timeout(function () {
                        var busca = "/pessoa/fisica/obj/" + $routeParams.id;
                        if (idPessoa) {
                            busca = "/pessoa/fisica/obj/" + idPessoa;
                        }
                        $http.get(busca)
                                .success(function (data) {
                                    novaPessoaFisica();
                                    $scope.fisica.idpessoa = $routeParams.id;
                                    $scope.fisica.nome = data.nome;
                                    $scope.fisica.cpf.cpf = (data.cpf != null) ? data.cpf.cpf : "";
                                    $scope.fisica.genero = data.genero;
                                    $scope.fisica.email.email = data.email.email;
                                    var d = new Date(data.datanascimento);
                                    $scope.fisica.datanasc = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                                    $scope.fisica.datanascimento = data.datanascimento;
                                    $scope.fisica.dataArrumada = $scope.fisica.datanasc.getDate() + "/" + ($scope.fisica.datanasc.getMonth() + 1) + '/' + $scope.fisica.datanasc.getFullYear();
                                    $scope.fisica.cep = data.endereco.cep;
                                    $scope.fisica.idfuncao = (data.funcao != null) ? data.funcao.idfuncao : "";
                                    if (data.endereco != null && data.endereco.cidade != null && data.endereco.cidade.estado != null) {
                                        $scope.fisica.codigoestado = data.endereco.cidade.estado.codigoestado;
                                        $scope.fisica.codigoibge = data.endereco.cidade.codigoIBGE;
                                        $scope.fisica.cidade = data.endereco.cidade.descricao + " - " + data.endereco.cidade.estado.sigla;
                                    } else {
                                        $scope.fisica.codigoestado = "";
                                        $scope.fisica.codigoibge = "";
                                        $scope.fisica.cidade = "Cidade não informada.";
                                    }
                                    $scope.listarCidades();
                                    $scope.fisica.logradouro = data.endereco.logradouro;
                                    $scope.fisica.numero = data.endereco.numero;
                                    $scope.fisica.complemento = data.endereco.complemento;
                                    $scope.fisica.bairro = data.endereco.bairro;
                                    $scope.fisica.telefone = (data.telefone)?data.telefone:"";
                                    $scope.fisica.telefonesecundario = (data.telefonesecundario)?data.telefonesecundario:"";
                                    $scope.fisica.telefones = $scope.fisica.telefone.telefone;
                                    if (data.telefonesecundario.telefone) {
                                        $scope.fisica.telefones += " / " + data.telefonesecundario.telefone;
                                    }
                                    $scope.fisica.tipo = data.tipo;
                                    if ($scope.fisica.tipo == "COLABORADOR") {
                                        $scope.fisica.funcao = " (" + data.funcao.descricao + ")";
                                    }

                                    $scope.fisica.imgSrc = data.imgSrc;
                                    $scope.isNovo = false;
                                }).error(deuErro);
                    }, 100);
                }
            }
        };

        $scope.chamaModalPessoa = function (genero) {
            if (!genero) {
                jQuery('#modalPessoaJuridica').modal('show', {backdrop: 'static'});
            }
            else {
                jQuery('#modalPessoaFisica').modal('show', {backdrop: 'static'});
            }
        };

        $scope.calculaIdade = function (data) {
            var quantos_anos = 0;
            if (data != undefined) {
                var ano_aniversario = data.substring(0, 4);
                var mes_aniversario = data.substring(5, 7);
                var dia_aniversario = data.substring(8, 10);

                var d = new Date,
                        ano_atual = d.getFullYear(),
                        mes_atual = d.getMonth() + 1,
                        dia_atual = d.getDate(),
                        ano_aniversario = +ano_aniversario,
                        mes_aniversario = +mes_aniversario,
                        dia_aniversario = +dia_aniversario,
                        quantos_anos = ano_atual - ano_aniversario;

                if (mes_atual < mes_aniversario || mes_atual == mes_aniversario && dia_atual < dia_aniversario) {
                    quantos_anos--;
                }
            }
            return quantos_anos < 0 ? 0 : quantos_anos;
        };

        $scope.resetFis = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novaPessoaFisica();
        };

        $scope.listarEstados = function () {
            $http.get("/uf/listar").success(function (data) {
                $scope.estados = data.listaDeRegistros;
            }).error(deuErro);
        };

        $scope.listarCidades = function () {
            if ($scope.fisica.codigoestado !== "" && $scope.fisica.codigoestado !== undefined) {
                $http.get("/cidade/listarPorCodigoEstado/" + $scope.fisica.codigoestado).success(function (data) {
                    $scope.cidades = data;
                }).error(deuErro);
            }
        };

        $scope.cidadeSelecionada = function (codigo) {
            if (codigo == $scope.fisica.codigoibge) {
                return true;
            } else {
                return false;
            }
        };

        function foto(id) {
            $http.get("/foto/user/" + id)
                    .success(function (data) {
                        $scope.urlFoto = data.foto;
                    }).error(deuErro);
        }
        $scope.ehMeninoMenina = {
            "MASCULINO": {
                "icone": "fa fa-male",
                "cor": "#9CC7FF"
            },
            "FEMININO": {
                "icone": "fa fa-female",
                "cor": "#FFC4C4"
            },
            null: {
                "icone": "fa fa-briefcase",
                "cor": "#FFD700"
            }
        };

        $scope.tipoPessoa = function () {
            if ($scope.fisica) {
                if ($scope.fisica.tipo === "USUÁRIO")
                    return false;
                else
                    return true;
            }
        };

        $scope.webcamFoto = function () {
            $(document).ready(function () {
                canvas = document.getElementById('imgCanvas');
                $scope.fisica.imgSrc = canvas.src;
            });
//            console.log($scope.fisica.imgSrc);
        };

        /*   SCRIPTS PARA CARREGAR OPTIONS DOS SELECTS    */
        $scope.getFuncoes = function () {
            $http.get("/funcao")
                    .success(function (data) {
                        $scope.funcoes = data;
                    })
                    .error(deuErro);
        };
        /*   FIM DOS SCRIPST   */
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