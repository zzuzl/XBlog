<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
    <%@include file="common/head.jsp" %>
    <script src="${root}/resource/js/validator.min.js"></script>

    <style type="text/css">
        #hd_nav {
            float: left;
        }

        #login_area {
            float: right;
        }

        #hd {
            height: 20px;
            background: #000000;
        }

        .form-control {
            width: 400px;
        }

        #div2 {
            padding-top: 30px;
        }

        #button1.btn {
            font-size: 23px;
            margin-top: 30px;
        }

        #captcha.form-control {
            width: 200px;
            margin-bottom: 20px;
        }

        #image {
            width: 140px;
            height: 30px;
        }

        #divv {
            margin-top: 10px;
            margin-left: 10px;
        }
    </style>
</head>
<body>

<div class="container" id="con">
    <div class="page-header" align="center" id="zc">
        <h1 id="zc1"></h1>
    </div>

    <div id="page">
        <form data-toggle="validator" role="form" id="form1">
            <div class="form-horizontal">
                <div class="form-group" id="div2">
                    <label for="inputEmail3" class="col-sm-2 control-label">邮 箱</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" id="inputEmail3" name="email"
                               placeholder="Email" required data-remote="${root}/user/exists">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
            </div>

            <div class="form-horizontal">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-10">
                        <input type="text" name="nickname" id="nickname" class="form-control" placeholder="username">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" data-minlength="6" class="form-control" id="inputPassword3"
                               placeholder="Password" required>
                        <div class="help-block with-errors"></div>
                    </div>

                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword4" data-match="#inputPassword3"
                               data-match-error="密码不匹配" placeholder="Password" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-10" id="float">
                        <input type="text" data-minlength="4" class="form-control" id="captcha"
                               required>
                        <div class="help-block with-errors"></div>
                        <img src="${root}/captcha/generate" id="image">
                        <a href="javascript:void(0)" onclick="obj.updateCaptcha()">换一张</a>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" id="button1">注册</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<div id="hidden" hidden>
    <div id="divv"><a href="${root}/">首页</a></div>
    <div class="page-header" align="center">
        <h1></h1>
    </div>
    <div class="jumbotron container" id="div" align="center">
        <p><a href="${root}/zc">请重新注册</a></p>
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
            console.log(e);
            if (e.isDefaultPrevented()) {
                // handle the invalid form...
            } else {
                $("#login-btn").attr('disabled', 'disabled');
                $("#login-btn").text("注册中");
                obj.register();
            }
            return false;
        });

    });
    var obj = {
        url: "${root}/user/register",
        register: function () {
            $.post(this.url, {
                nickname: $("#nickname").val(),
                email: $("#inputEmail3").val(),
                password: $("#inputPassword3").val(),
                captcha: $("#captcha").val()
            }, this.success, "JSON");
        },
        success: function (data) {
            console.log(data);
            if (data.success) {
                window.location = "${root}/check";
            } else {
                $("#error2").text(data.msg);
                $("#error2").show();
                $("#button1").removeAttr('disabled');
                $("#button1").text("注册");
                $("#con").hide();
                $("#hidden").show();
            }
        },
        updateCaptcha: function () {
            $('#image').attr('src', '${root}/captcha/generate?t=' + new Date());
        }
    }

</script>
</html>
