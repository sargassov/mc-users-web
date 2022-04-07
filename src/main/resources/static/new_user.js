angular.module('new_user', ['ngStorage']).controller('new_userController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token
    }

// войти
    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
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

//сохранение нового пользователя
    $scope.save = function () {
        $http({
            url: contextPath + '/users',
            method: 'POST',
            data: $scope.newUser
        }).then(function successCallback(response) {
            alert(response.data.message);
            $scope.newUser = null
        }, function errorCallback(response) {
            alert(response.data.message);
        });
    };

    $scope.clearForm = function () {
        $scope.newUser = null
        alert("clear");
    };
});