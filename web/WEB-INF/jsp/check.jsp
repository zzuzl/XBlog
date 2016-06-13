<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>邮件验证</title>
    <%@include file="common/head.jsp" %>
    <style type="text/css">
        #div {
            margin-top: 200px;
        }

        #zc {
            margin-top: 0;
            padding-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="page-header" align="center" id="zc">
    <h1 id="zc1"></h1>
</div>

<div class="jumbotron container" id="div">
    <div id="info">
        <h1>亲爱的用户，您好：</h1>
        <p>感谢你注册Blog，验证信息已发送至你的邮箱,请注意查收。</p>
    </div>
    <h3 id="msg"></h3>
    <a href="${root}/login" class="btn btn-primary">登录</a>
    <a href="${root}/" class="btn btn-primary">主页</a>
    <a href="${root}/register" class="btn btn-info">返回重新注册</a>
</div>

<script>
    $(function () {
        // 隐藏提示内容
        if('${requestScope.success}') {
            $('#info').hide();
            $('h3').text('${requestScope.msg}');
        }
    });
</script>
</body>
</html>
