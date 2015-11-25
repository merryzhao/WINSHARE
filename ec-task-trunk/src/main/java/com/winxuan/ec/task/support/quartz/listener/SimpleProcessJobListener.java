/*
 * @(#)SimpleProcessJobTrigger.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-28
 */
@Component("simpleProcessJobListener")
public class SimpleProcessJobListener implements JobListener{

	private static final Log LOG = LogFactory.getLog(SimpleProcessJobListener.class);
	
	public String getName() {
		return "simpleProcessJobListener";
	}

	public void jobToBeExecuted(JobExecutionContext context) {
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
	}

}

