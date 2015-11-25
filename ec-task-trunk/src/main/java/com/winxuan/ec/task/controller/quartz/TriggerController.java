package com.winxuan.ec.task.controller.quartz;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobPersistenceException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.exception.quartz.QuartzException;
import com.winxuan.ec.task.model.quartz.TaskTrigger;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.service.task.TriggerService;
import com.winxuan.ec.task.support.quartz.TaskTemplate;
import com.winxuan.framework.pagination.Pagination;
/**
 * 任务
 * @author  HideHai
 * @version 1.0,2011-8-29
 */
@Controller
@RequestMapping(value="/trigger")
public class TriggerController {
	private static final Log LOG = LogFactory.getLog(TriggerController.class);
	
	@Autowired
	private TriggerService triggerService;
	
	@Autowired
	private NotifyService notifyService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView viewList(){
		ModelAndView modelAndView = new ModelAndView("/task/trigger_list");
		Pagination pagination = new Pagination(); 
		pagination.setCurrentPage(1);
		pagination.setPageSize(MagicNumber.HUNDRED);
		List<TaskTrigger> taskTrigger = triggerService.viewList(pagination);
		modelAndView.addObject("taskTrigger", taskTrigger);
		modelAndView.addObject("nowTime", new Date());
		modelAndView.addObject("pagination",pagination);
		return modelAndView;
	}

	@RequestMapping(value="/run",method=RequestMethod.GET)
	public ModelAndView viewExecuting(){
		ModelAndView modelAndView = new ModelAndView("/task/trigger_run");
		List<TaskTrigger> taskTrigger = triggerService.viewExecuting();
		modelAndView.addObject("taskTrigger", taskTrigger);
		modelAndView.addObject("nowTime", new Date());
		return modelAndView;
	}

	@RequestMapping(value="/pause",method=RequestMethod.GET)
	public ModelAndView pause(@RequestParam(value="triggerName",required=true) String triggerName){
		ModelAndView modelAndView = new ModelAndView("/task/trigger_success");
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		try {
			Trigger trigger = triggerService.findQuartzTrigger(triggerName);
			TaskTemplate template = new TaskTemplate();
			template.setJobKey(triggerName);
			template.setTrigger(trigger);
			triggerService.pauseTrigger(template);
		} catch (QuartzException e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
			modelAndView.setViewName("/task/trigger_failure");
		}
		return modelAndView;
	}

	@RequestMapping(value="/resume",method=RequestMethod.GET)
	public ModelAndView resume(@RequestParam(value="triggerName",required=true) String triggerName){
		ModelAndView modelAndView = new ModelAndView("/task/trigger_success");
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		try {
			Trigger trigger = triggerService.findQuartzTrigger(triggerName);
			TaskTemplate template = new TaskTemplate();
			template.setJobKey(triggerName);
			template.setTrigger(trigger);
			triggerService.resumeTrigger(template);
		} catch (QuartzException e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
			modelAndView.setViewName("/task/trigger_failure");
		}
		return modelAndView;
	}

	@RequestMapping(value="/del",method=RequestMethod.GET)
	public ModelAndView del(@RequestParam(value="triggerName",required=true) String triggerName) throws JobPersistenceException{
		ModelAndView modelAndView = new ModelAndView("/task/trigger_success");
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		TaskTemplate template = new TaskTemplate();
		template.setJobKey(triggerName);
		try {
			triggerService.clearTrigger(template);
		}catch (QuartzException e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
			modelAndView.setViewName("/task/trigger_failure");
		}
		return modelAndView;			
	}

	@RequestMapping(value="/resume/auto",method=RequestMethod.GET)
	public ModelAndView autoResume(){
		ModelAndView modelAndView = new ModelAndView(new RedirectView("/info.html"));
		Pagination pagination = new Pagination(); 
		pagination.setCurrentPage(1);
		pagination.setPageSize(MagicNumber.HUNDRED);
		List<TaskTrigger> taskTrigger = triggerService.viewList(pagination);
		for(TaskTrigger trigger : taskTrigger){
			try{
				if("taskDeamon".equals(trigger.getJobName())){
					continue;
				}
				Trigger qTrigger = triggerService.findQuartzTrigger(trigger.getTriggerName());
				if("BLOCKED".equals(trigger.getState())){
					List<TaskTrigger> runTriggers = triggerService.viewExecuting();
					if(!runTriggers.contains(trigger)){
						TaskTemplate template = new TaskTemplate();
						template.setJobKey(qTrigger.getName());
						template.setTrigger(qTrigger);
						triggerService.resumeTrigger(template);
						String msg = String.format("%s resume %s", trigger.getTriggerName(),trigger.getState());
						notifyService.sendMail(qTrigger.getName(), qTrigger.getGroup(), qTrigger.getDescription(), msg);
						LOG.info(msg);
					}
				}else{
					LOG.info(String.format("%s now %s", trigger.getTriggerName(),trigger.getState()));
				}
			}catch (Exception e) {
				LOG.error(String.format("%s : deamon resume exception ! fire time: %s", trigger.getTriggerName(),trigger.getPrevFireTime()), e);
			}
		}

		return modelAndView;
	}
}
