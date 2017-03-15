package cn.zzuzl.xblog.common.enums;

import org.springframework.util.StringUtils;

public enum TaskStatusEnum {
    INIT_TASK_STATUS(0, "初始化"),
    LOCK_TASK_STATUS(1, "锁定"),
    SUCCESS_TASK_STATUS(2, "完成"),
    FAIL_TASK_STATUS(3, "失败");

    private int value;
    private String title;

    private TaskStatusEnum(int value, String title) {
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
        return TaskStatusEnum.valueOf(title).getValue();
    }
}
