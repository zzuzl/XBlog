package com.zzu.xblog.service;

import com.zzu.xblog.model.Article;
import com.zzu.xblog.model.User;
import com.zzu.xblog.util.Utils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Commit;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送service
 */
@Component
public class MailService {
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private VelocityEngine velocityEngine;
	private static final String FROM = "m15617536860@163.com";

	/**
	 * 发送重置密码邮件
	 *  @param email
	 * @param request
	 * @param userId
	 * @param hash
	 */
	public void sendResetPwdEmail(final String email, HttpServletRequest request, int userId, String hash) {
		String rootPath = Utils.getRootPath(request);
		rootPath += "/verify/resetPwd?id=" + userId + "&hash=" + hash;

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", rootPath);
		sendEmail(email, "tpl/common.vm", "重置密码", model);
	}

	/**
	 * 发送注册激活邮件
	 * @param salt
	 * @param user
	 * @param request
	 */
	public void sendRegisterEmail(final String salt, User user, HttpServletRequest request) {
		String rootPath = Utils.getRootPath(request);
		rootPath += "/verify/register?salt=" + salt;

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", rootPath);
		sendEmail(user.getEmail(), "tpl/common.vm", "注册激活", model);
	}

	/**
	 * 给粉丝发邮件
	 * @param email
	 * @param article
	 * @param request
     */
	public void sendEmailToFans(final String email, Article article, HttpServletRequest request) {
		String rootPath = Utils.getRootPath(request);
		rootPath += "/article/" + article.getArticleId();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", rootPath);
		model.put("nickname", article.getUser().getNickname());
		model.put("title", article.getTitle());
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
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom(FROM);
				message.setSubject(subject);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, location, "utf-8", model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
	}
}
