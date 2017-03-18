<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="app">
<head>
    <title>${requestScope.user.nickname} 的个人空间</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/index.css">
    <link rel="stylesheet" href="/resource/css/pc.css">
    <script src="//cdn.bootcss.com/layer/3.0.1/layer.min.js"></script>
    <script src="//cdn.bootcss.com/angular.js/1.5.11/angular.min.js"></script>
</head>
<body>
<%@include file="common/title.jsp" %>

<div class="container-fluid" style="margin-top: 100px" id="container">
    <div class="row">
        <div class="col-xs-3 col-xs-offset-1">
            <a href="javascript:void(0)" class="thumbnail" id="photo-thumbnail">
                <img src="${requestScope.user.photoSrc}" alt="暂无"/>
            </a>
        </div>
        <div class="col-xs-7">
            <h3>${requestScope.user.nickname}</h3>
            <h4 id="age">博龄：2年</h4>
            <h5>博客：
                <a href="http://xblog.zzuzl.cn/${requestScope.user.url}">
                    http://xblog.zzuzl.cn/${requestScope.user.url}
                </a>
            </h5>
            <h4>${requestScope.user.motto}</h4>
            <h4>兴趣：${requestScope.user.interest}</h4>
            <c:if test="${sessionScope.user.userId != requestScope.user.userId}">
                <a href="javascript:void(0)" type="button" class="btn btn-info"
                   onclick="obj.addOrCancelAttention()" id="add-btn">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    关注
                </a>
                <a href="javascript:void(0)" type="button" class="btn btn-info"
                   onclick="obj.addOrCancelAttention()" id="cancel-btn">
                    取消关注
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
                                <a href="/u/${item.to.url}" class="thumbnail" target="_blank">
                                    <img src="${item.to.photoSrc}" alt="暂无">
                                </a>
                                <div class="nickname-div">
                                    <a href="/u/${item.to.url}" target="_blank">${item.to.nickname}</a>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane" id="fans">
                    <c:forEach items="${requestScope.fans}" var="item">
                        <li class="user-li">
                            <a href="/u/${item.from.url}" class="thumbnail" target="_blank">
                                <img src="${item.from.photoSrc}" alt="暂无">
                            </a>
                            <div class="nickname-div">
                                <a href="/u/${item.from.url}" target="_blank">${item.from.nickname}</a>
                            </div>
                        </li>
                    </c:forEach>
                </div>
                <div role="tabpanel" class="tab-pane" id="dynamic" ng-controller="DynamicCtrl as vm">
                    <c:if test="${sessionScope.user != null}">
                        <div class="col-xs-10 col-xs-offset-1 dynamic-item" ng-repeat="item in vm.data">
                            <a href="/{{item.user.url}}" class="thumbnail" target="_blank">
                                <img ng-src="{{item.user.photoSrc}}" alt="暂无"/>
                            </a>
                            <div class="dynamic-content">
                                <a href="/u/{{item.user.url}}" data-ng-bind="item.user.nickname"></a>
                                <span data-ng-bind="item.operator"></span>
                                <a href="/p/{{item.article.articleId}}">《{{item.article.title}}》</a>
                                <span data-ng-bind="item.createTime | dateFormat"></span>
                                <c:if test="${sessionScope.user.userId == requestScope.user.userId}">
                                    <a href="javascript:void(0)" ng-click="vm.deleteDynamic(item.dynamicId)">
                                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    </a>
                                </c:if>
                            </div>
                                <%-- ng-bind-html 用于解析html --%>
                            <p ng-bind-html="item.content | trustHtml"></p>
                        </div>

                        <xl-page pageSize="15" n="5" method="load" cla="pagination-sm" ng-show="vm.totalPage>1"
                                 data="itemList" totalItem="totalItem" totalPage="totalPage"></xl-page>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <div id="tip">
                            注册用户登录后才能查看动态，请<a href="/login">登录</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.user.userId == requestScope.user.userId}">
        <div class="row">
            <h3 align="center">我的站内消息</h3>
            <a id="msg"></a>
            <div class="col-xs-10 col-xs-offset-1" ng-controller="MessageCtrl as vm">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th width="30px"><input type="checkbox" ng-model="vm.selectAll" ng-change="vm.all()"/></th>
                        <th width="20px"></th>
                        <th width="60%">标题</th>
                        <th width="20%">时间</th>
                        <th width="*">类型</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="item in vm.data">
                        <td><input type="checkbox" name="ids" ng-model="vm.ids[$index]" ng-change="vm.checkAll()"/></td>
                        <td><span class="state-unread" ng-if="item.state===1">●</span></td>
                        <td><a href="javascript:void(0)" ng-click="vm.openDlg(item)" data-ng-bind="item.title"></a></td>
                        <td data-ng-bind="item.sendTime | date:'yyyy-MM-dd'"></td>
                        <td data-ng-bind="item.type | msgType"></td>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th colspan="2"></th>
                        <th colspan="2">
                            <button type="button" class="btn btn-default">删除</button>
                            <button type="button" class="btn btn-default">标记为已读</button>
                        </th>
                    </tr>
                    </tfoot>
                </table>
                <xl-page pageSize="10" n="5" method="load" cla="pagination-sm" ng-show="vm.totalPage>1"
                         data="list" totalItem="totalItem" totalPage="totalPage"></xl-page>
            </div>
        </div>
    </c:if>
</div>

<%@include file="common/footer.jsp" %>

<script src="//cdn.bootcss.com/moment.js/2.17.1/moment-with-locales.min.js"></script>
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
                layer.msg('请先登录');
                window.location = '/login';
            }
        },
        addAttention: function () {
            $.post('/user/attention', {
                from: '${sessionScope.user.userId}',
                to: '${requestScope.user.userId}'
            }, function (data) {
                if (data.success) {
                    window.obj.attention = true;
                    window.obj.updateAttentionButton();
                } else {
                    layer.msg(data.msg);
                }
            }, 'JSON')
        },
        cancelAttention: function () {
            $.ajax({
                url: '/user/attention?from=${sessionScope.user.userId}&to=${requestScope.user.userId}',
                type: 'DELETE',
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        window.obj.attention = false;
                        window.obj.updateAttentionButton();
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        },
        updateAttentionButton: function () {
            if (this.attention) {
                $('#add-btn').hide();
                $('#cancel-btn').show();
            } else {
                $('#add-btn').show();
                $('#cancel-btn').hide();
            }
        }
    };

    $(function () {
        moment.locale('zh-CN');
        obj.updateAttentionButton();

        var regTime = "<fmt:formatDate value='${requestScope.user.regTime}' pattern='yyyy-MM-dd HH:mm'/>";
        var ageText = moment(regTime, "YYYY-MM-DD HH:mm").fromNow();
        var text = '博龄：' + ageText.substr(0, ageText.length - 1);
        $('#age').text(text);
    });

    /**
     * 最新动态,站内信
     */
    (function () {
        'use strict';

        angular.module('app', [])
                .config(function ($httpProvider) {
                    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                    $httpProvider.defaults.transformRequest = function (obj) {
                        var str = [];
                        for (var p in obj) {
                            str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                        }
                        return str.join("&");
                    };
                    $httpProvider.defaults.headers.post = {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    };
                })
                .controller('DynamicCtrl', DynamicCtrl)
                .controller('MessageCtrl', MessageCtrl);

        DynamicCtrl.$inject = ['$http'];

        function DynamicCtrl($http) {
            var vm = this;
            vm.init = false;

            // 加载动态
            vm.load = function (params, callback) {
                var url = "/user/dynamics/page/" + params.page + '?userId=${requestScope.user.userId}';

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
                        $http.delete('/user/dynamics/' + id)
                                .then(function (res) {
                                    if (res.data.success) {
                                        for (var i = 0; i < vm.data.length; i++) {
                                            if (vm.data[i].dynamicId === id) {
                                                vm.data.splice(i, 1);
                                            }
                                        }
                                    } else {
                                        layer.msg(res.data.msg);
                                    }
                                });
                    }
                }
            };
        }

        MessageCtrl.$inject = ['$http'];

        function MessageCtrl($http) {
            var vm = this;
            vm.init = false;
            vm.selectAll = false;

            // 加载消息
            vm.load = function (params, callback) {
                var url = "/user/messages" + '?page=' + params.page;

                $http.get(url).then(function (res) {
                    if (callback) {
                        vm.ids = new Array(res.data.list.length);
                        callback(res.data);
                        if (!vm.init) {
                            vm.init = true;
                        }
                    } else {
                        vm.data = res.list;
                        vm.total = res.totalItem;
                        vm.totalPage = res.totalPage;
                    }
                });
            };

            // 消息信息
            vm.openDlg = function (item) {
                console.log(item);
                layer.open({
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['500px', '300px'], //宽高
                    content: item.content
                });

                if (item.state === 1) {
                    // ajax
                    $http.post('/user/updateMsgState?id=' + item.id + '&state=2').then(function (res) {
                        if (res.data.success) {
                            item.state = 2;
                        } else {
                            layer.msg(res.data.msg);
                        }
                    });
                }
            };

            // 全选或全不选消息
            vm.all = function () {
                if (vm.selectAll) {
                    vm.ids.fill(true);
                } else {
                    vm.ids.fill(false);
                }
            };

            // 检查是否全选
            vm.checkAll = function () {
                var on = 0, off = 0;
                for (var i = 0; i < vm.ids.length; i++) {
                    if (vm.ids[i] === true) {
                        on++;
                    } else {
                        off++;
                    }
                }

                if (on === vm.ids.length) {
                    vm.selectAll = true;
                } else {
                    vm.selectAll = false;
                }
            };

            // 获取选中的id
            vm.getSelected = function () {
                var arr = [];
                for (var i = 0; i < vm.ids.length; i++) {
                    if (vm.ids[i] === true) {
                        arr.push(vm.data[i].id);
                    }
                }

                return arr;
            };

            // TODO
            // 删除消息
            vm.deleteMsg = function () {
                var selected = vm.getSelected();
                if (selected.length > 0) {
                    var r = confirm("确认删除这些消息吗?");
                    if (r === true) {
                        $http.delete('/user/dynamics/' + id).then(function (res) {
                            if (res.data.success) {
                                for (var i = 0; i < vm.data.length; i++) {
                                    if (vm.data[i].dynamicId === id) {
                                        vm.data.splice(i, 1);
                                    }
                                }
                            } else {
                                layer.msg(res.data.msg);
                            }
                        });
                    }
                }
            };

            // TODO
            // 标记为已读
            vm.asReaded = function () {
                var selected = vm.getSelected();
                if (selected.length > 0) {
                    $http.post('/user/updateMsgState', {
                        ids: selected,
                        state: 2
                    }).then(function (res) {
                        if (res.data.success) {
                            for(var i=0;i<selected.length;i++) {
                                vm.data[i].state = 2;
                            }
                        } else {
                            layer.msg(res.data.msg);
                        }
                    });
                }
            };
        }
    })();
</script>
<script src="/resource/js/filters.js"></script>
<script src="/resource/js/page.js"></script>
</body>
</html>
