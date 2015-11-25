/*
 * @(#)Shoppingcart.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 
 * description
 * 
 * @author liyang
 * @version 1.0,2011-7-18
 */
public interface CodeDao {

	@Get
	Code get(Long id);
	
	@Update
	void update(Code code);
	
	@Save
	void save(Code code);
	
	@Query("from Code c where c.id in :id")
	List<Code> find(@Parameter("id") Long[] id);
	
	@Query("from Code c where c.parent.id = ?")
	List<Code> findByParent(Long parent);
	
	@Query("from Code c where c.name = ?")
	Code findCodeByName(String name);
}
