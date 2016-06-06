<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>XBlog</title>
    <script src="${root}/resource/js/jquery-1.11.2.js"></script>
    <script src="${root}/resource/js/ajaxfileupload.js"></script>
</head>
<body>
<form enctype="multipart/form-data">
    <input id="file" type="file" name="file">
</form>

<script type="application/javascript">
    var uploader = {
        url: '${root}/file/upload',
        fileElementId: 'file',
        dataType: 'json',
        upload: function () {
            $.ajaxFileUpload({
                url: this.url,
                secureuri: false,
                fileElementId: this.fileElementId,        //file的id
                dataType: this.dataType,                  //返回数据类型为json
                success: function (data) {
                    console.log(data);
                },
                error: function (data, status, e) {
                    alert('上传失败！');
                }
            })
        }
    };

    $(function () {
        $("#file").change(function () {
            uploader.upload();
        });
    });
</script>
</body>
</html>
