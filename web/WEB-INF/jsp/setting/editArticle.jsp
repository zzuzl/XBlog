<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>编辑文章</title>
    <%@include file="../common/head.jsp" %>
    <link href="/resource/kindeditor/themes/default/default.css" rel="stylesheet"/>
    <link href="/resource/kindeditor/themes/simple/simple.css" rel="stylesheet"/>
    <script src="/resource/kindeditor/kindeditor-all-min-custom.js"></script>
    <script src="/resource/kindeditor/lang/zh-CN.js"></script>
    <script src="/resource/js/validator.min.js"></script>
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
    <script>
        var editorConfig = {
            width: '100%',
            height: '500px',
            minWidth: '700px',
            minHeight: '300px',
            items: [
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', //'multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about'
            ],
            langType: 'zh-CN',
            themeType: 'simple',
            // 改为上传文件到firebase
            uploadJson: '/file/uploadInArticle',
            afterChange: function () {
                var allCount = this.count();
                $("#contentLength").text("字数：" + allCount + "/" + 30000);
            }
        };

        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', editorConfig);
        });
    </script>

    <style>
        .ke-icon-hello {
            background: red;
            background-position: 0px -672px;
            width: 16px;
            height: 16px;
        }
    </style>
    <link rel="stylesheet" href="/resource/css/info.css">
</head>
<body>
<%@include file="../common/title.jsp" %>

<div class="container" style="margin-top: 80px" id="container">
    <div class="row">
        <div class="col-xs-3">
            <%@include file="../common/leftList.jsp" %>
        </div>
        <div class="col-xs-9" style="margin-top: 10px">
            <form role="form" id="form" novalidate>
                <div class="form-group">
                    <label class="title-label">博客标题</label>
                    <input class="form-control" type="text" name="title" id="title"
                           value="${requestScope.article.title}"
                           placeholder="输入文章标题（50字以内）" data-maxlength="50" required>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <label class="title-label">博客内容</label>
                        <textarea id="editor_id" name="content" cols="100"
                                  rows="8">${requestScope.article.content}</textarea>
                        <span id="contentLength"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="title-label">博客标签</label>
                    <input type="text" class="form-control" id="tag" required value="${requestScope.article.tag}"
                           placeholder="输入文章标签，以‘#’分隔开（100字以内）" data-maxlength="100"/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label class="title-label">博客分类</label>
                    <select class="form-control" style="width: 300px" id="cate" required>
                        <c:forEach items="${requestScope.list}" var="item">
                            <c:forEach items="${item.children}" var="cate">
                                <option value="${cate.cateId}">${cate.title}</option>
                            </c:forEach>
                        </c:forEach>
                    </select>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label class="title-label">博客描述</label>
                        <textarea id="description" placeholder="输入文章描述（300字以内）" required
                                  style="width: 100%" rows="3"
                                  data-maxlength="300">${requestScope.article.description}</textarea>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="row">
                    <div class="col-xs-2 col-xs-offset-3">
                        <button type="submit" class="btn btn-primary" style="margin: 30px;width: 300px">
                            <c:choose>
                                <c:when test="${requestScope.article == null}">
                                    发表文章
                                </c:when>
                                <c:otherwise>
                                    保存修改
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>

<script type="application/javascript">
    $(function () {
        $('#article-item').addClass('active');

        $('#form').validator().on('submit', function (e) {
            if (e.isDefaultPrevented()) {

            } else {
                if ('${sessionScope.user.userId}') {
                    if (${requestScope.article != null}) {
                        obj.updateArticle();
                    } else {
                        obj.postArticle();
                    }
                } else {
                    alert('请先登录');
                    window.location = '/login';
                }
            }

            return false;
        });

        if (${requestScope.article != null}) {
            $('#cate').val(${requestScope.article.category.cateId});
        }

        var obj = {
            postArticle: function () {
                $.post('/article', {
                    "category.cateId": $('#cate').val(),
                    "title": $("#title").val(),
                    "description": $('#description').val(),
                    "content": window.editor.html(),
                    "user.userId": '${sessionScope.user.userId}',
                    "tag": $('#tag').val()
                }, this.success, "JSON")
            },
            success: function (data) {
                if (data.success) {
                    window.location = '/${sessionScope.user.url}';
                } else {
                    alert(data.msg);
                }
            },
            updateArticle: function () {
                $.ajax({
                    url: '/article',
                    type: 'PUT',
                    data: {
                        "articleId": "${requestScope.article.articleId}",
                        "category.cateId": $('#cate').val(),
                        "title": $("#title").val(),
                        "description": $('#description').val(),
                        "content": window.editor.html(),
                        "user.userId": '${sessionScope.user.userId}',
                        "tag": $('#tag').val()
                    },
                    dataType: 'JSON',
                    success: function (data) {
                        if (data.success) {
                            window.location = '/${sessionScope.user.url}';
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
