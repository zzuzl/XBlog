package cn.zzuzl.xblog.model;

import java.util.Date;

/**
 * 动态实体
 */
public class Dynamic {
    private int dynamicId;
    private User user;
    private Article article;
    private Date createTime;
    private String operator;
    private String content;

    // 空的构造方法 用于返回mybatis自动注入属性
    public Dynamic() {
    }

    public Dynamic(User user, Article article, String operator, String content) {
        this.user = user;
        this.article = article;
        this.operator = operator;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
