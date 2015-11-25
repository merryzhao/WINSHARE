package com.winxuan.ec.front.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.CustomerCashApplyException;
import com.winxuan.ec.support.interceptor.ExceptionProcessor;

/**
 * description
 * @author  zhoujun XXXXX
 * @version 1.0,2011-12-12
 */
@Component
public class CashApplyCommonsException implements ExceptionProcessor{

	@Override
	public ModelAndView process(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = new ModelAndView("redirect:/customer/account/refundlist");
		return mav;
	}

	@Override
	public Class<? extends Exception> target() {	
		return CustomerCashApplyException.class;
	}

}
