package com.winxuan.ec.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 异常日志
 * @author Heyadong
 *
 */
public interface ExceptionLog {
	/**
	 * Controller 异常日志记录
	 * @param request 
	 * @param response
	 * @param handler
	 * @param ex
	 */
	void log(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex);
}
