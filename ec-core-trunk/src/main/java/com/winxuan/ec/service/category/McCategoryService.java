/*
 * @(#)McCategoryService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.category;

import com.winxuan.ec.model.category.McCategory;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface McCategoryService {

	/**
	 * 获得mc分类
	 * @param id mc分类编号
	 * @return 存在返回mc分类，不存在返回null
	 */
	McCategory get(String id);
}
