package com.zzu.xblog.interceptor;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.User;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 拦截session请求
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Common.USER);

        String accept = request.getHeader("Accept");
        if (user == null) {
            if (Utils.isMatch(accept, ".*application/json.*")) {
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                JSONObject object = new JSONObject();
                object.put(Common.SUCCESS, false);
                object.put(Common.MSG, "用户身份错误");
                writer.print(object.toString());
            }
        }
        return true;
    }
}
