package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.dao.ArticleDao;
import cn.zzuzl.xblog.dao.CommentDao;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.dao.DynamicDao;
import cn.zzuzl.xblog.exception.DataException;
import cn.zzuzl.xblog.model.Comment;
import cn.zzuzl.xblog.model.Dynamic;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 评论相关service
 */
@Service
public class CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private ArticleDao articleDao;
    @Resource
    private DynamicDao dynamicDao;

    /**
     * 获取文章对应的评论
     *
     * @return
     */
    public List<Comment> listArticleComments(int id) {
        if (id < 1) {
            return null;
        }

        List<Comment> comments = commentDao.listArticleComments(id);

        for (Comment comment : comments) {
            if (comment.getpId() > 0) {
                for (Comment temp : comments) {
                    if (comment.getpId() == temp.getCommentId()) {
                        comment.setParent(temp);
                        break;
                    }
                }
            }
        }

        return comments;
    }

    /**
     * 插入评论(事务)
     *
     * @param comment
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result insertComment(Comment comment) {
        Result result = comment.valid();
        if (result.isSuccess()) {
            if (commentDao.insertComment(comment) > 0 &&
                    articleDao.updateCommentCount(comment.getArticle().getArticleId(), 1) > 0) {
                result.setMsg("发表成功!");

                // 插入动态
                Dynamic dynamic = new Dynamic(comment.getUser(), comment.getArticle(), Common.COMMENT_OPERATOR, comment.getContent());
                dynamicDao.insertDynamic(dynamic);
            } else {
                result.setSuccess(false);
                result.setMsg("发表失败!");
                throw new DataException();
            }
        } else {
            result.setSuccess(false);
            result.setMsg("发表失败!");
        }

        return result;
    }
}
