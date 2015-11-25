/*
 * @(#)notifyserivce.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.task;

import org.quartz.Trigger;

import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-12-14
 */
public interface NotifyService {
	
	void notify(Trigger trigger,String msg);

	void notify(TaskAware taskAware,String msg);
	
	void sendMail(String name,String group,String desc,String msg);
	
	void sendMsg(String name,String group,String desc,String msg);
}

