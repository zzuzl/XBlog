package com.zzu.xblog.aop;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.Attention;
import com.zzu.xblog.model.Pager;
import com.zzu.xblog.service.MailService;
import com.zzu.xblog.service.UserService;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 标记切点
     */
    @Pointcut("execution(* com.zzu.xblog.service.*.insertArticle(..))")
    public void declarePointCutExpression() {
    }

    /**
     * afterreturning执行操作
     *
     * @param result
     */
    @AfterReturning(pointcut = "declarePointCutExpression()", returning = "result")
    private void sendEmailToFans(JSONObject result) {
        /*List<Attention> fansList = userService.getAllFans(article.getUser().getUserId());
        for (Attention attention : fansList) {
            mailService.sendEmailToFans(attention.getFrom().getEmail(), article, request);
        }*/
        System.out.println(result);
        System.out.println("------------------------------------------");
    }
}
