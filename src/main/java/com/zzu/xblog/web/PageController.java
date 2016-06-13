package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.*;
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
import javax.servlet.http.HttpServletRequest;
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

    /* 关于 */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
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

    /* 用户个人信息 */
    @RequestMapping(value = "/setting/userInfo", method = RequestMethod.GET)
    public String blog(HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        if (user == null) {
            return Common.PAGE_404;
        }
        return "setting/info";
    }

    /* 发表文章 */
    @RequestMapping(value = "/setting/editArticle", method = RequestMethod.GET)
    public String editArticle(Model model) {
        List<Category> list = categoryService.listCategory();
        model.addAttribute("list", list);
        return "setting/editArticle";
    }

    /* 修改头像 */
    @RequestMapping(value = "/setting/changePhoto", method = RequestMethod.GET)
    public String changePhoto() {
        return "setting/changePhoto";
    }

    /* 用户个人中心 */
    @RequestMapping(value = "/u/{url}", method = RequestMethod.GET)
    public String personalCenter(@PathVariable("url") String url, Model model, HttpSession session) {
        User user = userService.searchUserByUrl(url);
        User loginUser = (User) session.getAttribute(Common.USER);
        if (user != null) {
            List<Attention> fans = userService.getAllFans(user.getUserId());
            model.addAttribute("fans", fans);
            model.addAttribute("attentions", userService.getAllAttentions(user.getUserId()));
            model.addAttribute(Common.USER, user);

            if (fans != null && loginUser != null) {
                for (Attention attention : fans) {
                    if (attention.getFrom().getUserId() == loginUser.getUserId()) {
                        model.addAttribute("attention", attention);
                        break;
                    }
                }
            }
        } else {
            return Common.PAGE_404;
        }
        return "personalCenter";
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
    public String articleDetail(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Article article = articleService.detail(id);
        User user = (User) session.getAttribute(Common.USER);
        if (article != null) {
            model.addAttribute("article", article);
            model.addAttribute("comments", commentService.listArticleComments(id));

            if (user != null) {
                List<Like> likes = articleService.getLikes(user.getUserId(), id);
                if (likes != null) {
                    model.addAttribute("like", likes.get(0));
                }

                model.addAttribute("attention", userService.getOneAttention(user.getUserId(), article.getUser().getUserId()));
            }
        } else {
            return Common.PAGE_404;
        }
        return "articleDetail";
    }

    /* error页面 */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(HttpServletRequest request) {
        System.out.println(request.getAttribute("javax.servlet.error.status_code"));
        System.out.println(request.getAttribute("javax.servlet.error.message"));
        return "common/404";
    }

    /* 404页面 */
    @RequestMapping(value = "/notFound", method = RequestMethod.GET)
    public String notFound() {
        return "common/404";
    }
}
