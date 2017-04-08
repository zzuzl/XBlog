package cn.zzuzl.xblog.exception;

import cn.zzuzl.xblog.model.vo.ErrorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = {ResponseBody.class})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LogManager.getLogger(getClass());

    @ExceptionHandler(value = ServiceException.class)
    public final ResponseEntity<ErrorResult> handleServiceException(ServiceException ex, WebRequest request) {
        logError(ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResult errorResult = new ErrorResult(ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<ErrorResult>(errorResult, headers, HttpStatus.valueOf(ex.getErrorCode().getHttpStatus()));
    }

    private void logError(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }
}
