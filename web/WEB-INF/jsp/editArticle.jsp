<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发表博客</title>
    <%@include file="common/head.jsp" %>
    <link href="${root}/resource/kindeditor/themes/default/default.css" rel="stylesheet"/>
    <link href="${root}/resource/kindeditor/themes/simple/simple.css" rel="stylesheet"/>
    <script src="${root}/resource/kindeditor/kindeditor-all-min.js"></script>
    <script src="${root}/resource/kindeditor/lang/zh-CN.js"></script>
    <script>
        var editorConfig = {
            width: '100%',
            height: '700px',
            minWidth: '700px',
            minHeight: '500px',
            items: [
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about'
            ],
            langType: 'zh-CN',
            themeType: 'simple',
            uploadJson: '${root}/file/uploadInArticle',
            afterChange: function () {
                var allCount = this.count();
                $("#contentLength").text("字数：" + allCount + "/" + 30000);
            }
        };

        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', editorConfig);
        });
    </script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${root}/">XBlog</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="${root}/">主页</a></li>
                <li><a href="#">精华</a></li>
                <li class="active"><a href="${root}/about">关于</a></li>
            </ul>

            <div class="navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="photo-div">
                            <img src="${root}/${sessionScope.user.photoSrc}" width="32" height="32" id="photo_img">
                            <s></s>
                        </div>
                        <div class="user-div">
                            <div class="top-div">
                                <img src="${root}/resource/images/photo.jpg" width="100" height="100"/>
                                <div class="user-info">
                                    <h5>${sessionScope.user.nickname}</h5>
                                    <h6>${sessionScope.user.email}</h6>
                                </div>
                            </div>
                            <div class="bottom-div">
                                <div id="blog-btn" class="button">设置</div>
                                <div id="quit-btn" class="button" onclick="quit()">退出</div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-default navbar-btn">登录</button>
                        <a href="#">注册</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>

<div class="container" style="margin-top: 60px">
    <div class="row">
        <div class="col-xs-12">
            <input class="form-control" type="text" id="title" placeholder="输入文章标题（50字以内）">
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <textarea id="editor_id" name="content" cols="100" rows="8"></textarea>
            <span id="contentLength"></span>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <input type="text" class="form-control" id="tag" placeholder="输入文章标签，以‘#’分隔开（100字以内）"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h3>文章分类</h3>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <textarea id="description" placeholder="输入文章描述（300字以内）" style="width: 100%" rows="3"></textarea>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-1 col-xs-offset-3">
            <button type="button" onclick="obj.postArticle()" class="btn btn-primary">发表文章</button>
        </div>
        <div class="col-xs-1">
            <button type="button" class="btn btn-primary">立即保存</button>
        </div>
        <div class="col-xs-1">
            <button type="button" class="btn btn-primary">舍弃</button>
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
        postArticle: function () {
            $.post('${root}/article', {
                "category.cateId": 2,
                "title": $("#title").val(),
                "description": $('#description').val(),
                "content": window.editor.html(),
                "user.userId": 1,
                "tag": $('#tag').val()
            }, this.success, "JSON")
        },
        success: function (data) {
            if (data.success) {
                window.location = "${root}/";
            } else {
                alert(data.msg);
            }
        }
    };
</script>
</body>
</html>
