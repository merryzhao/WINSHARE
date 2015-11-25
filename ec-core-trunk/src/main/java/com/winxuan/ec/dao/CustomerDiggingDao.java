/*
 * @(#)CustomerDiggingDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import com.winxuan.ec.model.customer.CustomerDigging;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-29
 */
public interface CustomerDiggingDao {

	@Query("from CustomerDigging c where c.customer=? and c.productSale=?")
	CustomerDigging get(Customer customer, ProductSale productSale);
	
	@Query("from CustomerDigging c where c.client=? and c.productSale=?")
	CustomerDigging get(String client, ProductSale productSale);
	
	@Save
	void save(CustomerDigging digging);
}
