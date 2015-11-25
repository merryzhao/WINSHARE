/*
 * @(#)McCategoryServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.McCategoryDao;
import com.winxuan.ec.model.category.McCategory;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author renshiyong
 * @version 1.0,2011-7-18
 */
@Service("mcCategoryService")
@Transactional(rollbackFor = Exception.class)
public class McCategoryServiceImpl implements McCategoryService {

	@InjectDao
	private McCategoryDao mcCategoryDao;

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public McCategory get(String id) {
		return mcCategoryDao.get(id);
	}

}
