package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

/**
 * 分类实体类
 */
public class Category {
	private int cateId;
	private String title;
	private Category parent;

	@Override
	public String toString() {
		return "Category{" +
				"cateId=" + cateId +
				", title='" + title + '\'' +
				", parent=" + parent +
				'}';
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
}
