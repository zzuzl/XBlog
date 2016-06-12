package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.Category;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.ArticleService;
import com.zzu.xblog.service.CategoryService;
import com.zzu.xblog.service.CommentService;
import com.zzu.xblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    @Resource
    private CommentService commentService;

    /* 主页 */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        List<Category> list = categoryService.listCategory();
        model.addAttribute("list", list);
        return "index";
    }

    /* 登录 */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /* 注册 */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "zc";
    }

    /* 关于 */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    /* 发表文章 */
    @RequestMapping(value = "/editArticle", method = RequestMethod.GET)
    public String editArticle(Model model) {
        List<Category> list = categoryService.listCategory();
        model.addAttribute("list", list);
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
        } else {
            return Common.PAGE_404;
        }
        return "blog";
    }

    /* 文章详情 */
    @RequestMapping(value = "/p/{id}", method = RequestMethod.GET)
    public String articleDetail(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.detail(id);
        if (article != null) {
            model.addAttribute("article", article);
            model.addAttribute("comments", commentService.listArticleComments(id));
        } else {
            return Common.PAGE_404;
        }
        return "articleDetail";
    }

    /* 用户个人信息 */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String blog(HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return Common.PAGE_404;
        }
        return "info";
    }

    /* 404页面 */
    @RequestMapping(value = "/notFound", method = RequestMethod.GET)
    public String notFound() {
        return "common/404";
    }
}
