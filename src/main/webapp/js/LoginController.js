module.controller("LoginController", ["$scope", "$http", function ($scope, $http) {

    var optsMsg = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "7000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    
    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.", null, optsMsg);
    }
    function erroLogin() {
        toastr.error("Login ou senha incorretos. Tente novamente.", null, optsMsg);
    }
    
    function erroBloqueio() {
        toastr.warning("Esse usuário está com o acesso bloqueado. Procure um administrador.", null, optsMsg);
    }

    $scope.verificaTelaLogin = function () {
        $http.get("/login/usuariologado")
                .success(function (data) {
                    if (data.idpessoa) {
//                        console.log(data.idpessoa);
                        window.location.href = "/";
                    }
                })
                .error(deuErro);
    };

    $scope.verificaUsers = function () {
        $http.get("/usuario")
                .success(function (data) {
                    //console.log(data);
                    if (data[0]) {
                        $scope.yesUser = true;
                    }
                    else {
                        $scope.yesUser = false;
                    }
                })
                .error(deuErro);
    };
    
    $scope.logar = function () {
        $http.get("/login/statusLogin/" + $scope.login.login)
                .success(function (data) {
                    //console.log(data.length);
                    if (data.length > 0) {
                        var statusUsuario = data[0].status;
                        if (statusUsuario === 'ATIVO') {
                            $http.post("/login/efetuarlogin", $scope.login)
                                    .success(function (data) {
                                        window.location.href = "/";
                                    })
                                    .error(erroLogin);
                        }
                        else {
                            erroBloqueio();
                        }
                    }
                    else {
                        erroLogin();
                    }
                })
                .error(deuErro);
    };
}]);