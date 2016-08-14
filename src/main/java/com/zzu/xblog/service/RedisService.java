package com.zzu.xblog.service;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.ArticleDao;
import com.zzu.xblog.dao.UserDao;
import com.zzu.xblog.model.Category;
import com.zzu.xblog.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * redis存储service
 */
@Service
public class RedisService implements InitializingBean {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private ArticleDao articleDao;
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserDao userDao;
    private final Logger logger = LogManager.getLogger(getClass());
    private long lastTime = System.currentTimeMillis();
    private long lastSyncUserRankMinute = System.currentTimeMillis() / (1000 * 60);

    /**
     * 更新博客浏览量
     *
     * @param id
     */
    public void updateViewCount(int id) {
        Object temp = redisTemplate.boundHashOps("viewCount").get(id + "");
        int num = 0;
        if (temp != null) {
            num = (Integer) temp;
        }

        logger.info(id + "----" + num);

        redisTemplate.boundHashOps("viewCount").put(id + "", num + 1);
        long gap = (System.currentTimeMillis() - lastTime) / 1000;
        lastTime = System.currentTimeMillis();

        if (num > 5 || gap > 60) {
            backupData();
        }
    }

    /**
     * 备份redis到mysql
     */
    private void backupData() {
        Map<Object, Object> map = redisTemplate.boundHashOps("viewCount").entries();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            int articleId = Integer.parseInt((String) entry.getKey());
            int count = (Integer) entry.getValue();
            if (count > 0) {
                articleDao.updateViewCount(articleId, count);
                redisTemplate.boundHashOps("viewCount").put(entry.getKey(), 0);
            }
        }
    }

    /**
     * 存储验证码
     *
     * @param email
     * @param hash
     */
    public void addLink(String email, String hash) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", email);
        model.put("time", System.currentTimeMillis());
        model.put("hash", hash);

        redisTemplate.boundHashOps(Common.OPERATE_RESET_PWD).put(hash, model);
    }

    /**
     * 临时存储注册未完成的user
     *
     * @param user
     */
    public void addUser(String hash, User user, String password) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("time", System.currentTimeMillis());
        model.put("password", password);
        model.put("user", user);

        model.put("user", user);
        model.put("password", password);
        redisTemplate.boundHashOps(Common.REGISTER).put(hash, model);
    }

    /**
     * 返回user
     *
     * @param hash
     * @return
     */
    public Map<String, Object> getUserModel(String hash) {
        return (Map<String, Object>) redisTemplate.boundHashOps(Common.REGISTER).get(hash);
    }

    /**
     * 删除user model
     *
     * @param hash
     */
    public void deleteUserModel(String hash) {
        redisTemplate.boundHashOps(Common.REGISTER).delete(hash);
    }

    /**
     * 根据id获取验证码
     *
     * @param hash
     * @return
     */
    public Map<String, Object> getLink(String hash) {
        return (Map<String, Object>) redisTemplate.boundHashOps(Common.OPERATE_RESET_PWD).get(hash);
    }

    /**
     * 删除验证码
     *
     * @param hash
     */
    public void deleteLink(String hash) {
        redisTemplate.boundHashOps(Common.OPERATE_RESET_PWD).delete(hash);
    }

    /**
     * 把mysql的分类信息同步到redis
     *
     */
    public void syncCategory() {
        logger.debug("---------------syncCategory----------------");
        List<Category> categoryList = categoryService.listCategory();
        redisTemplate.delete("category");

        if(categoryList != null) {
            for (Category category : categoryList) {
                redisTemplate.boundListOps("category").leftPushAll(category);
            }
        }
    }

    /**
     * 获取所有的分类
     *
     * @return
     */
    public List<Object> getAllCategory() {
        List<Object> categories = null;
        BoundListOperations<Object, Object> listOperations = redisTemplate.boundListOps("category");
        if (listOperations == null || listOperations.size() < 1) {
            syncCategory();
            categories = getAllCategory();
        } else {
            categories = listOperations.range(0, listOperations.size());
        }

        return categories;
    }

    /**
     * 同步用户排行
     */
    private void syncUserRank() {
        logger.debug("------------------syncUserRank---------------------");
        List<User> users = userDao.getUserRank(Common.DEFAULT_ITEM_COUNT);

        redisTemplate.delete("userRank");
        if (users != null) {
            for (User user : users) {
                redisTemplate.boundListOps("userRank").leftPushAll(user);
            }
        }
    }

    /**
     * 获取用户排行，每12小时更新
     *
     * @return
     */
    public List<Object> getUserRank() {
        long current = System.currentTimeMillis() / (1000 * 60);

        if ((current - lastSyncUserRankMinute) / 60 > 12) {
            lastSyncUserRankMinute = current;
            syncUserRank();
        }

        List<Object> users = null;
        BoundListOperations<Object, Object> userRank = redisTemplate.boundListOps("userRank");
        if (userRank == null || userRank.size() < 1) {
            syncUserRank();
            users = getUserRank();
        } else {
            users = userRank.range(0, userRank.size());
        }
        return users;
    }

    public void afterPropertiesSet() throws Exception {
        syncCategory();
        syncUserRank();
    }
}
