package cn.zzuzl.xblog.exception;

public class ServiceException extends RuntimeException {
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode,String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
