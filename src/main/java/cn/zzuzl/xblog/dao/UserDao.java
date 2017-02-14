package cn.zzuzl.xblog.dao;

import cn.zzuzl.xblog.model.Attention;
import cn.zzuzl.xblog.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户dao
 */
@Component
public interface UserDao {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据email查找用户，找不到返回null
     *
     * @param email
     * @return
     */
    User searchUserByEmail(String email);

    /**
     * 根据url查找用户，找不到返回null
     *
     * @param url
     * @return
     */
    User searchUserByUrl(String url);

    /**
     * 修改用户hash
     *
     * @param userId
     * @param salt
     * @param hash
     */
    void changePwd(@Param("userId") int userId, @Param("salt") String salt, @Param("hash") String hash);

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    User getUserById(int id);

    /**
     * 修改用户头像
     *
     * @param photoSrc
     * @param userId
     * @return
     */
    int changePhoto(@Param("photoSrc") String photoSrc, @Param("userId") int userId);

    /**
     * 返回一个用户的所有粉丝
     *
     * @param userId
     * @return
     */
    List<Attention> getAllFans(int userId);

    /**
     * 获取一个用户所有关注的人
     *
     * @param userId
     * @return
     */
    List<Attention> getAllAttentions(int userId);

    /**
     * 获取指定的关注
     *
     * @param from
     * @param to
     * @return
     */
    Attention getOneAttention(@Param("from") int from, @Param("to") int to);

    /**
     * 添加关注
     *
     * @param from
     * @param to
     * @return
     */
    int insertAttention(@Param("from") int from, @Param("to") int to);

    /**
     * 删除关注
     *
     * @param from
     * @param to
     * @return
     */
    int deleteAttention(@Param("from") int from, @Param("to") int to);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 更新关注的数量
     *
     * @param userId
     * @param count
     * @return
     */
    int updateAttentionCount(@Param("userId") int userId, @Param("count") int count);

    /**
     * 更新粉丝的数量
     *
     * @param userId
     * @param count
     * @return
     */
    int updateFansCount(@Param("userId") int userId, @Param("count") int count);

    /**
     * 重新设置关注和粉丝数量
     *
     * @param userId
     * @return
     */
    int resetCounts(@Param("userId") int userId);

    /**
     * 获取主页用户排行 count个
     *
     * @param count
     * @return
     */
    List<User> getUserRank(@Param("count") int count);
}
