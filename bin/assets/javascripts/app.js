var app = angular.module('todoApp', [ 'ngCookies' ]);

app.service('srvShareData', function($window) {
	var KEY = 'App.SelectedValue';
	var CID = "App.cid";

	var addData = function(newObj) {
		var mydata = $window.sessionStorage.getItem(KEY);
		if (mydata) {
			mydata = JSON.parse(mydata);
		} else {
			mydata = [];
		}
		// mydata.push(newObj);
		mydata = newObj || [];
		$window.sessionStorage.setItem(KEY, JSON.stringify(mydata));
	};

	var getData = function() {
		var mydata = $window.sessionStorage.getItem(KEY);
		if (mydata) {
			mydata = JSON.parse(mydata);
		}
		return mydata || [];
	};

	var setCid = function(cid) {
		console.log("setting cid to: " + cid);
		$window.sessionStorage.setItem(CID, cid);
	};

	var getCid = function() {
		return $window.sessionStorage.getItem(CID);
	};
	
	return {
		addData : addData,
		getData : getData,
		setCid : setCid,
		getCid : getCid
	};
});

app.controller('TodoListController', function($scope, $http, $cookies,
		$cookieStore, srvShareData) {
	
	//generate mock customer id 
	var customerID = Math.floor(Math.random() * 10);
	
	console.log(customerID);
	
	//provide the id to the cookie
	$cookieStore.put("customerID", customerID);
	
	//store the customer id to be read by confirmation page
	srvShareData.setCid(customerID);

	$scope.basketedProducts = [];

	$scope.checkout = function(sportProducts, newsProducts) {
		$scope.basketedProducts = sportProducts.concat(newsProducts);
		srvShareData.addData($scope.basketedProducts);
		window.location.href = "confirmation";
	};

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

app.controller('ConfirmationCtrl', function($scope, srvShareData) {
	$scope.customerName = "Customer #" + srvShareData.getCid();
	$scope.basketedProducts = srvShareData.getData();
});
