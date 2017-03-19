package cn.zzuzl.xblog.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class Utils {
    private static final String STRING = "abcdefghijklmnopqrstuvwxyz123456789";
    private static final int DEFAULT_LENGTH = 5;
    private static final Logger logger = LogManager.getLogger(Utils.class);

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
     * 判断一个字符串是否符合正则
     *
     * @param input
     * @param pattern
     * @return
     */
    public static boolean isMatch(String input, String pattern) {
        if (input == null || pattern == null) {
            return false;
        }

        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(input);
        return matcher.find();
    }

    /**
     * 转换合适的文件大小文本
     *
     * @param size
     * @return
     */
    public static String getSizeText(Long size) {
        String result = "0B";
        if (size != null && size > 0) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("count", 0);
            Long num = convertSize(size, map);
            switch (map.get("count")) {
                case 0:
                    result = num + "B";
                    break;
                case 1:
                    result = num + "KB";
                    break;
                case 2:
                    result = num + "MB";
                    break;
                case 3:
                    result = num + "GB";
                    break;
            }
        }
        return result;
    }

    private static Long convertSize(Long size, Map<String, Integer> map) {
        if (size < 1024) {
            return size;
        } else {
            Integer i = map.get("count");
            if (i > 3) {
                return size;
            } else {
                map.put("count", i + 1);
                return convertSize(size / 1024, map);
            }
        }
    }

    // 返回一个系统消息实例
    public static void main(String[] args) {
        // logger.info(isMatch("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8", ".*text/html.*"));
        System.out.println(getSizeText(10240 * 1024 * 1024L));
    }
}
