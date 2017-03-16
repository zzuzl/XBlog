package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.dao.*;
import cn.zzuzl.xblog.model.task.Task;
import cn.zzuzl.xblog.model.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务相关service
 */
@Service
public class TaskService {
    @Resource
    private TaskDao taskDao;
    private Logger logger = LogManager.getLogger(getClass());

    public List<Task> listTask(int taskType, List<Integer> taskStatusList) {
        List<Task> tasks = new ArrayList<Task>();
        tasks = taskDao.listTask(taskType, taskStatusList);
        return tasks;
    }

    public Result updateTaskStatus(Long id, Integer taskStatus) {
        Result result = new Result(true);
        try {
            if (taskDao.updateTaskStatus(id, taskStatus) <= 0) {
                result.setSuccess(false);
                result.setMsg("更新失败");
            }
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
            result.setMsg("更新失败");
        }

        return result;
    }

    public Result insertTask(Task task) {
        Result result = new Result(true);
        try {
            taskDao.insertTask(task);
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
            result.setMsg("插入失败");
        }
        return result;
    }
}
