/*
 * @(#)CustomerCommentService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.customer;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerCommentReply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.pagination.Pagination;

/**
 * 用户评论
 * @author  HideHai
 * @version 1.0,2011-10-10
 */
public interface CustomerCommentService {
	/**
	 *  根据编号获取评论
	 * @param id
	 * @return
	 */
	CustomerComment get(Long id);
	
	/**
	 * 保存评论
	 * @param customerComment
	 */
	void save(CustomerComment customerComment,ProductSale productSale);
	
	int saveComment(CustomerComment customerComment,ProductSale productSale);
	
	/**
	 * 根据指定商品获取评论集合---文轩网
	 * @param productSale
	 * @return
	 */
	List<CustomerComment> findByProductSale(ProductSale productSale,Pagination pagination);
	
	/**
	 * 根据指定商品获取评论集合---渠道
	 * @param productSale
	 * @param pagination
	 * @param channel
	 * @return
	 */
	List<CustomerComment> findByProductSale(ProductSale productSale,Pagination pagination,Channel channel);
	
	/**
	 * 获取商品指定数量的评论--文轩网
	 * @param productSale
	 * @param size
	 * @return
	 */
	List<CustomerComment> findByProductSale(ProductSale productSale,int size,short order);
	
	/**
	 * 获取商品指定数量的评论--渠道
	 * @param productSale
	 * @param size
	 * @param order
	 * @param channel
	 * @return
	 */
	List<CustomerComment> findByProductSale(ProductSale productSale,int size,short order,Channel channel);
	
	/**
	 * 根据指定商品获取评论数量---文轩网
	 * @param productSale
	 * @return
	 */
	long getCountByProductSale(ProductSale productSale);
	
	/**
	 * 根据指定商品获取评论数量---渠道
	 * @param productSale
	 * @param channel
	 * @return
	 */
	long getCountByProductSale(ProductSale productSale,Channel channel);
	
	/**
	 * 根据指定商品和用户获取评论数量
	 * @param productSale
	 * @param customer
	 * @return
	 */
	long getCountByProductSaleAndCustomer(ProductSale productSale,Customer customer);
	
	/**
	 * 更新有无用数量
	 */
	void updateCustomerComment(CustomerComment customerComment,Customer customer,boolean isUseful);
	
	/**
	 * 保存评论回复
	 * @param reply
	 */
	void saveReply(CustomerCommentReply reply);
	
	/**
	 * 删除评论
	 */
	void delete(CustomerComment customerComment);
	/**
	 * 更新评论
	 */
	void update(CustomerComment customerComment);

    CustomerCommentReply findReply(Long id);

    void updateReply(CustomerCommentReply re);

    /**
     * 取得渠道融合后的最大记录
     * @param channel
     * @return
     */
	CustomerComment getMaxMergeByChannel(Channel channel);

	/**
	 * 保存渠道的评论信息
	 * @param customerComments
	 */
	void save(CustomerComment customerComment);
	
	List<CustomerComment> findByQuoteComment(CustomerComment customerComment);
	
	/**
	 * 查询分评论等级
	 * @param params
	 * @param pagination
	 * @return
	 */
	List<CustomerComment> findByProductSale(Map<String, Object> params,
			Pagination pagination);
	
	
}

