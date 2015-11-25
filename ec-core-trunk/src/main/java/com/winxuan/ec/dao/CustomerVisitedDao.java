/*
 * @(#)CustomerVisitedDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.customer.CustomerVisited;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-29
 */
public interface CustomerVisitedDao {

	@Save
	void save(CustomerVisited visited);

	@Update
	void update(CustomerVisited visited);

	@Query("from CustomerVisited c where c.client=? and c.productSale=?")
	CustomerVisited get(String client, ProductSale productSale);

	@Query("select c.productSale from CustomerVisited c where c.client=? and c.deleted=false order by c.visitTime desc")
	List<ProductSale> findAvailableByClient(String client,
			@MaxResults int maxResults);

	@Query(value = "update customer_visited set deleted=1 where client=? and deleted=0", executeUpdate = true, sqlQuery = true)
	void deleteByClient(String client);
}
