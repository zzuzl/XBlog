/**
 * 分页指令
 */
(function () {
    'use strict';

    angular
        .module('app')
        .directive("xlPage", [function () {
            function link(scope, element, attrs) {
                var vm = scope.vm;

                vm.currentPage = 1;
                vm.total = 0;
                vm.pageSize = attrs.pagesize;
                vm.maxPage = 1;
                vm.pages = [];
                vm.N = attrs.n;//最多显示n个页码
                vm.cla = attrs.cla;

                // 加载数据的方法未定义
                if (!vm[attrs.method]) {
                    throw new Error("load method is undefined");
                }
                // 下一页
                vm.next = function () {
                    if (vm.currentPage < vm.maxPage) {
                        vm.currentPage++;
                        vm.getData();
                    }
                };
                // 上一页
                vm.prev = function () {
                    if (vm.currentPage > 1) {
                        vm.currentPage--;
                        vm.getData();
                    }
                };

                vm.createPage = function (data) {
                    // 最大页
                    vm.maxPage = Math.ceil(data[attrs.totalitem] / vm.pageSize);
                    vm.pages = [];

                    if (vm.currentPage > Math.ceil(vm.N / 2)) {
                        if (vm.maxPage > vm.N) {
                            var temp = vm.maxPage > Math.floor(vm.N / 2) ? Math.floor(vm.N / 2) : vm.maxPage;
                            for (var i = temp - N; i <= temp; i++) {
                                vm.pages.push(i);
                            }
                        } else {
                            for (var i = 1; i <= vm.maxPage; i++) {
                                vm.pages.push(i);
                            }
                        }
                    } else {
                        var temp = vm.maxPage > vm.N ? scope.vm.N : vm.maxPage;
                        for (var i = 1; i <= temp; i++) {
                            vm.pages.push(i);
                        }
                    }

                    vm.data = data[attrs.data];
                    vm.total = data[attrs.totalitem];
                };

                // 获取数据
                vm.getData = function (page) {
                    if (page > 0) {
                        vm.currentPage = page;
                    }

                    vm[attrs.method]({page: vm.currentPage}, vm.createPage);
                };

                vm.getData(1);
            }

            return {
                restrict: 'E',
                template: "<nav style='margin: 0 auto;text-align: center' ng-show='vm.total>0'>" +
                "<ul class='pagination {{vm.cla}}'>" +
                "<li ng-class='{disabled:vm.currentPage<=1}'>" +
                "<a href='javascript:void(0)' ng-click='vm.getData(1)'>&laquo;&laquo;</a>" +
                "</li>" +
                "<li ng-class='{disabled:vm.currentPage<=1}'>" +
                "<a href='javascript:void(0)' aria-label='Previous' ng-click='vm.getData(vm.currentPage - 1)'>&laquo;</a>" +
                "</li>" +
                "<li ng-repeat='page in vm.pages' ng-class='{active:page===vm.currentPage}'>" +
                "<a href='javascript:void(0)' ng-click='vm.getData(page)'>{{page}}</a>" +
                "</li>" +
                "<li ng-class='{disabled:vm.currentPage>=vm.maxPage}'>" +
                "<a href='javascript:void(0)' aria-label='Next' ng-click='vm.getData(vm.currentPage + 1)'>&raquo;</a>" +
                "</li>" +
                "<li ng-class='{disabled:vm.currentPage>=vm.maxPage}'>" +
                "<a href='javascript:void(0)' ng-click='vm.getData(vm.maxPage)'>&raquo;&raquo;</a>" +
                "</li>" +
                "</ul>" +
                "</nav>",
                link: link
            }
        }]);
})();