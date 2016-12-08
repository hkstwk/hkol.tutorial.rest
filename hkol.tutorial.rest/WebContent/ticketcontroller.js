var app = angular.module('app',['ngResource']);

app.controller('TicketCtl', function($scope, $http, TicketService) {
   // $scope.ticket = new Ticket();
	
	// GET all tickets
	$scope.tickets = TicketService.query();
    
	// GET one specific ticket
    $scope.setDataForGetTicket = function(ticketID) {
    	$scope.currentTicket = TicketService.get({ticket: ticketID});
    }
    
    // DELETE one specific ticket
    $scope.setDataForDeleteTicket = function(ticketID) {
    	$scope.deletedTicket = TicketService.delete({ticket: ticketID});
    }
    
    // REMOVE item from ng-repeat array. Example use: refresh array after deleting 1 item in database
    $scope.remove = function(array, index){
        array.splice(index, 1);
    }
    
    $scope.saveTicket = function() {
    //	$scope.ticket.data = "{ date: " + $scope.date + ", hours: " + $scope.hours + "}";
    	var response = TicketService.save({date: '10 december 2016', hours: '10'});
    	$scope.tickets.push(response);	
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