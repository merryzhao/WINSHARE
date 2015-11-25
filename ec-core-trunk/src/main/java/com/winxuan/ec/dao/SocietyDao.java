/*
 * @(#)SocietyDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.society.Keenness;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-4
 */
public interface SocietyDao {
	
	@Query("FROM Keenness WHERE available = ?")
	List<Keenness> findKeeness(boolean available);

}

