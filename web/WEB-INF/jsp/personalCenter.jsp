<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="app">
<head>
    <title>${requestScope.user.nickname} 的个人空间</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="${root}/resource/css/index.css">
    <link rel="stylesheet" href="${root}/resource/css/pc.css">
    <script src="${root}/resource/angular-1.4.8/angular.min.js"></script>
</head>
<body>
<%@include file="common/title.jsp" %>

<div class="container" style="margin-top: 100px" id="container">
    <div class="row">
        <div class="col-xs-3 col-xs-offset-1">
            <a href="javascript:void(0)" class="thumbnail" id="photo-thumbnail">
                <img src="${root}/${requestScope.user.photoSrc}" alt="暂无"/>
            </a>
        </div>
        <div class="col-xs-7">
            <h3>${requestScope.user.nickname}</h3>
            <h4 id="age">博龄：2年</h4>
            <h5>博客：
                <a href="http://${requestScope.host}:8888${root}/${requestScope.user.url}">
                    http://${requestScope.host}:8888${root}/${requestScope.user.url}
                </a>
            </h5>
            <h4>${requestScope.user.motto}</h4>
            <h4>兴趣：${requestScope.user.interest}</h4>
            <c:if test="${sessionScope.user.userId != requestScope.user.userId}">
                <a href="javascript:void(0)" type="button" class="btn btn-info"
                   onclick="obj.addOrCancelAttention()" id="attention-btn">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    关注
                </a>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist" id="tabs">
                <li role="presentation" class="active">
                    <a href="#attention" aria-controls="attention" role="tab" data-toggle="tab">
                        ${requestScope.user.nickname}的关注(${requestScope.user.attentionCount})
                    </a>
                </li>
                <li role="presentation">
                    <a href="#fans" aria-controls="fans" role="tab" data-toggle="tab">
                        ${requestScope.user.nickname}的粉丝(${requestScope.user.fansCount})
                    </a>
                </li>
                <li role="presentation">
                    <a href="#dynamic" aria-controls="dynamic" role="tab" data-toggle="tab">
                        最新动态
                    </a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="attention">
                    <ul>
                        <c:forEach items="${requestScope.attentions}" var="item">
                            <li class="user-li">
                                <a href="${root}/u/${item.to.url}" class="thumbnail" target="_blank">
                                    <img src="${root}/${item.to.photoSrc}" alt="暂无">
                                </a>
                                <div class="nickname-div">
                                    <a href="${root}/u/${item.to.url}" target="_blank">${item.to.nickname}</a>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane" id="fans">
                    <c:forEach items="${requestScope.fans}" var="item">
                        <li class="user-li">
                            <a href="${root}/u/${item.from.url}" class="thumbnail" target="_blank">
                                <img src="${root}/${item.from.photoSrc}" alt="暂无">
                            </a>
                            <div class="nickname-div">
                                <a href="${root}/u/${item.from.url}" target="_blank">${item.from.nickname}</a>
                            </div>
                        </li>
                    </c:forEach>
                </div>
                <div role="tabpanel" class="tab-pane" id="dynamic" ng-controller="DynamicCtrl as vm">
                    <c:if test="${sessionScope.user != null}">
                        <div class="col-xs-10 col-xs-offset-1 dynamic-item" ng-repeat="item in vm.data">
                            <a href="${root}/{{item.user.url}}" class="thumbnail" target="_blank">
                                <img src="${root}/{{item.user.photoSrc}}" alt="暂无">
                            </a>
                            <div class="dynamic-content">
                                <a href="${root}/u/{{item.user.url}}">我是张三</a>
                                <span data-ng-bind="item.operator"></span>
                                <a href="${root}/p/{{item.article.articleId}}" data-ng-bind="item.article.title"></a>
                                <span data-ng-bind="item.createTime | dateFormat"></span>
                                <c:if test="${sessionScope.user.userId == requestScope.user.userId}">
                                    <a href="javascript:void(0)" ng-click="vm.deleteDynamic(item.dynamicId)">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    </a>
                                </c:if>
                            </div>
                            <p data-ng-bind="item.content"></p>
                        </div>

                        <xl-page pageSize="15" n="5" method="load" cla="pagination-sm" ng-show="vm.totalPage>1"
                                 data="itemList" totalItem="totalItem" totalPage="totalPage"></xl-page>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <div id="tip">
                            注册用户登录后才能查看动态，请<a href="${root}/login">登录</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script src="${root}/resource/js/moment.js"></script>
<script src="${root}/resource/js/moment-with-locales.js"></script>
<script type="application/javascript">
    var obj = {
        attention: ${requestScope.attention != null},
        isLogin: function () {
            return '${sessionScope.user.userId}';
        },
        addOrCancelAttention: function () {
            if (this.isLogin()) {
                if (this.attention) {
                    this.cancelAttention();
                } else {
                    this.addAttention();
                }
                this.updateAttentionButton();
            } else {
                alert('请先登录');
                window.location = '${root}/login';
            }
        },
        addAttention: function () {
            $.post('${root}/user/attention', {
                from: '${sessionScope.user.userId}',
                to: '${requestScope.article.user.userId}'
            }, function (data) {
                if (data.success) {
                    window.obj.attention = true;
                } else {
                    alert(data.msg);
                }
            }, 'JSON')
        },
        cancelAttention: function () {
            $.ajax({
                url: '${root}/user/attention',
                type: 'DELETE',
                data: {
                    from: '${sessionScope.user.userId}',
                    to: '${requestScope.article.user.userId}'
                },
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        window.obj.attention = false;
                    } else {
                        alert(data.msg);
                    }
                }
            });
        },
        updateAttentionButton: function () {
            if (this.attention) {
                $('#attention-btn').text("取消关注");
            } else {
                $('#attention-btn').html("<span class='glyphicon glyphicon-plus' aria-hidden='true'></span>关注");
            }
        }
    };

    $(function () {
        moment.locale('zh-CN');
        obj.updateAttentionButton();

        var regTime = "<fmt:formatDate value='${requestScope.user.regTime}' pattern='yyyy-MM-dd HH:mm'/>";
        var ageText = moment(regTime, "YYYY-MM-DD").fromNow();
        var text = '博龄：' + ageText.substr(0, ageText.length - 1);
        $('#age').text(text);
    });

    /**
     * 最新动态
     */
    (function () {
        'use strict';

        angular.module('app', [])
                .controller('DynamicCtrl', DynamicCtrl);

        DynamicCtrl.$inject = ['$http'];

        function DynamicCtrl($http) {
            var vm = this;
            vm.init = false;

            // 加载动态
            vm.load = function (params, callback) {
                var url = "${root}/user/dynamics/page/" + params.page + '?userId=${requestScope.user.userId}';

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

            // 删除动态
            vm.deleteDynamic = function (id) {
                if (id) {
                    var r = confirm("确认删除该动态吗?");
                    if (r === true) {
                        $http.delete('${root}/user/dynamics/' + id)
                                .then(function (res) {
                                    if (res.data.success) {
                                        for (var i = 0; i < vm.data.length; i++) {
                                            if (vm.data[i].dynamicId === id) {
                                                vm.data.splice(i, 1);
                                            } else {
                                                alert(res.data.msg);
                                            }
                                        }
                                    }
                                });
                    }
                }
            };
        }
    })();
</script>
<script src="${root}/resource/js/filters.js"></script>
<script src="${root}/resource/js/page.js"></script>
</body>
</html>
