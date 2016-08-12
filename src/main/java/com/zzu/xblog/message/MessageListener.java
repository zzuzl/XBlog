package com.zzu.xblog.message;

/**
 * 接收订阅的消息
 */
public interface MessageListener {
    void handleMessage(String message);
}
