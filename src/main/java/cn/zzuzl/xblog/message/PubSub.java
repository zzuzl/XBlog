package cn.zzuzl.xblog.message;

/**
 * 发布订阅功能
 */
public interface PubSub {
    void sendMessage(String channel, String message);
}
