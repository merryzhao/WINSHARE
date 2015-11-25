/*
 * @(#)TestValidController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.test;


import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.front.controller.test.TestValid.ChangeName;
import com.winxuan.ec.front.controller.test.TestValid.ChangePass;
import com.winxuan.ec.support.validator.utils.BeanValidator;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-16
 */
@Controller
@RequestMapping(value="/test/valid")
public class TestValidController {

	private static final Log LOG = LogFactory.getLog(TestValidController.class);

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView toValid(){
		Random random=new Random();
		int randomCode=random.nextInt();
		HttpServletResponse response = WebContext.currentResponse();
		Cookie cookie=new Cookie("randomCode",randomCode+"");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		ModelAndView modelAndView = new ModelAndView("test/testValid");
		TestValid testValid = new TestValid();
		testValid.setName("aa");
		testValid.setRandomCode(randomCode+"");
		modelAndView.addObject("testValid", testValid);
		return modelAndView;
	}

	@RequestMapping(value="/post",method=RequestMethod.POST)
	public ModelAndView testvalid(TestValid testValid,BindingResult result){
		ModelAndView modelAndView = new ModelAndView("test/testValid2");
		if(BeanValidator.isValid(result, testValid,Default.class,ChangeName.class,ChangePass.class)){
			Cookie cookie = CookieUtils.getCookie("randomCode");
			String cookieValue = cookie == null ? null : cookie.getValue();
			if (cookieValue == null || cookieValue != null && !cookieValue.equals(testValid.getRandomCode())) {
				return modelAndView;
			}else{
				CookieUtils.removeCookie("randomCode", "/", "www.winxuan.com:8080");
			}
			LOG.info("valid success");
		}else{
			LOG.info("valid fail");
		}
		return modelAndView;
	}

}

