<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 兼容ie8 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="renderer" content="webkit">

<!--[if lt IE 9]>
<script src="/resource/js/html5.js"></script>
<script src="/resource/js/respond.min.js"></script>
<![endif]-->

<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resource/css/common.css">
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="Shortcut Icon" href=/resource/favicon.ico>
<script src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="//cdn.bootcss.com/layer/3.0.1/layer.min.js"></script>
<script type="application/javascript">
    $(function () {
        var height = window.innerHeight;

        $('#container').css('min-height', height + 'px');

        // ajax send
        $(document).ajaxSend(function (evt, request, settings) {
            // 风格3
            layer.load(2);
            // 风格4
            // layer.msg('处理中', {icon: 16, shade: 0.01});
        });

        // ajax complete
        $(document).ajaxComplete(function () {
            layer.closeAll('loading');
        });

        // 统一处理error
        $(document).ajaxError(function (event, request, settings) {
            var res = request.responseJSON;
            switch (res.code) {
                case 400:
                    layer.msg(res.message);
                    break;
                case 401:
                    layer.msg(res.message, {
                        end: function () {
                            window.location = '/login?returnUrl=' + window.location.href;
                        }
                    });
                    break;
                case 403:
                    layer.msg(res.message);
                    break;
                case 500:
                    layer.msg(res.message);
                    break;
            }
        });
    });
</script>