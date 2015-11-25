/*
 * @(#)FlushLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.task;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-16
 */
@Component("flushLog")
public class FlushLog implements TaskAware,Serializable {

	private static final long serialVersionUID = -2852629324569929297L;
	private static final String DELETE_SQL = "DELETE FROM quartz_log where datediff(now(),stamp) >3";
	private static final Log LOG = LogFactory.getLog(FlushLog.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	@Override
	public String getName() {
		return "flushLog";
	}

	@Override
	public String getDescription() {
		return "移除计划任务3天前的日志信息";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		int count = jdbcTemplateEcCore.update(DELETE_SQL);
		LOG.info(getName() + "execute over! delete :"+count);
	}

}

