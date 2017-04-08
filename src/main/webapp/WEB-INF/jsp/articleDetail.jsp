<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>${requestScope.article.title} - XBlog</title>
    <%@include file="common/head.jsp" %>
    <link href="http://xblog-mis.oss-cn-shanghai.aliyuncs.com/kindeditor/themes/default/default.css" rel="stylesheet"/>
    <script src="http://xblog-mis.oss-cn-shanghai.aliyuncs.com/kindeditor/kindeditor-all-min.js"></script>
    <script src="http://xblog-mis.oss-cn-shanghai.aliyuncs.com/kindeditor/lang/zh-CN.js"></script>
    <script src="//cdn.bootcss.com/vue/2.2.4/vue.min.js"></script>
    <link href="/resource/css/blog.css" rel="stylesheet">
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
<%@include file="common/title.jsp" %>
<div class="container-fluid" style="margin-top: 60px" id="container">
    <h2 class="articleTitle">${requestScope.article.title}</h2>
    <div class="row">
        <div class="col-xs-8 col-xs-offset-2">
            <div class="author">
                <a href="/${requestScope.article.user.url}">
                    <img src="${requestScope.article.user.photoSrc}" class="authorImg"/>
                </a>
                <div class="info">
                    <a href="/${requestScope.article.user.url}">${requestScope.article.user.nickname}</a>
                    <div class="gzDiv-1">
                        <i class="fa fa-plus" aria-hidden="true"></i>
                        关注
                    </div>
                    <div class="meta">
                        <span><fmt:formatDate value="${requestScope.article.postTime}"
                                              pattern="yyyy-MM-dd HH:mm"/></span>
                        <span>阅读(${requestScope.article.viewCount})</span>
                        <span>评论(${requestScope.article.commentCount})</span>
                    </div>
                </div>
            </div>
            <%-- 放文章内容 --%>
            <div id="content-body">
                ${requestScope.article.content}
            </div>
            <h6 class="dash-h6"></h6>

            <h4>分类：${requestScope.article.category.title}</h4>
            <h4>标签：
                <c:forEach items="${requestScope.tags}" var="tag">
                    <span class="label label-success">${tag}</span>
                </c:forEach>
            </h4>
            <div class="well">
                <a href="/${requestScope.article.user.url}">
                    <img src="${requestScope.article.user.photoSrc}" class="authorImg"/>
                </a>
                <div class="info">
                    <a href="/${requestScope.article.user.url}">${requestScope.article.user.nickname}</a>
                    <div class="gzDiv-1">
                        <i class="fa fa-plus" aria-hidden="true"></i>
                        关注
                    </div>
                    <div class="meta">
                        <span id="age"></span>
                        <span>拥有<a href="/u/${requestScope.article.user.url}">${requestScope.article.user.fansCount}</a>个粉丝</span>
                        <span>正在关注<a
                                href="/u/${requestScope.article.user.url}">${requestScope.article.user.attentionCount}</a>个人</span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-2 col-xs-offset-6">
                    <a type="button" href="javascript:void(0)" class="btn btn-danger gz"
                       v-on:click="changeAttention">
                        <span v-if="!attention"><i class="fa fa-plus" aria-hidden="true"></i>关注</span>
                        <span v-if="attention">取消关注</span>
                    </a>
                    <a type="button" href="javascript:void(0)" class="btn btn-info gz"
                       id="like-btn" v-on:click="like">
                        <i class="fa fa-thumbs-up" aria-hidden="true"></i>{{likeCount}}
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
                                    <a href="#comment" v-on:click="reply('${item.user.nickname}','${item.commentId}')">
                                        回复
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <a id="comment"></a>
                    <div class="comment-area">
                        <div class="click-close" v-if="pId && pId >= 0">
                            回复：
                            <span class="text">{{nickname}}</span>
                            <button type="button" class="close" aria-label="Close"
                                    v-on:click="pId=0">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <textarea id="editor_id" name="content" cols="100" rows="8"></textarea>
                        <button type="button" class="btn btn-default btn-sm"
                                v-on:click="postComment">提交评论
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

<%@include file="common/footer.jsp" %>

<script src="//cdn.bootcss.com/moment.js/2.17.1/moment-with-locales.min.js"></script>
<script type="application/javascript">
    $(function () {
        moment.locale('zh-CN');

        var regTime = "<fmt:formatDate value='${requestScope.article.user.regTime}' pattern='yyyy-MM-dd HH:mm'/>";
        var ageText = moment(regTime, "YYYY-MM-DD HH:mm").fromNow();
        var text = '已在XBlog' + ageText.substr(0, ageText.length - 1);
        $('#age').text(text);

        if (!'${sessionScope.user.userId}') {
            $('.comment-area').hide();
            $('#tips').show();
        }
    });

    var vm = new Vue({
        el: '#container',
        data: {
            articleId: '${requestScope.article.articleId}',
            userId: '${sessionScope.user.userId}',
            pId: 0,
            liked:${requestScope.like != null},
            attention: ${requestScope.attention != null},
            likeCount: ${requestScope.article.likeCount},
            nickname: ''
        },
        methods: {
            postComment: function () {
                if (window.editor.html().length > 0) {
                    $.post('/comment', {
                        'article.articleId': this.articleId,
                        'user.userId': this.userId,
                        'content': window.editor.html(),
                        'pId': this.pId
                    }, function (data) {
                        if (data.success) {
                            window.location.reload();
                            $('html, body').animate({
                                scrollTop: $("#comment-top").offset().top
                            }, 500);
                            window.editor.html("");
                        } else {
                            layer.msg(data.msg);
                        }
                    }, 'JSON');
                }
            },
            reply: function (nickname, pId) {
                this.pId = pId;
                this.nickname = nickname;
            },
            like: function () {
                $.post('/article/like', {
                    articleId: this.articleId,
                    userId: this.userId
                }, function (data) {
                    if (data.success) {
                        this.likeCount += 1;
                        /*$('#like-btn').addClass('active');
                        $('#like-btn').attr('disabled', 'disabled');
                        $('#like-btn').css('color', 'black');*/
                    } else {
                        layer.msg(data.msg);
                    }
                }, 'JSON')
            },
            changeAttention: function () {
                if (this.attention) {
                    $.ajax({
                        url: '/user/attention?from=${sessionScope.user.userId}&to=${requestScope.article.user.userId}',
                        type: 'DELETE',
                        dataType: 'JSON',
                        success: function (data) {
                            if (data.success) {
                                vm.attention = false;
                            } else {
                                layer.msg(data.msg);
                            }
                        }
                    });
                } else {
                    $.post('/user/attention', {
                        from: '${sessionScope.user.userId}',
                        to: '${requestScope.article.user.userId}'
                    }, function (data) {
                        if (data.success) {
                            vm.attention = true;
                        } else {
                            layer.msg(data.msg);
                        }
                    }, 'JSON')
                }
            }
        }
    });

    $(function () {
        $('#content-body img').addClass('img-responsive');
    });
</script>
</body>
</html>
