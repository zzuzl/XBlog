package cn.zzuzl.xblog.model.task;

import cn.zzuzl.xblog.common.enums.TaskStatusEnum;
import cn.zzuzl.xblog.dao.TaskDao;
import cn.zzuzl.xblog.util.SpringContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/15.
 */
public class Task implements BaseTask {
    private Logger logger = LogManager.getLogger(getClass());
    private Long id;
    private Integer taskType;
    private String taskData;
    private Integer retryCount;
    private Integer taskStatus;
    private Date createTime;
    private Date updateTime;

    private TaskDao taskDao;

    public Task() {
        taskDao = (TaskDao) SpringContextUtil.getContext().getBean(TaskDao.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void lock() {
        logger.info("lock...");
        taskDao.updateTaskStatus(id, TaskStatusEnum.LOCK_TASK_STATUS.getValue());
    }

    public void success() {
        logger.info("success...");
        taskDao.updateTaskStatus(id, TaskStatusEnum.SUCCESS_TASK_STATUS.getValue());
    }

    public void fail() {
        logger.info("fail...");
        taskDao.updateTaskStatus(id, TaskStatusEnum.FAIL_TASK_STATUS.getValue());
    }
}
