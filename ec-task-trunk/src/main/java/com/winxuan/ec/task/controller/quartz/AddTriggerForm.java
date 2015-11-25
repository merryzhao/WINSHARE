/*
 * @(#)AddTriggerForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.controller.quartz;

import java.io.Serializable;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-14
 */
public class AddTriggerForm implements Serializable{

	private static final long serialVersionUID = -1822608533678093799L;
	
	@NotEmpty
	private String taskName;
	
	private String taskGroup;
	/**
	 * 选择类型
	 */
	@Min(value=1)
	private int type;
	/**
	 * 指定的时间
	 */
	private boolean useDateTime;
	/**
	 * 指定的间隔
	 */
	private boolean useInterval;
	/**
	 * 自定义的时间
	 */
	private boolean useAppointTime;
	/**
	 * 指定的时间
	 */
	private String datetime;
	/**
	 * 指定时间类型
	 */
	private int dateTimeType;
	/**
	 * 使用Cron字符串
	 */
	private boolean useCron;
	
	/**
	 * cron
	 */
	private String cron;
	/**
	 * 间隔的类型
	 */
	private int	intervalType;
	/**
	 * 自定义字符串
	 */
	private String appointStr;
	/**
	 * 自定义字符串的类型
	 */
	private int appointType;
	
	
	
	public boolean isUseDateTime() {
		return type == 1;
	}
	public void setUseDateTime(boolean useDateTime) {
		this.useDateTime = useDateTime;
	}
	public boolean isUseInterval() {
		return useInterval;
	}
	public void setUseInterval(boolean useInterval) {
		this.useInterval = useInterval;
	}
	public boolean isUseAppointTime() {
		return type == 2;
	}
	public void setUseAppointTime(boolean useAppointTime) {
		this.useAppointTime = useAppointTime;
	}
	public boolean isUseCron() {
		return type == 3;
	}
	public void setUseCron(boolean useCron) {
		this.useCron = useCron;
	}
	public int getIntervalType() {
		return intervalType;
	}
	public void setIntervalType(int intervalType) {
		this.intervalType = intervalType;
	}
	public String getAppointStr() {
		return appointStr;
	}
	public void setAppointStr(String appointStr) {
		this.appointStr = appointStr;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAppointType() {
		return appointType;
	}
	public void setAppointType(int appointType) {
		this.appointType = appointType;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public int getDateTimeType() {
		return dateTimeType;
	}
	public void setDateTimeType(int dateTimeType) {
		this.dateTimeType = dateTimeType;
	}
	
	
}

