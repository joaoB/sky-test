angular.module('todoApp', []).controller('TodoListController',
		function($scope, $http, $filter) {

			$http.get('/products').success(function(data) {
				$scope.sports = data.filter(function(e) {
					return e.category == 'sports'; 
				});
				
				$scope.news = data.filter(function(e) {
					return e.category == 'news'; 
				});
				
				
				
			});
			
			
		});
