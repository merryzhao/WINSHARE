/*
 * @(#)AutoTaskProcessor.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.StatefulJob;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.util.MethodInvoker;

import com.winxuan.ec.task.support.quartz.TaskDetails;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-5
 */
public class AutoTaskProcessor implements BeanPostProcessor,Serializable,Ordered{

	private static final long serialVersionUID = 2470161236793627207L;
	private static final Log LOG = LogFactory.getLog(AutoTaskProcessor.class);
	private Map<String, Object> taskCache = new HashMap<String, Object>();

	@Autowired
	private TaskDetails taskDetails; 
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
	throws BeansException {
		if(bean instanceof TaskAware){
			try {
				getTaskFromJobDetailFactory(bean);
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
	throws BeansException {
		return bean;
	}

	private  void getTaskFromJobDetailFactory(Object bean) throws Exception{
		TaskAware taskAware = (TaskAware) bean;
		String taskName = taskAware.getName();
		if(!StringUtils.isBlank(taskName)){
			String beanId = applicationContext.getBeanNamesForType(bean.getClass())[0];
			if(!beanId.equals(taskName)){
				LOG.error(String.format("Task Name [%s] not Equals IOC Compent ID[%s]! \r\n Skip This Task!", taskAware.getName(),beanId));
				return;
			}
			Object object = taskCache.get(taskName);
			if(object == null){
				taskCache.put(taskName, bean);
				JobDetail detail = new JobDetail();
				detail.setName(taskName);
				detail.setGroup(taskAware.getGroup());
				detail.setDescription(taskAware.getDescription());
				detail.setJobClass(StatefulMethodInvokingJob.class);
				detail.setDurability(false);
				detail.setVolatility(false);
				detail.setRequestsRecovery(false);
				detail.getJobDataMap().put("targetBean", taskName);
				detail.getJobDataMap().put("targetMethod", "start");

				LOG.info(String.format("Create Task: %s ", detail.getName()));

				scheduler.addJob(detail, true);
				taskDetails.addJobDetail(taskName, detail);
			}
		}else{
			LOG.info(String.format("Task Name Error: %s ", bean));
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * description
	 * @author  HideHai
	 * @version 1.0,2011-9-5
	 */
	public static class MethodInvokingJob implements Job
	{
		protected Log logger = LogFactory.getLog(getClass());

		public void execute(JobExecutionContext context) throws JobExecutionException
		{
			try
			{
				logger.debug("start");
				logger.debug(String.format("init spring content:%s",context.getScheduler().getContext().get("applicationContext")));
				ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");

				Object targetObject = context.getMergedJobDataMap().get("targetBean");
				String targetMethod = context.getMergedJobDataMap().getString("targetMethod");
				Object[] arguments = (Object[])context.getMergedJobDataMap().get("arguments");

				logger.debug("targetObject is "+targetObject);
				logger.debug("targetMethod is "+targetMethod);
				logger.debug("arguments are "+arguments);
				logger.debug("creating MethodInvoker");

				Object bean = applicationContext.getBean(targetObject.toString());
				MethodInvoker methodInvoker = new MethodInvoker();
				methodInvoker.setTargetClass(null);
				methodInvoker.setTargetObject(bean);
				methodInvoker.setTargetMethod(targetMethod);
				methodInvoker.setArguments(arguments);
				methodInvoker.prepare();
				logger.info("Invoking: "+methodInvoker.getPreparedMethod().toGenericString());
				methodInvoker.invoke();
			}
			catch(Exception e){
				throw new JobExecutionException(e);
			}
			finally{
				logger.debug("end");
			}
		}
	}
	/**
	 * description
	 * @author  HideHai
	 * @version 1.0,2011-9-5
	 */
	public static class StatefulMethodInvokingJob extends MethodInvokingJob implements StatefulJob
	{
		// No additional functionality; just needs to implement StatefulJob.
	}

}

