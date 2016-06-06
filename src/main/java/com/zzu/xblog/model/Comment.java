package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

import com.zzu.xblog.common.Common;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 评论实体类
 */
public class Comment {
    private int commentId;
    private String content;
    private Date postTime;
    private Article article;
    private Comment parent;
    private int pId;

    private static final int MAX_CONTENT_LENGTH = 300;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", postTime=" + postTime +
                ", article=" + article +
                ", parent=" + parent +
                ", pId=" + pId +
                '}';
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public JSONObject valid() {
        JSONObject result = new JSONObject();
        result.put(Common.SUCCESS, false);

        if (Utils.isEmpty(content) || content.length() > MAX_CONTENT_LENGTH) {
            result.put(Common.MSG, "内容长度为1-300");
        } else if (article == null || article.getArticleId() < 1) {
            result.put(Common.MSG, "错误：未选择文章");
        } else if (pId < 0) {
            result.put(Common.MSG, "错误：回复错误");
        } else {
            result.put(Common.SUCCESS, true);
        }
        return result;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }
}
