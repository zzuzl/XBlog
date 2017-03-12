<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>关于</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/index.css">
    <style>
        .media-left img {
            height: 80px;
            width: 80px;
        }
    </style>
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
    <h4>其他</h4>
    <ul>
        <li><a href="https://lucene.apache.org/" target="_blank">Lucene</a></li>
        <li><a href="https://github.com/kindsoft/kindeditor" target="_blank">kindeditor</a></li>
        <li><a href="https://github.com/coobird/thumbnailator" target="_blank">thumbnailator</a></li>
        <li><a href="https://github.com/fengyuanchen/cropper" target="_blank">cropper</a></li>
    </ul>
    <h3>小组成员</h3>
    <div class="media">
        <div class="media-left">
            <a href="#">
                <img class="media-object" src="resource/images/default-head-photo.png" alt="...">
            </a>
        </div>
        <div class="media-body">
            <p>
                说点什么
            </p>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp"%>

<script type="application/javascript">
    $(function () {
        $('#about-li').addClass('active');
    });
</script>
</body>
</html>
