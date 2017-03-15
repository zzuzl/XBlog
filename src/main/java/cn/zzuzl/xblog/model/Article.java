package cn.zzuzl.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.util.Utils;

import java.util.Date;

/**
 * 文章实体类
 */
public class Article {
    private int articleId;
    private Category category;
    private String title;
    private String content;
    private String description;
    private Date postTime;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private User user;
    private String tag;
    private Article pre;
    private Article next;

    public Article() {}

    public Article(int articleId, Category category, String title, String content, String description, Date postTime, int viewCount, int commentCount, int likeCount, User user, String tag, Article pre, Article next) {
        this.articleId = articleId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.description = description;
        this.postTime = postTime;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.user = user;
        this.tag = tag;
        this.pre = pre;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", postTime=" + postTime +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", user=" + user +
                ", tag='" + tag + '\'' +
                '}';
    }

    private static final int MAX_TITLE_LENGTH = 25;
    private static final int MAX_DESC_LENGTH = 50;
    private static final int MAX_CONTENT_LENGTH = 20000;

    public Result valid() {
        Result result = new Result();

        if (category == null || category.getCateId() <= 0) {
            result.setMsg("文章分类错误");
        } else if (Utils.isEmpty(title) || title.length() > MAX_TITLE_LENGTH) {
            result.setMsg("标题长度不合法，1-25");
        } else if (Utils.isEmpty(description) || description.length() > MAX_DESC_LENGTH) {
            result.setMsg("文章描述长度不合法，1-50");
        } else if (Utils.isEmpty(content) || content.length() > MAX_CONTENT_LENGTH) {
            result.setMsg("文章内容长度不合法，20000字以内");
        } else {
            result.setSuccess(true);
        }
        return result;
    }

    public Article getNext() {
        return next;
    }

    public void setNext(Article next) {
        this.next = next;
    }

    public Article getPre() {
        return pre;
    }

    public void setPre(Article pre) {
        this.pre = pre;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
