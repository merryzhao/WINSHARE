/*
 * @(#)AreaDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.shop.ProShop;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911
 * 
 */
public interface ProShopDao {

	@Get
	ProShop get(Long id);

	@Update
	void update(ProShop proShop);

	@Save
	void save(ProShop proShop);

	@SaveOrUpdate
	void saveOrUpdate(ProShop proShop);

	@Delete
	void delete(ProShop proShop);

	@Query("from ProShop p ")
	@Conditions({ @Condition("p.available = :available"),
			@Condition("p.name = :name"), @Condition("p.url = :url"),
			@Condition("p.description = :description"),
			@Condition("p.name = :name") })
	@OrderBys({ @OrderBy("p.index asc"), @OrderBy("p.index desc") })
	List<ProShop> find(@ParameterMap Map<String, Object> parameters,
			@Order Short sort, @Page Pagination pagination);

}
