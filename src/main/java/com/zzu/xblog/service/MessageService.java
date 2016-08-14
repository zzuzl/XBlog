package com.zzu.xblog.service;

import com.zzu.xblog.common.enums.MessageState;
import com.zzu.xblog.common.enums.MessageType;
import com.zzu.xblog.dao.MessageDao;
import com.zzu.xblog.dto.Result;
import com.zzu.xblog.model.Message;
import com.zzu.xblog.model.User;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageService {
    @Resource
    private MessageDao messageDao;

    private Result addMessage(Message message) {
        Result result = new Result(true);
        if (messageDao.addMessage(message) < 1) {
            result.setSuccess(false);
            result.setMsg("插入消息失败");
        }

        return result;
    }

    public void sendChangePwdMsg(User user) {
        Message message = new Message(null, user,
                MessageType.SYSTEM_MESSAGE.getValue(),
                MessageState.UNREAD.getValue(),
                "恭喜你，密码修改成功，请牢记密码！",
                "恭喜你，密码已重置");
        addMessage(message);
    }

    public void sendRegSuccess(User user) {
        Message message = new Message(null, user,
                MessageType.SYSTEM_MESSAGE.getValue(),
                MessageState.UNREAD.getValue(),
                "恭喜你，注册成功，赶紧发篇博客吧！",
                "恭喜你，注册成功");
        addMessage(message);
    }

    public void sendNewArticle(User user, String title, String content) {
        Message message = new Message(null, user,
                MessageType.SYSTEM_MESSAGE.getValue(),
                MessageState.UNREAD.getValue(),
                content, title);
        addMessage(message);
    }

    public Result<Message> searchMessages(int userId, int type, int state, int page, int pageSize) {
        Result<Message> result = new Result<Message>(page, pageSize);
        result.setSuccess(true);
        if (pageSize < 1) {
            result.setSuccess(false);
            result.setMsg("页数错误");
        } else {
            result.setList(messageDao.searchMessage(0, userId, type, state, (page - 1) * pageSize, pageSize));
            result.setTotalItem(messageDao.getCount(0, userId, type, state));
        }
        return result;
    }

    public Message getById(int id) {
        return messageDao.getById(id);
    }

    public Result updateMsgState(int id, int state) {
        Result result = new Result(true);
        if (state == MessageState.UNREAD.getValue() ||
                state == MessageState.READ.getValue() ||
                state == MessageState.DELETED.getValue()) {
            if(messageDao.updateState(id,state) < 1) {
                result.setSuccess(false);
                result.setMsg("更新状态失败");
            }
        } else {
            result.setSuccess(false);
            result.setMsg("状态不合法");
        }
        return result;
    }
}
