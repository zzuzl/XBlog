package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dto.Result;
import com.zzu.xblog.service.MailService;
import com.zzu.xblog.service.RedisService;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 邮件发送controller
 */
@Controller
@RequestMapping("mail")
public class MailController {
	@Resource
	private MailService mailService;
	@Resource
	private RedisService redisService;

	/**
	 * 发送邮件
	 * @param operate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send/{operate}", method = RequestMethod.POST)
	@ResponseBody
	public Result sendEmail(@PathVariable("operate") String operate,
							HttpServletRequest request) {
		int userId = 1;
		String email = "672399171@qq.com";
		Result result = new Result();
		result.setSuccess(true);
		result.setMsg("邮件已成功发送至" + email + ",请注意查收!");
		if (Common.OPERATE_RESET_PWD.equalsIgnoreCase(operate)) {
			String salt = Utils.randomString();
			String hash = Utils.MD5(email, salt);
			mailService.sendResetPwdEmail(email, request, userId, hash);
			redisService.addLink(userId, email, salt, hash);
		} else {
			result.setSuccess(false);
			result.setMsg("操作错误!");
		}
		return result;
	}
}
