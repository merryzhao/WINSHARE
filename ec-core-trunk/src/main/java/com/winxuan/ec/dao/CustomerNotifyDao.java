/*
 * @(#)CustomerNotifyDao
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerFavorite;
import com.winxuan.ec.model.customer.CustomerNotify;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author huangyixiang
 * @version 1.0,2011-10-24
 */
public interface CustomerNotifyDao {

	@Get
	CustomerFavorite get(Long id);

	@Save
	void save(CustomerNotify customerNotify);

	@Update
	void update(CustomerNotify customerNotify);

	@Delete
	void delete(CustomerNotify customerNotify);
	
	@Query("select distinct n from CustomerNotify n ")
	@Conditions({
			@Condition("n.customer = :customer "),
			@Condition("n.productSale.saleStatus=:saleStatus and true=:flagArrival "),
			@Condition("(n.productSale.salePrice < n.addSellPrice or n.productSale.salePrice<:expectedprice) and true=:flagPriceReduce "),
			@Condition("n.sort = :sort "),
		 	@Condition("n.type = :type "),
		 	@Condition("n.addTime >= :addTimeMin "),
		 	@Condition("n.addTime < :addTimeMax "),
		 	@Condition("n.isNotified = :isNotified ")})
	@OrderBys({
			@OrderBy("n.addTime desc"),
			@OrderBy("n.addSellPrice/n.addListPrice - n.productSale.salePrice/n.productSale.product.listPrice desc"),
			@OrderBy("n.addSellPrice - n.productSale.salePrice desc"),
			@OrderBy("n.productSale.salePrice desc"),
			@OrderBy("n.productSale.salePrice/n.productSale.product.listPrice desc")})
	List<CustomerNotify> find(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination, @Order short order);
	
	/*
	@Query("select n.category,count(*) from CustomerNotify n where n.customer=:customer group by n.category order by n.favoriteList.size desc")
	@Conditions({ @Condition("n.sort = :sort "),
		 	@Condition("n.type = :type ")})
	List<Object[]> group(@Parameter("customer") Customer customer,
			@Parameter("type") Code type);
	*/
	
	@Query("from CustomerNotify n where n.customer=:customer and n.productSale=:productSale")
	@Conditions({ @Condition("n.type = :type ")})
	CustomerNotify getByCustomerAndProductSale(@Parameter("customer") Customer customer,
			@Parameter("productSale") ProductSale productSale, @Parameter("type") Code type);
	
	@Query(value="select (case when to_days(now())-to_days(addtime) <= 30 then '1' "+
			"when to_days(now())-to_days(addtime) <= 91 then '2'"+ 
			"when to_days(now())-to_days(addtime) <= 182 then '3'"+
			"when to_days(now())-to_days(addtime) <= 365 then '4'"+ 
			"else '5' end) time,count(*) count "+ 
			"from customer_notify where customer=? and type=? group by "+ 
	"(case when to_days(now())-to_days(addtime) <= 30 then '1'"+ 
			"when to_days(now())-to_days(addtime) <= 91 then '2'"+ 
			"when to_days(now())-to_days(addtime) <= 182 then '3'"+ 
			"when to_days(now())-to_days(addtime) <= 365 then '4'"+ 
			"else '5' end)" , sqlQuery=true)
	List<Map<String,Object>> groupByDate(Long customer,Long type);
	
}
