package cn.zzuzl.xblog.common.enums;

/**
 * Created by zhanglei53 on 2016/8/12.
 */
public enum MessageState {
    UNREAD(1, "未读"),
    READ(2, "已读"),
    DELETED(3, "已删除");

    private int value;
    private String title;

    private MessageState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle(int value) {
        for (MessageState messageState : MessageState.values()) {
            if (messageState.getValue() == value) {
                return messageState.getTitle();
            }
        }
        return null;
    }
}
