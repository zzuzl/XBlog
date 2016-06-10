package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.Category;
import com.zzu.xblog.model.Pager;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.ArticleService;
import com.zzu.xblog.service.CategoryService;
import com.zzu.xblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 负责页面跳转的controller
 */
@Controller
public class PageController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;

    /* 主页 */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        List<Category> list = categoryService.listCategory();
        model.addAttribute("list", list);
        return "index";
    }

    /* 关于 */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    /* 发表文章 */
    @RequestMapping(value = "/editArticle", method = RequestMethod.GET)
    public String editArticle() {
        return "editArticle";
    }

    /* 修改头像 */
    @RequestMapping(value = "/changePhoto", method = RequestMethod.GET)
    public String changePhoto() {
        return "changePhoto";
    }

    /* 用户的博客 */
    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    public String blog(@PathVariable("url") String url, Model model) {
        User user = userService.searchUserByUrl(url);
        if (user != null) {
            List<Article> list = articleService.listMyArticle(1, 100, user.getUserId());
            model.addAttribute("list", list);
            model.addAttribute(Common.USER, user);
        }
        return "blog";
    }

    /* 文件上传测试 */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String file() {
        return "file";
    }
}
