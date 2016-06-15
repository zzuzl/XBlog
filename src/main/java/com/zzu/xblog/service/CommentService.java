package com.zzu.xblog.service;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.ArticleDao;
import com.zzu.xblog.dao.CommentDao;
import com.zzu.xblog.dao.DynamicDao;
import com.zzu.xblog.dto.Result;
import com.zzu.xblog.exception.DataException;
import com.zzu.xblog.model.Comment;
import com.zzu.xblog.model.Dynamic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional
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
