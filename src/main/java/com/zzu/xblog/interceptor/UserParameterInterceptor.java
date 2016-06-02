package com.zzu.xblog.interceptor;

import com.zzu.xblog.common.Common;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 提交用户注册时，过滤注册信息
 */
public class UserParameterInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*System.out.println("preHandle");
		response.setHeader("Content-Type","application/json;charset=UTF-8");
		JSONObject result = new JSONObject();
		result.put(Common.SUCCESS, true);
		result.put(Common.MSG, "邮箱已存在");
		response.getWriter().print(result);*/

		return true;
	}
}
