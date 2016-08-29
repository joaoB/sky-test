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

	// generate mock customer id
	var customerID = Math.floor(Math.random() * 10);

	// provide the id to the cookie
	$cookieStore.put("customerID", customerID);

	// store the customer id to be read by confirmation page
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
		// selected news
		$scope.newsProduct = [];

		// watch sports for changes
		$scope.$watch('sports|filter:{selected:true}', function(nv) {
			$scope.selection = nv.map(function(sport) {
				return sport;
			});
		}, true);

		// watch news for changes
		$scope.$watch('news|filter:{selected:true}', function(nv) {
			$scope.newsProduct = nv.map(function(n) {
				return n;
			});
		}, true);

	});

});

app.controller('ConfirmationCtrl', function($scope, $http, srvShareData) {
	var cid = parseInt(srvShareData.getCid());
	$scope.customerName = "Customer #" + cid;
	$scope.basketedProducts = srvShareData.getData();

	$scope.buy = function(products) {

		var productsIds = products.map(function(p) {
			return p.id;
		});
		
		var data = {
			customerID : cid,
			products : productsIds
		};
		$http.post('/confirmation', JSON.stringify(data)).success(
				function(data) {
					$scope.status = data.status;
				});
	};

});
