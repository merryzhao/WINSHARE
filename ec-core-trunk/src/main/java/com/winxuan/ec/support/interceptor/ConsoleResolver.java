/*
 * @(#)ConsoleResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import org.springframework.web.context.request.NativeWebRequest;

import com.winxuan.ec.model.user.Employee;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-11
 */
public class ConsoleResolver extends AbstractArgumentResovler{

	@Override
	protected Object limitResolver(Object object, InjectType type,
			InjectName value, NativeWebRequest webRequest) {
		Object result = UNRESOLVED;
		if (object.equals(Employee.class)) {
			result = getCurrentPrincipal();
		}
		return result;
	}

}

