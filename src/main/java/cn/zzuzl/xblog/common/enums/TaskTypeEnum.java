package cn.zzuzl.xblog.common.enums;

import org.springframework.util.StringUtils;

public enum  TaskTypeEnum {
    MAIL_TASK(1, "发送通知邮件");

    private int value;
    private String title;

    private TaskTypeEnum(int value, String title) {
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

    public Integer of(String title) {
        if(StringUtils.isEmpty(title)) {
            return null;
        }
        return TaskTypeEnum.valueOf(title).getValue();
    }
}
