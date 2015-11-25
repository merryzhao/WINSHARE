/*
 * @(#)OrderPayForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.admin.controller.customer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.model.channel.Channel;

/**
 * description
 * 
 * @author chenlong
 * @version 1.0,2011-8-2
 */
public class SearchForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 名称
	private String name;
	// 会员类型（等级）
	private Short grade;
	// 会员来源
	private Long source;
	// 用户渠道
	private Long channel;
	// 时间查询类型，注册时间，最后登录时间，最后购买时间
	private String timeSlot;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;

	public String getName() {
		if ("".equals(name)) {
			name = null;
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getStartTime() {
		if(StringUtils.isBlank(startTime)){
			return null;
		}
		return startTime+" 00:00:00";
	}
	
	private List<String> getNames(String name){
		if(StringUtils.isBlank(name)){
			return null;
		}
		String[] names=name.split("\r\n");
		List<String> nameStrs=new ArrayList<String>();
		for(String s:names){
			if(!StringUtils.isBlank(s)){
				nameStrs.add(s);
			}
		}
		return nameStrs.isEmpty()?null:nameStrs;
	}

	public Date getDateByString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date timeDate = new Date();
		try {
			if (StringUtils.isBlank(time)) {
				timeDate = null;
			} else {
				timeDate = sdf.parse(time);
			}
		} catch (ParseException e) {
			time = null;
		}
		return timeDate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		if(StringUtils.isBlank(endTime)){
			return null;
		}
		return this.endTime+" 23:59:59";
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 返回controller需要的Map
	public Map<String, Object> getParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("names", getNames(this.getName()));
		parameters.put("grade", this.getGrade());
		parameters.put("source", this.getSource());
		if ("registerTime".equals(timeSlot)) {
			parameters.put("registerTimeStart",
					getDateByString(this.getStartTime()));
			parameters.put("registerTimeEnd",
					getDateByString(this.getEndTime()));
		} else if ("lastLoginTime".equals(timeSlot)) {
			parameters.put("lastLoginTimeStart",
					getDateByString(this.getStartTime()));
			parameters.put("lastLoginTimeEnd",
					getDateByString(this.getEndTime()));
		} else if ("lastTradeTime".equals(timeSlot)) {
			parameters.put("lastTradeTimeStart",
					getDateByString(this.getStartTime()));
			parameters.put("lastTradeTimeEnd",
					getDateByString(this.getEndTime()));
		}
		return parameters;
	}

	public void getChildChannels(Channel channel, List<Long> longs) {
		if (channel != null) {
			longs.add(channel.getId());
			for (Channel c : channel.getChildren()) {
				getChildChannels(c, longs);
			}
 		}
 	}

	public Short getGrade() {
		if(grade.shortValue()==-1){
			return null;
		}
		return grade;
	}

	public void setGrade(Short grade) {
		this.grade = grade;
	}

	public Long getSource() {
		if(source.longValue()==-1){
			return null;
		}
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

}
