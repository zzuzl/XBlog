package cn.zzuzl.xblog.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器,过滤用户身份信息
 */
public class UserParameterInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LogManager.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// logger.info(request.getHeaderNames());
		// logger.info(request.getQueryString());

		/*logger.info("preHandle");

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
