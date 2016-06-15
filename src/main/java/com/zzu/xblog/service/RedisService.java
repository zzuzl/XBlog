package com.zzu.xblog.service;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.ArticleDao;
import com.zzu.xblog.model.Category;
import com.zzu.xblog.model.User;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis存储service
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private ArticleDao articleDao;
    private long lastTime = System.currentTimeMillis();

    /**
     * 更新博客浏览量
     *
     * @param id
     */
    public void updateViewCount(int id) {
        Object temp = redisTemplate.boundHashOps("viewCount").get(id + "");
        int num = 0;
        if (temp != null) {
            num = (int) temp;
        }

        System.out.println(id + "----" + num);

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
            int count = (int) entry.getValue();
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
     * @param categoryList
     */
    public void syncCategory(List<Category> categoryList) {
        for(Category category : categoryList) {
            redisTemplate.boundListOps("category").leftPushAll(category);
        }
    }

    /**
     * 获取所有的分类
     *
     * @return
     */
    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        BoundListOperations<Object, Object> listOperations = redisTemplate.boundListOps("category");
        if (listOperations == null || listOperations.size() < 1) {
            return null;
        } else {
            for (long i = 0; i < listOperations.size(); i++) {
                Category category = (Category) listOperations.index(i);
                categories.add(category);
            }
        }

        return categories;
    }
}
