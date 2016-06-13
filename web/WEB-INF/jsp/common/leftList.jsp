<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="user-info-2">
    <img src="${root}/${sessionScope.user.photoSrc}" width="80" height="80">
    <div class="user-detail">
        ${sessionScope.user.email}
        ${sessionScope.user.nickname}
    </div>
</div>
<div class="list-group">
    <a href="${root}/setting/userInfo" class="list-group-item" id="info-item">
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        修改个人信息
    </a>
    <a href="${root}/setting/changePwd" class="list-group-item" id="pwd-item">
        <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
        修改登录密码
    </a>
    <a href="${root}/setting/changePhoto" class="list-group-item" id="photo-item">
        <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
        修改头像照片
    </a>
    <a href="${root}/setting/editArticle" class="list-group-item" id="article-item">
        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
        发表新的博文
    </a>
</div>