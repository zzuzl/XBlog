package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.CaptchaService;
import com.zzu.xblog.service.MailService;
import com.zzu.xblog.service.RedisService;
import com.zzu.xblog.service.UserService;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("user")
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	private CaptchaService captchaService;
	@Resource
	private RedisService redisService;
	@Resource
	private MailService mailService;

	/* 用户登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(String email, String password) {
		JSONObject result = new JSONObject();
		User user = userService.login(email, password);
		if (user != null) {
			result.put(Common.SUCCESS, true);
			result.put(Common.USER, user);
		} else {
			result.put(Common.SUCCESS, false);
			result.put(Common.ERROR, "用户名或密码错误");
		}
		return result;
	}

	/* 根据email查询用户 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public User searchUserByEmail(@RequestParam(value = "email", required = true) String email) {
		return userService.searchUserByEmail(email);
	}

	/* 注册 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject register(@Valid @ModelAttribute("user") User user, String password,
	                           BindingResult bindingResult, HttpServletRequest request) {
		JSONObject result = user.valid(password);
		if (result.getBoolean(Common.SUCCESS)) {
			if (userService.searchUserByEmail(user.getEmail()) != null) {
				result.put(Common.SUCCESS, false);
				result.put(Common.MSG, "该邮箱已被注册");
			} else {
				String salt = Utils.randomString(10);
				mailService.sendRegisterEmail(salt, user, request);
				redisService.addUser(salt, user,password);
			}
		}
		return result;
	}

	/* 修改密码 */
	@RequestMapping(value = "/changePwd", method = RequestMethod.PUT)
	@ResponseBody
	public JSONObject changePwd(String email, String newPassword, String originPassword) {
		JSONObject result = new JSONObject();
		result.put(Common.SUCCESS, true);
		User user = userService.login(email, originPassword);
		if (user == null) {
			result.put(Common.SUCCESS, false);
			result.put(Common.ERROR, "邮箱密码不匹配");
		} else {
			userService.changePwd(email, newPassword);
		}

		return result;
	}

	/* 测试redis */
	@RequestMapping(value = "/redis", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject redis() {
		JSONObject result = new JSONObject();
		int userId = 1;
		String email = "672399171@qq.com";
		String salt = Utils.randomString();
		String hash = Utils.MD5(email, salt);
		result.put(Common.SUCCESS, true);
		redisService.addLink(userId, email, salt, hash);

		Map<String, Object> map = redisService.getLink(userId);
		System.out.println(map);

		return result;
	}

	/* 重置密码 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.PUT)
	@ResponseBody
	public JSONObject resetPwd(String email, String password) {
		JSONObject result = new JSONObject();
		userService.changePwd(email, password);
		return result;
	}

	/* 根据id查询用户信息 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User userDetail(@PathVariable("id") Integer id) {
		return userService.getUserById(id);
	}

	/* 获取验证码 */
	@RequestMapping(value = "/getKey", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject searchUserByEmail(HttpServletRequest req) {
		JSONObject object = new JSONObject();
		object.put("key", captchaService.getGeneratedKey(req));
		return object;
	}

}
