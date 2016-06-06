package com.zzu.xblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 负责页面跳转的controller
 */
@Controller
public class PageController {

    /* 主页 */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        return "index";
    }

    /* 文件上传测试 */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String file() {
        return "file";
    }
}
