package com.zzu.xblog.service;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dao.ArticleDao;
import com.zzu.xblog.dao.CommentDao;
import com.zzu.xblog.exception.DataException;
import com.zzu.xblog.model.Comment;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
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

    /**
     * 获取文章对应的评论
     *
     * @return
     */
    public List<Comment> listArticleComments(int id) {
        if (id < 1) {
            return null;
        }
        return commentDao.listArticleComments(id);
    }

    /**
     * 插入评论(事务)
     *
     * @param comment
     * @return
     */
    @Transactional
    public JSONObject insertComment(Comment comment) {
        JSONObject result = comment.valid();
        if (result.getBoolean(Common.SUCCESS)) {
            if (commentDao.insertComment(comment) > 0 &&
                    articleDao.updateCommentCount(comment.getArticle().getArticleId(), 1) > 0) {
                result.put(Common.MSG, "发表成功!");
            } else {
                result.put(Common.SUCCESS, false);
                result.put(Common.MSG, "发表失败!");
                throw new DataException();
            }
        } else {
            result.put(Common.SUCCESS, false);
            result.put(Common.MSG, "发表失败!");
        }

        return result;
    }
}
