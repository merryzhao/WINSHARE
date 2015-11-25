/*
 * @(#)OrderLogisticsUtils.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-12-29
 */
public class OrderLogisticsUtils {

	private static JsonConfig jsonConfig = new JsonConfig();
	private static boolean init = false;

	private static void afterFilter(){
		PropertyFilter propertyFilter = new PropertyFilter() {
			List<String> filter = Arrays.asList(new String[]{"message","nu","status","link","companytype","data","time","context"});
			@Override
			public boolean apply(Object srouce, String name, Object value) {
				return !filter.contains(name);
			}
		};
		jsonConfig.setJsonPropertyFilter(propertyFilter);
		init = true;
	}

	public static JsonConfig config(){
		if(!init){
			afterFilter();
		}
		return jsonConfig;
	}

}

