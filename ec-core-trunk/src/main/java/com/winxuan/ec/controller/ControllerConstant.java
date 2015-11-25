/*
 * @(#) ControllerConstant.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.controller;

/**
 * @author Min-Huang
 * @version 1.0,Jul 27, 2011
 */
public class ControllerConstant {

	/**
	 * ModelAndView中存放消息的key
	 */
	public static final String MESSAGE_KEY="message";

	/**
	 * ModelAndView中存放消息的key
	 */

	public static final String RESULT_KEY="result";

	/**
	 * ModelAndView中存放需要JSON序列化的对象Key
	 */

	public static final String JSON_OBJECT_KEY="JSON";

	/**
	 * 返回结果－表单参数错误
	 */
	public static final short RESULT_PARAMETER_ERROR=0;

	/**
	 * 返回结果－成功状态 
	 */
	public static final short RESULT_SUCCESS=1;

	/**
	 * 返回结果－服务器内部错误
	 */
	public static final short RESULT_INTERNAL_ERROR=2;

	/**
	 * 返回结果－警告
	 */
	public static final short RESULT_WARNING=3;
	
	/**
	 * 邮箱正则
	 */
	public static final String EMAIL_EXP = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";

}
