/*
 * @(#)DateResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;

import com.winxuan.ec.support.web.resolver.annotation.DateDefinition;
import com.winxuan.ec.support.web.resolver.annotation.DateType;
import com.winxuan.framework.util.DateUtils;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-3
 */
public class DateResolver extends AbstractWebArgumentResolver {

	@Override
	protected boolean isApplicable(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(DateDefinition.class) != null
				&& methodParameter.getParameterType() == Date.class;
	}

	@Override
	protected Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws ParseException {
		DateDefinition definition = methodParameter
				.getParameterAnnotation(DateDefinition.class);
		String paramName = definition.target();
		String paramValue = StringUtils.isBlank(paramName) ? null : request
				.getParameter(paramName);
		if (paramValue == null) {
			return null;
		}
		String format = definition.format();
		Date date = new SimpleDateFormat(format).parse(paramValue);
		DateType type = definition.type();
		if (type == DateType.START_DAY_TIME) {
			date = DateUtils.getEarlyInTheDay(date);
		} else if (type == DateType.END_DAY_TIME) {
			date = DateUtils.getLateInTheDay(date);
		}
		return date;
	}

}
