<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<%-- 兼容ie8 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="renderer" content="webkit">

<!--[if lt IE 9]>
<script src="${root}/resource/js/html5.js"></script>
<script src="${root}/resource/js/respond.min.js"></script>
<![endif]-->

<link rel="stylesheet" href="${root}/resource/bower_components/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet" href="${root}/resource/bower_components/bootstrap/dist/css/bootstrap-theme.css">
<link rel="stylesheet" href="${root}/resource/bower_components/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${root}/resource/css/common.css">
<link rel="stylesheet" href="${root}/resource/css/index.css">
<script type="application/javascript" src="${root}/resource/bower_components/jquery/dist/jquery.js"></script>
<script type="application/javascript" src="${root}/resource/bower_components/bootstrap/dist/js/bootstrap.js"></script>

<script type="application/javascript">
    // 适配页面高度
    $(function () {
        //浏览器当前窗口可视区域高度
        var height = window.innerHeight;

        $('#container').css('min-height', height + 'px');
    });
</script>