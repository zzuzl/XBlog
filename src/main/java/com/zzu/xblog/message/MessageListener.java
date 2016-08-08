package com.zzu.xblog.message;

import java.io.Serializable;

/**
 * 接收订阅的消息
 */
public interface MessageListener {
    void handleMessage(String message);
}
