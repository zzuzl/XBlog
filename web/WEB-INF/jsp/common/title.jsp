<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/resource/font-awesome-4.6.3/css/font-awesome.min.css">

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
            <a class="navbar-brand" href="/">XBlog</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li id="index-li"><a href="/">主页</a></li>
                <li id="about-li"><a href="/about">关于</a></li>
            </ul>

            <div class="navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="photo-div">
                            <img src="/${sessionScope.user.photoSrc}" width="32" height="32" id="photo_img">
                            <s></s>
                        </div>
                        <div class="user-div">
                            <div class="top-div">
                                <img src="/${sessionScope.user.photoSrc}" width="100" height="100"/>
                                <div class="user-info">
                                    <h5><a href="/u/${sessionScope.user.url}">${sessionScope.user.nickname}</a>
                                    </h5>
                                    <h6>${sessionScope.user.email}</h6>
                                    <h6>
                                        <a href="/setting/userInfo">
                                            <i class="fa fa-cogs" aria-hidden="true"></i>
                                            设置
                                        </a>
                                        <a href="/setting/editArticle">
                                            发表博客
                                        </a>
                                    </h6>
                                    <a href="/u/${sessionScope.user.url}#msg">${sessionScope.unreadMsgCount}条新消息</a>
                                </div>
                            </div>
                            <div class="bottom-div">
                                <a href="/${sessionScope.user.url}" id="blog-btn" class="button">
                                    <i class="fa fa-home" aria-hidden="true"></i>
                                    我的博客
                                </a>
                                <div id="quit-btn" class="button" onclick="quit()">
                                    <i class="fa fa-sign-out" aria-hidden="true"></i>
                                    退出
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a type="button" href="/login" class="btn btn-default navbar-btn">登录</a>
                        <a href="/register">注册</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <form class="navbar-form navbar-right" role="search" id="searchForm" action="/search" method="get">
                <div class="input-group">
                    <input type="text" class="form-control" name="keyword"
                           placeholder="输入搜索内容" value="${requestScope.keyword}">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">搜索</button>
                    </span>
                </div>
            </form>
        </div>
    </div>
</nav>

<script>

    $(function () {
        /* 头像个人信息切换 */
        $("#photo_img").click(function (event) {
            event.stopPropagation();
            $("s").toggle();
            $(".user-div").toggle();
        });

        $("s,.user-div").click(function (event) {
            event.stopPropagation();
        });

        $(document).click(function () {
            $("s").hide();
            $(".user-div").hide();
        });

        $('#searchForm').submit(function () {
            var keyword = $("input[name='keyword']").val();
            console.log("keyword:" + keyword);
            if (keyword.trim().length < 1) {
                return false;
            }
        });
    });

    /* 退出 */
    function quit() {
        $.post('/user/logout', function (data) {

        }, 'JSON');
        window.location = '/';
    }
</script>