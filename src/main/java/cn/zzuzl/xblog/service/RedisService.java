package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.dao.ArticleDao;
import cn.zzuzl.xblog.dao.UserDao;
import cn.zzuzl.xblog.model.Article;
import cn.zzuzl.xblog.model.Category;
import cn.zzuzl.xblog.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis存储service
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private ArticleDao articleDao;
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserDao userDao;
    private final Logger logger = LogManager.getLogger(getClass());
    private long lastTime = System.currentTimeMillis();

    /**
     * 更新博客浏览量
     *
     * @param id
     */
    public void updateViewCount(int id) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Long val = valueOperations.increment(Common.KEY_VIEW_COUNT + id, 1);

        long gap = (System.currentTimeMillis() - lastTime) / 1000;
        lastTime = System.currentTimeMillis();
        if (gap > 60 || val > 5) {
            articleDao.updateViewCount(id, val.intValue());
            valueOperations.set(Common.KEY_VIEW_COUNT + id, 0);
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
     * 获取所有的分类
     *
     * @return
     */
    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<Category>();
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();

        if (listOperations.size(Common.KEY_CATEGORY) < 1) {
            redisTemplate.delete(Common.KEY_CATEGORY);
            categories = categoryService.listCategory();
            if (categories != null) {
                redisTemplate.expire(Common.KEY_CATEGORY, 10, TimeUnit.MINUTES);
                listOperations.leftPushAll(Common.KEY_CATEGORY, categories);
            }
        } else {
            List<Object> objects = listOperations.range(Common.KEY_CATEGORY, 0, listOperations.size(Common.KEY_CATEGORY));
            if (objects != null && objects.size() > 0) {
                for (Object o : objects) {
                    if (o instanceof Category) {
                        categories.add((Category) o);
                    }
                }
            }
        }

        return categories;
    }

    /**
     * 获取用户排行
     *
     * @return
     */
    public List<User> getUserRank() {
        List<User> users = new ArrayList<User>();

        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        if (listOperations.size(Common.KEY_USERRANK) < 1) {
            redisTemplate.delete(Common.KEY_USERRANK);
            users = userDao.getUserRank(Common.DEFAULT_ITEM_COUNT);
            if (users != null) {
                Collections.reverse(users);
                redisTemplate.expire(Common.KEY_USERRANK, 10, TimeUnit.MINUTES);
                listOperations.leftPushAll(Common.KEY_USERRANK, users);
            }
        } else {
            List<Object> objects = listOperations.range(Common.KEY_USERRANK, 0, listOperations.size(Common.KEY_USERRANK));
            if (objects != null && objects.size() > 0) {
                for (Object o : objects) {
                    if (o instanceof User) {
                        users.add((User) o);
                    }
                }
            }
        }
        return users;
    }

    /**
     * 缓存中查文章，不存在则查库
     *
     * @param id
     * @return
     */
    public Article queryArticleFromCacheById(Integer id) {
        if (id == null) {
            return null;
        }
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Article article = (Article) hashOperations.get(Common.KEY_ARTICLEDETAIL, String.valueOf(id));
        if (article == null) {
            article = articleDao.detail(id);
            if (article != null) {
                redisTemplate.expire(Common.KEY_ARTICLEDETAIL, 30, TimeUnit.MINUTES);
                hashOperations.put(Common.KEY_ARTICLEDETAIL, String.valueOf(id), article);
            }
        }
        return article;
    }

    /**
     * 删除缓存
     *
     * @param key
     * @param hashKey
     */
    public Long deleteHashCache(Object key, Object hashKey) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            return null;
        }
        return redisTemplate.opsForHash().delete(String.valueOf(key), String.valueOf(hashKey));
    }
}
