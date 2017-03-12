<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${requestScope.article.title} - XBlog</title>
    <%@include file="common/head.jsp" %>
    <link href="/resource/kindeditor/themes/default/default.css" rel="stylesheet"/>
    <link href="/resource/kindeditor/themes/simple/simple.css" rel="stylesheet"/>
    <script src="/resource/kindeditor/kindeditor-all-min.js"></script>
    <script src="/resource/kindeditor/lang/zh-CN.js"></script>
    <link rel="stylesheet" href="/resource/css/blog.css">
    <link href="/resource/css/index.css" rel="stylesheet"/>
    <link href="/resource/css/detail.css" rel="stylesheet"/>
    <script>
        var editorConfig = {
            width: '100%',
            height: '100px',
            minHeight: '50px',
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'link'
            ],
            langType: 'zh-CN',
            themeType: 'simple',
            emoticonsPath: 'http://xblog-mis.oss-cn-shanghai.aliyuncs.com/xblog/images/'
        };

        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', editorConfig);
        });
    </script>
</head>
<body>
<div class="header" style="background: url('/resource/images/body_bg.png')">
    <div class="blog-title" style="background: url('/resource/images/title-yellow.png')">
        <h1><a href="/${requestScope.article.user.url}">${requestScope.article.user.nickname}</a></h1>
        <h4>${requestScope.article.user.motto}</h4>
    </div>
    <div id="navigator">
        <ul class="navList">
            <li><a href="/">XBlog</a></li>
            <li><a id="index-li" href="/${requestScope.article.user.url}">首页</a></li>
        </ul>
    </div>
</div>
<div class="container-fluid" style="margin-top: 60px" id="container">
    <div class="row">
        <div class="col-xs-9">
            <div class="row">
                <div class="col-xs-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <%-- 放文章内容 --%>
                            <div id="content-body">
                                ${requestScope.article.content}
                            </div>
                            <h6 class="dash-h6"></h6>
                            <h4>分类：${requestScope.article.category.title}</h4>
                            <h4>标签：${requestScope.article.tag}</h4>
                            <div class="row">
                                <div class="col-xs-2">
                                    <a href="/u/${requestScope.article.user.url}" class="thumbnail">
                                        <img src="${requestScope.article.user.photoSrc}" alt="暂无">
                                    </a>
                                </div>
                                <div class="col-xs-2">
                                    <h5>
                                        <a href="/u/${requestScope.article.user.url}">${requestScope.article.user.nickname}</a>
                                    </h5>
                                    <h5>
                                        <a href="/u/${requestScope.article.user.url}">关注：${requestScope.article.user.attentionCount}</a>
                                    </h5>
                                    <h5>
                                        <a href="/u/${requestScope.article.user.url}">粉丝：${requestScope.article.user.fansCount}</a>
                                    </h5>
                                </div>
                                <div class="col-xs-2 col-xs-offset-6">
                                    <c:if test="${requestScope.article.user.userId != sessionScope.user.userId}">
                                        <a type="button" href="javascript:void(0)" class="btn btn-danger gz"
                                           id="add-btn" onclick="obj.addOrCancelAttention()">
                                            <i class="fa fa-plus" aria-hidden="true"></i>关注
                                        </a>
                                        <a type="button" href="javascript:void(0)" class="btn btn-danger gz"
                                           id="cancel-btn" onclick="obj.addOrCancelAttention()">
                                            取消关注
                                        </a>
                                    </c:if>
                                    <a type="button" href="javascript:void(0)" class="btn btn-info gz"
                                       id="like-btn" onclick="obj.like()">
                                        <i class="fa fa-thumbs-up" aria-hidden="true"></i>${requestScope.article.likeCount}
                                    </a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6">
                                    <c:if test="${requestScope.article.pre != null}">
                                        <span>上一篇：</span>
                                        <a href="/p/${requestScope.article.pre.articleId}">${requestScope.article.pre.title}</a>
                                    </c:if>
                                </div>
                                <div class="col-xs-6" style="text-align: right">
                                    <c:if test="${requestScope.article.next != null}">
                                        <span>下一篇：</span>
                                        <a href="/p/${requestScope.article.next.articleId}">${requestScope.article.next.title}</a>
                                    </c:if>
                                </div>
                            </div>
                            <h6 class="dash-h6">
                                <a href="/${requestScope.article.user.url}" target="_blank">
                                    ${requestScope.article.user.nickname}
                                </a>
                                发表于：
                                <fmt:formatDate value="${requestScope.article.postTime}"
                                                pattern="yyyy-MM-dd HH:mm"/>
                                <span>阅读(${requestScope.article.viewCount})</span>
                                <span>评论(${requestScope.article.commentCount})</span>
                            </h6>
                            <div class="row">
                                <div class="col-xs-12">
                                    <%-- 放评论 --%>
                                    <label>评论列表</label>
                                    <a id="comment-top"></a>
                                    <c:forEach items="${requestScope.comments}" var="item">
                                        <div class="comment-item row">
                                            <div class="col-xs-2">
                                                <a href="/${item.user.url}" class="thumbnail">
                                                    <img src="${item.user.photoSrc}"/>
                                                </a>
                                            </div>
                                            <div class="col-xs-10">
                                                <div class="title">
                                                <span class="time">
                                                    <fmt:formatDate value="${item.postTime}"
                                                                    pattern="yyyy-MM-dd HH:mm"/>
                                                </span>
                                                <span class="name">
                                                    <a href="/u/${item.user.url}">${item.user.nickname}</a>
                                                </span>
                                                </div>
                                                <div class="content">
                                                    <c:if test="${item.pId > 0}">
                                                        <h6>
                                                            回复
                                                            <a href="/${item.parent.user.url}">${item.parent.user.nickname}</a>
                                                            :
                                                        </h6>
                                                    </c:if>
                                                    <p>${item.content}</p>
                                                    <a href="#comment"
                                                       onclick="obj.reply('${item.user.nickname}','${item.commentId}')">
                                                        回复
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <a id="comment"></a>
                                    <div class="comment-area">
                                        <div class="click-close">
                                            回复：
                                            <span class="text">张三</span>
                                            <button type="button" class="close" aria-label="Close"
                                                    onclick="obj.close()">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <textarea id="editor_id" name="content" cols="100" rows="8"></textarea>
                                        <button type="button" class="btn btn-default btn-sm"
                                                onclick="obj.postComment()">提交评论
                                        </button>
                                    </div>
                                    <h5 id="tips">
                                        注册用户登录后才能发表评论，请<a href="/login">登录</a>或
                                        <a href="/register">注册</a>，访问<a href="/">网站首页</a>。
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="panel panel-info">
                <div class="panel-heading">公告</div>
                <div class="panel-body">
                    <h5>昵称：
                        <a href="/u/${requestScope.article.user.url}" target="_blank">
                            ${requestScope.article.user.nickname}</a></h5>
                    <h5 id="age">博龄：</h5>
                    <h5>粉丝：<a
                            href="/u/${requestScope.article.user.url}">${requestScope.article.user.fansCount}</a>
                    </h5>
                    <h5>关注：<a
                            href="/u/${requestScope.article.user.url}">${requestScope.article.user.attentionCount}</a>
                    </h5>
                    <c:if test="${requestScope.article.user.userId != sessionScope.user.userId}">
                        <h5><a href="/u/${requestScope.article.user.url}">+加关注</a></h5>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script src="/resource/js/moment.js"></script>
<script src="/resource/js/moment-with-locales.js"></script>
<script type="application/javascript">
    $(function () {
        moment.locale('zh-CN');

        var regTime = "<fmt:formatDate value='${requestScope.article.user.regTime}' pattern='yyyy-MM-dd HH:mm'/>";
        var ageText = moment(regTime, "YYYY-MM-DD HH:mm").fromNow();
        var text = '博龄：' + ageText.substr(0, ageText.length - 1);
        $('#age').text(text);

        if (!'${sessionScope.user.userId}') {
            $('.comment-area').hide();
            $('#tips').show();
        }

        if (obj.isLogin() && obj.hasLiked()) {
            obj.changeLikeBtnState();
        }

        obj.updateAttentionButton();
    });

    var obj = {
        pId: 0,
        attention: ${requestScope.attention != null},
        check: function () {
            return '${sessionScope.user.userId}' && window.editor.html().length > 0;
        },
        hasLiked: function () {
            return ${requestScope.like != null};
        },
        isLogin: function () {
            return '${sessionScope.user.userId}';
        },
        postComment: function () {
            if (this.check()) {
                $.post('/comment', {
                    'article.articleId': '${requestScope.article.articleId}',
                    'user.userId': '${sessionScope.user.userId}',
                    'content': window.editor.html(),
                    'pId': obj.pId
                }, this.success, 'JSON');
            }
        },
        success: function (data) {
            if (data.success) {
                window.location.reload();
                $('html, body').animate({
                    scrollTop: $("#comment-top").offset().top
                }, 500);
                window.editor.html("");
            } else {
                alert(data.msg);
            }
        },
        reply: function (nickname, pId) {
            this.pId = pId;
            $('.click-close .text').text(nickname);
            $('.click-close').show();
        },
        close: function () {
            $('.click-close').hide();
            this.pId = 0;
        },
        like: function () {
            if (this.isLogin()) {
                if (this.hasLiked()) {
                    return;
                }
                $.post('/article/like', {
                    articleId: '${requestScope.article.articleId}',
                    userId: '${sessionScope.user.userId}'
                }, function (data) {
                    if (data.success) {
                        $('#like-btn').html("<i class='fa fa-thumbs-up' aria-hidden='true'></i>${requestScope.article.likeCount+1}");
                        window.obj.changeLikeBtnState();
                    } else {
                        alert(data.msg);
                    }
                }, 'JSON')
            } else {
                alert('请先登录！');
                window.location = '/login';
            }
        },
        addOrCancelAttention: function () {
            if (this.isLogin()) {
                if (this.attention) {
                    this.cancelAttention();
                } else {
                    this.addAttention();
                }
                this.updateAttentionButton();
            } else {
                alert('请先登录');
                window.location = '/login';
            }
        },
        addAttention: function () {
            $.post('/user/attention', {
                from: '${sessionScope.user.userId}',
                to: '${requestScope.article.user.userId}'
            }, function (data) {
                if (data.success) {
                    window.obj.attention = true;
                    window.obj.updateAttentionButton();
                } else {
                    alert(data.msg);
                }
            }, 'JSON')
        },
        cancelAttention: function () {
            $.ajax({
                url: '/user/attention?from=${sessionScope.user.userId}&to=${requestScope.article.user.userId}',
                type: 'DELETE',
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        window.obj.attention = false;
                        window.obj.updateAttentionButton();
                    } else {
                        alert(data.msg);
                    }
                }
            });
        },
        updateAttentionButton: function () {
            if (this.attention) {
                $('#add-btn').hide();
                $('#cancel-btn').show();
            } else {
                $('#add-btn').show();
                $('#cancel-btn').hide();
            }
        },
        changeLikeBtnState: function () {
            $('#like-btn').addClass('active');
            $('#like-btn').attr('disabled', 'disabled');
            $('#like-btn').css('color', 'black');
        }
    };

    $(function () {
        $('#content-body img').addClass('img-responsive');
    });
</script>
</body>
</html>
