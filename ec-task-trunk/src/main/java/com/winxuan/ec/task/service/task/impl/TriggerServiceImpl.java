package com.winxuan.ec.task.service.task.impl;


import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.dao.quartz.TriggerDao;
import com.winxuan.ec.task.exception.quartz.QuartzException;
import com.winxuan.ec.task.model.quartz.TaskTrigger;
import com.winxuan.ec.task.service.task.TriggerService;
import com.winxuan.ec.task.support.quartz.TaskDetails;
import com.winxuan.ec.task.support.quartz.TaskTemplate;
import com.winxuan.ec.task.support.quartz.listener.SimpleProcessTriggerListener;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

@Service("triggerService")
public class TriggerServiceImpl implements TriggerService,Serializable{

	private static final long serialVersionUID = -4441305828005695980L;
	private static final Log LOG = LogFactory.getLog(TriggerServiceImpl.class);

	@InjectDao
	private TriggerDao triggerDao;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private TaskDetails taskDetails;
	@Autowired
	private SimpleProcessTriggerListener simpleProcessTriggerListener;

	public List<TaskTrigger> viewList(Pagination pagination) {
		List<TaskTrigger> taskTrigger= triggerDao.find(pagination);
		return taskTrigger;
	}
	

	@Override
	public List<TaskTrigger> viewExecuting() {
		try {
			@SuppressWarnings("unchecked")
			List<JobExecutionContext> contexts = scheduler.getCurrentlyExecutingJobs();
			List<TaskTrigger> triggers = new ArrayList<TaskTrigger>();
			if(contexts != null && !contexts.isEmpty()){
				for(JobExecutionContext context : contexts){
					JobDetail detail = context.getJobDetail();
					TaskTrigger trigger = triggerDao.get(detail.getName());
					if(trigger != null){
						triggers.add(trigger);
					}
				}
			}
			return triggers;
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}



	public void add(){
		try {
			JobDetail detail = taskDetails.getJobDetail("processTest");
			CronTrigger trigger = new CronTrigger(detail.getName(),detail.getGroup(), "0/10 * * * * ?");
			trigger.setJobName(detail.getName());
			trigger.setJobGroup(detail.getGroup());
			trigger.setDescription(detail.getDescription());
			trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
			scheduler.addJob(detail, true);
			if(scheduler.getTrigger(detail.getName(),detail.getGroup())!= null){
				scheduler.unscheduleJob(trigger.getName(), trigger.getGroup());
			}else{
				scheduler.scheduleJob(trigger);
			}
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(),e);
		} catch (ParseException e) {
			LOG.error(e.getMessage(),e);
		}
	}

	private void isValidJob(TaskTemplate template) throws QuartzException{
		if(template == null || template.getJobKey() == null){
			throw new QuartzException("任务参数错误!");
		}
		if(null == taskDetails.getJobDetail(template.getJobKey())){
			throw new QuartzException("没有这个任务!");
		}			
	}

	public synchronized void addJobTrigger(TaskTemplate template) throws QuartzException{
		isValidJob(template);
		JobDetail jobDetail = taskDetails.getJobDetail(template.getJobKey());
		Trigger trigger = template.getTrigger();
		trigger.setName(jobDetail.getName());
		trigger.setGroup(jobDetail.getGroup());
		trigger.setJobName(jobDetail.getName());
		trigger.setJobGroup(jobDetail.getGroup());
		trigger.setDescription(jobDetail.getDescription());
		trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		try {
			scheduler.addJob(jobDetail, true);
			scheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			throw new QuartzException(e);
		}
	}

	public void resumeTrigger(TaskTemplate template) throws QuartzException{
		isValidJob(template);
		JobDetail jobDetail = taskDetails.getJobDetail(template.getJobKey());
		try {
			TaskTrigger  taskTrigger = triggerDao.get(jobDetail.getName());
			if(simpleProcessTriggerListener.isExecutingTrigger(jobDetail.getName())){
				throw new QuartzException("任务正在运行!");
			}
			if(taskTrigger.getState().equals("ACQUIRED")){
				throw new QuartzException("任务正在运行!");
			}
			scheduler.resumeJob(jobDetail.getName(), jobDetail.getGroup());
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(),e);
			throw new QuartzException(e);
		}
	}

	public void clearTrigger(TaskTemplate template)  throws QuartzException{
		isValidJob(template);
		JobDetail jobDetail = taskDetails.getJobDetail(template.getJobKey());
		try {
			if(simpleProcessTriggerListener.isExecutingTrigger(jobDetail.getName())){
				throw new QuartzException("任务正在运行!");
			}
			scheduler.pauseJob(jobDetail.getName(),jobDetail.getGroup());
			scheduler.unscheduleJob(jobDetail.getName(), jobDetail.getGroup());
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(),e);
			throw new QuartzException(e);
		}
		simpleProcessTriggerListener.removeExecutingTrigger(jobDetail.getName());
	}

	@Override
	public TaskTrigger findTrigger(String jobKey) {
		return triggerDao.get(jobKey);
	}

	@Override
	public void pauseTrigger(TaskTemplate template) throws QuartzException {
		isValidJob(template);
		JobDetail jobDetail = taskDetails.getJobDetail(template.getJobKey());
		try {
			TaskTrigger  taskTrigger = triggerDao.get(jobDetail.getName());
			if(simpleProcessTriggerListener.isExecutingTrigger(jobDetail.getName())){
				throw new QuartzException("任务正在运行!");
			}
			if(taskTrigger.getState().equals("PAUSED")){
				throw new QuartzException("任务已经暂停!");
			}
			scheduler.pauseJob(jobDetail.getName(), jobDetail.getGroup());
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(),e);
			throw new QuartzException(e);
		}
	}

	@Override
	public Trigger findQuartzTrigger(String jobKey) throws QuartzException{
		JobDetail jobDetail = taskDetails.getJobDetail(jobKey);
		try {
			return scheduler.getTrigger(jobDetail.getName(),jobDetail.getGroup());
		} catch (SchedulerException e) {
			throw new QuartzException(e);
		}
	}


}
