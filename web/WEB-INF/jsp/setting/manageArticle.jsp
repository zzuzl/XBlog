<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>管理我的博客</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="${root}/resource/css/info.css">
    <link rel="stylesheet" href="${root}/resource/css/blog.css">
    <script src="${root}/resource/js/validator.min.js"></script>
</head>
<body>
<%@include file="../common/title.jsp" %>

<div class="container" style="margin-top: 80px" id="container">
    <div class="row">
        <div class="col-xs-3">
            <%@include file="../common/leftList.jsp" %>
        </div>
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
    </div>
</div>

<%@include file="../common/footer.jsp" %>
<script type="application/javascript">
    $(function () {
        $('#manage-item').addClass('active');
    });
</script>
</body>
</html>
