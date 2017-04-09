package cn.zzuzl.xblog.exception;

import cn.zzuzl.xblog.model.vo.ErrorResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理
 */
@ControllerAdvice(annotations = {Controller.class}, basePackageClasses = {cn.zzuzl.xblog.interceptor.AuthInterceptor.class})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LogManager.getLogger(getClass());

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public final ErrorResult handleServiceException(ServiceException ex, HttpServletResponse response) {
        logError(ex);
        response.setStatus(ex.getErrorCode().getHttpStatus());
        return new ErrorResult(ex.getErrorCode().getCode(), ex.getMessage());
    }

    private void logError(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }
}
