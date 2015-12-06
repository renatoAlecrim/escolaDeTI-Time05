module.config(function($routeProvider, $locationProvider) {  
//------- Rotas do Usuário --------
    $routeProvider
    .when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeController'
    }).when('/Usuario/listar', {
        templateUrl: 'views/usuarioListar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/novo', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/editar/:id', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/editar', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/meudados/:id', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    })
//------- Rotas do Perfil --------
    .when('/Perfil/listar', {
        templateUrl: 'views/perfilListar.html',
        controller: 'PerfilController'
    }).when('/Perfil/novo', {
        templateUrl: 'views/perfilCadastrar.html',
        controller: 'PerfilController'
    }).when('/Perfil/editar/:id', {
        templateUrl: 'views/perfilCadastrar.html',
        controller: 'PerfilController'
    
//------- Rotas do Pessoa --------
    }).when('/Pessoa/listar', {
        templateUrl: 'views/pessoaListar.html',
        controller: 'PessoaFisicaController'
    }).when('/Juridica/nova', {
        templateUrl: 'views/pessoaJuridicaCadastrar.html',
        controller: 'PessoaJuridicaController'
    }).when('/Juridica/editar/:id', {
        templateUrl: 'views/pessoaJuridicaCadastrar.html',
        controller: 'PessoaJuridicaController'
    }).when('/Fisica/nova', {
        templateUrl: 'views/pessoaFisicaCadastrar.html',
        controller: 'PessoaFisicaController'
    }).when('/Fisica/editar/:id', {
        templateUrl: 'views/pessoaFisicaCadastrar.html',
        controller: 'PessoaFisicaController'
//------- Convênio -------        
    }).when('/Convenio/novo', {
        templateUrl: 'views/convenioCadastrar.html',
        controller: 'ConvenioController'
    }).when('/Convenio/listar', {
        templateUrl: 'views/convenioListar.html',
        controller: 'ConvenioController'
    }).when('/Convenio/editar/:id', {
        templateUrl: 'views/convenioCadastrar.html',
        controller: 'ConvenioController'
    })
 //------- Rotas do Etnia --------   
    .when('/Etnia/nova', {
        templateUrl: 'views/etniaCadastrar.html',
        controller: 'EtniaController'
    }).when('/Etnia/listar', {
        templateUrl: 'views/etniaListar.html',
        controller: 'EtniaController'
    }).when('/Etnia/editar/:id', {
        templateUrl: 'views/etniaCadastrar.html',
        controller: 'EtniaController'
    })
    //------- Rotas do Função --------   
    .when('/Funcao/nova', {
        templateUrl: 'views/funcaoCadastrar.html',
        controller: 'FuncaoController'
    }).when('/Funcao/listar', {
        templateUrl: 'views/funcaoListar.html',
        controller: 'FuncaoController'
    }).when('/Funcao/editar/:id', {
        templateUrl: 'views/funcaoCadastrar.html',
        controller: 'FuncaoController'
    })
    //------- Rotas da Terra Indigena --------   
    .when('/TerraIndigena/nova', {
        templateUrl: 'views/terraCadastrar.html',
        controller: 'TerraController'
    }).when('/TerraIndigena/listar', {
        templateUrl: 'views/terraListar.html',
        controller: 'TerraController'
    }).when('/TerraIndigena/editar/:id', {
        templateUrl: 'views/terraCadastrar.html',
        controller: 'TerraController'
    })
    //------- Rotas da Familia --------   
    .when('/Familia/nova', {
        templateUrl: 'views/familiaCadastrar.html',
        controller: 'FamiliaController'
    }).when('/Familia/listar', {
        templateUrl: 'views/familiaListar.html',
        controller: 'FamiliaController'
    }).when('/Familia/editar/:id', {
        templateUrl: 'views/familiaCadastrar.html',
        controller: 'FamiliaController'
    })
    //------- Rotas do Indigena --------   
    .when('/Indigena/novo', {
        templateUrl: 'views/indigenaCadastrar.html',
        controller: 'IndigenaController'
    }).when('/Indigena/listar', {
        templateUrl: 'views/indigenaListar.html',
        controller: 'IndigenaController'
    }).when('/Indigena/editar/:id', {
        templateUrl: 'views/indigenaCadastrar.html',
        controller: 'IndigenaController'
    })
    //------- Rotas de Estadia de familia
    .when('/Estadia/listar', {
        templateUrl: 'views/estadiaListar.html',
        controller: 'EstadiaController'
    }).when('/Estadia/nova', {
        templateUrl: 'views/estadiaCadastrar.html',
        controller: 'EstadiaController'
    }).when('/Estadia/editar/:id', {
        templateUrl: 'views/estadiaCadastrar.html',
        controller: 'EstadiaController'
    })
    
    //------- Rotas de Visita
    .when('/Visita/listar', {
        templateUrl: 'views/visitaListar.html',
        controller: 'VisitaController'
    }).when('/Visita/nova', {
        templateUrl: 'views/visitaCadastrar.html',
        controller: 'VisitaController'
    }).when('/Visita/editar/:id', {
        templateUrl: 'views/visitaCadastrar.html',
        controller: 'VisitaController'
    })
    
    //------- Rotas do Evento -------- 
    .when('/Eventos/novo', {
        templateUrl: 'views/eventosCadastrar.html',
        controller: 'EventosController'
    }).when('/Eventos/novo/:data', {
        templateUrl: 'views/eventosCadastrar.html',
        controller: 'EventosController'
    }).when('/Eventos/listar', {
        templateUrl: 'views/eventosListar.html',
        controller: 'EventosController'
    }).when('/Eventos/editar/:id', {
        templateUrl: 'views/eventosCadastrar.html',
        controller: 'EventosController'
    })
    //------- Rotas de Relatorios  -------- 
    .when('/Relatorios/indios', {
        templateUrl: 'views/relatorioIndios.html',
        controller: 'RelatorioController'
    })
    .when('/Relatorios/estadias', {
        templateUrl: 'views/relatorioEstadia.html',
        controller: 'RelatorioController'
    })
    .when('/Relatorios/colaboradores', {
        templateUrl: 'views/relatorioColaborador.html',
        controller: 'RelatorioController'
    })
    .when('/Relatorios/visitas', {
        templateUrl: 'views/relatorioVisita.html',
        controller: 'RelatorioController'
    })
    
    
    
    .otherwise ({ redirectTo: '/' });
    $locationProvider.html5Mode(false);
});