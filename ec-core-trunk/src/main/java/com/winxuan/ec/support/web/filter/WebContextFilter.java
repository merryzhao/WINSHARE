/*
 * @(#)WebContextFilter.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winxuan.framework.util.web.WebContext;

/**
 * 将request和response注册到WebContext中，结束时清除
 * @author  HideHai
 * @version 1.0,2011-7-27
 */
public class WebContextFilter implements Filter{

	Logger log = LoggerFactory.getLogger(getClass());


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{ 
		try {
			HttpServletRequest heq = (HttpServletRequest)request;
			WebContext.registry(heq, (HttpServletResponse)response);
			chain.doFilter(request, response);
		}
		finally{
			WebContext.release();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
