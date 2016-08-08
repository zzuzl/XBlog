package com.zzu.xblog.message.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzu.xblog.message.MessageListener;
import com.zzu.xblog.model.Attention;
import com.zzu.xblog.model.User;
import com.zzu.xblog.model.message.NewArticleMessage;
import com.zzu.xblog.service.MailService;
import com.zzu.xblog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("messageListener")
public class MessageListenerImpl implements MessageListener {
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void handleMessage(String message) {
        if (message == null) {
            logger.error("message is null");
        } else {
            logger.info(message);
            ObjectMapper mapper = new ObjectMapper();
            NewArticleMessage newArticleMessage = null;
            try {
                newArticleMessage = mapper.readValue(message,NewArticleMessage.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(newArticleMessage != null) {
                List<Attention> attentionList = userService.getAllFans(newArticleMessage.getUserId());
                for (Attention attention : attentionList) {
                    User from = attention.getFrom();

                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("articleId", newArticleMessage.getArticleId());
                    model.put("nickname", newArticleMessage.getNickname());
                    model.put("title", newArticleMessage.getArticleTitle());
                    mailService.sendEmailToFans(from.getEmail(), model, null);
                }
                logger.info("发送fans邮件完毕！");
            } else {
                logger.error("newArticleMessage is null");
            }
        }
    }
}
