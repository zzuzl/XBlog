package com.zzu.xblog.service;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.UserDao;
import com.zzu.xblog.exception.DataException;
import com.zzu.xblog.model.Attention;
import com.zzu.xblog.model.User;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 用户相关service
 */
@Component
public class UserService {
    @Resource
    private UserDao userDao;

    /**
     * 使用email登录
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
     *
     * @param id
     * @return
     */
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }


    /**
     * 修改用户头像
     *
     * @param photoSrc
     * @param userId
     * @return
     */
    public JSONObject changePhoto(String photoSrc, int userId) {
        JSONObject result = new JSONObject();
        result.put(Common.SUCCESS, false);

        if (userId < 1) {
            result.put(Common.MSG, "用户身份错误");
        } else {
            if (userDao.changePhoto(photoSrc, userId) > 0) {
                result.put(Common.SUCCESS, true);
            } else {
                result.put(Common.MSG, "数据操作错误");
            }
        }
        return result;
    }

    /**
     * 返回一个用户的所有粉丝
     *
     * @param userId
     * @return
     */
    public List<Attention> getAllFans(int userId) {
        if (userId < 1) {
            return null;
        }
        return userDao.getAllFans(userId);
    }

    /**
     * 获取一个用户所有关注的人
     *
     * @param userId
     * @return
     */
    public List<Attention> getAllAttentions(int userId) {
        if (userId < 1) {
            return null;
        }
        return userDao.getAllAttentions(userId);
    }

    /**
     * 添加关注
     *
     * @param from
     * @param to
     * @return
     */
    @Transactional
    public JSONObject insertAttention(int from, int to) {
        JSONObject result = new JSONObject();
        result.put(Common.SUCCESS, false);

        if (from < 1 || to < 1) {
            result.put(Common.MSG, "用户验证失败");
        } else {
            if (userDao.insertAttention(from, to) > 0 &&
                    userDao.updateAttentionCount(from, 1) > 0 &&
                    userDao.updateFansCount(to, 1) > 0) {
                result.put(Common.SUCCESS, true);
            } else {
                result.put(Common.MSG, "数据操作失败");
                throw new DataException();
            }
        }

        return result;
    }

    /**
     * 删除关注
     *
     * @param from
     * @param to
     * @return
     */
    @Transactional
    public JSONObject deleteAttention(int from, int to) {
        JSONObject result = new JSONObject();
        result.put(Common.SUCCESS, false);

        if (from < 1 || to < 1) {
            result.put(Common.MSG, "用户验证失败");
        } else {
            if (userDao.deleteAttention(from, to) > 0 &&
                    userDao.updateAttentionCount(from, -1) > 0 &&
                    userDao.updateFansCount(to, -1) > 0) {
                result.put(Common.SUCCESS, true);
            } else {
                result.put(Common.MSG, "数据操作失败");
                throw new DataException();
            }
        }

        return result;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public JSONObject updateUser(User user) {
        JSONObject result = user.valid();
        if (result.getBoolean(Common.SUCCESS)) {
            if (userDao.updateUser(user) < 1) {
                result.put(Common.SUCCESS, false);
                result.put(Common.MSG, "数据操作错误");
            }
        }
        return result;
    }
}
