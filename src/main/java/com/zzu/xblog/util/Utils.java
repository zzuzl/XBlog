package com.zzu.xblog.util;

import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class Utils {
    private static final String STRING = "abcdefghijklmnopqrstuvwxyz123456789";
    private static final int DEFAULT_LENGTH = 5;

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().equalsIgnoreCase("");
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String formateDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 默认的格式化日期
     *
     * @param date
     * @return
     */
    public static String formateDate(Date date) {
        return formateDate(date, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 日期字符串转化为日期对象
     *
     * @param s
     * @param pattern
     * @return
     */
    public static Date parseDate(String s, String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期字符串转日期对象的默认实现
     *
     * @param s
     * @return
     */
    public static Date parseDate(String s) {
        return parseDate(s, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String randomString(int length) {
        if (length <= 0 || length > 100) {
            return "";
        } else {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(STRING.charAt(random.nextInt(STRING.length())));
            }
            return sb.toString();
        }
    }

    /**
     * 生成随机字符串默认方法
     *
     * @return
     */
    public static String randomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            sb.append(STRING.charAt(random.nextInt(STRING.length())));
        }
        return sb.toString();
    }

    /**
     * 生成MD5
     *
     * @param input
     * @param random
     * @return
     */
    public static String MD5(String input, String random) {
        return DigestUtils.appendMd5DigestAsHex(input.getBytes(), new StringBuilder(random)).toString();
    }

    /**
     * 生成MD5默认方法
     *
     * @param input
     * @return
     */
    public static String MD5(String input) {
        return MD5(input, randomString(DEFAULT_LENGTH));
    }

    /**
     * 检查email合法性
     *
     * @param email
     * @return
     */
    public static boolean validEmail(String email) {
        boolean result = !isEmpty(email);
        if (result) {
            Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\\.[a-z]+(\\.[a-z]+)?");
            Matcher matcher = pattern.matcher(email);
            if (!matcher.find()) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 获取项目根路径
     *
     * @param request
     * @return
     */
    public static String getRootPath(HttpServletRequest request) {
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();

        return "http://" + serverName + ":" + port + contextPath;
    }

    /**
     * 返回uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取时间差（单位：minute）
     *
     * @param time
     * @return
     */
    public static long getGapMinute(long time) {
        return (System.currentTimeMillis() - time) / (1000 * 60);
    }

    /**
     * 获取主机ip
     *
     * @return
     */
    public static String getHostAddress() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return "";
    }

    public static void main(String[] args) {
        /*for(int i=0;i<10;i++) {
            System.out.println(randomString(10));
		}*/
		/*String random = randomString();
		System.out.println(random);
		String str = MD5("123456", random);
		System.out.println(str + "  " + str.length());*/

        //System.out.println(validEmail("672399171@qq.com"));
        //System.out.println(uuid());
        //System.out.println(MD5("672399171@qq.com"));
    }
}
