<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.user.nickname} 的个人空间</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="${root}/resource/css/index.css">
    <link rel="stylesheet" href="${root}/resource/css/pc.css">
</head>
<body>
<%@include file="common/title.jsp" %>

<div class="container" style="margin-top: 100px" id="container">
    <div class="row">
        <div class="col-xs-3 col-xs-offset-1">
            <a href="javascript:void(0)" class="thumbnail" id="photo-thumbnail">
                <img src="${root}/${requestScope.user.photoSrc}" alt="暂无"/>
            </a>
        </div>
        <div class="col-xs-7">
            <h2>${requestScope.user.nickname}(${requestScope.user.sex})</h2>
            <h4>博龄：2年</h4>
            <h5>博客：
                <a href="http://localhost:8888/${root}/${requestScope.user.url}">
                    http://localhost:8888/${root}/${requestScope.user.url}
                </a>
            </h5>
            <h4>${requestScope.user.motto}</h4>
            <h4>兴趣：${requestScope.user.interest}</h4>
            <c:if test="${sessionScope.user.userId != requestScope.user.userId}">
                <a href="javascript:void(0)" type="button" class="btn btn-info"
                   onclick="obj.addOrCancelAttention()" id="attention-btn">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    关注
                </a>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-10 col-xs-offset-1">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist" id="tabs">
                <li role="presentation" class="active">
                    <a href="#attention" aria-controls="attention" role="tab" data-toggle="tab">
                        ${requestScope.user.nickname}的关注(${requestScope.user.attentionCount})
                    </a>
                </li>
                <li role="presentation">
                    <a href="#fans" aria-controls="fans" role="tab" data-toggle="tab">
                        ${requestScope.user.nickname}的粉丝(${requestScope.user.fansCount})
                    </a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="attention">
                    <ul>
                        <c:forEach items="${requestScope.attentions}" var="item">
                            <li class="user-li">
                                <a href="${root}/u/${item.to.url}" class="thumbnail" target="_blank">
                                    <img src="${root}/${item.to.photoSrc}" alt="暂无">
                                </a>
                                <div class="nickname-div">
                                    <a href="${root}/u/${item.to.url}" target="_blank">${item.to.nickname}</a>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div role="tabpanel" class="tab-pane" id="fans">
                    <c:forEach items="${requestScope.fans}" var="item">
                        <li class="user-li">
                            <a href="${root}/u/${item.from.url}" class="thumbnail" target="_blank">
                                <img src="${root}/${item.from.photoSrc}" alt="暂无">
                            </a>
                            <div class="nickname-div">
                                <a href="${root}/u/${item.from.url}" target="_blank">${item.from.nickname}</a>
                            </div>
                        </li>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script type="application/javascript">
    var obj = {
        attention: '${requestScope.attention != null}',
        isLogin: function () {
            return '${sessionScope.user.userId}';
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
                window.location = '${root}/login';
            }
        },
        addAttention: function () {
            $.post('${root}/user/attention', {
                from: '${sessionScope.user.userId}',
                to: '${requestScope.article.user.userId}'
            }, function (data) {
                if (data.success) {
                    window.obj.attention = true;
                } else {
                    alert(data.msg);
                }
            }, 'JSON')
        },
        cancelAttention: function () {
            $.ajax({
                url: '${root}/user/attention',
                type: 'DELETE',
                data: {
                    from: '${sessionScope.user.userId}',
                    to: '${requestScope.article.user.userId}'
                },
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        window.obj.attention = false;
                    } else {
                        alert(data.msg);
                    }
                }
            });
        },
        updateAttentionButton: function () {
            if (this.attention) {
                $('#attention-btn').text("取消关注");
            } else {
                $('#attention-btn').text("<span class='glyphicon glyphicon-plus' aria-hidden='true'></span>关注");
            }
        }
    };

    $(function () {
        obj.updateAttentionButton();
    });
</script>
</body>
</html>
