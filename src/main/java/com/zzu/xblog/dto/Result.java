package com.zzu.xblog.dto;

import java.util.Map;

/**
 * dto,作为后端返回前端的数据传输对象
 */
public class Result {
    private boolean success = false;
    private String msg = null;
    private Map<String,Object> data;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
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
}
