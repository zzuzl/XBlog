package cn.zzuzl.xblog.common;

/**
 * Created by Administrator on 2016/6/1.
 */

/**
 * 静态常量存放
 */
public class Common {
    // 前后端交互
    public static final String SUCCESS = "success";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String USER = "user";
    public static final String FILENAME = "filename";
    public static final String SIMPLE_FILENAME = "simpleFilename";
    public static final String FILEPATH = "filePath";
    public static final String ARTICLE_ARRAY = "articleArray";
    public static final String UNREAD_MSG_COUNT = "unreadMsgCount";

    // 错误页面
    public static final String PAGE_404 = "common/404";

    // 邮件验证类型
    public static final String OPERATE_RESET_PWD = "resetpwd";
    public static final String REGISTER = "register";

    // 个人动态operator
    public static final String POST_OPERATOR = "发表博客：";
    public static final String COMMENT_OPERATOR = "评论博客：";

    // 每页默认显示个数
    public static final int DEFAULT_ITEM_COUNT = 15;
    // 默认头像图片尺寸
    public static final int PHOTO_SIZE = 128;

    // 上传图片缩放宽度限制
    public static final int MAX_PICTURE_WIDTH = 900;

    // 权限码
    public static final String AUTH_USER_LOGIN = "auth_user_login";

    // 项目名称
    public static final String APP_NAME = "xblog";

    // redis缓存key
    public static final String KEY_VIEW_COUNT = "viewCount";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_USERRANK = "userRank";
    public static final String KEY_ARTICLEDETAIL = "articleDetail";
}
