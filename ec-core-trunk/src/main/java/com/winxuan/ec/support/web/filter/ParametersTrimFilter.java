/*
 * @(#)ParametersTrimFilter.java
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

/**
 * request trim 过滤器
 * 
 * @author liuan
 * @version 1.0,2011-11-15
 */
public class ParametersTrimFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ParametersTrimRequestWrapper wrapper = new ParametersTrimRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(wrapper, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	/**
	 * request参数trim
	 * 
	 * 
	 */
	class ParametersTrimRequestWrapper extends HttpServletRequestWrapper
			implements Serializable {

		private static final long serialVersionUID = 292165407469351546L;

		public ParametersTrimRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			return TrimUtils.trim(super.getParameter(name));
		}

		@Override
		public String[] getParameterValues(String name) {
			return TrimUtils.trim(super.getParameterValues(name));
		}
	}

	/**
	 * trim工具
	 * 
	 */
	static class TrimUtils {

		public static String[] trim(String[] array) {
			if (array != null) {
				String[] result = new String[array.length];
				for (int i = 0; i < array.length; i++) {
					result[i] = array[i] == null ? null : array[i].trim();
				}
				return result;
			}
			return null;
		}

		public static String trim(String str) {
			return str == null ? null : str.trim();
		}

	}

}
