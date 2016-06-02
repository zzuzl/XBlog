package com.zzu.xblog.service;

/**
 * Created by Administrator on 2016/6/2.
 */

import com.zzu.xblog.model.User;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * redis存储service
 */
@Service
public class RedisService {
	@Resource
	private RedisTemplate<Object, Object> redisTemplate;

	/**
	 * 存储验证码
	 *
	 * @param userId
	 * @param email
	 * @param salt
	 * @param hash
	 */
	public void addLink(int userId, String email, String salt, String hash) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("email", email);
		model.put("time", System.currentTimeMillis());
		model.put("salt", salt);
		model.put("hash", hash);

		redisTemplate.boundListOps(userId).leftPush(model);
	}

	/**
	 * 临时存储注册未完成的user
	 *
	 * @param user
	 */
	public void addUser(String salt, User user,String password) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", System.currentTimeMillis());
		model.put("password", password);
		model.put("user", user);
		redisTemplate.boundListOps(salt).leftPush(model);
	}

	/**
	 * 返回user
	 *
	 * @param salt
	 * @return
	 */
	public Map<String, Object> getUserModel(String salt) {
		return (Map<String, Object>) redisTemplate.boundListOps(salt).leftPop();
	}

	/**
	 * 根据id获取验证码
	 *
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getLink(int userId) {
		return (Map<String, Object>) redisTemplate.boundListOps(userId).leftPop();
	}
}
