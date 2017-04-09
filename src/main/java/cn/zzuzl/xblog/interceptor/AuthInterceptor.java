package cn.zzuzl.xblog.interceptor;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.common.annotation.Auth;
import cn.zzuzl.xblog.exception.ErrorCode;
import cn.zzuzl.xblog.exception.ServiceException;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.util.LoginContext;
import cn.zzuzl.xblog.util.Utils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截session请求,添加登录上下文
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Common.USER);
        // 添加登录上下文
        LoginContext.getContext().set(user);

        // 获取方法的注解
        if (handler instanceof HandlerMethod) {
            final HandlerMethod method = (HandlerMethod) handler;
            Auth auth = method.getMethodAnnotation(Auth.class);
            if (auth == null) {
                return true;
            }
        }

        // 验证身份
        String accept = request.getHeader("Accept");
        if (user == null) {
            if (Utils.isMatch(accept, ".*application/json.*")) {
                throw new ServiceException(ErrorCode.UNAUTHORIZED, "未登录或用户身份已过期");
            }
        }
        return true;
    }
}
