/*
 * @(#)EmployeeController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.employee;


import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.validator.AuthenticationException;
import com.winxuan.framework.validator.AuthorityNotAvailableException;
import com.winxuan.framework.validator.IdentityValidator;
import com.winxuan.framework.validator.impl.PasswordVerifier;

/**
 *  后台用户
 * @author  HideHai
 * @version 1.0,2011-8-3
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	private static final String ERRORMESSAGE="errorMessage";
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private IdentityValidator identityValidator;
	@Autowired
	private EmployeeService employeeService;



	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(@RequestParam(value="from",required=false)String from){
		ModelAndView modelAndView = new ModelAndView("/employee/_login");
		if(identityValidator.isLogined()){
			modelAndView.setView(new RedirectView("/order/search"));
			return modelAndView;
		}
		LoginForm loginForm = new LoginForm();
		loginForm.setName("Root");
		loginForm.setFrom(from);
		modelAndView.addObject(loginForm);
		return modelAndView;
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(@Valid LoginForm loginForm,BindingResult result){
		ModelAndView modelAndView = new ModelAndView("/employee/_login");
		boolean success;
		Employee employee;
		if(!result.hasErrors()){
			try {
				success = identityValidator.login(new PasswordVerifier(loginForm.getName(), loginForm.getPassword()));
				if(success){
					employee = employeeService.getByName(loginForm.getName());
					modelAndView.addObject(employee);
					if(StringUtils.isNotEmpty(loginForm.getFrom())){
						modelAndView.setView(new RedirectView(loginForm.getFrom()));		
					}else{
						modelAndView.setView(new RedirectView("/order/search"));	
					}
				}else{
					result.rejectValue("name", "LoginError.loginForm");
				}
			} catch (AuthenticationException e) {
				modelAndView.addObject(ERRORMESSAGE, "账户名或密码不正确！");
			} catch(AuthorityNotAvailableException e){
				modelAndView.addObject(ERRORMESSAGE, "账户被禁用！");
			}
		}
		return modelAndView;
	}

	@RequestMapping(value="/current",method=RequestMethod.GET)
	public ModelAndView current(@MyInject Employee employee){
		ModelAndView modelAndView = new ModelAndView("/customer/current");
		if(employee != null ){
			modelAndView.addObject(employee);			
		}
		return modelAndView;
	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView register(LoginForm loginForm,BindingResult result){
		return null;
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(@RequestParam(value="from",required=false)String from){
		ModelAndView mav = new ModelAndView("/employee/_login");
		identityValidator.logout();
		LoginForm loginForm = new LoginForm();
		loginForm.setFrom(from);
		mav.addObject(loginForm);
		return mav;
	}

}

