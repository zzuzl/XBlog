<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>改变头像</title>
    <%@include file="common/head.jsp" %>
    <link href="${root}/resource/cropper-master/dist/cropper.min.css" rel="stylesheet">
    <script src="${root}/resource/cropper-master/dist/cropper.min.js"></script>
    <script src="${root}/resource/js/ajaxfileupload.js"></script>
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
            width: 500px;
            height: 500px;
        }
    </style>
</head>
<body>

<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-xs-7">
            <div id="box">
                <img id="image" src="${root}/resource/images/picture.jpg">
            </div>
        </div>
        <div class="col-xs-5">
            <div id="preview" class="thumbnail"></div>
            <input type="file" id="file" name="file" onchange="obj.uploadFile()"/>
            <button type="button" onclick="obj.savePhoto()" class="btn btn-primary">确定</button>
        </div>
    </div>
</div>

<div class="panel-footer">
    <div>
        <a href="#">关于XBlog</a>
        <a href="#">联系我们</a>©20016-2026
        <a href="#">XBlog</a>保留所有权利
        <a href="#" target="_blank">豫ICP备09004260号</a>
    </div>
</div>

<script type="application/javascript">
    var obj = {
        changeSrc: function (filename) {
            $('#image').cropper('replace','${root}/' + filename);
            this.filename = filename;
        },
        uploadFile: function () {
            $.ajaxFileUpload({
                url: '${root}/file/upload',
                secureuri: false,
                fileElementId: 'file',
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        obj.changeSrc(data.filename);
                    } else {
                        alert('文件上传失败！');
                    }
                },
                error: function (data, status, e) {
                    alert('文件上传失败！' + e);
                }
            })
        },
        savePhoto: function () {
            var data = $('#image').cropper('getData');
            $.post('${root}/file/changePhoto', {
                filename: obj.filename || '',
                x: data.x,
                y: data.y,
                width: data.width,
                height: data.height
            }, this.handleResult, 'JSON');
        },
        handleResult: function (data) {
            if (data.success) {
                alert('头像保存成功');
            } else {
                alert(data.msg);
            }
        }
    };

    $(function () {
        // 初始化cropper
        $('#image').cropper({
            aspectRatio: 1,
            crop: function (e) {

            }, preview: "#preview"
        });
    });
</script>
</body>
</html>
