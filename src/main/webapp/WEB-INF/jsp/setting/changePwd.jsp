<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>修改登录密码</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/info.css">
    <script src="/resource/js/validator.min.js"></script>
</head>
<body>
<%@include file="../common/title.jsp" %>

<div class="container" style="margin-top: 80px" id="container">
    <div class="row">
        <div class="col-xs-3">
            <%@include file="../common/leftList.jsp" %>
        </div>
        <div class="col-xs-4 col-xs-offset-1" style="margin-top: 10px">
            <form id="form" role="form" novalidate>
                <div class="form-group">
                    <label for="originalPassword">原密码</label>
                    <input type="password" class="form-control" data-minlength="6" required id="originalPassword"/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label for="originalPassword">新密码</label>
                    <input type="password" class="form-control" data-minlength="6" data-maxlength="32" required
                           id="newPassword"/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label for="originalPassword">重复新密码</label>
                    <input type="password" class="form-control" required data-match="#newPassword" id="repeatPassword"/>
                    <div class="help-block with-errors"></div>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 200px;margin: 0 auto;display: block">
                    保存修改
                </button>
            </form>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>
<script type="application/javascript">
    $(function () {
        $('#pwd-item').addClass('active');

        $('#form').validator({
            errors: {
                match: "两次输入不一致"
            }
        }).on('submit', function (e) {
            if (e.isDefaultPrevented()) {
                // handle the invalid form...
            } else {
                obj.changePwd();
            }

            return false;
        });

        // 发送put请求时，必须使用标准json格式，加双引号
        var obj = {
            changePwd: function () {
                $.ajax('/user/changePwd', {
                    data: {
                        originalPassword: $('#originalPassword').val(),
                        newPassword: $('#newPassword').val()
                    },
                    dataType: 'JSON',
                    type: 'PUT',
                    success: function (data) {
                        if (data.success) {
                            alert('修改成功');
                            window.location.reload();
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        };
    });
</script>
</body>
</html>
