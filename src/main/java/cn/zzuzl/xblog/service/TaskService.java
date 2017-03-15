package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.dao.*;
import cn.zzuzl.xblog.model.*;
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

    public List<Task> listTask(int taskType, List<Integer> taskStatusList) {
        List<Task> tasks = new ArrayList<Task>();
        tasks = taskDao.listTask(taskType, taskStatusList);
        return tasks;
    }


}
