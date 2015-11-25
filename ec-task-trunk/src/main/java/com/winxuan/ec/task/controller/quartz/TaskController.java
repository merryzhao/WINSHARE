/*
 * @(#)TaskController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.controller.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.exception.quartz.QuartzException;
import com.winxuan.ec.task.service.task.TriggerService;
import com.winxuan.ec.task.support.quartz.TaskDetails;
import com.winxuan.ec.task.support.quartz.TaskTemplate;

/**
 * 任务
 * @author  HideHai
 * @version 1.0,2011-8-29
 */
@Controller
@RequestMapping(value="/task")
public class TaskController {

	private static final Log LOG = LogFactory.getLog(ControllerConstant.class);

	@Autowired
	private TaskDetails taskDetails;
	@Autowired
	private TriggerService triggerService;

	private static final int HOUR = Integer.valueOf(1);
	private static final int MINUTE = Integer.valueOf(2);

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView viewList(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("/task/task_list");
		Map<String, JobDetail> map = taskDetails.getJobDetails();
		List<JobDetail> jobDetails = new ArrayList<JobDetail>();
		for(String s : map.keySet()){
			JobDetail jobDetail = map.get(s);
			if(null == triggerService.findTrigger(jobDetail.getName())){
				jobDetails.add(jobDetail);
			}
		}
		modelAndView.addObject("jobDetails", jobDetails);
		modelAndView.addObject("nowTime", new Date());
		return modelAndView;
	}

	@RequestMapping(value="/addTrigger",method=RequestMethod.POST)
	public ModelAndView addTrigger(AddTriggerForm addTriggerForm){
		ModelAndView modelAndView = new ModelAndView("/task/addtrigger_Failure");
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_PARAMETER_ERROR);
		try {
			Trigger trigger = parseTrigger(addTriggerForm);
			if(trigger != null){
				TaskTemplate template = new TaskTemplate();
				template.setJobKey(addTriggerForm.getTaskName());
				template.setTrigger(trigger);
				LOG.info(addTriggerForm.getTaskName() + " " + trigger);
				triggerService.addJobTrigger(template);
				modelAndView.setViewName("/task/addtrigger");
				modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
			}else{
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"trigger is null");				
			}
		} catch (QuartzException e) {
			LOG.info(e);
			modelAndView.setViewName("/task/addtrigger_Failure");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
		}
		return modelAndView;
	}

	private Trigger parseTrigger(AddTriggerForm addTriggerForm) throws QuartzException{
		CronTrigger cronTrigger = new CronTrigger();
		if(addTriggerForm.isUseCron()){		//cron表达式
			try {
				CronExpression expression = new CronExpression(addTriggerForm.getCron());
				cronTrigger.setCronExpression(expression);
				return cronTrigger;
			} catch (ParseException e) {
				throw new QuartzException("cron表达式错误!"+e.getMessage());
			}
		}else if(addTriggerForm.isUseDateTime()){	//指定时间点
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date fireDate;
			try {
				fireDate = format.parse(addTriggerForm.getDatetime());
			} catch (ParseException e) {
				throw new QuartzException("时间转换错误!"+e.getMessage());
			}
			if(addTriggerForm.getDateTimeType()==1){
				return TriggerUtils.makeDailyTrigger(fireDate.getHours(), fireDate.getMinutes());
			}else{
				SimpleTrigger simpleTrigger = new SimpleTrigger();
				simpleTrigger.setStartTime(fireDate);
				simpleTrigger.setNextFireTime(fireDate);
				simpleTrigger.setRepeatCount(0);
				return simpleTrigger;
			}
		}else if(addTriggerForm.isUseAppointTime()){	//间隔时间运行
			int appointType = addTriggerForm.getAppointType();
			String tempCron = null;
			if(appointType == MagicNumber.ONE){
				tempCron = "0 5 + * * ?";
				validateRequestStr(HOUR,addTriggerForm.getAppointStr());
				tempCron = tempCron.replaceFirst("\\+", addTriggerForm.getAppointStr());
			}else if(appointType == MagicNumber.TWO){
				tempCron = "0 + * * * ?";
				validateRequestStr(MINUTE,addTriggerForm.getAppointStr());
				tempCron = tempCron.replaceFirst("\\+", addTriggerForm.getAppointStr());
			}
			try {
				cronTrigger.setCronExpression(tempCron);
			} catch (ParseException e) {
				throw new QuartzException("时间转换错误!"+e.getMessage());
			}
			return cronTrigger;
		}
		return null;
	}

	private void validateRequestStr(int type,String value) throws QuartzException{
		if(StringUtils.isEmpty(value)){
			throw new QuartzException("填写自定义时间");
		}
		String[] tempUnit = value.split(",");
		for(int i=0;i < tempUnit.length;i++){
			if(NumberUtils.isNumber(tempUnit[i])){
				Integer tempValue = NumberUtils.toInt(tempUnit[i]);
				if(type==HOUR){
					if(!(tempValue>=0 && tempValue<=24)){
						throw new QuartzException("小时格式不正确!");
					}
				}
				if(type==MINUTE){
					if(!(tempValue>=0 && tempValue<=60)){
						throw new QuartzException("分钟格式不正确!");
					}
				}	
			}else{
				throw new QuartzException("用数字表示间隔时间!");
			}
		}
	}
}

