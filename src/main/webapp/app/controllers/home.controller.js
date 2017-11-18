(function () {
  'use strict';

  angular
    .module('appStock')
    .controller('HomeController', ['$http', function ($http) {

      var vm = this;
      vm.products = [];
      vm.product = {};

      $http.get('/stock-web/api/product').then(function (response) {
        vm.products = response.data;
      });

      vm.save = function () {
        $http.post('/stock-web/api/product', vm.product).then(function (response) {
          console.log(response.data);
        });
      };

      vm.delete = function (product) {
        $http.delete('/stock-web/api/product', {id: product.id}).then(function (response) {
          console.log(response.data);
        });
      };

    }]);

})();