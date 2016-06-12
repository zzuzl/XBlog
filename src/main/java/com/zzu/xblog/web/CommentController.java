package com.zzu.xblog.web;

import com.zzu.xblog.dto.Result;
import com.zzu.xblog.model.Comment;
import com.zzu.xblog.service.CommentService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 评论相关controller
 */
@Controller
@RequestMapping("comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    /* 查询文章对应评论 */
    /*@RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> list(@PathVariable("articleId") Integer articleId) {
        return commentService.listArticleComments(articleId);
    }*/

    /* 发表评论 */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Result postComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult bindingResult) {
        return commentService.insertComment(comment);
    }
}
