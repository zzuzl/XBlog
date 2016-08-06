package com.zzu.xblog.web;

import com.zzu.xblog.common.Common;
import com.zzu.xblog.model.User;
import com.zzu.xblog.service.RedisService;
import com.zzu.xblog.service.UserService;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 邮件验证码controller
 */
@Controller
@RequestMapping("verify")
public class VerifyController {
    @Resource
    private RedisService redisService;
    @Resource
    private UserService userService;

    /**
     * 验证重置密码邮件验证码
     *
     * @param hash
     * @param model
     * @return
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
    public String resetPwd(@RequestParam("hash") String hash, Model model) {
        model.addAttribute(Common.SUCCESS, true);
        model.addAttribute(Common.MSG, "验证成功!");

        Map<String, Object> map = redisService.getLink(hash);
        if (map == null || hash == null || !hash.equals(map.get("hash"))) {
            model.addAttribute(Common.SUCCESS, false);
            model.addAttribute(Common.MSG, "验证失败,验证码错误或已失效!");
        } else {
            long time = (Long) map.get("time");
            if (Utils.getGapMinute(time) > 30) {
                model.addAttribute(Common.SUCCESS, false);
                model.addAttribute(Common.MSG, "验证码已过期!");
            }
        }
        model.addAttribute("hash", hash);

        return "/findPwd";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@RequestParam("hash") String hash, Model model) {
        model.addAttribute(Common.SUCCESS, false);

        Map<String, Object> map = redisService.getUserModel(hash);
        User user = null;
        if (map == null || (user = (User) map.get("user")) == null) {
            model.addAttribute(Common.MSG, "激活失败!");
        } else if (Utils.getGapMinute((Long) map.get("time")) > 30) {
            model.addAttribute(Common.MSG, "验证码已过期!");
        } else if (userService.register(user, (String) map.get("password")) < 1) {
            model.addAttribute(Common.MSG, "注册失败，邮箱已存在!");
        } else {
            model.addAttribute(Common.SUCCESS, true);
            model.addAttribute(Common.MSG, "恭喜你，注册成功!");
            redisService.deleteUserModel(hash);
        }

        return "check";
    }
}
