package cn.zzuzl.xblog.worker;

import cn.zzuzl.xblog.common.enums.TaskStatusEnum;
import cn.zzuzl.xblog.common.enums.TaskTypeEnum;
import cn.zzuzl.xblog.service.TaskService;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class MailTaskExecutor {

    @Resource
    private TaskService taskService;

    public void test() {
        System.out.println("test");
        List<Integer> taskStatusList = Collections.emptyList();
        taskStatusList.add(TaskStatusEnum.INIT_TASK_STATUS.getValue());
        taskStatusList.add(TaskStatusEnum.FAIL_TASK_STATUS.getValue());
        taskService.listTask(TaskTypeEnum.MAIL_TASK.getValue(), taskStatusList);
    }

}
