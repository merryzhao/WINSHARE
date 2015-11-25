package com.winxuan.ec.admin.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.api.exception.OpenPlatformDeniedException;
import com.winxuan.api.model.application.OpenApplication;
import com.winxuan.api.service.app.ApplicationService;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author HideHai
 * @version 0.1 ,2012-6-14
 **/
@Controller
@RequestMapping(value="/app")
public class AppController {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/list/audit",method=RequestMethod.GET)
	public ModelAndView audit(Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("app/list");
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("available", false);
		List<OpenApplication> openApplications = applicationService.find(para, pagination);
		modelAndView.addObject("applications", openApplications);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value="/list/unaudit",method=RequestMethod.GET)
	public ModelAndView unaudit(Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("app/list");
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("available", true);
		List<OpenApplication> openApplications = applicationService.find(para, pagination);
		modelAndView.addObject("applications", openApplications);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("app/list");
		Map<String, Object> para = new HashMap<String, Object>();
		List<OpenApplication> openApplications = applicationService.find(para, pagination);
		modelAndView.addObject("applications", openApplications);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value="/audit",method=RequestMethod.PUT)
	public ModelAndView audit(@RequestParam("appKey") Integer appKey,@MyInject Employee employee,
			@RequestParam("viewName") String viewName, @RequestParam(value="message",required=false) String message) throws OpenPlatformDeniedException{
		ModelAndView modelAndView = new ModelAndView(viewName);
		OpenApplication application = applicationService.get(appKey);
		if(application != null){
			applicationService.audit(application, employee.getId());
			Map<String, Object> model = new HashMap<String,Object>();
			if (application.getEmail() !=null && message != null){
			    model.put("message", message);
			    mailService.sendMail(application.getEmail(), "文轩网消息提醒", "api.ftl", model);
			} 
		}
		modelAndView.addObject("result", 1);
		modelAndView.addObject("application", application);
		return modelAndView;
	}
	

	@RequestMapping(method=RequestMethod.DELETE)
	public ModelAndView audit(@RequestParam("appKey") Integer appKey,@RequestParam("viewName") String viewName){
		ModelAndView modelAndView = new ModelAndView("app/application");
		OpenApplication application = applicationService.get(appKey);
		if(application != null){
			applicationService.delete(application);
		}
		modelAndView.addObject("result", 1);
		modelAndView.addObject("application", application);
		return modelAndView;
	}
}

