package cn.zzuzl.xblog.dao;

import cn.zzuzl.xblog.model.Dynamic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 动态相关dao
 */
@Component
public interface DynamicDao {

    /**
     * 获取一个用户的动态
     * @param userId
     * @param start
     * @param count
     * @return
     */
    List<Dynamic> getDynamics(@Param("userId") int userId,
                              @Param("start") int start,
                              @Param("count") int count);

    /**
     * 获取动态的个数
     * @param userId
     * @return
     */
    int getDynamicCount(int userId);

    /**
     * 插入动态
     * @param dynamic
     * @return
     */
    int insertDynamic(Dynamic dynamic);

    /**
     * 删除动态
     * @param id
     * @return
     */
    int deleteDynamic(int id);

    /**
     * 通过id获取动态
     * @param id
     * @return
     */
    Dynamic getDynamicById(int id);
}
