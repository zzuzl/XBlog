<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="app">
<head>
    <title>${requestScope.keyword} - 搜索结果</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/index.css">
    <script src="//cdn.bootcss.com/angular.js/1.5.11/angular.min.js"></script>
</head>
<body>
<%@include file="common/title.jsp" %>
<div class="container" style="margin-top: 100px" id="container" ng-controller="SearchCtrl as vm">
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
            <div class="list-item row" ng-repeat="item in vm.data">
                <div class="col-xs-1 head-photo">
                    <a href="/{{item.user.url}}" target="_blank" class="thumbnail">
                        <img src="{{item.user.photoSrc}}" alt="暂无"/>
                    </a>
                </div>
                <div class="col-xs-11">
                    <h4>
                        <a href="/p/{{item.articleId}}" target="_blank" data-ng-bind="item.title"></a>
                    </h4>
                    <p class="list-body-content" data-ng-bind="item.description"></p>
                    <div class="list-foot">
                        <a href="/{{item.user.url}}" class="lightblue" data-ng-bind="item.user.nickname"></a>
                        发表于：<span data-ng-bind="item.postTime | dateFormat"></span>
                        <span class="comment">
                            <i class="fa fa-comments unClicked" aria-hidden="true"></i>
                            评论({{item.commentCount}})
                        </span>
                        <span class="view">
                            <i class="fa fa-eye unClicked" aria-hidden="true"></i>
                            浏览({{item.viewCount}})
                        </span>
                        <span class="zan">
                            <i class="fa fa-thumbs-up" aria-hidden="true"></i>
                            赞({{item.likeCount}})
                        </span>
                    </div>
                </div>
            </div>

            <div class="alert alert-danger" ng-show="vm.total<=0 && vm.init">
                暂无数据
            </div>

            <xl-page pageSize="15" n="5" method="load" cla="pagination-sm" ng-show="vm.totalPage>1"
                     data="itemList" totalItem="totalItem" totalPage="totalPage"></xl-page>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script type="application/javascript">
    /**
     * 搜索页
     */
    (function () {
        'use strict';

        angular.module('app',[])
                .controller('SearchCtrl', SearchCtrl);

        SearchCtrl.$inject = ['$http'];

        function SearchCtrl($http) {
            var vm = this;
            vm.init = false;

            // 加载文章数据
            vm.load = function (params, callback) {
                var url = "/article/search/" + params.page + '?keyword=${requestScope.keyword}';

                $http.get(url).then(function (res) {
                    if (callback) {
                        callback(res.data);
                        if (!vm.init) {
                            vm.init = true;
                        }
                    } else {
                        vm.data = res.data.itemList;
                        vm.total = res.data.totalItem;
                    }
                });
            };
        }
    })();
</script>
<script src="/resource/js/filters.js"></script>
<script src="/resource/js/page.js"></script>
</body>
</html>
