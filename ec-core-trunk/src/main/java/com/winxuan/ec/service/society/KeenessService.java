/*
 * @(#)KeenessService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.society;

/**
 * 关键词处理
 * @author  HideHai
 * @version 1.0,2011-11-4
 */
public interface KeenessService {
	
	
	/**
	 *  替换关键词
	 * @param content
	 * @return
	 */
	String replaceSimple(String content);
	
	/**
	 * 替换关键词，进行富信息处理
	 * @param content
	 * @return
	 */
	String replaceRich(String content);
	
	/**
	 * 缓存关键词
	 */
	void reloadKeeness();

}

