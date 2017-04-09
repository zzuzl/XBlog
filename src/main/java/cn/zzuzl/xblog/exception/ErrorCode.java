package cn.zzuzl.xblog.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * @JsonFormat(shape = JsonFormat.Shape.OBJECT) 把枚举以对象方式转化成json
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode implements Serializable {
    BAD_REQUEST(400, 400, "请求参数错误"),
    UNAUTHORIZED(401, 401, "未授权的用户"),
    FORBIDDEN(403, 403, "禁止访问"),
    INTERNAL_SERVER_ERROR(500, 500, "内部错误"),
    DATA_ERROR(1000, 400, "数据操作错误"),
    ARTICLE_STATUS_WRONG(1001, 400, "文章状态错误"),
    USER_ERROR(1002, 400, "操作用户错误");

    private int code;
    private int httpStatus;
    private String defaultMsg;

    private ErrorCode(int code, int httpStatus, String defaultMsg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMsg = defaultMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }

    public void setDefaultMsg(String defaultMsg) {
        this.defaultMsg = defaultMsg;
    }
}
