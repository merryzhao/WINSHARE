package com.winxuan.ec.front.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.support.interceptor.ExceptionProcessor;

/**
 * descriptionSSDD
 * @author  zhoujun 
 * @version 1.0,2011-12-12
 */
@Component
public class ReturnOrderCommonsException implements ExceptionProcessor{

	@Override
	public ModelAndView process(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = new ModelAndView("redirect:/customer/returnorder");
		return mav;
	}
 
	@Override
	public Class<? extends Exception> target() {	
		return ReturnOrderException.class;
	}

}
