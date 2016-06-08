package com.zzu.xblog.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * 转换请求
 */
public class RequestInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        //System.out.println("preHandle");
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
        //System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
        //System.out.println("afterCompletion");
    }
}
