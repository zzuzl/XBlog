package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

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

	@Override
	public String toString() {
		return "Comment{" +
				"commentId=" + commentId +
				", content='" + content + '\'' +
				", postTime=" + postTime +
				", article=" + article +
				", parent=" + parent +
				'}';
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
