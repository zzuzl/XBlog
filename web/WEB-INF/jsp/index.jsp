<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="app">
<head>
    <title>XBlog</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="${root}/resource/jquery_pagination-master/src/pagination.css">
    <link rel="stylesheet" href="${root}/resource/css/index.css">
    <script src="${root}/resource/bower_components/bootstrap/js/collapse.js"></script>
    <script src="${root}/resource/angular-1.4.8/angular.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${root}/">XBlog</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">主页</a></li>
                <li><a href="#">精华</a></li>
                <li><a href="#">关于</a></li>
            </ul>

            <div class="navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <div class="photo-div">
                            <img src="${root}/resource/images/photo.jpg" width="32" height="32" id="photo_img">
                            <s></s>
                        </div>
                        <div class="user-div">
                            <div class="top-div">
                                <img src="${root}/resource/images/photo.jpg" width="100" height="100"/>
                                <div class="user-info">
                                    <h5>我的昵称</h5>
                                    <h6>672399171@qq.com</h6>
                                </div>
                            </div>
                            <div class="bottom-div">
                                <div id="blog-btn" class="button">设置</div>
                                <div id="quit-btn" class="button">退出</div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-default navbar-btn">登录</button>
                        <a href="#">注册</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
<div class="container" ng-controller="IndexCtrl as vm">
    <div class="jumbotron" style="margin-top: 60px">
        <h1>欢迎来到XBlog!</h1>
        <p></p>
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
    </div>

    <div class="row">
        <div class="col-md-3">
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
                                    <li><a href="#">${child.title}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-md-9">
            <div class="list-item row" ng-repeat="item in vm.data">
                <div class="col-md-1">
                    <a href="#" target="_blank">
                        <img src="${root}/{{item.user.photo_src}}" alt="暂无"/>
                    </a>
                </div>
                <div class="col-md-11">
                    <h4>
                        <a href="${root}/article/{{item.articleId}}" data-ng-bind="item.title"></a>
                    </h4>
                    <p class="list-body-content" data-ng-bind="item.description"></p>
                    <div class="list-foot">
                        <a href="#" class="lightblue" data-ng-bind="item.user.nickname"></a>
                        发表于：<span data-ng-bind="item.postTime | dateFormat"></span>
                        <span class="comment">
                            <i class="fa fa-comments unClicked" aria-hidden="true"></i>
                            <a href="#">评论({{item.commentCount}})</a>
                        </span>
                        <span class="view">
                            <i class="fa fa-eye unClicked" aria-hidden="true"></i>
                            <a href="#">浏览({{item.viewCount}})</a>
                        </span>
                        <span class="zan">
                            <i class="fa fa-thumbs-up" ng-class="vm.currentClass" aria-hidden="true"></i>
                            <a href="javascript:void(0)" ng-click="vm.like(item)">赞({{item.likeCount}})</a>
                        </span>
                    </div>
                </div>
            </div>

            <!-- 分页 -->
            <!--<div id="pagination"></div>-->
            <nav class="pagination-div">
                <ul class="pagination">
                    <li><a href="#" aria-label="Previous">&laquo;</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#" aria-label="Next">&raquo;</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<div class="panel-footer">
    <div>
        <a href="#">关于XBlog</a>
        <a href="#">联系我们</a>©20016-2026
        <a href="#">XBlog</a>保留所有权利
        <a href="#" target="_blank">豫ICP备09004260号</a>
    </div>
</div>

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
    });

    /* 头像个人信息切换 */
    $("#photo_img").click(function (event) {
        event.stopPropagation();
        $("s").toggle();
        $(".user-div").toggle();
    });

    $("s,.user-div").click(function (event) {
        event.stopPropagation();
    });

    $(document).click(function () {
        $("s").hide();
        $(".user-div").hide();
    });


    /**
     * 主页
     */
    (function () {
        'use strict';

        angular.module('app', [])
                .controller('IndexCtrl', IndexCtrl);

        IndexCtrl.$inject = ['$http'];

        function IndexCtrl($http) {
            var vm = this;
            vm.currentPage = 1;
            vm.currentClass = 'unClicked';

            // 加载文章数据
            vm.load = function (page) {
                vm.currentPage = page || vm.currentPage;
                $http.get("${root}/article/" + vm.currentPage)
                        .then(function (res) {
                            vm.data = res.data;
                            if (!angular.isArray(vm.data)) {
                                vm.data = [];
                                vm.data.push(res.data);
                            }
                            window.scrollTop = 0;
                        });
            };

            // 点赞
            vm.like = function (item) {
                if (vm.currentClass !== 'clicked') {
                    vm.syncLike(item, function () {
                        vm.currentClass = 'clicked';
                        item.likeCount++;
                    }, function (text) {
                        alert(text);
                    });
                }
            };

            // 更新点赞到服务器
            vm.syncLike = function (item, success, error) {
                $http.post('${root}/article/like', {
                    userId: item.user.userId,
                    articleId: item.articleId
                }).then(function (res) {
                    if (res.data.success) {
                        success();
                    } else {
                        error(res.data.msg);
                    }
                });
            };

            // 初始化加载第一页
            vm.load();
        }
    })();
</script>
<script src="${root}/resource/js/filters.js"></script>
</body>
</html>
