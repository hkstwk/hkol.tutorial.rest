var app = angular.module('app',['ngResource']);

//app.controller('TicketCtl', function($scope, $http) {
//    $http.get("http://localhost:8080/hkol.tutorial.rest/api/v1/tickets")
//    .then(function (response) {$scope.tickets = response.data;});
//});

app.controller('TicketCtl', function($scope, $http, TicketService) {
    
	// GET all tickets
	$scope.tickets = TicketService.query();
    
	// GET one specific ticket
    $scope.setDataForTicket = function(ticketID) {
    	$scope.currentTicket = TicketService.get({ticket, ticketID});
    }
});

app.controller('DeleteCtl', function($scope, $http) {
    $http.delete("http://localhost:8080/hkol.tutorial.rest/api/v1/tickets/delete/" + $scope._id.$oid).
        then(function(response) {
        	$http.get("http://localhost:8080/hkol.tutorial.rest/api/v1/tickets")
            .then(function (response) {$scope.tickets = response.data;});
        });
});

app.factory('TicketService', function ($resource) {
    return $resource('http://localhost:8080/hkol.tutorial.rest/api/v1/tickets/:ticket',{ticket: "@ticket"});
});



//app.factory('TicketService', function ($resource) {
//    return $resource('http://jsonplaceholder.typicode.com/users/:user',{user: "@user"});
//});

