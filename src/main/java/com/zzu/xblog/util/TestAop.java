package com.zzu.xblog.util;

import com.zzu.xblog.service.ArticleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/6/10.
 */
public class TestAop {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleService articleService = context.getBean("articleService", ArticleService.class);
        articleService.listArticle(1, 15, 0);
    }
}
