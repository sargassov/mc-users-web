angular.module('ulist', ['ngStorage']).controller('ulistController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app';

     if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token
     }


    $scope.loadUsers = function () {
        $http.get(contextPath + '/users')
            .then(function (response) {
                $scope.UserList = response.data;
            });
    };

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

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.deleteUser = function (id){
        $http.delete(contextPath + '/users/' + id)
            .then(function successCallback(response) {
                alert(response.data.message);
                $scope.loadUsers();
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    }

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };


    $scope.loadUsers();
});