<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${requestScope.user.nickname}的博客</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="${root}/resource/css/blog.css">
</head>
<body>
<div class="header" style="background: url('${root}/resource/images/body_bg.png')">
    <div class="blog-title" style="background: url('${root}/resource/images/title-yellow.png')">
        <h1><a href="${root}/${requestScope.user.url}">${requestScope.user.nickname}</a></h1>
        <h4>${requestScope.user.motto}</h4>
    </div>
    <div id="navigator">
        <ul class="navList">
            <li><a href="${root}/">XBlog</a></li>
            <li><a style="background: white;color: #2aabd2;" href="${root}/${requestScope.user.url}">首页</a></li>
        </ul>
    </div>
</div>
<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-xs-9">
            <c:forEach items="${list}" var="item">
                <div class="list-item">
                    <div class="row">
                        <div class="col-xs-3">
                            <div class="dateTitle">
                                <fmt:formatDate value="${item.postTime}" pattern="yyyy年MM月dd日"/>
                            </div>
                        </div>
                        <div class="col-xs-9 title">
                            <a href="${root}/p/${item.articleId}">${item.title}</a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 content">
                            <p>${item.description}</p>
                            <a href="${root}/p/${item.articleId}">查看全文</a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 count">
                            发表于：<span><fmt:formatDate value="${item.postTime}" pattern="yyyy-MM-dd HH:mm"/></span>
                            <span>${item.user.nickname}</span>
                            <span>阅读(${item.viewCount})</span>
                            <span>评论(${item.commentCount})</span>
                            <span>赞(${item.likeCount})</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="col-xs-3">
            <div class="panel panel-info">
                <div class="panel-heading">公告</div>
                <div class="panel-body">
                    <h5>昵称：${requestScope.user.nickname}</h5>
                    <h5 id="age">博龄：</h5>
                    <h5>粉丝：<a href="#">${requestScope.user.fansCount}</a></h5>
                    <h5>关注：<a href="#">${requestScope.user.attentionCount}</a></h5>
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

        var regTime = "<fmt:formatDate value='${requestScope.user.regTime}' pattern='yyyy-MM-dd HH:mm'/>";
        var ageText = moment(regTime, "YYYY-MM-DD").fromNow();
        var text = '博龄：' + ageText.substr(0, ageText.length - 1);
        $('#age').text(text);
    });
</script>
</body>
</html>
