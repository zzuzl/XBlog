package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.common.Common;
import cn.zzuzl.xblog.model.vo.Result;
import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.service.CaptchaService;
import cn.zzuzl.xblog.service.MailService;
import cn.zzuzl.xblog.service.RedisService;
import cn.zzuzl.xblog.service.UserService;
import cn.zzuzl.xblog.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 邮件发送controller
 */
@Controller
@RequestMapping("mail")
public class MailController {
    @Resource
    private MailService mailService;
    @Resource
    private RedisService redisService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private UserService userService;

    /**
     * 发送邮件
     *
     * @param operate
     * @param email
     * @param captcha
     * @param request
     * @return
     */
    @RequestMapping(value = "/send/{operate}", method = RequestMethod.POST)
    @ResponseBody
    public Result sendEmail(@PathVariable("operate") String operate,
                            @RequestParam("email") String email,
                            @RequestParam("captcha") String captcha,
                            HttpServletRequest request) {
        Result result = new Result();
        String key = captchaService.getGeneratedKey(request);
        if (!captcha.equalsIgnoreCase(key)) {
            result.setMsg("验证码错误!");
        } else {
            User user = userService.searchUserByEmail(email);

            if (user == null) {
                result.setMsg("您当前不是我们的用户，请先注册!");
            } else {
                if (Common.OPERATE_RESET_PWD.equalsIgnoreCase(operate)) {
                    String hash = Utils.MD5(email);
                    mailService.sendResetPwdEmail(email, request, hash);
                    redisService.addLink(email, hash);

                    result.setSuccess(true);
                    result.setMsg("邮件已成功发送至" + email + ",请注意查收!");
                } else {
                    result.setMsg("操作错误!");
                }
            }
        }

        return result;
    }
}
