package cn.zzuzl.xblog.dao;


import cn.zzuzl.xblog.model.task.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 任务相关的dao
 */
@Component
public interface TaskDao {
    List<Task> listTask(@Param("taskType") Integer taskType,
                        @Param("taskStatusList") List<Integer> taskStatusList);

    int updateTaskStatus(@Param("id") Long id,
                     @Param("id") Integer taskStatus);

    int insertTask(Task task);

}
