package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.common.annotation.Auth;
import cn.zzuzl.xblog.common.annotation.Logined;
import cn.zzuzl.xblog.exception.ErrorCode;
import cn.zzuzl.xblog.exception.ServiceException;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.Article;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.service.ArticleService;
import cn.zzuzl.xblog.service.RedisService;
import cn.zzuzl.xblog.util.JsonUtil;
import cn.zzuzl.xblog.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章相关controller
 */
@Controller
@RequestMapping("article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private RedisService redisService;
    private Logger logger = LogManager.getLogger(getClass());

    /* 获取第n页的文章，默认每页15篇 */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Article> listArticle(@PathVariable("page") Integer page, Integer cate) {
        logger.info("listArticle(),page:{},cate:{}", page, cate);
        cate = cate == null ? 0 : cate;
        Result<Article> result = null;
        try {
            result = articleService.listArticle(page, Common.DEFAULT_ITEM_COUNT, cate);
        } catch (ServiceException e) {
            logger.error("listArticle(),page:{},cate:{}", page, cate);
            throw e;
        } catch (Exception e) {
            logger.error("listArticle(),page:{},cate:{}", page, cate);
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "查询出错");
        }
        return result;
    }

    /* 获取文章详细信息 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Article detail(@PathVariable("id") Integer id, HttpSession session) {
        logger.info("detail():id:{}", id);
        Article article = null;
        try {
            List<Integer> array = (List<Integer>) session.getAttribute(Common.ARTICLE_ARRAY);
            if (array == null) {
                array = new ArrayList<Integer>();
            }
            if (!array.contains(id)) {
                array.add(id);
                redisService.updateViewCount(id);
            }
            session.setAttribute(Common.ARTICLE_ARRAY, array);
            article = articleService.detail(id);
        } catch (ServiceException e) {
            logger.error("article detail error:id:{}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("article detail error:id:{}", id, e);
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "查询出错");
        }

        return article;
    }

    /* 发表文章 */
    @Auth
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Result postArticle(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult, @Logined User user) {
        logger.info("postArticle(),article:", JsonUtil.toJson(article));
        Result result = null;
        if (!isMe(article.getUser(), user)) {
            throw new ServiceException(ErrorCode.USER_ERROR, ErrorCode.USER_ERROR.getDefaultMsg());
        } else {
            try {
                result = articleService.insertArticle(article);
            } catch (ServiceException e) {
                logger.error("postArticle,error:article:{}", JsonUtil.toJson(article), e);
                throw e;
            } catch (Exception e) {
                logger.error("postArticle,error:article:{}", JsonUtil.toJson(article), e);
                throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "发表文章失败");
            }
        }
        return result;
    }

    /* 修改文章 */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public Result editArticle(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult, @Logined User user) {
        logger.info("editArticle,article:{}", JsonUtil.toJson(article));
        Result result = null;
        try {
            Article article1 = articleService.detail(article.getArticleId());
            if (article1 == null || !isMe(article1.getUser(), user)) {
                throw new ServiceException(ErrorCode.USER_ERROR, ErrorCode.USER_ERROR.getDefaultMsg());
            }
            result = articleService.updateArticle(article);
        } catch (ServiceException e) {
            logger.error("editArticle,error!article:{}", JsonUtil.toJson(article), e);
            throw e;
        } catch (Exception e) {
            logger.error("editArticle,error!article:{}", JsonUtil.toJson(article), e);
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "修改失败");
        }

        return result;
    }

    /* 删除文章 */
    @Auth
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteArticle(@RequestParam("id") Integer id, HttpSession session, @Logined User user) {
        logger.info("deleteArticle:id:{}", id);
        Result result = null;

        try {
            Article article = articleService.detail(id);
            if (!isMe(article.getUser(), user)) {
                throw new ServiceException(ErrorCode.USER_ERROR, ErrorCode.USER_ERROR.getDefaultMsg());
            }
            result = articleService.deleteArticle(id);
        } catch (ServiceException e) {
            logger.error("deleteArticle error!,id:{}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("deleteArticle error!,id:{}", id, e);
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "删除失败");
        }

        return result;
    }

    /* 文章点赞 */
    @Auth
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public Result insertLike(@RequestParam("articleId") Integer articleId, @RequestParam("userId") Integer userId, @Logined User user) {
        logger.info("insertLike,articleId:{},userId:{}", articleId, userId);
        Result result = null;
        try {
            if (Utils.isEmpty(userId, user) || user.getUserId() != userId) {
                throw new ServiceException(ErrorCode.USER_ERROR, ErrorCode.USER_ERROR.getDefaultMsg());
            }
            result = articleService.insertLike(userId, articleId);
        } catch (ServiceException e) {
            logger.error("insertLike,error!articleId:{},userId:{}", articleId, userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("insertLike,error!articleId:{},userId:{}", articleId, userId, e);
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "点赞失败");
        }

        return articleService.insertLike(userId, articleId);
    }

    /* 修改文章 */
    @Auth
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateArticle(@Valid @ModelAttribute("article") Article article,
                                BindingResult bindingResult, @PathVariable("id") Integer id, @Logined User user) {
        Article article1 = articleService.detail(id);
        if (article1 == null || !isMe(article1.getUser(), user)) {
            throw new ServiceException(ErrorCode.USER_ERROR, ErrorCode.USER_ERROR.getDefaultMsg());
        }
        article.setArticleId(id);
        return articleService.updateArticle(article);
    }

    /* 获取某个用户的第n页文章，默认每页15篇 */
    @RequestMapping(value = "/page/{page}/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> listMyArticle(@PathVariable("page") Integer page, @PathVariable("id") Integer id) {
        return articleService.listMyArticle(page, Common.DEFAULT_ITEM_COUNT, id);
    }

    /* 搜索文章 */
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Article> searchArticle(@RequestParam("keyword") String keyword,
                                         @PathVariable("page") Integer page) {
        return articleService.searchArticle(page, Common.DEFAULT_ITEM_COUNT, keyword);
    }

    /* 是否是当前登录人操作的 */
    private boolean isMe(User user, User user1) {
        if (Utils.isEmpty(user, user1)) {
            return false;
        } else {
            return user.getUserId() == user1.getUserId();
        }
    }
}
