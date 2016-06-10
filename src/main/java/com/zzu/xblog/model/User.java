package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

import com.mysql.jdbc.Util;
import com.zzu.xblog.common.Common;
import com.zzu.xblog.util.Utils;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 用户实体类
 */
public class User {
	private int userId;
	private String email;
	private String hash;
	private String salt;
	private String nickname;
	private Date regTime;
	private int fansCount;
	private int attentionCount;
	private String photoSrc;
	private String motto;
	private String interest;
	private String sex;
	private String url;

	private static final int MAX_EMAIL_LENGTH = 45;
	private static final int MIN_PWD_LENGTH = 6;
	private static final int MAX_PWD_LENGTH = 30;
	private static final int MIN_NICKNAME_LENGTH = 1;
	private static final int MAX_NICKNAME_LENGTH = 20;

	public JSONObject valid(String password) {
		JSONObject result = new JSONObject();
		result.put(Common.SUCCESS, false);
		if (Utils.isEmpty(password) || password.length() < MIN_PWD_LENGTH ||
				password.length() > MAX_PWD_LENGTH) {
			result.put(Common.MSG, "密码长度不合法");
		} else {
			result = valid();
		}

		return result;
	}

	public JSONObject valid() {
		JSONObject result = new JSONObject();
		result.put(Common.SUCCESS, false);
		if (!Utils.validEmail(email) || email.length() > MAX_EMAIL_LENGTH) {
			result.put(Common.MSG, "email不合法");
		} else if (Utils.isEmpty(nickname) || nickname.length() < MIN_NICKNAME_LENGTH ||
				nickname.length() > MAX_NICKNAME_LENGTH) {
			result.put(Common.MSG, "昵称长度不合法");
		} else {
			result.put(Common.SUCCESS, true);
		}

		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", email='" + email + '\'' +
				", hash='" + hash + '\'' +
				", salt='" + salt + '\'' +
				", nickname='" + nickname + '\'' +
				", regTime=" + regTime +
				", fansCount=" + fansCount +
				", attentionCount=" + attentionCount +
				", photoSrc='" + photoSrc + '\'' +
				", motto='" + motto + '\'' +
				", interest='" + interest + '\'' +
				", sex='" + sex + '\'' +
				", url='" + url + '\'' +
				'}';
	}

	public void setPwd(String password) {
		String salt = Utils.randomString();
		String hash = Utils.MD5(password, salt);
		setHash(hash);
		setSalt(salt);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public int getFansCount() {
		return fansCount;
	}

	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}

	public int getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(int attentionCount) {
		this.attentionCount = attentionCount;
	}

	public String getPhotoSrc() {
		return photoSrc;
	}

	public void setPhotoSrc(String photoSrc) {
		this.photoSrc = photoSrc;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
