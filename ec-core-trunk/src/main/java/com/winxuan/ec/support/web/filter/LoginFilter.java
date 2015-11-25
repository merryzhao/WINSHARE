/*
 * @(#)LoginFilter.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.winxuan.ec.support.principal.PrincipalHolder;
import com.winxuan.framework.validator.IdentityValidator;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-8-11
 */
public class LoginFilter implements Filter {

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final int NOT_LOGGED_CODE = 401;

	private String loginUrl;
	private String encoding;
	private Pattern[] exclude;

	private IdentityValidator identityValidator;

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (isExclude(request) || identityValidator.isLogined()) {
			servletRequest.setAttribute("com.winxuan.ec.model.user.User",
					identityValidator.currentPrincipal());
			chain.doFilter(servletRequest, servletResponse);
		} else {
			String format = request.getParameter("format");
			if ("json".equals(format) || "jsonp".equals(format)
					|| "xml".equals(format)) {
				response.sendError(NOT_LOGGED_CODE, "Not logged in");
			} else {
				response.sendRedirect(loginUrl
						+ URLEncoder.encode(request.getRequestURL().append(request.getQueryString() == null ? "" : "?" + request.getQueryString()).toString(),
								encoding));
			}
		}
		PrincipalHolder.reset();
	}

	private boolean isExclude(HttpServletRequest request) {
		if (exclude == null || exclude.length == 0) {
			return false;
		}
		String currentUri = request.getRequestURI();
		for (Pattern excludeUri : exclude) {
			if (excludeUri.matcher(currentUri).find()) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig config) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
		identityValidator = context.getBean(IdentityValidator.class);
		if (identityValidator == null) {
			throw new ServletException("identityValidator is not initialized");
		}
		loginUrl = config.getInitParameter("loginUrl");
		encoding = config.getInitParameter("encoding");
		if (StringUtils.isBlank(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		String excludeString = config.getInitParameter("exclude");
		if (!StringUtils.isBlank(excludeString)) {
			String[] excludeArray = excludeString.split(",");
			exclude = new Pattern[excludeArray.length];
			for (int i = 0; i < excludeArray.length; i++) {
				exclude[i] = Pattern.compile(excludeArray[i]);
			}
		}
	}
}
