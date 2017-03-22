<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>我收藏的文章</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/info.css">
    <script src="/resource/js/validator.min.js"></script>
    <style>
        .list-item {
            width: 100%;
            min-height: 80px;
            padding: 10px;
            background: white;
            border: 1px solid #c0c0c0;
            margin: -1px;
            z-index: 1;
        }

        .list-item .dateTitle {
            width: 100%;
            background: #2aabd2;
            color: white;
            text-align: center;
            height: 20px;
            line-height: 20px;
            padding-left: 15px;
            padding-right: 15px;
        }

        .list-item .title {
            font-size: 15px;
            color: black;
            text-decoration: none;
        }

        .list-item .content {
            margin-top: 3px;
            font-size: 13px;
        }

        .list-item .count {
            color: #CCCCCC;
            font-size: 13px;
        }
    </style>
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
                            <span class="label label-primary" style="display:inline-block;width: 100%">
                                <fmt:formatDate value="${item.postTime}" pattern="yyyy年MM月dd日"/>
                            </span>
                        </div>
                        <div class="col-xs-8 title">
                            <a href="/p/${item.articleId}">${item.title}</a>
                        </div>
                        <div class="col-xs-1 operate">
                            <a href="/setting/editArticle/${item.articleId}">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            </a>
                            <a href="javascript:void(0)" onclick="obj.deleteArticle('${item.articleId}')">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 content">
                            <p>${item.description}</p>
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
        $('#collect-item').addClass('active');
    });

    var obj = {
        deleteArticle: function (id) {
            var r = confirm("确定要删除吗？");
            if (r === true) {
                $.ajax({
                    url: '/article?id=' + id,
                    type: 'DELETE',
                    dataType: 'JSON',
                    success: function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }
        }
    };
</script>
</body>
</html>
