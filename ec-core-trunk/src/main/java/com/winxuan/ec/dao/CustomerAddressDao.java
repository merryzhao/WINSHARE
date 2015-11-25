/*
 * @(#)CustomerAddressDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 用户地址Dao
 *
 * @author wumaojie
 * @version 1.0,2011-7-20
 */
public interface CustomerAddressDao {

	@Get
	CustomerAddress get(Long id);
	
	@Save
	void save(CustomerAddress customerAddress);
	
	@Update
	void update(CustomerAddress customerAddress);
	
	@Delete
	void delete(CustomerAddress customerAddress);
	
	@Query("from CustomerAddress ca where ca.id = ? and ca.customer = ?")
	CustomerAddress getByCustomerAndId(Long id , Customer customer);
	
}
