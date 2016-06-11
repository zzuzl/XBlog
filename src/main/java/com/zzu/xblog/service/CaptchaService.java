package com.zzu.xblog.service;

import com.google.code.kaptcha.servlet.KaptchaExtend;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码生成service
 */
@Service
public class CaptchaService extends KaptchaExtend {
	/**
	 * 生成验证码图片
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.captcha(req, resp);
	}

	/**
	 * 获取验证码对应的值
	 * @param req
	 * @return
	 */
	public String getGeneratedKey(HttpServletRequest req) {
		return super.getGeneratedKey(req);
	}
}
