package com.zzu.xblog.dao;

import com.zzu.xblog.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 评论相关dao
 */
@Component
public interface CommentDao {

    /**
     * 获取文章对应的评论
     * @return
     */
    List<Comment> listArticleComments(int id);

    /**
     * 插入评论
     * @param comment
     * @return
     */
    int insertComment(Comment comment);
}
