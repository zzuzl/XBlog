package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.RedisService;
import com.zzu.xblog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 邮件验证码controller
 */
@Controller
@RequestMapping("verify")
public class VerifyController {
	@Resource
	private RedisService redisService;
	@Resource
	private UserService userService;

	/**
	 * 验证重置密码邮件验证码
	 *
	 * @param id
	 * @param hash
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
	public String resetPwd(Integer id, String hash, Model model) {
		model.addAttribute(Common.SUCCESS, true);
		model.addAttribute(Common.MSG, "验证成功!");

		Map<String, Object> map = redisService.getLink(id);
		if (map == null || hash == null || !hash.equals(map.get("hash"))) {
			model.addAttribute(Common.SUCCESS, false);
			model.addAttribute(Common.MSG, "验证失败!");
		} else {
			long time = (Long) map.get("time");
			if (getGapMinute(time) > 30) {
				model.addAttribute(Common.SUCCESS, false);
				model.addAttribute(Common.MSG, "验证码已过期!");
			}
		}

		return "index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(String salt, Model model) {
		model.addAttribute(Common.SUCCESS, false);

		Map<String, Object> map = redisService.getUserModel(salt);
		User user = (User) map.get("user");
		if (user == null) {
			model.addAttribute(Common.MSG, "激活失败!");
		} else {
			long time = (Long) map.get("time");
			if (getGapMinute(time) > 30) {
				model.addAttribute(Common.MSG, "验证码已过期!");
			} else {
				int count = userService.register(user, (String) map.get("password"));
				if (count > 0) {
					model.addAttribute(Common.MSG, "注册失败，邮箱已存在!");
				} else {
					model.addAttribute(Common.SUCCESS, true);
					model.addAttribute(Common.MSG, "注册成功!");
				}
			}
		}

		return "index";
	}

	/**
	 * 获取时间差（单位：minute）
	 *
	 * @param time
	 * @return
	 */
	private long getGapMinute(long time) {
		return (System.currentTimeMillis() - time) / (1000 * 60);
	}
}
