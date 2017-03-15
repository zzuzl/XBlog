package cn.zzuzl.xblog.dao;


import cn.zzuzl.xblog.model.Article;
import cn.zzuzl.xblog.model.Like;
import cn.zzuzl.xblog.model.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务相关的dao
 */
@Component
public interface TaskDao {
    List<Task> listTask(@Param("taskType") Integer taskType,
                        @Param("taskStatusList") List<Integer> taskStatusList);

    int finishedTask(@Param("id") Long id,
                     @Param("id") Integer taskStatus);

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
    Article getPre(@Param("articleId") int articleId, @Param("userId") int userId);

    /**
     * 获取下一篇
     *
     * @param articleId
     * @param userId
     * @return
     */
    Article getNext(@Param("articleId") int articleId, @Param("userId") int userId);

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
}
