package cn.zzuzl.xblog.service;

import cn.zzuzl.xblog.model.User;
import cn.zzuzl.xblog.util.ConfigProperty;
import cn.zzuzl.xblog.util.Utils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送service
 */
@Service
public class MailService {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private VelocityEngine velocityEngine;
    @Resource
    private ConfigProperty configProperty;

    /**
     * 发送重置密码邮件
     *
     * @param email
     * @param hash
     */
    public void sendResetPwdEmail(final String email, String hash) {
        String rootPath = configProperty.getRoot() + "/verify/resetPwd?hash=" + hash;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("url", rootPath);
        sendEmail(email, "tpl/common.vm", "重置密码", model);
    }

    /**
     * 发送注册激活邮件
     *
     * @param hash
     * @param user
     */
    public void sendRegisterEmail(final String hash, User user) {
        String rootPath = configProperty.getRoot() + "/verify/register?hash=" + hash;

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("url", rootPath);
        sendEmail(user.getEmail(), "tpl/common.vm", "注册激活", model);
    }

    /**
     * 给粉丝发邮件
     *
     * @param email
     * @param model
     */
    public void sendEmailToFans(final String email, Map<String, Object> model) {
        String rootPath = configProperty.getRoot() + "/p/" + model.get("articleId");
        model.put("url", rootPath);
        sendEmail(email, "tpl/fans.vm", "关注动态", model);
    }

    /**
     * 发送邮件核心方法
     *
     * @param email
     * @param location
     * @param model
     */
    private void sendEmail(final String email, final String location, final String subject, final Map<String, Object> model) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                final String nickname = MimeUtility.encodeText(configProperty.getUsername());
                final String from = nickname + "<" + configProperty.getFromAddress() + ">";
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(email);
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, location, "utf-8", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }
}
