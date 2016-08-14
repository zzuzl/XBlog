<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>修改头像照片</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="/resource/css/info.css">
    <link href="/resource/cropper-master/dist/cropper.min.css" rel="stylesheet">
    <script src="/resource/cropper-master/dist/cropper.min.js"></script>
    <script src="/resource/js/ajaxfileupload.js"></script>
    <link rel="stylesheet" href="/resource/font-awesome-4.6.3/css/font-awesome.css">
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
            <button type="button" id="okBtn" disabled onclick="obj.savePhoto()" class="btn btn-primary">确定修改</button>
            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
                     style="width: 0;" id="process">
                    0%
                </div>
            </div>
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
<script src="https://www.gstatic.com/firebasejs/3.2.1/firebase.js"></script>
<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyCe0MCq1tfz7QHszSy0xJs_a-U4JWV1Qkc",
        authDomain: "xblog-90664.firebaseapp.com",
        databaseURL: "https://xblog-90664.firebaseio.com",
        storageBucket: "xblog-90664.appspot.com"
    };
    firebase.initializeApp(config);
</script>
<script type="application/javascript">

    var obj = {
        changeSrc: function (filename) {
            $('#image').cropper('replace', '/' + filename);
            this.filename = filename;
            $('#okBtn').removeAttr('disabled');
        },
        uploadFile: function () {
            $.ajaxFileUpload({
                url: '/file/upload',
                secureuri: false,
                fileElementId: 'file',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        obj.changeSrc(data.filename);
                    } else {
                        alert(data.msg);
                    }
                },
                error: function (data, status, e) {
                    alert('文件上传失败！' + e);
                }
            })
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
                // 组装blob，上传裁剪后的图片
                obj.getBlob(obj.filename);
            } else {
                alert(data.msg);
            }
        },
        getBlob: function (filename) {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/' + filename, true);
            xhr.responseType = 'blob';
            var contentType = obj.getContentType(filename);

            xhr.onload = function (e) {
                if (this.status == 200) {
                    // 组装成blob对象
                    var blob = new Blob([this.response], {type: contentType});
                    // firebase api 存储真正的图片
                    console.log(blob);
                    obj.uploadToFirebase(blob);
                }
            };
            xhr.send();
        },
        uploadToFirebase: function (blob) {
            var file = blob;
            var metadata = {
                contentType: blob.type
            };

            var path = "images/headPhotos/${sessionScope.user.userId}.jpg";
            var storageRef = firebase.storage().ref();
            var uploadTask = storageRef.child(path).put(file, metadata);

            // Listen for state changes, errors, and completion of the upload.
            uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED, // or 'state_changed'
                    function (snapshot) {
                        // Get task progress, including the number of bytes uploaded and the total number of bytes to be uploaded
                        var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                        obj.updateProcess(progress);
                    }, function (error) {

                    }, function () {
                        // Upload completed successfully, now we can get the download URL
                        var downloadURL = uploadTask.snapshot.downloadURL;
                        console.log(downloadURL);

                        // ajax保存下载链接
                        $.post('/user/modifyPhotoSrc', {
                            src: downloadURL
                        }, function (data) {
                            if (data.success) {
                                alert('头像保存成功');
                                window.location.reload();
                            } else {
                                alert(data.msg);
                            }
                        })
                    });
        },
        updateProcess: function (process) {
            $('#process').attr('aria-valuenow', process);
            $('#process').text(process + '%');
            $('#process').css('width', process + '%');
        },
        getContentType : function (filename) {
            var contentType = 'image/jpeg';
            var type = filename.substr(filename.lastIndexOf('.') + 1);
            if(type == 'gif' || type == 'GIF') {
                contentType = 'image/gif';
            } else if(type == 'png' || type == 'PNG') {
                contentType = 'image/png';
            } else if(type == 'bmp' || type == 'bmp') {
                contentType = 'image/bmp';
            }
            return contentType;
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
