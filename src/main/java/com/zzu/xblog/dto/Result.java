package com.zzu.xblog.dto;

import com.zzu.xblog.common.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * dto,作为后端返回前端的数据传输对象
 */
public class Result<E> {
    private boolean success = false;
    private String msg = null;
    private Map<String,Object> data;
    private List<E> list = new ArrayList<E>();
    private int totalPage;
    private int totalItem;
    private int page;
    private int pageSize;

    public Result(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        resetPage();
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    private void resetPage() {
        if (page <= 0) {
            page = 1;
        }
        if (pageSize <= 0) {
            pageSize = Common.DEFAULT_ITEM_COUNT;
        }
        totalPage = totalItem % pageSize == 0 ? totalItem / pageSize : totalItem / pageSize + 1;
        if (page > totalPage) {
            page = totalPage;
        }
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Result() {}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
        resetPage();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        resetPage();
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
