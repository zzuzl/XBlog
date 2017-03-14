<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>找回密码</title>
    <%@include file="common/head.jsp" %>
    <script src="/resource/js/validator.min.js"></script>
    <style type="text/css">
        #panel {
            width: 500px;
            height: 350px;
            padding: 50px;
            margin: 0 auto;
            margin-top: 200px;
        }

        #panel .panel-heading {
            text-align: center;
        }

        #panel .panel-body {
            background: #CCCCCC;
            border-radius: 5px;
        }

        #button, #button2 {
            width: 100%;
        }

        #captcha, #captcha2 {
            width: 150px;
            float: left;
            display: inline-block;
        }

        #image, #image2 {
            height: 34px;
            margin-left: 15px;
            margin-right: 5px;
        }

        #form-hidden {
            display: none;
        }

        #error {
            display: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-6 col-xs-offset-3">
            <div class="panel panel-primary" id="panel">
                <div class="panel-heading">
                    找回密码
                </div>
                <div class="panel-body">
                    <form role="form" id="form" novalidate>
                        <div class="form-group">
                            <input type="email" id="email" class="form-control" placeholder="输入注册的Email" required/>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <input type="text" id="captcha" class="form-control" name="captcha"
                                   data-remote="/captcha/verify" placeholder="验证码" required/>
                            <img src="/captcha/generate" id="image"/>
                            <a href="javascript:void(0)" onclick="obj.changeCaptcha('#image')">换一张</a>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-info" id="button">找回密码</button>
                        </div>
                    </form>

                    <form role="form" id="form-hidden" novalidate>
                        <div class="form-group">
                            <input type="password" id="password" class="form-control"
                                   placeholder="输入新密码" required data-minlength="6" data-maxlength="32"/>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <input type="text" id="captcha2" class="form-control" name="captcha"
                                   data-remote="/captcha/verify" placeholder="验证码" required/>
                            <img src="/captcha/generate" id="image2"/>
                            <a href="javascript:void(0)" onclick="obj.changeCaptcha('#image2')">换一张</a>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-info" id="button2">重置密码</button>
                        </div>
                    </form>
                    <div class="alert alert-danger" id="error"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('#form').validator({
            errors: {
                remote: '验证码错误'
            }
        }).on('submit', function (e) {
            if (e.isDefaultPrevented()) {
                // handle the invalid form...
            } else {
                $("#button").text("正在发送邮件...");
                $("#button").attr('disabled', 'disabled');
                obj.find();
            }

            return false;
        });

        if (${requestScope.hash != null}) {
            if (${requestScope.success != null && requestScope.success}) {
                $('.panel-heading').text('重置密码');
                $('#form').hide();
                $('#form-hidden').show();

                $('#form-hidden').validator({
                    errors: {
                        remote: '验证码错误'
                    }
                }).on('submit', function (e) {
                    if (e.isDefaultPrevented()) {
                        // handle the invalid form...
                    } else {
                        $("#button2").attr('disabled', 'disabled');
                        $('#form-hidden').hide();
                        obj.reset();
                    }

                    return false;
                });
            } else {
                $('#form').text('${requestScope.msg}');
            }
        }
    });

    var obj = {
        url: "/mail/send/resetpwd",
        find: function () {
            $.post(this.url, {
                email: $('#email').val(),
                captcha: $('#captcha').val()
            }, this.success, "JSON");
        },
        success: function (data) {
            if (data.success) {
                $('#form').text(data.msg);
                $('#error').hide();
            } else {
                $('#error').show();
                $('#error').text(data.msg);
                $("#button").removeAttr('disabled');
                $("#button").text("找回密码");
            }
        },
        reset: function () {
            $.ajax('/user/resetPwd', {
                data: {
                    password: $('#password').val(),
                    captcha: $('#captcha2').val(),
                    hash: '${requestScope.hash}'
                },
                dataType: 'JSON',
                type: 'PUT',
                success: function (data) {
                    if (data.success) {
                        alert('密码重置成功');
                        window.location = '/login';
                    } else {
                        $('#error').show();
                        $('#error').text(data.msg);
                        $('#form-hidden').show();
                        $("#button2").removeAttr('disabled');
                    }
                }
            });
        },
        changeCaptcha: function (id) {
            $(id).attr('src', '/captcha/generate?t=' + new Date().getTime());
        }
    };
</script>
</body>
</html>
