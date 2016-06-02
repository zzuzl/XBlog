package com.zzu.xblog.dao;

import com.zzu.xblog.model.User;
import org.springframework.stereotype.Component;

/**
 * 用户dao
 */
@Component
public interface UserDao {

	/**
	 * 添加用户
	 *
	 * @param user
	 * @return
	 */
	int addUser(User user);

	/**
	 * 根据email查找用户，找不到返回null
	 *
	 * @param email
	 * @return
	 */
	User searchUserByEmail(String email);

	/**
	 * 修改用户hash
	 *
	 * @param email
	 * @param hash
	 */
	void changePwd(String email, String salt, String hash);

	/**
	 * 根据id查找用户
	 * @param id
	 * @return
	 */
	User getUserById(int id);
}
