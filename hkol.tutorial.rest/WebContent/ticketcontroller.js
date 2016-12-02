var app = angular.module('app', []);

app.controller('TicketCtl', function($scope, $http) {
    $http.get("http://localhost:8080/hkol.tutorial.rest/api/v1/tickets")
    .then(function (response) {$scope.tickets = response.data;});
});