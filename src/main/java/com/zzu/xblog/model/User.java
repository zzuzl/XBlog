package com.zzu.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

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
				'}';
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
