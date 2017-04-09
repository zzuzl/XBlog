package cn.zzuzl.xblog.util;

import cn.zzuzl.xblog.model.User;

/**
 * Created by Administrator on 2017/4/8.
 */
public class DomainUtil {

    /**
     * 是否为当前用户
     * @param user
     * @return
     */
    public static boolean isCurrentUser(User user) {
        if(user == null || LoginContext.getLoginUserId() == null) {
            return false;
        } else {
            return user.getUserId() == LoginContext.getLoginUserId();
        }
    }
}
