<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>修改头像照片</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/info.css">
    <link href="http://xblog-mis.oss-cn-shanghai.aliyuncs.com/cropper-master/dist/cropper.min.css" rel="stylesheet">
    <script src="http://xblog-mis.oss-cn-shanghai.aliyuncs.com/cropper-master/dist/cropper.min.js"></script>
    <script src="http://xblog-mis.oss-cn-shanghai.aliyuncs.com/ajaxfileupload.js"></script>
    <script src="//cdn.bootcss.com/layer/3.0.1/layer.min.js"></script>
    <style>
        img {
            max-width: 100%;
        }

        #preview {
            width: 200px;
            height: 200px;
            overflow: hidden;
        }

        #box {
            width: 100%;
        }

        #okBtn {
            width: 150px;
            margin: 20px;
        }

        ol {
            padding: 0;
            color: rebeccapurple;
        }

        #label {
            color: firebrick;
        }
    </style>
</head>
<body>
<%@include file="../common/title.jsp" %>

<div class="container" style="margin-top: 80px" id="container">
    <div class="row">
        <div class="col-xs-3">
            <%@include file="../common/leftList.jsp" %>
        </div>
        <div class="col-xs-6">
            <div id="box">
                <img id="image" src="/resource/images/picture.jpg">
            </div>
        </div>
        <div class="col-xs-3">
            <div id="preview" class="thumbnail"></div>
            <input type="file" id="file" name="file" onchange="obj.uploadFile()"/>
            <label id="label"></label>
            <button type="button" id="okBtn" disabled onclick="obj.savePhoto()" class="btn btn-primary">确定修改</button>
            <ol>
                <li>点击选择图片</li>
                <li>图片变化后拖动选框选择合适位置</li>
                <li>点击确定修改</li>
                <li>上传图片仅支持.gif,.jpg,.png,.bmp</li>
                <li>上传图片大小不超过1MB</li>
            </ol>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>

<script type="application/javascript">
    var lastPicName = "";
    updateLabel(lastPicName);

    // 更新label的值
    function updateLabel(name) {
        lastPicName = name;
        $('#label').text('当前已选图片：' + lastPicName);
    }

    var obj = {
        changeSrc: function (filename) {
            $('#image').cropper('replace', '/' + filename);
            this.filename = filename;
            $('#okBtn').removeAttr('disabled');
        },
        uploadFile: function () {
            var f = document.getElementById("file").files;

            if (f[0].type.length > 0 && f[0].type.indexOf('image') >= 0) {
                if (f[0].size > 1024 * 1024) {
                    layer.msg('文件过大，不能超过1M');
                } else {
                    updateLabel(f[0].name);

                    $.ajaxFileUpload({
                        url: '/file/upload',
                        secureuri: false,
                        fileElementId: 'file',
                        dataType: 'json',
                        success: function (data) {
                            if (data.success) {
                                obj.changeSrc(data.filename);
                            } else {
                                layer.msg(data.msg);
                            }
                        },
                        error: function (data, status, e) {
                            layer.msg('文件上传失败！' + e);
                        }
                    })
                }
            } else {
                layer.msg('请上传图片!');
            }
        },
        savePhoto: function () {
            var data = $('#image').cropper('getData');
            $.post('/file/changePhoto', {
                filename: obj.filename || '',
                x: data.x,
                y: data.y,
                width: data.width,
                height: data.height
            }, this.handleResult, 'JSON');
        },
        handleResult: function (data) {
            if (data.success) {
                layer.msg('头像保存成功');
                window.location.reload();
            } else {
                layer.msg(data.msg);
            }
        }
    };

    $(function () {
        $('#photo-item').addClass('active');
        // 初始化cropper
        $('#image').cropper({
            aspectRatio: 1,
            crop: function (e) {

            },
            preview: "#preview"
        });

        $(window).resize(function () {
            $('#box').height($('#box').width() + 'px');
        });

        $('#box').height($('#box').width() + 'px');
    });
</script>
</body>
</html>
