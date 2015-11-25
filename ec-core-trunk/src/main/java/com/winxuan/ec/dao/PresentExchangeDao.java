/*
 * @(#)PresentExchangeDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.math.BigDecimal;
import java.util.List;

import com.winxuan.ec.model.present.PresentExchange;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-23
 */
public interface PresentExchangeDao {

	@Query("FROM PresentExchange p ORDER BY p.value")
	List<PresentExchange> find();
	
	@Query("FROM PresentExchange p where p.id=?")
	PresentExchange find(Long id);

	@Query("FROM PresentExchange p WHERE p.value=? AND p.points = ?")
	boolean isExisted(BigDecimal value, int points);
}
