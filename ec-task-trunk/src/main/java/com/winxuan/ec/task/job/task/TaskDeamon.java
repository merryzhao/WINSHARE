/*
 * @(#)TaskDeamon.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.task;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-12-29
 */
@Component("taskDeamon")
public class TaskDeamon implements TaskAware,TaskConfigure,Serializable{

	private static final long serialVersionUID = 4589347848508313496L;
	private static final Log LOG = LogFactory.getLog(TaskDeamon.class);
	
	@Autowired
	private NotifyService notifyService;

	@Override
	public int getNotifyLevel() {
		return TaskConfigure.LEVEL_ALL;
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, msg[0]);
	}

	@Override
	public String getName() {
		return "taskDeamon";
	}

	@Override
	public String getDescription() {
		return "恢复未执行的阻塞任务";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://10.1.2.46:8006/trigger/resume/auto");
		try {
			LOG.info(String.format("%s do request...",getName()));
			HttpResponse response = httpClient.execute(request);
			if (response != null
					&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				LOG.info(String.format("%s  request!",getName()));
			}
		}catch (Exception e) {
			LOG.error(e.getMessage(),e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

}

