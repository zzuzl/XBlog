package com.zzu.xblog.dao;


import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.Like;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文章相关的dao
 */
@Component
public interface ArticleDao {
    /**
     * 获取文章列表，从start开始的count篇，默认按发表时间排序(userId不为null时，获取某个用户的文章)
     *
     * @param start
     * @param count
     * @return
     */
    List<Article> listArticle(@Param("start") int start,
                              @Param("count") int count,
                              @Param("userId") int userId,
                              @Param("cate") int cate);

    /**
     * 获取一篇文章详细信息
     *
     * @param id
     * @return
     */
    Article detail(int id);

    /**
     * 获取上一篇
     *
     * @param articleId
     * @param userId
     * @return
     */
    Article getPre(@Param("articleId") int articleId,@Param("userId") int userId);

    /**
     * 获取下一篇
     *
     * @param articleId
     * @param userId
     * @return
     */
    Article getNext(@Param("articleId") int articleId,@Param("userId") int userId);

    /**
     * 插入文章
     *
     * @param article
     * @return
     */
    int insertArticle(Article article);

    /**
     * 更新文章的评论数量
     *
     * @param articleId
     * @param count
     * @return
     */
    int updateCommentCount(@Param("articleId") int articleId, @Param("count") int count);

    /**
     * 修改文章
     *
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    int deleteArticle(int id);

    /**
     * 插入点赞
     *
     * @param userId
     * @param articleId
     * @return
     */
    int insertLike(@Param("userId") int userId, @Param("articleId") int articleId);

    /**
     * 获取所有的赞
     *
     * @param userId
     * @param articleId
     * @return
     */
    List<Like> getLikes(@Param("userId") int userId, @Param("articleId") int articleId);

    /**
     * 更新点赞量
     *
     * @param articleId
     * @param count
     * @return
     */
    int updateLikeCount(@Param("articleId") int articleId, @Param("count") int count);

    /**
     * 更新浏览量
     *
     * @param articleId
     * @param count
     * @return
     */
    int updateViewCount(@Param("articleId") int articleId, @Param("count") int count);

    /**
     * 文章数量
     *
     * @return
     */
    int getArticleCount(@Param("cate") int cate);
}
