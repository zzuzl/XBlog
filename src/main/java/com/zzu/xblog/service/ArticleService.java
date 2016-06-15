package com.zzu.xblog.service;

/**
 * Created by ZhangLei on 2016/6/4.
 */

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.ArticleDao;
import com.zzu.xblog.dao.DynamicDao;
import com.zzu.xblog.dao.LuceneDao;
import com.zzu.xblog.dao.UserDao;
import com.zzu.xblog.dto.Result;
import com.zzu.xblog.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private DynamicDao dynamicDao;

    /**
     * 获取第page页文章列表
     *
     * @param page
     * @param count
     * @return
     */
    public Pager<Article> listArticle(int page, int count, int cate) {
        if (count < 1) {
            count = Common.DEFAULT_ITEM_COUNT;
        }
        if (page < 1) {
            page = 1;
        }
        int start = (page - 1) * count;
        List<Article> articles = articleDao.listArticle(start, count, 0, cate);
        Pager<Article> pager = new Pager<Article>(articleDao.getArticleCount(cate), page);
        pager.setItemList(articles);

        return pager;
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
        Article article = articleDao.detail(id);
        article.setPre(articleDao.getPre(id, article.getUser().getUserId()));
        article.setNext(articleDao.getNext(id, article.getUser().getUserId()));
        return article;
    }

    /**
     * 发表文章
     *
     * @param article
     * @param request
     * @return
     */
    public Result insertArticle(Article article, HttpServletRequest request) {
        Result result = article.valid();

        if (result.isSuccess()) {
            if (article.getUser() != null) {
                if (articleDao.insertArticle(article) < 1) {
                    result.setSuccess(false);
                    result.setMsg("发表失败!");
                } else {
                    User user = userDao.getUserById(article.getUser().getUserId());
                    article.setUser(user);

                    // 发送邮件

                    Dynamic dynamic = new Dynamic(user, article, Common.POST_OPERATOR, article.getDescription());
                    dynamicDao.insertDynamic(dynamic);
                }
            } else {
                result.setSuccess(false);
                result.setMsg("发表失败，用户不存在!");
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
    public Result updateArticle(Article article) {
        Result result = article.valid();
        if (result.isSuccess()) {
            if (articleDao.updateArticle(article) < 1) {
                result.setSuccess(false);
                result.setMsg("数据操作错误");
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
    public List<Article> listMyArticle(int page, int count, int userId) {
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
        return articleDao.listArticle(start, count, userId, -1);
    }

    /**
     * 插入点赞
     *
     * @param userId
     * @param articleId
     * @return
     */
    @Transactional
    public Result insertLike(int userId, int articleId) {
        Result result = new Result();

        if (userId < 1 || articleId < 1) {
            result.setMsg("用户未登录或文章出错");
        } else {
            if (articleDao.insertLike(userId, articleId) > 0 &&
                    articleDao.updateLikeCount(articleId, 1) > 0) {
                result.setSuccess(true);
            } else {
                result.setMsg("您已赞过");
            }
        }
        return result;
    }

    /**
     * 获取所有的赞
     *
     * @param userId
     * @param articleId
     * @return
     */
    public List<Like> getLikes(int userId, int articleId) {
        if (userId < 1) {
            userId = 0;
        }
        if (articleId < 1) {
            articleId = 0;
        }
        if (userId == 0 && articleId == 0) {
            return null;
        }
        return articleDao.getLikes(userId, articleId);
    }

    /**
     * 查询文章
     *
     * @param page
     * @param count
     * @param keyword
     * @return
     */
    public Pager<Article> searchArticle(int page, int count, String keyword) {
        return luceneDao.searchArticle(page, count, keyword);
    }
}
