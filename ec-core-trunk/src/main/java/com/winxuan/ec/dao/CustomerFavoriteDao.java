/*
 * @(#)CustomerFavorateDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerFavorite;
import com.winxuan.ec.model.customer.CustomerFavoriteTag;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-8-31
 */
public interface CustomerFavoriteDao {

	@Get
	CustomerFavorite get(Long id);

	@Save
	void save(CustomerFavorite customerFavorite);

	@Update
	void update(CustomerFavorite customerFavorite);

	@Delete
	void delete(CustomerFavorite customerFavorite);

	@Query("select distinct f from CustomerFavorite f left join f.tagList t where f.customer = :customer")
	@Conditions({ @Condition("f.sort = :sort "),
			@Condition("t = :tag") ,
			@Condition("t is null and true = :flag")})
	@OrderBys({
			@OrderBy("f.addTime desc,f.id desc"),
			@OrderBy("f.productSale.salePrice asc"),
			@OrderBy("f.productSale.salePrice/f.productSale.product.listPrice asc") })
	List<CustomerFavorite> find(@Parameter("customer") Customer customer,
			@Parameter("sort") Code sort,
			@Parameter("tag") CustomerFavoriteTag tag,
			@Parameter("flag") Boolean flag,
			@Page Pagination pagination, @Order short index);
	
	@Query("from CustomerFavorite f where f.customer = ? and f.sort.id= 11001 and f.productSale.saleStatus.id = 13002 order by f.addTime desc")
	List<CustomerFavorite> find(Customer customer, @MaxResults int size);

	@Query("from CustomerFavorite f where f.customer=? and f.addStatus.id=13001 and f.productSale.status.id=13002")
	long countOfNew(Customer customer);

	@Query("from CustomerFavorite f where f.customer=? and f.productSale.salePrice<f.addPrice")
	long countOfReduction(Customer customer);

	@Query("from CustomerFavorite f where f.customer=? and f.productSale=?")
	CustomerFavorite getByCustomerAndProductSale(Customer customer,
			ProductSale productSale);
}
