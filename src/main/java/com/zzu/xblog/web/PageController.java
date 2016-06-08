package com.zzu.xblog.web;

import com.zzu.xblog.model.Category;
import com.zzu.xblog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /* 主页 */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        List<Category> list = categoryService.listCategory();
        model.addAttribute("list",list);
        return "index";
    }

    /* 文件上传测试 */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String file() {
        return "file";
    }
}
