module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", "ServiceFuncoes", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, ServiceFuncoes, $rootScope) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar indígena";
        $scope.ent = $rootScope.ent = "indigena";
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

        $scope.todosIndigenas = function () {
            $http.get("/indigena")
                    .success(function (data) {
                        //console.log(data);
                        $scope.indigenas = data;
                    })
                    .error(deuErro);
        };

        function novoIndio() {
            $scope.indio = {
                nome: "",
                cpf: "",
                etnia: "",
                genero: "",
                dataNascimento: "",
                conveniosselecionados: [],
                telefone: "",
                terraIndigena: "",
                escolaridade: "",
                estadoCivil: "",
                codigoSUS: "",
                imgSrc: "/fotos/default.png"
            };
            $scope.isNovoIndio = true;
        }

        $scope.indioComFoto = function (codAssindi) {
            $scope.carregarIndio(codAssindi);
            getFoto(codAssindi);
            $scope.listarFamiliasPorIndigena(codAssindi);
        };

        $scope.carregarIndio = function (codAssindi) {
            if ($location.path() === "/Indigena/novo") {
                novoIndio();
            }
            else {
                $timeout(function () {
                    var busca;
                    if (codAssindi) {
                        busca = "/indigena/obj/" + codAssindi;
                    }
                    else {
                        busca = "/indigena/obj/" + $routeParams.id;
                    }

                    $http.get(busca)
                            .success(function (data) {
                                //console.log(data.terraIndigena);
                                var dados = data;
                                var d = new Date(data.dataNascimento);
                                dados.cpf = data.cpf.cpf;
                                dados.telefone = (data.telefone) ? data.telefone.telefone : "";
                                dados.dataNascimento = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                                dados.dataArrumada = dados.dataNascimento.getDate() + "/" + (dados.dataNascimento.getMonth() + 1) + '/' + dados.dataNascimento.getFullYear();
                                dados.nomeEtnia = data.etnia.descricao;
                                dados.etnia = data.etnia.idetnia;
                                dados.terraIndigena = {
                                    cidade: data.terraIndigena.cidade.descricao,
                                    nometerra: data.terraIndigena.nometerra,
                                    sigla: data.terraIndigena.cidade.estado.sigla,
                                    descricao: data.terraIndigena.cidade.estado.descricao,
                                    idterraindigena: data.terraIndigena.idterraindigena
                                };

                                dados.conveniosselecionados = data.convenio;
                                if (!dados.cpf) {
                                    dados.cpfInformado = "CPF não informado";
                                }
                                else {
                                    dados.cpfInformado = dados.cpf;
                                }
                                if (!dados.telefone) {
                                    dados.telInformado = "Telefone não informado";
                                }
                                else {
                                    dados.telInformado = dados.telefone;
                                }
                                if (!dados.codigosus) {
                                    dados.codigosus = "Não informado";
                                }

                                if (dados.ocorrencia.length == 0) {
                                    dados.nenhumaOcorrencia = "Nenhuma ocorrência para este indígena.";
                                }

                                $scope.indio = dados;

                                $scope.isNovoIndio = false;

                            })
                            .error(deuErro);
                }, 100);

            }
        };

        $scope.listarFamiliasPorIndigena = function (idIndigena) {
            $scope.nenhumaFamilia = "";
            $http.get("/familia/familiasporindigena/" + idIndigena)
                    .success(function (data) {
                        $scope.familias = data;
                        if ($scope.familias.length == 0) {
                            $scope.nenhumaFamilia = "Nenhuma família para este indígena.";
                        }
                    })
                    .error(deuErro);
        };

        $scope.indioEscolaridade = {
            "FUNDAMENTALINCOMPLETO": "Fundamental incompleto",
            "FUNDAMENTALCOMPLETO": "Fundamental completo",
            "MEDIOINCOMPLETO": "Médio incompleto",
            "MEDIOCOMPLETO": "Médio completo",
            "SUPERIORINCOMPLETO": "Superior incompleto",
            "SUPERIORCOMPLETO": "Superior completo"
        };

        $scope.corStatus = {null: "success", "BLOQUEADO": "danger", "": "info"};

        function getFoto(id) {
            $http.get("/foto/indio/" + id)
                    .success(function (data) {
                        $scope.urlFoto = data.foto;
                    }).error(deuErro);
        }

        $scope.print = function (divNome) {
            var printContents = document.getElementById(divNome).innerHTML;
            document.body.innerHTML = printContents;
            $location.path("/Indigena/listar");
            location.reload(true);
            window.print();
        };

        $scope.salvarIndio = function () {
//        var cpfSemPonto = tiraCaracter($scope.indio.cpf, ".");
//        var cpfSemPonto = tiraCaracter(cpfSemPonto, "-");
            var dataNasc = dataToDate($scope.indio.dataNascimento);
            var indioCompleto = {
                nome: $scope.indio.nome,
                cpf: {
                    cpf: $scope.indio.cpf
                },
                etnia: $scope.indio.etnia,
                genero: $scope.indio.genero,
                dataNascimento: dataNasc + "T00:00:00-03",
                convenio: $scope.indio.conveniosselecionados,
                telefone: {
                    telefone: $scope.indio.telefone
                },
                terraIndigena: $scope.indio.terraIndigena.idterraindigena,
                escolaridade: $scope.indio.escolaridade,
                estadoCivil: $scope.indio.estadoCivil,
                codigoSUS: $scope.indio.codigoSUS,
                imgSrc: $scope.indio.imgSrc
            };

            if ($scope.isNovoIndio) {
                //console.log(indioCompleto);
                $http.post("/indigena", indioCompleto)
                        .success(function () {
                            toastr.success("Indígena cadastrado com sucesso!");
                            $location.path("/Indigena/listar");
                        })
                        .error(erroCadastraIndio);
            }
            else {
                indioCompleto.codigoAssindi = $routeParams.id;
                //console.log(indioCompleto);
                $http.put("/indigena", indioCompleto)
                        .success(function () {
                            toastr.success("Indígena atualizado com sucesso!");
                            $location.path("/Indigena/listar");
                        })
                        .error(deuErro);
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

        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }

        $scope.dateToData = function (valor) {

            var data = "";
            if (valor != null && valor != "" && valor != undefined) {
                data = ServiceFuncoes.dateToData(valor);
            }
            return data;
        };

        $scope.editarIndio = function (indio) {
            $location.path("/Indigena/editar/" + indio.codigoassindi);
        };

        $scope.reset = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novoIndio();
        };

        function erroCadastraIndio() {
            toastr.error("Não foi possível cadastrar o indígena. ", "Erro");
        }

        $scope.ehMeninoMenina = {
            "MASCULINO": {
                "icone": "fa fa-male",
                "cor": "#9CC7FF"
            },
            "FEMININO": {
                "icone": "fa fa-female",
                "cor": "#FFC4C4"
            }
        };

        $scope.webcamFoto = function () {
            $(document).ready(function () {
                canvas = document.getElementById('imgCanvas');
                $scope.indio.imgSrc = canvas.src;
            });
            //console.log($scope.indio.imgSrc);
        };

        /*  SCRIPTS PARA CARREGAR OPTIONS DOS SELECTS  */
        $scope.getEtnias = function () {
            $http.get("/etnia")
                    .success(function (data) {
                        $scope.etnias = "";
                        $scope.etnias = data;
                    })
                    .error(deuErro);
        };

        $scope.getTerras = function () {
            $http.get("/terraIndigena")
                    .success(function (data) {
                        $scope.terras = data;
//                        console.log($scope.terras);
                    })
                    .error(deuErro);
        };

        $scope.getConvenios = function () {
            $http.get("/convenio")
                    .success(function (data) {
                        //console.log(data);
                        $scope.convenios = data;
                    })
                    .error(deuErro);
        };
        /*  FIM DOS SCRIPTS  */
        $scope.carregaScript = function (nScript) {
            $timeout(function () {
                var script = document.createElement('script');
                script.src = nScript + ".js";
                document.getElementsByTagName('head')[0].appendChild(script);
            }, 100);
        };

        function novaOcorrencia() {
            $scope.ocorrencia = {
                dataOcorrencia: "",
                dataBloqueio: "",
                descricao: ""
            };
        }

        $scope.carregarOcorrencia = function () {
            novaOcorrencia();
        };

        $scope.getOcorrencias = function () {
            var url = ($routeParams.id == undefined) ? "" : "/ocorrencias/" + $routeParams.id;
            $http.get("/ocorrencia" + url).success(function (data) {
                $scope.ocorrencias = data;
            }).error(deuErro);
        };

        $scope.getIdIndigena = function (indigena) {
            $routeParams.id = indigena.codigoassindi;
        };

        $scope.confirmaExclusao = function (entidade, nomeEntidade, nomeRegistro, id) {
            jQuery('#apagarModal').modal('show', {backdrop: 'static'});
            $scope.dadosExclusao = {};
            $scope.dadosExclusao.entidade = entidade;
            $scope.dadosExclusao.nomeEntidade = nomeEntidade;
            $scope.dadosExclusao.nomeRegistro = nomeRegistro;
            $scope.dadosExclusao.id = id;
            $scope.dadosExclusao.opcional = $routeParams.id;
        };

        $scope.excluiRegistro = function () {
            ServiceFuncoes.excluiRegistro($scope.dadosExclusao.entidade, $scope.dadosExclusao.nomeEntidade, $scope.dadosExclusao.nomeRegistro, $scope.dadosExclusao.id, $scope.dadosExclusao.opcional);
            $timeout(function () {
                $scope.getOcorrencias();
            }, 100);
        };

        $scope.salvarOcorrencia = function () {
            var dataOcorrencia = dataToDate($scope.ocorrencia.dataOcorrencia);
            var dataBloqueio = ($scope.ocorrencia.dataBloqueio != "") ? dataToDate($scope.ocorrencia.dataBloqueio) : "";
            var OcorrenciaCompleta = {
                dataocorrencia: dataOcorrencia + "T00:00:00-03",
                databloqueio: (dataBloqueio != "") ? dataBloqueio + "T00:00:00-03" : null,
                descricao: $scope.ocorrencia.descricao,
                idIndigena: $routeParams.id
            };
            if (new Date(dataBloqueio) > new Date(dataOcorrencia) || dataBloqueio == "") {
                $http.post("/ocorrencia", OcorrenciaCompleta)
                        .success(function () {
                            toastr.success("Ocorrência salva com sucesso.", "Salvo");
                            if($location.path() === "/Indigena/listar"){
                                $scope.atualizarListagens($scope.busca.numregistros, $scope.pagina, $scope.campoAtual, '', '', $scope.ent, '');
                            } else {
                                $scope.getOcorrencias();
                            }
                        })
                        .error(deuErro);
            } else {
                toastr.warning("Data da ocorrência deve ser anterior à data de bloqueio.");
            }
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }
    }]);