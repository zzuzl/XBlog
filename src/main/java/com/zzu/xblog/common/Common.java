package com.zzu.xblog.common;

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
	public static final String REQUEST = "request";
	public static final String ARTICLE_ARRAY = "articleArray";

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

}
