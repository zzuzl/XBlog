package cn.zzuzl.xblog.worker;

import cn.zzuzl.xblog.common.enums.TaskStatusEnum;
import cn.zzuzl.xblog.common.enums.TaskTypeEnum;
import cn.zzuzl.xblog.model.Article;
import cn.zzuzl.xblog.model.Attention;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.model.task.BaskTaskTemplate;
import cn.zzuzl.xblog.model.task.Task;
import cn.zzuzl.xblog.model.vo.NewArticleVO;
import cn.zzuzl.xblog.service.MailService;
import cn.zzuzl.xblog.service.TaskService;
import cn.zzuzl.xblog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MailTaskExecutor extends BaskTaskTemplate<Task> {
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private TaskService taskService;

    private Logger logger = LogManager.getLogger(getClass());

    public List<Task> getTasks() {
        logger.info("list MailTask...");
        List<Integer> taskStatusList = Collections.emptyList();
        taskStatusList.add(TaskStatusEnum.INIT_TASK_STATUS.getValue());
        taskStatusList.add(TaskStatusEnum.FAIL_TASK_STATUS.getValue());
        return taskService.listTask(TaskTypeEnum.MAIL_TASK.getValue(), taskStatusList);
    }

    public void process(Task task) throws Exception {
        logger.info("MailTask process...");
        String taskData = task.getTaskData();
        if (!StringUtils.isEmpty(taskData)) {
            ObjectMapper mapper = new ObjectMapper();
            NewArticleVO newArticleVO = mapper.readValue(taskData, NewArticleVO.class);

            if (newArticleVO != null) {
                User user = new User();
                user.setUserId(newArticleVO.getUserId());

                Article article = new Article();
                article.setArticleId(newArticleVO.getArticleId());

                // 给粉丝发站内消息
                List<Attention> attentionList = userService.getAllFans(newArticleVO.getUserId());
                for (Attention attention : attentionList) {
                    User from = attention.getFrom();

                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("nickname", newArticleVO.getNickname());
                    model.put("title", newArticleVO.getArticleTitle());
                    mailService.sendEmailToFans(from.getEmail(), model);
                }

                logger.info("发送fans站内消息！");
            }

        }
    }
}
