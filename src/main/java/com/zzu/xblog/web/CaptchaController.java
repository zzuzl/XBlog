package com.zzu.xblog.web;

/**
 * Created by Administrator on 2016/6/1.
 */

import com.zzu.xblog.service.CaptchaService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码生成controller
 */
@Controller
@RequestMapping("captcha")
public class CaptchaController {
	@Resource
	private CaptchaService captchaService;

	/**
	 * 生成验证码图片
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public void generateCaptcha(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		captchaService.captcha(req, res);
	}

	/**
	 * 获取验证码
	 * @param req
	 * @return
     */
	@RequestMapping(value = "/getKey", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject searchUserByEmail(HttpServletRequest req) {
		JSONObject object = new JSONObject();
		object.put("key", captchaService.getGeneratedKey(req));
		return object;
	}
}
