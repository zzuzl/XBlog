package cn.zzuzl.xblog.web;

import cn.zzuzl.xblog.service.CaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码生成controller
 */
@Controller
@RequestMapping("captcha")
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    /**
     * 生成验证码图片
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generateCaptcha(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        captchaService.captcha(req, res);
    }

    /**
     * 验证验证码是否正确,如果正确返回200，否则返回411
     *
     * @param captcha
     * @return
     */
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public void verifyCaptcha(@RequestParam("captcha") String captcha,
                                  HttpServletResponse response, HttpServletRequest req) {
        if (captchaService.getGeneratedKey(req).equalsIgnoreCase(captcha)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
        }
    }
}
