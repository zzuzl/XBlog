package cn.zzuzl.xblog.util;

import cn.zzuzl.xblog.model.User;

/**
 * Created by Administrator on 2017/4/8.
 */
public class LoginContext {
    private static ThreadLocal<User> context = new ThreadLocal<User>();

    public static ThreadLocal<User> getContext() {
        return context;
    }

    public static User getLoginUser() {
        if(context != null) {
            return context.get();
        }
        return null;
    }

    public static Integer getLoginUserId() {
        User user = getLoginUser();
        if(user != null) {
            return user.getUserId();
        }
        return null;
    }

}
