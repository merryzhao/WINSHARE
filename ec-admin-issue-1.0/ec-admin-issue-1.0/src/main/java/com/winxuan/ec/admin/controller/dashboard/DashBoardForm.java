package com.winxuan.ec.admin.controller.dashboard;

import java.util.Date;

import com.winxuan.ec.support.util.MagicNumber;

/**
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-11-2下午01:12:17 -_-!
 */
public class DashBoardForm {
	private Date start;
	private Date end;
	private Long[] channel;
	private Long[] status;
	private int limit;
	private Date lastUpdateTime;

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public boolean channelIsEmpty() {
		return !(channel != null && channel.length > 0);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long[] getChannel() {
		return channel;
	}

	public void setChannel(Long[] channel) {
		this.channel = channel;
	}

	public int getEffectiveLimit() {
		if (this.limit == MagicNumber.ZERO) {
			return MagicNumber.FIVE_HUNDRED;
		}
		return this.limit;

	}
}
