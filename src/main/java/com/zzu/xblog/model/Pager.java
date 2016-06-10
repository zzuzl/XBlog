package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/8.
 */

import java.util.List;

/**
 * 分页  泛型类
 *
 * @param <T>
 */
public class Pager<T> {
    private int totalItem;
    private int totalPage;
    private int currentPage;
    private int pageSize;
    private List<T> itemList;

    public Pager(int totalItem, int currentPage) {
        this(totalItem, currentPage, 15);
    }

    public Pager(int totalItem, int currentPage, int pageSize) {
        this.totalItem = totalItem;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        int temp = totalItem % pageSize;
        this.totalPage = totalItem / pageSize;
        if (temp != 0) {
            this.totalPage++;
        }
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }
}
