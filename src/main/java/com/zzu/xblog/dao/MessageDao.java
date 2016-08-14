package com.zzu.xblog.dao;

import com.zzu.xblog.model.Message;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MessageDao {
    int addMessage(Message message);

    List<Message> searchMessage(@Param("from") int from,
                                @Param("to") int to,
                                @Param("type") int type,
                                @Param("state") int state,
                                @Param("start") int start,
                                @Param("count") int count);

    int getCount(@Param("from") int from,
                 @Param("to") int to,
                 @Param("type") int type,
                 @Param("state") int state);

    int deleteMessage(@Param("id") int id);

    int updateState(@Param("id") int id,
                    @Param("state") int state);

    Message getById(@Param("id") int id);

    int getUnreadMsgCount(@Param("id") int id);
}
