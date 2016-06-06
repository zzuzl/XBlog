package com.zzu.xblog.interceptor;

import com.zzu.xblog.common.Common;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器,过滤用户身份信息
 */
public class UserParameterInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*System.out.println("preHandle");

		HttpSession session = request.getSession();
		if(session.getAttribute(Common.USER) == null) {
			response.setHeader("Content-Type","application/json;charset=UTF-8");
			JSONObject result = new JSONObject();
			result.put(Common.SUCCESS, false);
			result.put(Common.MSG, "用户身份未认证或已过期");
			response.getWriter().print(result);
			return false;
		}*/

		return true;
	}
}
