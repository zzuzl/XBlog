<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="app" lang="zh-CN">
<head>
    <title>XBlog</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/index.css">
    <script src="/resource/bower_components/bootstrap/js/collapse.js"></script>
    <script src="/resource/angular-1.4.8/angular.min.js"></script>
    <script src="/resource/js/app.js"></script>
</head>
<body>
<%@include file="common/title.jsp" %>

<div class="container" ng-controller="IndexCtrl as vm" id="container">
    <div class="jumbotron" style="margin-top: 60px">
        <h1>欢迎来到XBlog!</h1>
        <p>
            <a class="btn btn-primary btn-lg" href="/about" role="button">Learn more</a>
            <c:if test="${sessionScope.user != null}">
                <a class="btn btn-info btn-lg" href="/setting/editArticle" role="button">写博客</a>
            </c:if>
        </p>
    </div>

    <div class="row">
        <div class="col-xs-3">
            <div class="navbar navbar-default" id="mycollapse">
                <ul class="nav nav-pills nav-stacked">
                    <c:forEach items="${list}" var="item" varStatus="i">
                        <li>
                            <a href="#second-level-${i.index}" class="second-level accordion-toggle"
                               data-toggle="collapse"
                               data-parent="#mycollapse">
                                    ${item.title}
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="nav collapse" id="second-level-${i.index}">
                                <c:forEach items="${item.children}" var="child">
                                    <li>
                                        <a href="javascript:void(0)" ng-click="vm.load({page:1,cate:${child.cateId}})">
                                                ${child.title}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <label style="margin: 10px;font-size: 20px;text-align: center">用户排行</label>
            <ol class="user-rank">
                <c:forEach items="${requestScope.userRank}" var="item" varStatus="index">
                    <li>
                        <div class="index-order">
                            ${index.index + 1}.
                        </div>
                        <a href="/${item.url}" class="thumbnail" title="${item.nickname}" target="_blank">
                            <img src="${item.photoSrc}"/>
                        </a>
                        <%--<a href="/u/${item.url}" class="nickname" title="${item.nickname}" target="_blank">
                            ${item.nickname}
                        </a>--%>
                    </li>
                </c:forEach>
            </ol>
        </div>
        <div class="col-xs-9">
            <div class="list-item row" ng-repeat="item in vm.data">
                <div class="col-xs-1 head-photo">
                    <a href="/{{item.user.url}}" target="_blank" class="thumbnail">
                        <img src="{{item.user.photoSrc}}" alt="暂无"
                             onerror="this.src='/resource/images/default-head-photo.png'"/>
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
                            <a href="/p/{{item.articleId}}#comment" target="_blank">评论({{item.commentCount}})</a>
                        </span>
                        <span class="view">
                            <i class="fa fa-eye unClicked" aria-hidden="true"></i>
                            <a href="javascript:void(0)">浏览({{item.viewCount}})</a>
                        </span>
                        <span class="zan">
                            <i class="fa fa-thumbs-up" ng-class="item.currentClass" aria-hidden="true"></i>
                            <a href="javascript:void(0)" ng-click="vm.like(item)">赞({{item.likeCount}})</a>
                        </span>
                    </div>
                </div>
            </div>

            <div class="alert alert-danger" ng-show="vm.total<=0 && vm.init">
                暂无数据
            </div>

            <%-- 分页 --%>
            <xl-page pageSize="15" n="5" method="load" cla="pagination-sm" ng-show="vm.totalPage>1"
                     data="itemList" totalItem="totalItem" totalPage="totalPage"></xl-page>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script type="application/javascript">

    /* 手风琴效果 */
    $(function () {
        $(document).on('click', '.accordion-toggle', function (event) {
            event.stopPropagation();
            var $this = $(this);
            var parent = $this.data('parent');
            var actives = parent && $(parent).find('.collapse.in');

            // From bootstrap itself
            if (actives && actives.length) {
                actives.data('collapse');
                actives.collapse('hide');
            }

            var target = $this.attr('data-target') || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, ''); //strip for ie7
            $(target).collapse('toggle');
        });

        // 选中主页
        $('#index-li').addClass('active');
    });

    /**
     * 主页
     */
    (function () {
        'use strict';

        angular.module('app')
                .controller('IndexCtrl', IndexCtrl);

        IndexCtrl.$inject = ['$http'];

        function IndexCtrl($http) {
            var vm = this;
            vm.init = false;

            // 加载文章数据
            vm.load = function (params, callback) {
                var url = "/article/page/" + params.page;
                if (params.cate === undefined) {
                    vm.cate = 0;
                } else {
                    vm.cate = params.cate;
                }
                url += '?cate=' + vm.cate;

                $http.get(url).then(function (res) {
                    if(res.data.itemList) {
                        for (var i = 0; i < res.data.itemList.length; i++) {
                            res.data.itemList[i].currentClass = 'unClicked';
                        }
                    }
                    if (callback) {
                        callback(res.data);
                        if (!vm.init) {
                            vm.init = true;
                        }
                    } else {
                        vm.data = res.data.itemList;
                        vm.total = res.data.totalItem;
                    }
                    window.scrollTop = 0;
                });
            };

            // 点赞
            vm.like = function (item) {
                if (vm.currentClass !== 'clicked') {
                    vm.syncLike(item, function () {
                        item.currentClass = 'clicked';
                        item.likeCount++;
                    }, function (text) {
                        alert(text);
                    });
                }
            };

            // 更新点赞到服务器
            vm.syncLike = function (item, success, error) {
                if ('${sessionScope.user.userId}') {
                    $http.post('/article/like', {
                        "userId": '${sessionScope.user.userId}',
                        "articleId": item.articleId
                    }).then(function (res) {
                        if (res.data.success) {
                            success();
                        } else {
                            error(res.data.msg);
                        }
                    });
                } else {
                    alert('登录后可评论和赞');
                    window.location = '/login';
                }
            };
        }
    })();
</script>
<script src="/resource/js/filters.js"></script>
<script src="/resource/js/page.js"></script>
</body>
</html>
