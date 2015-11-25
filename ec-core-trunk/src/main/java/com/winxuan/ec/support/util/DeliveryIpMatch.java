/*
 * @(#)NetworkAddressMatch.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.util;

import com.winxuan.framework.util.StringUtils;

/**
 * IP地址匹配
 * @author  HideHai
 * @version 1.0,2011-8-22
 */
public class DeliveryIpMatch {
	
	private static String[] targetAddrs = null;
	/**
	 * 
	 * @param from	10.100.145.71
	 * @param target 10.100.100.100
	 * @return
	 */
	public static boolean isMatch(String from,String targets){
		if(StringUtils.isNullOrEmpty(from) || StringUtils.isNullOrEmpty(targets)){
			return false;
		}
		if(targetAddrs == null){
			targetAddrs =  targets.split(",");
		}
		for(String target : targetAddrs){
			if(match(from, target)){
				return true;
			}
		}
		return false;
	}

	private static boolean match(String from,String target){
		String[] targetArray = target.split("\\.");
		String[] fromArray = from.split("\\.");
		for(int i=targetArray.length-1;i>=0;i--){
			String dotStr = targetArray[i];
			if(!dotStr.equals(fromArray[i])  && !"*".equals(dotStr) ){
				return false;
			}
		}
		return true;
	}
}

