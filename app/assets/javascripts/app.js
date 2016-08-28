angular.module('todoApp', []).controller('TodoListController',
		function($scope, $http, filterFilter) {

			$http.get('/products').success(function(data) {
				$scope.sports = data.filter(function(e) {
					return e.category == 'sports';
				});

				$scope.news = data.filter(function(e) {
					return e.category == 'news';
				});

				// selected fruits
				$scope.selection = [];

				// helper method to get selected fruits
				$scope.selectedFruits = function selectedFruits() {
					return filterFilter($scope.fruits, {
						selected : true
					});
				};

				// watch fruits for changes
				$scope.$watch('sports|filter:{selected:true}', function(nv) {
					$scope.selection = nv.map(function(sport) {
						return sport.name;
					});
				}, true);
			});

		});
