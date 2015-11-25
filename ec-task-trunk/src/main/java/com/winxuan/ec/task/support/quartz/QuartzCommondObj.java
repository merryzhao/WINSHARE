/*
 * @(#)QuartzCommondObj.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.UnableToInterruptJobException;
import org.quartz.spi.JobFactory;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-12-6
 */
public class QuartzCommondObj implements Scheduler{

	@Override
	public String getSchedulerName() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSchedulerInstanceId() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchedulerContext getContext() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDelayed(int seconds) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStarted() throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void standby() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInStandbyMode() throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown(boolean waitForJobsToComplete)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isShutdown() throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SchedulerMetaData getMetaData() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getCurrentlyExecutingJobs() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJobFactory(JobFactory factory) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date scheduleJob(Trigger trigger) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean unscheduleJob(String triggerName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Date rescheduleJob(String triggerName, String groupName,
			Trigger newTrigger) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addJob(JobDetail jobDetail, boolean replace)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void triggerJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerJobWithVolatileTrigger(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerJob(String jobName, String groupName, JobDataMap data)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerJobWithVolatileTrigger(String jobName, String groupName,
			JobDataMap data) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseJobGroup(String groupName) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseTrigger(String triggerName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseTriggerGroup(String groupName) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumeJobGroup(String groupName) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumeTrigger(String triggerName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumeTriggerGroup(String groupName) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseAll() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumeAll() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getJobGroupNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getJobNames(String groupName) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trigger[] getTriggersOfJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getTriggerGroupNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getTriggerNames(String groupName) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getPausedTriggerGroups() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobDetail getJobDetail(String jobName, String jobGroup)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trigger getTrigger(String triggerName, String triggerGroup)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTriggerState(String triggerName, String triggerGroup)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCalendar(String calName, Calendar calendar, boolean replace,
			boolean updateTriggers) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteCalendar(String calName) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Calendar getCalendar(String calName) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCalendarNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean interrupt(String jobName, String groupName)
			throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addGlobalJobListener(JobListener jobListener)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addJobListener(JobListener jobListener)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeGlobalJobListener(String name)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeJobListener(String name) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List getGlobalJobListeners() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getJobListenerNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobListener getGlobalJobListener(String name)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobListener getJobListener(String name) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGlobalTriggerListener(TriggerListener triggerListener)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTriggerListener(TriggerListener triggerListener)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeGlobalTriggerListener(String name)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTriggerListener(String name) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List getGlobalTriggerListeners() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getTriggerListenerNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TriggerListener getGlobalTriggerListener(String name)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TriggerListener getTriggerListener(String name)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSchedulerListener(SchedulerListener schedulerListener)
			throws SchedulerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeSchedulerListener(SchedulerListener schedulerListener)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List getSchedulerListeners() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

}

