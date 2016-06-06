package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.ArticleService;
import com.zzu.xblog.service.RedisService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章相关controller
 */
@Controller
@RequestMapping("article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private RedisService redisService;

    /* 获取第n页的文章，默认每页15篇 */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> listArticle(@PathVariable("page") Integer page) {
        return articleService.listArticle(page, Common.DEFAULT_ITEM_COUNT);
    }

    /* 获取文章详细信息 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Article detail(@PathVariable("id") Integer id, HttpSession session) {
        List<Integer> array = (List<Integer>) session.getAttribute(Common.ARTICLE_ARRAY);
        if (array == null) {
            array = new ArrayList<Integer>();
        }
        if (!array.contains(id)) {
            array.add(id);
            redisService.updateViewCount(id);
        }
        session.setAttribute(Common.ARTICLE_ARRAY, array);
        return articleService.detail(id);
    }

    /* 发表文章 */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject postArticle(@Valid @ModelAttribute("article") Article article,
                                  BindingResult bindingResult, HttpServletRequest request) {
        return articleService.insertArticle(article, request);
    }

    /* 修改文章 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateArticle(@Valid @ModelAttribute("article") Article article,
                                    BindingResult bindingResult, @PathVariable("id") Integer id) {
        article.setArticleId(id);
        return articleService.updateArticle(article);
    }

    /* 获取某个用户的第n页文章，默认每页15篇 */
    @RequestMapping(value = "/page/{page}/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> listArticle(@PathVariable("page") Integer page, @PathVariable("id") Integer id) {
        return articleService.listArticle(page, Common.DEFAULT_ITEM_COUNT, id);
    }

    /* 全局搜索文章 */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> searchArticle(String keyword) {
        System.out.println(keyword);
        return articleService.searchArticle(keyword);
    }
}
