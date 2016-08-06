<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>注册页面</title>
    <%@include file="common/head.jsp" %>
    <script src="/resource/js/validator.min.js"></script>
    <style type="text/css">
        .form-control {
            width: 400px;
        }

        #div2 {
            padding-top: 30px;
        }

        #button1 {
            font-size: 23px;
            margin-top: 30px;
        }

        #captcha {
            width: 150px;
            float: left;
            margin-right: 15px;
        }

        #image {
            height: 34px;
        }

        #divv {
            margin-top: 10px;
            margin-left: 10px;
        }

        #error {
            display: none;
        }
    </style>
</head>
<body>

<div class="container" id="con">
    <div class="page-header" align="center" id="zc">
        <h1 id="zc1"></h1>
    </div>

    <div class="well" id="page">
        <form data-toggle="validator" role="form" id="form1">
            <div class="form-horizontal">
                <div class="form-group" id="div2">
                    <label for="email" class="col-sm-2 control-label">邮 箱</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Email" required data-remote="/user/exists">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="nickname" class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-10">
                        <input type="text" name="nickname" id="nickname" class="form-control" placeholder="昵称" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" data-minlength="6" class="form-control" id="password"
                               placeholder="密码" data-minlength-error="密码至少6位" required>
                        <div class="help-block with-errors"></div>
                    </div>

                </div>
                <div class="form-group">
                    <label for="password2" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password2" data-match="#password"
                               data-match-error="密码不匹配" placeholder="确认密码" required>
                        <div class="help-block with-errors"></div>
                    </div>

                </div>
                <div class="form-group">
                    <label for="captcha" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="captcha" data-remote="/captcha/verify"
                               id="captcha" required data-remote-error="验证码错误">
                        <img src="/captcha/generate" id="image">
                        <a href="javascript:void(0)" onclick="obj.updateCaptcha()">换一张</a>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" id="button1">注册</button>
                    </div>
                </div>
                <p class="bg-danger" id="error"></p>
            </div>
        </form>
    </div>
</div>
<div id="hidden" hidden>
    <div id="divv"><a href="/">首页</a></div>
    <div class="page-header" align="center">
        <h1></h1>
    </div>
    <div class="jumbotron container" id="div" align="center">
        <p><a href="/zc">请重新注册</a></p>
    </div>
</div>

</body>
<script>

    $(function () {
        $("#form1").validator({
            errors: {
                remote: 'email已被注册'
            }
        }).on('submit', function (e) {
            if (e.isDefaultPrevented()) {
                // handle the invalid form...
            } else {
                $("#button1").attr('disabled', 'disabled');
                $("#button1").text("注册中");
                obj.register();
            }
            return false;
        });
    });
    var obj = {
        url: "/user/register",
        register: function () {
            $.post(this.url, {
                nickname: $("#nickname").val(),
                email: $("#email").val(),
                password: $("#password").val(),
                captcha: $("#captcha").val()
            }, this.success, "JSON");
        },
        success: function (data) {
            if (data.success) {
                window.location = "/check";
            } else {
                $("#error").text(data.msg);
                $("#error").show();
                $("#button1").removeAttr('disabled');
                $("#button1").text("注册");
            }
        },
        updateCaptcha: function () {
            $('#image').attr('src', '/captcha/generate?t=' + new Date());
        }
    }
</script>
</html>
