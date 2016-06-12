package com.zzu.xblog.dto;

/**
 * Created by Administrator on 2016/6/12.
 */

/**
 * dto,作为后端返回前端的数据传输对象
 */
public class Result {
    private boolean success = false;
    private String msg = null;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
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
