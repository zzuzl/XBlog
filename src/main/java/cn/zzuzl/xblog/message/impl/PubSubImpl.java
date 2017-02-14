package cn.zzuzl.xblog.message.impl;

import cn.zzuzl.xblog.message.PubSub;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("pubSub")
public class PubSubImpl implements PubSub {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    // 发送消息
    public void sendMessage(String channel, String message) {
        redisTemplate.setValueSerializer(redisTemplate.getKeySerializer());
        redisTemplate.convertAndSend(channel, message);
        redisTemplate.setValueSerializer(redisTemplate.getDefaultSerializer());
    }
}
