angular.module('app', [])
	.controller('controller', function($http) {
		var self = this;
		$http.get('/cityWeatherList').then(function(response) {
			self.cityWeatherList = response.data.cityWeatherList;
		})
	}
);