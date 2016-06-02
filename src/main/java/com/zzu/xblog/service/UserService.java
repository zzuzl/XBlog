package com.zzu.xblog.service;

import com.zzu.xblog.dao.UserDao;
import com.zzu.xblog.model.User;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;

import static com.zzu.xblog.util.Utils.MD5;
import static com.zzu.xblog.util.Utils.randomString;

@Component
public class UserService {
	@Resource
	private UserDao userDao;

	/**
	 * 使用email
	 *
	 * @param email
	 * @param password
	 * @return
	 */
	public User login(String email, String password) {
		User user = userDao.searchUserByEmail(email);
		if (user == null) {
			return null;
		} else {
			String hash = Utils.MD5(password, user.getSalt());
			if (hash.equals(user.getHash())) {
				user.setSalt(null);
				user.setHash(null);
				return user;
			} else {
				return null;
			}
		}
	}

	/**
	 * 根据email查询用户
	 *
	 * @param email
	 * @return
	 */
	public User searchUserByEmail(String email) {
		User user = userDao.searchUserByEmail(email);
		if (user != null) {
			user.setSalt(null);
			user.setHash(null);
		}
		return user;
	}

	/**
	 * 用户注册，返回插入用户的数量，主键自动存于userId
	 *
	 * @param user
	 * @return
	 */
	public int register(User user, String password) {
		if (Utils.isEmpty(user.getEmail()) ||
				Utils.isEmpty(user.getNickname()) ||
				Utils.isEmpty(password)) {
			return 0;
		}

		if (searchUserByEmail(user.getEmail()) == null) {
			user.setPwd(password);
			return userDao.addUser(user);
		} else {
			return 0;
		}
	}

	/**
	 * 修改密码
	 *
	 * @param email
	 * @param newPassword
	 */
	public void changePwd(String email, String newPassword) {
		String salt = Utils.randomString();
		String hash = Utils.MD5(newPassword, salt);
		userDao.changePwd(email, salt, hash);
	}

	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

}
