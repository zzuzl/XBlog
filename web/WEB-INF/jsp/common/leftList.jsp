<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="user-info-2">
    <img src="/${sessionScope.user.photoSrc}" width="80" height="80">
    <div class="user-detail">
        ${sessionScope.user.email}
        ${sessionScope.user.nickname}
    </div>
</div>
<div class="list-group">
    <a href="/setting/userInfo" class="list-group-item" id="info-item">
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        修改个人信息
    </a>
    <a href="/setting/changePwd" class="list-group-item" id="pwd-item">
        <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
        修改登录密码
    </a>
    <a href="/setting/changePhoto" class="list-group-item" id="photo-item">
        <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
        修改头像照片
    </a>
    <a href="/setting/editArticle" class="list-group-item" id="article-item">
        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
        编辑我的文章
    </a>
    <a href="/setting/manageArticle" class="list-group-item" id="manage-item">
        <span class="glyphicon glyphicon-th" aria-hidden="true"></span>
        管理我的文章
    </a>
</div>