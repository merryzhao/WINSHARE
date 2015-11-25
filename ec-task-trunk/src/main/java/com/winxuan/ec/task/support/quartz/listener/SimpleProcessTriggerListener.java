/*
 * @(#)SimpleProcessTriggerListener.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz.listener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;


/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-28
 */
public class SimpleProcessTriggerListener implements TriggerListener{

	private static final Log LOG = LogFactory.getLog(SimpleProcessTriggerListener.class);
	private static Map<String,Object> executingTriggers = new HashMap<String,Object>();


	public String getName() {
		return "simpleProcessTriggerListener";
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		synchronized (executingTriggers) {
			executingTriggers.put(trigger.getName(), context);
		}
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	public void triggerMisfired(Trigger trigger) {
		String msg = String.format("%s Misfired! Time : ", trigger.getName(),trigger.getPreviousFireTime());
		LOG.warn(msg);
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			int triggerInstructionCode) {
		synchronized (executingTriggers) {
			executingTriggers.remove(trigger.getName());
			LOG.info(trigger.getName() + " Complete!");
		}
	}

	public List<Object> getExecutingTriggers(){
		synchronized (executingTriggers) {
			return java.util.Collections.unmodifiableList(new ArrayList(
					executingTriggers.values()));
		}
	}

	public void removeExecutingTrigger(String instanceId){
		executingTriggers.remove(instanceId);
	}

	public boolean isExecutingTrigger(String name){
		if(executingTriggers.get(name) != null){
			return true;
		}
		return false;
	}


}

