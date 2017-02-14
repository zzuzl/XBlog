package cn.zzuzl.xblog.model;

import java.util.Date;

public class Message {
    private long id;
    private User from;
    private User to;
    private int type;
    private int state;
    private String content;
    private String title;
    private Date sendTime;

    public Message(User from, User to, int type, int state, String content, String title) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.state = state;
        this.content = content;
        this.title = title;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
