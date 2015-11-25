/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.report.Column;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;

/**
 * description
 * @author liyang
 * @version 1.0,2011-8-22
 */

public interface ColumnDao {
	@Get
	Column getColumn(Long id);
	
	@Update
	void update(Column column);
	
	@Save
	void save(Column column);
	
	@Delete
	void delete(Column column);
}
