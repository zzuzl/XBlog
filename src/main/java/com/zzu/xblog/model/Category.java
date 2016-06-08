package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 分类实体类
 */
public class Category {
	private int cateId;
	private String title;
	private Category parent;
	private List<Category> children;

	public List<Category> getChildren() {
		if(children == null) {
			children = new ArrayList<>();
		}
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
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
