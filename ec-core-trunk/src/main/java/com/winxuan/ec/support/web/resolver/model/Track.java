/*
 * @(#)Track.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver.model;

import java.io.Serializable;

import com.winxuan.framework.util.RandomCodeUtils;


/**
 * description
 * @author  liuan
 * @version 1.0,2011-12-6
 */
public class Track implements Serializable{
	/**
	 * 跟踪客户端的持久cookie名称
	 */
	public static final String PERSISTENCE_COOKIE_NAME = "c";

	/**
	 * 跟踪客户端的会话cookie名称
	 */
	public static final String SESSION_COOKIE_NAME = "s";

	/**
	 * 持久cookie年龄
	 */
	public static final int PERSISTENCE_COOKIE_AGE = Integer.MAX_VALUE;

	/**
	 * cookie域
	 */
	public static final String COOKIE_DOMAIN = ".winxuan.com";

	/**
	 * cookie路径
	 */
	public static final String COOKIE_PATH = "/";

	/**
	 * cookie值的模式
	 */
	public static final short COOKIE_VALUE_MODE = RandomCodeUtils.MODE_LETTER_UPPERCASE_NUMBER;

	/**
	 * cookie值的长度
	 */
	public static final int COOKIE_VALUE_LENGTH = 8;

	/**
	 * 放入request里的名称
	 */
	public static final String REQUEST_ATTRIBUTE_NAME = Track.class.getName();
	
	private static final long serialVersionUID = 4745950560193239543L;

	private String sessionId;

	private String persistenceId;

	/**
	 * 获得会话cookie值
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 获得持久cookie值
	 * @return
	 */
	public String getPersistenceId() {
		return persistenceId;
	}

	public void setPersistenceId(String persistenceId) {
		this.persistenceId = persistenceId;
	}
}
