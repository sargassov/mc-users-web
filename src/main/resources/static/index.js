angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadUsers = function () {
        $http.get(contextPath + '/users')
            .then(function (response) {
                $scope.usersList = response.data;
            });
    };

    $scope.loadUserById = function (userId) {
        $http.get(contextPath + '/users/' + userId)
            .then(function (response) {
                $scope.usersList = response.data;
                $scope.loadUsers();
            });
    };

    $scope.deleteUser = function (userId) {
        $http.delete(contextPath + '/users/' + userId)
            .then(function (response) {
                $scope.loadUsers();
            });
    }



    $scope.loadUsers();
});