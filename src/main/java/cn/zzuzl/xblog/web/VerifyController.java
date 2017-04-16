package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.service.UserService;
import cn.zzuzl.xblog.service.RedisService;
import cn.zzuzl.xblog.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
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
        } else {
            Result result = userService.register(user, (String) map.get("password"), hash);
            model.addAttribute(Common.SUCCESS, result.isSuccess());
            model.addAttribute(Common.MSG, result.getMsg());
            if (result.isSuccess()) {
                redisService.deleteUserModel(hash);
            }
        }

        return "check";
    }
}
