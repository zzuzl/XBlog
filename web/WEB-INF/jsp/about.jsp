<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>关于</title>
    <%@include file="common/head.jsp" %>
    <style>

    </style>
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
                <li><a href="${root}/">主页</a></li>
                <li><a href="#">精华</a></li>
                <li class="active"><a href="${root}/about">关于</a></li>
            </ul>

            <div class="navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="photo-div">
                            <img src="${root}/${sessionScope.user.photoSrc}" width="32" height="32" id="photo_img">
                            <s></s>
                        </div>
                        <div class="user-div">
                            <div class="top-div">
                                <img src="${root}/resource/images/photo.jpg" width="100" height="100"/>
                                <div class="user-info">
                                    <h5>${sessionScope.user.nickname}</h5>
                                    <h6>${sessionScope.user.email}</h6>
                                </div>
                            </div>
                            <div class="bottom-div">
                                <div id="blog-btn" class="button">设置</div>
                                <div id="quit-btn" class="button" onclick="quit()">退出</div>
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

<div class="container" style="margin-top: 60px">
    <h2 id="title">
        关于
    </h2>
    <p>
        XBlog是一个简约、轻量级的开源多人博客系统，您可以在这里浏览文章，畅所欲言，和你的小伙伴一起愉快的学习知识，赶紧加入吧！
    </p>
    <h3>技术架构</h3>
    <h4>前端技术</h4>
    <ul>
        <li>html、css、javascript</li>
        <li><a href="https://jquery.com/" target="_blank">jquery</a></li>
        <li><a href="https://angularjs.org/" target="_blank">AngularJs</a></li>
    </ul>
    <h4>后端技术</h4>
    <ul>
        <li>JAVA WEB</li>
        <li><a href="https://projects.spring.io/spring-framework/" target="_blank">Spring</a></li>
        <li><a href="https://projects.spring.io/spring-framework/" target="_blank">SpringMVC</a></li>
        <li><a href="http://www.mybatis.org/mybatis-3/" target="_blank">Mybatis</a></li>
    </ul>
    <h4>数据库技术</h4>
    <ul>
        <li><a href="https://www.mysql.com/" target="_blank">MySQL</a></li>
        <li><a href="http://redis.io/" target="_blank">Redis</a></li>
    </ul>
    <h3>小组成员</h3>
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

</script>
</body>
</html>
