package cn.zzuzl.xblog.worker;

import cn.zzuzl.xblog.common.enums.TaskStatusEnum;
import cn.zzuzl.xblog.common.enums.TaskTypeEnum;
import cn.zzuzl.xblog.model.task.BaskTaskTemplate;
import cn.zzuzl.xblog.model.task.Task;
import cn.zzuzl.xblog.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Component
public class MailTaskExecutor extends BaskTaskTemplate<Task> {
    private Logger logger = LogManager.getLogger(getClass());

    @Resource
    private TaskService taskService;

    public List<Task> getTasks() {
        logger.info("list MailTask...");
        List<Integer> taskStatusList = Collections.emptyList();
        taskStatusList.add(TaskStatusEnum.INIT_TASK_STATUS.getValue());
        taskStatusList.add(TaskStatusEnum.FAIL_TASK_STATUS.getValue());
        return taskService.listTask(TaskTypeEnum.MAIL_TASK.getValue(), taskStatusList);
    }

    public void process(Task task) throws Exception {
        logger.info("MailTask process...");
    }
}
