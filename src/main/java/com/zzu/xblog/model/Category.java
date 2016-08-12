package com.zzu.xblog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类实体类
 */
public class Category implements Serializable {
    private int cateId;
    private String title;
    private Category parent;
    private List<Category> children;

    public List<Category> getChildren() {
        if (children == null) {
            children = new ArrayList<Category>();
        }
        return children;
    }

    public Category(int cateId, String title, Category parent, List<Category> children) {
        this.cateId = cateId;
        this.title = title;
        this.parent = parent;
        this.children = children;
    }

    public Category() {
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
