package cn.zzuzl.xblog.exception;

/**
 * 数据库操作错误异常
 */
public class DataException extends RuntimeException {
    private static final String DEFAULT_MSG = "数据操作错误";

    public DataException(String message) {
        super(message);
    }

    public DataException() {
        super(DEFAULT_MSG);
    }
}
