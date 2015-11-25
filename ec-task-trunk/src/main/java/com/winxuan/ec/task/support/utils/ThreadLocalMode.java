/*
 * @(#)ThreadLocalUtils.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.utils;

import com.winxuan.ec.support.web.enumerator.RecommendMode;


/**
/**
 * description
 * 用来获取 计算推荐的基础数据 mode
 * @author  huangyixiang
 * @version 2011-11-9
 *
 */
@SuppressWarnings("all")
public class ThreadLocalMode {
	private static final  ThreadLocal TL = new ThreadLocal();
	
	public static  void set(RecommendMode recommendMode){
		TL.set(recommendMode);
	}
	
	public static RecommendMode get(){
		return (RecommendMode)TL.get();
	}
	
	public static String getBaseTable(){
		return ((RecommendMode)get()).getBaseTable();
	}
	
	public static String getRecommendTable(){
		return ((RecommendMode)get()).getRecommendTable();
	}
	
	public static short getMode(){
		return ((RecommendMode)get()).getMode();
	}
	
	public static String getItemIdName(){
		return ((RecommendMode)get()).getItemIdName();
	}
}
