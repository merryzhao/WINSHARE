/*
 * @(#)CustomerFavoriteTagDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.customer.CustomerFavoriteTag;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-1
 */
public interface CustomerFavoriteTagDao {

	@Get
	CustomerFavoriteTag get(Long id);

	@Update
	void update(CustomerFavoriteTag tag);

	@Delete
	void delete(CustomerFavoriteTag tag);
	
	@Save
	void save(CustomerFavoriteTag tag);

	@Query("from CustomerFavoriteTag t where t.customer=? order by t.createTime desc")
	List<CustomerFavoriteTag> findByCustomer(Customer customer);

	@Query("from CustomerFavoriteTag t where t.customer=? order by t.createTime desc")
	List<CustomerFavoriteTag> findByCustomer(Customer customer,
			@MaxResults int maxResults);

	@Query("select t,t.favoriteList.size from CustomerFavoriteTag t where t.customer=? order by t.createTime")
	List<Object[]> group(Customer customer);
	
	@Query("from CustomerFavorite cf left join cf.tagList t where cf.customer=? and t is null")
	long countByNoTag(Customer customer);

	@Query("from CustomerFavorite cf where cf.customer=?")
	long countByAllTag(Customer customer);

	@Query("from CustomerFavoriteTag t where t.customer=? and t.tagName=?")
	CustomerFavoriteTag getByCustomerAndName(Customer customer, String tagName);
	
	@Query("from CustomerFavoriteTag t where t.customer=? and t.id=?")
	CustomerFavoriteTag getByCustomerAndId(Customer customer, Long id);
}
