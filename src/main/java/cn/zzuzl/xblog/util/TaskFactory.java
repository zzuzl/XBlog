package cn.zzuzl.xblog.util;

import cn.zzuzl.xblog.common.enums.TaskStatusEnum;
import cn.zzuzl.xblog.model.task.Task;

public class TaskFactory {

    public static Task newTask(Integer taskType) {
        Task task = new Task();
        task.setTaskType(taskType);
        task.setTaskStatus(TaskStatusEnum.INIT_TASK_STATUS.getValue());
        return task;
    }
}
