/*
 * @(#)CustomerComment.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerCommentReply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 鐢ㄦ埛璇勮
 * @author  HideHai
 * @version 1.0,2011-10-10a
 */
public interface CustomerComplaintDao {

	@Get
	CustomerComment get(Long id);

	@SaveOrUpdate
	void save(CustomerComment customerComment);

	@Update
	void update(CustomerComment customerComment);

	@Delete
	void delete(CustomerComment customerComment);

	@Query("FROM CustomerComment c WHERE c.productSale=? ")
	@OrderBys({
		@OrderBy("c.commentTime desc"),
		@OrderBy("c.commentTime asc")
	})
	List<CustomerComment> getByProductSale(ProductSale productSale,@MaxResults int size,@Order short orderIndex);
	
	@Query("FROM CustomerComment c")
	@Conditions({
		@Condition("c.id in :ids"),
		@Condition("c.productSale = :productSale"),
		@Condition("c.productSale.shop.id = :shopId"),
		@Condition("c.customer = :customer"),
		@Condition("c.content like CONCAT('%',:content ,'%')"),
		@Condition("c.usefulCount >=:usefulCount"),
		@Condition("c.uselessCount >=:uselessCount"),
		@Condition("c.productSale.id = :productSaleId"),
		@Condition("c.productSale.id in :productSaleIds"),
		@Condition("c.productSale.outerId in :outerIds"),
		@Condition("c.productSale.product.name like :productName"),
		@Condition("c.commentTime >=:commentTimeStart"),
		@Condition("c.commentTime <=:commentTimeEnd"),
		@Condition("c.customer.name = :customerName")
	})
	@OrderBys({
		@OrderBy("c.commentTime desc"),
		@OrderBy("c.commentTime asc"),
		@OrderBy("c.id desc"), 
		@OrderBy("c.id asc")
	})
	List<CustomerComment> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,
			@Order short orderIndex);

	@Query("FROM CustomerComment c")
	@Conditions({
		@Condition("c.id in :ids"),
		@Condition("c.productSale = :productSale"),
		@Condition("c.productSale.shop.id = :shopId"),
		@Condition("c.customer = :customer"),
		@Condition("c.usefulCount >=:usefulCount"),
		@Condition("c.uselessCount >=:uselessCount")
	})
	long findCount(@ParameterMap Map<String, Object> parameters);

	@Query("SELECT count(*) FROM CustomerComment c WHERE c.customer=? AND c.usefulCount>0")
	long findUsefulCount(Customer customer);
	
	@Query("SELECT count(*) FROM CustomerComment c WHERE c.customer=? AND c.uselessCount>0")
	long finduselessCount(Customer customer);
	
	@Save
	void saveReply(CustomerCommentReply reply);

	@Update
    void updateReply(CustomerCommentReply re);

	@Get
    CustomerCommentReply findReply(Long id);
}

