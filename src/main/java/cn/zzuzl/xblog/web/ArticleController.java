package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.Article;
import cn.zzuzl.xblog.model.Pager;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.service.ArticleService;
import cn.zzuzl.xblog.service.RedisService;
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
    public Pager<Article> listArticle(@PathVariable("page") Integer page, Integer cate) {
        return articleService.listArticle(page, Common.DEFAULT_ITEM_COUNT, cate);
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
    public Result postArticle(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult) {
        return articleService.insertArticle(article);
    }

    /* 修改文章 */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public Result editArticle(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult) {
        return articleService.updateArticle(article);
    }

    /* 删除文章 */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteArticle(@RequestParam("id") Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Common.USER);
        Article article = articleService.detail(id);

        Result result = new Result();
        if (user == null || article == null ||
                user.getUserId() != article.getUser().getUserId()) {
            result.setMsg("身份错误");
        } else {
            result = articleService.deleteArticle(id);
        }
        return result;
    }

    /* 文章点赞 */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public Result postArticle(@RequestParam("articleId") Integer articleId, @RequestParam("userId") Integer userId) {
        return articleService.insertLike(userId, articleId);
    }

    /* 修改文章 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateArticle(@Valid @ModelAttribute("article") Article article,
                                BindingResult bindingResult, @PathVariable("id") Integer id) {
        article.setArticleId(id);
        return articleService.updateArticle(article);
    }

    /* 获取某个用户的第n页文章，默认每页15篇 */
    @RequestMapping(value = "/page/{page}/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> listMyArticle(@PathVariable("page") Integer page, @PathVariable("id") Integer id) {
        return articleService.listMyArticle(page, Common.DEFAULT_ITEM_COUNT, id);
    }

    /* 搜索文章 */
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Pager<Article> searchArticle(@RequestParam("keyword") String keyword,
                                        @PathVariable("page") Integer page) {
        return articleService.searchArticle(page, Common.DEFAULT_ITEM_COUNT, keyword);
    }
}
