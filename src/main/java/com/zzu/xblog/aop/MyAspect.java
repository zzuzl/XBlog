package com.zzu.xblog.aop;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.dto.Result;
import com.zzu.xblog.model.*;
import com.zzu.xblog.service.DynamicService;
import com.zzu.xblog.service.MailService;
import com.zzu.xblog.service.UserService;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * aop
 */
@Component
@Aspect
public class MyAspect {
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private DynamicService dynamicService;

    /**
     * 标记发表文章的切点
     */
    @Pointcut("execution(* com.zzu.xblog.service.*.insertArticle(..))")
    public void insertArticlePointcut() {
    }

    /**
     * 标记发表评论的切点
     */
    @Pointcut("execution(* com.zzu.xblog.service.*.insertComment(..))")
    public void insertCommentPointcut() {
    }

    /**
     * 插入文章后
     *
     * @param result
     */
    @AfterReturning(pointcut = "insertArticlePointcut()", returning = "result")
    private void afterInsertArticle(Result result) {
        Map<String, Object> data = result.getData();
        HttpServletRequest request = (HttpServletRequest) data.get(Common.REQUEST);
        Article article = (Article) data.get("article");
        if (article != null) {
            User user = article.getUser();
            if (user != null) {
                Map<String, Object> model = new HashMap<>();
                model.put("userId", user.getUserId());
                model.put("nickname", user.getNickname());
                model.put("articleId", article.getArticleId());
                model.put("title", article.getTitle());
                sendEmailToFans(user.getUserId(), model, request);

                Dynamic dynamic = new Dynamic(user, article, Common.POST_OPERATOR, article.getDescription());
                insertDynamic(dynamic);
            }
        }

        System.out.println("------------------给fans发送邮件,插入动态------------------------");
    }

    /**
     * 给fans发送邮件
     *
     * @param userId
     * @param model
     * @param request
     */
    private void sendEmailToFans(int userId, Map<String, Object> model, HttpServletRequest request) {
        List<Attention> fansList = userService.getAllFans(userId);
        for (Attention attention : fansList) {
            mailService.sendEmailToFans(attention.getFrom().getEmail(), model, request);
        }
    }

    /**
     * 插入动态
     *
     * @param dynamic
     */
    private void insertDynamic(Dynamic dynamic) {
        dynamicService.insertDynamic(dynamic);
    }

    /**
     * 插入评论后
     *
     * @param result
     */
    @AfterReturning(pointcut = "insertCommentPointcut()", returning = "result")
    private void afterInsertComment(Result result) {
        Map<String, Object> data = result.getData();
        Comment comment = (Comment) data.get("comment");
        if (comment != null) {
            User user = comment.getUser();
            if (user != null) {
                Dynamic dynamic = new Dynamic(user, comment.getArticle(), Common.COMMENT_OPERATOR, comment.getContent());
                insertDynamic(dynamic);
            }
        }

        System.out.println("------------------发表评论,插入动态------------------------");
    }
}
