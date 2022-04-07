angular.module('editor', ['ngStorage']).controller('editorController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

// войти
    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                }
            }, function errorCallback(response) {
                alert('WRONG USERNAME OR PASSWORD');
            });
    };
// выйти
    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };
// почистить данные пользователя
    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };
// проверка на аутентификацию
    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    // $scope.loadUsers = function () {
    //     $http.get(contextPath + '/users')
    //         .then(function (response) {
    //             $scope.UserList = response.data;
    //         });
    // };
// редактирование
    $scope.edit = function () {
        var user = {
            login: $localStorage.springWebUser.username,
            name: $scope.user.name,
            lastname: $scope.user.lastname,
            birthDate: $scope.user.birthDate,
            address: $scope.user.address,
            info: $scope.user.info,
            password: $scope.user.password
        }
        $http({
            url: contextPath + '/users',
            method: 'PUT',
            data: user
        }).then(function successCallback(response) {
            alert(response.data.message);
        }, function errorCallback(response) {
            alert(response.data.message);
        });
    };
});