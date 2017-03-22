package cn.zzuzl.xblog.interceptor;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.dao.UserDao;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.model.vo.TokenVerifyResult;
import cn.zzuzl.xblog.service.TokenService;
import cn.zzuzl.xblog.service.UserService;
import cn.zzuzl.xblog.util.Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截session请求
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HttpSession session = request.getSession();
        // User user = (User) session.getAttribute(Common.USER);
        User user = null;
        String token = request.getHeader(Common.TOKEN);
        TokenVerifyResult result = tokenService.verifyToken(token);
        if (result.isValid()) {
            user = userService.getUserById(result.getUserId());
        }

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
