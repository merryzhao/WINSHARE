/*
 * @(#)TaskTemplate.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz;

import java.io.Serializable;

import org.quartz.Trigger;


/**
 * 任务信息
 * @author  HideHai
 * @version 1.0,2011-8-28
 */
public class TaskTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7978640456541465389L;

	/**
	 * 系统的任务标示
	 */
	private String jobKey;
	
	/**
	 * 任务触发器
	 */
	private Trigger trigger;
	
	

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	
	
	
}

