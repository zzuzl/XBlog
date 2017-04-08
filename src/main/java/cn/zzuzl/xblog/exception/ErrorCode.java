package cn.zzuzl.xblog.exception;

public enum ErrorCode {
    BAD_REQUEST(400, 400),
    UNAUTHORIZED(401, 401),
    FORBIDDEN(403, 403),
    INTERNAL_SERVER_ERROR(500, 500),
    ARTICLE_STATUS_WRONG(1100, 400);

    private int code;
    private int httpStatus;

    private ErrorCode(int code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
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
}
