package com.zzu.xblog.model.message;

import java.io.Serializable;
import java.util.Date;

public class NewArticleMessage implements Serializable {
    private String messageTitle;
    private int articleId;
    private String articleTitle;
    private int userId;
    private String userUrl;
    private String nickname;
    private Date postTime;

    public NewArticleMessage(String messageTitle, int articleId, String articleTitle,int userId, String userUrl, String nickname, Date postTime) {
        this.messageTitle = messageTitle;
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.userId = userId;
        this.userUrl = userUrl;
        this.nickname = nickname;
        this.postTime = postTime;
    }

    public NewArticleMessage() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}
