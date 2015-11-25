/*
 * @(#)SellerDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.user.Seller;
import com.winxuan.framework.dynamicdao.annotation.Get;
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
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-8-9
 */
public interface SellerDao {

	@Get
	Seller get(Long id);
	
	@Query("from Seller e WHERE e.name = ?")
	Seller getByName(String name);
	
	@Query("from Seller e where e.name=? and e.password=?")
	Seller getByNameAndPassword(String name,String password);
	
	@Query("from Seller e where e.name=?")
	boolean nameIsExisted(String name);

	@Query("from Seller s")
	@Conditions({ @Condition("s.name=:sellerName"),
			@Condition("s.shop.shopName like :shopName"),
			@Condition("s.shop.businessScope =:businessScope"),
			@Condition("s.shop.state.id=:shopState"),
			@Condition("s.shopManager=:shopManager"),
			@Condition("s.shop.createDate >=:createDateBegin"),
			@Condition("s.shop.createDate <=:createDateEnd"),
			@Condition("s.shop.activeDate >=:activeDateBegin"),
			@Condition("s.shop.activeDate <=:activeDateEnd"),
			@Condition("s.name =:name"),
			@Condition("s.shop.endDate >=:endDateBegin"),
			@Condition("s.shop.endDate <=:endDateEnd") })
	@OrderBys({ @OrderBy("s.available desc,s.id asc") })
	List<Seller> find(@ParameterMap Map<String, Object> params,
			@Page Pagination pagination,@Order short orderIndex);

	@Query("from Seller")
	List<Seller> find();

	@Update
	void update(Seller seller);

}
