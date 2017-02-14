package cn.zzuzl.xblog.common.enums;

public enum MessageType {
    SYSTEM_MESSAGE(1, "系统消息"),
    ATTENTION_MESSAGE(2, "关注人消息"),
    ACTIVITY_MESSAGE(3, "活动消息");

    private int value;
    private String title;

    private MessageType(int value, String title) {
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
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getValue() == value) {
                return messageType.getTitle();
            }
        }
        return null;
    }
}
