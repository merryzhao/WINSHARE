/*
 * @(#)RemoteAddrFilter.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.winxuan.framework.util.StringUtils;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-8
 */
public class RemoteAddrFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		RemoteAddrRequestWrapper requestWrapper = new RemoteAddrRequestWrapper(
				(HttpServletRequest) request);
		filterChain.doFilter(requestWrapper, response);

	}

	public void destroy() {

	}

	/**
	 * description
	 * 
	 * @author qiaoyao
	 * @version 1.0,2011-8-8
	 */
	class RemoteAddrRequestWrapper extends HttpServletRequestWrapper implements
			Serializable {

		private static final long serialVersionUID = 1L;

		public RemoteAddrRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getRemoteAddr() {
			String ip = super.getHeader("x-forwarded-for");
			if (!StringUtils.isNullOrEmpty(ip)) {
				String[] arr = ip.split(",");
				return arr[0];
			}
			return super.getRemoteAddr();
		}
	}

}
