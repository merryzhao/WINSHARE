/*
 * @(#)QuartzShutdownListener.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.winxuan.ec.task.service.task.impl.TriggerServiceImpl;

/**
 * 容器关闭时关闭quartz
 * 解决热部署quartz线程未结束问题。
 * @author  HideHai
 * @version 1.0,2011-12-14
 */
public class QuartzShutdownListener implements ServletContextListener{
	
	private static final Log LOG = LogFactory.getLog(TriggerServiceImpl.class);

	@Override
	public void contextDestroyed(ServletContextEvent sc) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
		Scheduler scheduler = (Scheduler) context.getBean("scheduler");
		LOG.info("Shutdown Quartz.....");
		try {
			if(scheduler != null){
			scheduler.shutdown();
			}else{
				LOG.warn("scheduler not found on shutdown method!!");
			}
		} catch (SchedulerException e) {
			LOG.error("Shutdown Exception :" + e, e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		LOG.info("init Quartz Shutdown Listener.....");
	}

}

