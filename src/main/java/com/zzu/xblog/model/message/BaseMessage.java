package com.zzu.xblog.model.message;

/**
 * Created by zhanglei53 on 2016/8/12.
 */
public abstract class BaseMessage {
    private String msgType;

    public BaseMessage(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
