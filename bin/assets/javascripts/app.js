angular.module('todoApp', []).controller('TodoListController',
		function($scope, $http, filterFilter) {

			$http.get('/products').success(function(data) {
				$scope.sports = data.filter(function(e) {
					return e.category == 'sports';
				});

				$scope.news = data.filter(function(e) {
					return e.category == 'news';
				});

				// selected sports
				$scope.selection = [];

				$scope.newsProduct = [];
				
				// helper method to get selected fruits
				$scope.selectedFruits = function selectedFruits() {
					return filterFilter($scope.fruits, {
						selected : true
					});
				};

				// watch sports for changes
				$scope.$watch('sports|filter:{selected:true}', function(nv) {
					$scope.selection = nv.map(function(sport) {
						return sport;
					});
				}, true);
				
				// watch sports for changes
				$scope.$watch('news|filter:{selected:true}', function(nv) {
					$scope.newsProduct = nv.map(function(n) {
						return n;
					});
				}, true);
				
			});

		});
