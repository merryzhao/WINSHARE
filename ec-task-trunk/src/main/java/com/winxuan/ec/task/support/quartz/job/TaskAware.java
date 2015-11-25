/*
 * @(#)TaskAware.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz.job;

/**
 * job接口
 * 需要使用Quartz管理的任务实现此接口和方法
 * 方法名不能重复，否则会被覆盖.
 * @author  HideHai
 * @version 1.0,2011-9-5
 */
public interface TaskAware {
	
	public static final String GROUP_DEFAULT = "DEFAULT";
	public static final String GROUP_EC_CORE = "EC-CORE";
	public static final String GROUP_EC_FRONT = "EC-FRONT";
	public static final String GROUP_EC_SEARCH = "EC-SEARCH";

	/**
	 * 必须和注解ID保持一致
	 * @return
	 */
	String getName();
	
	String getDescription();
	
	String getGroup();
	
	void start();
}

