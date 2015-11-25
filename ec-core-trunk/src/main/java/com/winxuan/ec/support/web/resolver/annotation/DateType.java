/*
 * @(#)DateType.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver.annotation;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-12-3
 */
public enum DateType {
	/**
	 * 根据format转换
	 */
	DEFAULT,

	/**
	 * 日期时间，且时间转换为一天中的开始时间
	 */
	START_DAY_TIME,

	/**
	 * 日期时间，且时间转换为一天中的结束时间
	 */
	END_DAY_TIME
}
