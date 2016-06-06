package com.zzu.xblog.service;

/**
 * Created by ZhangLei on 2016/6/4.
 */

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.ArticleDao;
import com.zzu.xblog.dao.LuceneDao;
import com.zzu.xblog.dao.UserDao;
import com.zzu.xblog.exception.DataException;
import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文章相关service
 */
@Service
public class ArticleService {
    @Resource
    private ArticleDao articleDao;
    @Resource
    private UserDao userDao;
    @Resource
    private LuceneDao luceneDao;

    /**
     * 获取第page页文章列表
     *
     * @param page
     * @param count
     * @return
     */
    public List<Article> listArticle(int page, int count) {
        if (count < 1) {
            count = Common.DEFAULT_ITEM_COUNT;
        }
        if (page < 1) {
            page = 1;
        }
        int start = (page - 1) * count;
        return articleDao.listArticle(start, count, 0);
    }

    /**
     * 获取一篇文章详细信息
     *
     * @param id
     * @return
     */
    public Article detail(int id) {
        if (id < 1) {
            return null;
        }
        return articleDao.detail(id);
    }

    /**
     * 发表文章
     *
     * @param article
     * @return
     */
    public JSONObject insertArticle(Article article, HttpServletRequest request) {
        JSONObject result = article.valid();

        if (result.getBoolean(Common.SUCCESS)) {
            if (article.getUser() != null) {
                if (articleDao.insertArticle(article) < 1) {
                    result.put(Common.SUCCESS, false);
                    result.put(Common.MSG, "发表失败!");
                } else {
                    User user = userDao.getUserById(article.getUser().getUserId());
                    article.setUser(user);
                    result.put(Common.DATA, article);
                    result.put(Common.REQUEST, request);
                }
            } else {
                result.put(Common.SUCCESS, false);
                result.put(Common.MSG, "发表失败，用户不存在!");
            }
        }

        return result;
    }

    /**
     * 修改文章
     *
     * @param article
     * @return
     */
    public JSONObject updateArticle(Article article) {
        JSONObject result = article.valid();
        if (result.getBoolean(Common.SUCCESS)) {
            if (articleDao.updateArticle(article) < 1) {
                result.put(Common.SUCCESS, false);
                result.put(Common.MSG, "数据操作错误");
            }
        }
        return result;
    }

    /**
     * 获取某个用户发表的第n页文章
     *
     * @param page
     * @param count
     * @param userId
     * @return
     */
    public List<Article> listArticle(int page, int count, int userId) {
        if (userId < 1) {
            return null;
        }
        if (count < 1) {
            count = Common.DEFAULT_ITEM_COUNT;
        }
        if (page < 1) {
            page = 1;
        }
        int start = (page - 1) * count;
        return articleDao.listArticle(start, count, userId);
    }

    /**
     * 插入点赞
     *
     * @param userId
     * @param articleId
     * @return
     */
    @Transactional
    public JSONObject insertLike(int userId, int articleId) {
        JSONObject result = new JSONObject();
        result.put(Common.SUCCESS, false);

        if (userId < 1 || articleId < 1) {
            result.put(Common.MSG, "用户未登录或文章出错");
        } else {
            if (articleDao.insertLike(userId, articleId) > 0 &&
                    articleDao.updateLikeCount(articleId, 1) > 0) {
                result.put(Common.SUCCESS, true);
            } else {
                result.put(Common.MSG, "数据操作错误");
                throw new DataException();
            }
        }
        return result;
    }

    /**
     * 查询文章
     * @param keyword
     * @return
     */
    public List<Article> searchArticle(String keyword) {
        return luceneDao.searchArticle(keyword);
    }
}
