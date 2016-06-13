<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>关于</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="${root}/resource/css/index.css">
</head>
<body>
<%@include file="common/title.jsp"%>

<div class="container" style="margin-top: 60px" id="container">
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

<%@include file="common/footer.jsp"%>

<script type="application/javascript">
    $(function () {
        $('#about-li').addClass('active');
    });
</script>
</body>
</html>
