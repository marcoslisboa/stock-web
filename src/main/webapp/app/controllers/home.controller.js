(function () {
  'use strict';

  angular
    .module('appEstoque')
    .controller('HomeController', ['$http', function ($http) {

      var vm = this;
      vm.products = [];
      vm.product = {};

      $http.get('/store/api/product').then(function (response) {
        vm.products = response.data;
      });

      vm.save = function () {
        $http.post('/store/api/product', vm.product).then(function (response) {
          console.log(response.data);
        });
      };

      vm.delete = function (product) {
        $http.delete('/store/api/product', {id: product.id}).then(function (response) {
          console.log(response.data);
        });
      };

    }]);

})();