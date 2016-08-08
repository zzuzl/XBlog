package com.zzu.xblog.message;

import java.io.Serializable;

/**
 * 发布订阅功能
 */
public interface PubSub {
    void sendMessage(String channel, Serializable message);
}
