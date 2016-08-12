package com.zzu.xblog.model.message;

import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.User;

/**
 * Created by zhanglei53 on 2016/8/12.
 */
public class ArticleMessage {
    private Article article;
    private User user;

    public ArticleMessage(Article article, User user) {
        this.article = article;
        this.user = user;
    }

    public ArticleMessage() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
