package com.winxuan.ec.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理
 * @author Heyadong
 *
 */
public interface ExceptionProcessor {
	
	/**
	 * 异常处理方法
	 * @param request 
	 * @param response 
	 * @param handler
	 * @param ex
	 * @return modelAndView
	 */
	ModelAndView process(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex);
	/**
	 * 目标处理异常
	 * @return exception
	 */
	Class<? extends Exception> target();
}
