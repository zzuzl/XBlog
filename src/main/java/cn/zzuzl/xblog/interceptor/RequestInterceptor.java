package cn.zzuzl.xblog.interceptor;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.util.Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截session请求
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Common.USER);

        String accept = request.getHeader("Accept");
        if (user == null) {
            if (Utils.isMatch(accept, ".*text/html.*")) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
