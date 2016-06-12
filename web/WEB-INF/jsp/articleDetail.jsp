<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${requestScope.article.title} - XBlog</title>
    <%@include file="common/head.jsp" %>
    <link href="${root}/resource/kindeditor/themes/default/default.css" rel="stylesheet"/>
    <link href="${root}/resource/kindeditor/themes/simple/simple.css" rel="stylesheet"/>
    <script src="${root}/resource/kindeditor/kindeditor-all-min.js"></script>
    <script src="${root}/resource/kindeditor/lang/zh-CN.js"></script>
    <link rel="stylesheet" href="${root}/resource/css/blog.css">
    <link href="${root}/resource/css/index.css" rel="stylesheet"/>
    <link href="${root}/resource/css/detail.css" rel="stylesheet"/>
    <script>
        var editorConfig = {
            width: '100%',
            height: '100px',
            minHeight: '50px',
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'link'
            ],
            langType: 'zh-CN',
            themeType: 'simple'
        };

        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', editorConfig);
        });
    </script>
</head>
<body>
<div class="header" style="background: url('${root}/resource/images/body_bg.png')">
    <div class="blog-title" style="background: url('${root}/resource/images/title-yellow.png')">
        <h1><a href="${root}/${requestScope.article.user.url}">${requestScope.article.user.nickname}</a></h1>
        <h4>${requestScope.article.user.motto}</h4>
    </div>
    <div id="navigator">
        <ul class="navList">
            <li><a href="${root}/">XBlog</a></li>
            <li><a id="index-li" href="${root}/${requestScope.article.user.url}">首页</a></li>
        </ul>
    </div>
</div>
<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-xs-9">
            <div class="row">
                <div class="col-xs-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <%-- 放文章内容 --%>
                            ${requestScope.article.content}
                            <h6 class="dash-h6"></h6>
                            <h4>分类：${requestScope.article.category.title}</h4>
                            <h4>标签：${requestScope.article.tag}</h4>
                            <div class="row">
                                <div class="col-xs-2">
                                    <a href="${root}/${requestScope.article.user.url}" class="thumbnail">
                                        <img src="${root}/${requestScope.article.user.photoSrc}" alt="暂无">
                                    </a>
                                </div>
                                <div class="col-xs-2">
                                    <h5><a href="#">${requestScope.article.user.nickname}</a></h5>
                                    <h5><a href="#">关注：${requestScope.article.user.attentionCount}</a></h5>
                                    <h5><a href="#">粉丝：${requestScope.article.user.fansCount}</a></h5>
                                </div>
                                <div class="col-xs-2">
                                    <a type="button" href="#" class="btn btn-danger gz">关注我</a>
                                </div>
                                <div class="col-xs-1 col-xs-offset-4">
                                    <a type="button" href="#" class="btn btn-info gz">推荐</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6">
                                    <span>上一篇：</span>
                                    <a href="#">高性能滚动 scroll 及页面渲染优化</a>
                                </div>
                                <div class="col-xs-6" style="text-align: right">
                                    <span>下一篇：</span>
                                    <a href="#">高性能滚动 scroll 及页面渲染优化</a>
                                </div>
                            </div>
                            <h6 class="dash-h6">
                                <a href="${root}/${requestScope.article.user.url}" target="_blank">
                                    ${requestScope.article.user.nickname}
                                </a>
                                发表于：
                                <fmt:formatDate value="${requestScope.article.postTime}"
                                                pattern="yyyy-MM-dd HH:mm"/>
                                <span>阅读(${requestScope.article.viewCount})</span>
                                <span>评论(${requestScope.article.commentCount})</span>
                            </h6>
                            <div class="row">
                                <div class="col-xs-12">
                                    <%-- 放评论 --%>
                                    <label>评论列表</label>
                                    <c:forEach items="${requestScope.comments}" var="item">
                                        <div class="comment-item">
                                            <div class="title">
                                                <span class="time">
                                                    <fmt:formatDate value="${item.postTime}"
                                                                    pattern="yyyy-MM-dd HH:mm"/>
                                                </span>
                                                <span class="name">
                                                    <a href="${root}/${item.user.url}">${item.user.nickname}</a>
                                                </span>
                                            </div>
                                            <div class="content">
                                                <c:if test="${item.pId > 0}">
                                                    <h6>
                                                        回复
                                                        <a href="${root}/${item.parent.user.url}">${item.parent.user.nickname}</a>
                                                        :
                                                    </h6>
                                                </c:if>
                                                <p>${item.content}</p>
                                                <a href="#comment"
                                                   onclick="obj.reply('${item.user.nickname}','${item.commentId}')">
                                                    回复
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <a id="comment"></a>
                                    <div class="comment-area">
                                        <div class="click-close">
                                            回复：
                                            <span class="text">张三</span>
                                            <button type="button" class="close" aria-label="Close"
                                                    onclick="obj.close()">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <textarea id="editor_id" name="content" cols="100" rows="8"></textarea>
                                        <button type="button" class="btn btn-default btn-sm"
                                                onclick="obj.postComment()">提交评论
                                        </button>
                                    </div>
                                    <h5 id="tips">
                                        注册用户登录后才能发表评论，请
                                        <a href="${root}/login">登录</a>
                                        或
                                        <a href="${root}/register">注册</a>
                                        ，访问
                                        <a href="${root}/">网站首页</a>
                                        。
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="panel panel-info">
                <div class="panel-heading">公告</div>
                <div class="panel-body">
                    <h5>昵称：${requestScope.article.user.nickname}</h5>
                    <h5 id="age">博龄：</h5>
                    <h5>粉丝：<a href="#">${requestScope.article.user.fansCount}</a></h5>
                    <h5>关注：<a href="#">${requestScope.article.user.attentionCount}</a></h5>
                    <h5><a href="#">+加关注</a></h5>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script src="${root}/resource/js/moment.js"></script>
<script src="${root}/resource/js/moment-with-locales.js"></script>
<script type="application/javascript">
    $(function () {
        moment.locale('zh-CN');

        var regTime = "<fmt:formatDate value='${requestScope.article.user.regTime}' pattern='yyyy-MM-dd HH:mm'/>";
        var ageText = moment(regTime, "YYYY-MM-DD").fromNow();
        var text = '博龄：' + ageText.substr(0, ageText.length - 1);
        $('#age').text(text);

        if (!'${sessionScope.user.userId}') {
            $('.comment-area').hide();
            $('#tips').show();
        }
    });

    var obj = {
        check: function () {
            return '${sessionScope.user.userId}' && window.editor.html().length > 0;
        },
        postComment: function () {
            if (this.check()) {
                $.post('${root}/comment', {
                    'article.articleId': '${requestScope.article.articleId}',
                    'user.userId': '${sessionScope.user.userId}',
                    'content': window.editor.html(),
                    'pId': obj.pId
                }, this.success, 'JSON');
            }
        },
        success: function (data) {
            if (data.success) {
                window.location.reload();
            } else {
                alert(data.msg);
            }
        },
        reply: function (nickname, pId) {
            this.pId = pId;
            $('.click-close .text').text(nickname);
            $('.click-close').show();
        },
        close: function () {
            $('.click-close').hide();
            this.pId = 0;
        },
        pId: 0
    };
</script>
</body>
</html>
