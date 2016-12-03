var app = angular.module('app', []);

app.controller('AppCtl', function($scope) {
	$scope.clickHandler = function(){
	  	window.alert('Clicked');
	  }
});
