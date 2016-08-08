package com.zzu.xblog.message.impl;

import com.zzu.xblog.message.PubSub;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.Serializable;

@Component("pubSub")
public class PubSubImpl implements PubSub {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    // 发送消息
    public void sendMessage(String channel, Serializable message) {
        redisTemplate.convertAndSend(channel,message);
    }
}
