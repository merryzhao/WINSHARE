/*
 * @(#)HttpServletRequestWrapper.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.wrapper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Request去除两边空格
 * @author  HideHai
 * @version 1.0,2011-11-15
 */
public class RequestWrapper extends HttpServletRequestWrapper{

	public RequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String para){
		String value =  super.getParameter(para);
		if(value != null){
			value = trimParameter(value);	
		}
		return value;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if(values != null){
			for(int i=0;i<values.length;i++){
				values[i] = trimParameter(values[i]);
			}
		}
		return values;
	}


	@Override
	public Map<String,String[]> getParameterMap() {
		Map<String, String[]> map = super.getParameterMap();
		if(map != null && !map.isEmpty()){
			for(String s : map.keySet()){
				String[] values = map.get(s);
				if(values != null){
					for(int i=0;i<values.length;i++){
						values[i] = trimParameter(values[i]);
					}
				}
			}
		}
		return map;
	}

	private String trimParameter(String content){
		return content == null ? null : content.trim();
	}
}

