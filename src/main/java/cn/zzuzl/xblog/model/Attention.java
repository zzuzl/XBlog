package cn.zzuzl.xblog.model;

/**
 * Created by Administrator on 2016/6/1.
 */

import java.util.Date;

/**
 * 关注实体类
 */
public class Attention {
	private int id;
	private User from;
	private User to;
	private Date createTime;

	@Override
	public String toString() {
		return "Attention{" +
				"id=" + id +
				", from=" + from +
				", to=" + to +
				", createTime=" + createTime +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
