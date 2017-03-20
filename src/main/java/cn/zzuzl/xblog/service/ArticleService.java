package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.common.enums.TaskTypeEnum;
import cn.zzuzl.xblog.dao.*;
import cn.zzuzl.xblog.model.*;
import cn.zzuzl.xblog.model.task.Task;
import cn.zzuzl.xblog.util.TaskFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.vo.NewArticleVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

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
    @Resource
    private DynamicDao dynamicDao;
    @Resource
    private TaskDao taskDao;
    @Resource
    private RedisService redisService;
    @Resource
    private PlatformTransactionManager transactionManager;

    private Logger logger = LogManager.getLogger(getClass());

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
        if (article != null) {
            article.setPre(articleDao.getPre(id, article.getUser().getUserId()));
            article.setNext(articleDao.getNext(id, article.getUser().getUserId()));
        }
        return article;
    }

    /**
     * 发表文章
     *
     * @param article
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public Result insertArticle(Article article) {
        Result result = article.valid();

        if (result.isSuccess()) {
            if (article.getUser() != null) {
                if (articleDao.insertArticle(article) < 1) {
                    result.setSuccess(false);
                    result.setMsg("发表失败!");
                } else {
                    User user = userDao.getUserById(article.getUser().getUserId());
                    article.setUser(user);

                    String taskData = null;
                    try {
                        taskData = buildTaskData(article);
                    } catch (JsonProcessingException e) {
                        logger.error(e);
                        e.printStackTrace();
                    }

                    if (!StringUtils.isEmpty(taskData)) {
                        Task task = TaskFactory.newTask(TaskTypeEnum.MAIL_TASK.getValue());
                        task.setTaskData(taskData);
                        taskDao.insertTask(task);
                    }

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
            } else {
                // 删除文章缓存
                redisService.deleteHashCache(Common.KEY_ARTICLEDETAIL, article.getArticleId());
            }
        }
        return result;
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    public Result deleteArticle(int id) {
        Result result = new Result();
        if (id < 1 || articleDao.deleteArticle(id) < 1) {
            result.setMsg("文章不存在");
        } else {
            // 删除文章缓存
            redisService.deleteHashCache(Common.KEY_ARTICLEDETAIL, id);
            result.setSuccess(true);
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
    public Result insertLike(int userId, int articleId) {
        Result result = new Result();

        if (userId < 1 || articleId < 1) {
            result.setMsg("用户未登录或文章出错");
        } else {
            TransactionStatus status = null;
            try {
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                status = transactionManager.getTransaction(def);

                // 插入like
                if (articleDao.insertLike(userId, articleId) > 0 &&
                        articleDao.updateLikeCount(articleId, 1) > 0) {
                    result.setSuccess(true);
                } else {
                    result.setMsg("您已赞过");
                }
                transactionManager.commit(status);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
                result.setSuccess(false);
                result.setMsg("error:" + e.getMessage());
                if (status != null) {
                    transactionManager.rollback(status);
                }
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

    /**
     * 构建任务数据
     *
     * @param article
     * @return
     * @throws JsonProcessingException
     */
    public String buildTaskData(Article article) throws JsonProcessingException {
        String taskData = "";
        if (article != null) {
            ObjectMapper mapper = new ObjectMapper();
            int userId = 0;
            String url = "";
            String nickName = "";
            if (article.getUser() != null) {
                userId = article.getUser().getUserId();
                url = article.getUser().getUrl();
                nickName = article.getUser().getNickname();
            }
            NewArticleVO newArticleVO = new NewArticleVO(
                    article.getArticleId(),
                    userId,
                    article.getTitle(),
                    article.getDescription(),
                    url,
                    nickName);
            taskData += mapper.writeValueAsString(newArticleVO);
        }

        return taskData;
    }
}
