/*
 * @(#)CustomerCommentServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.customer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CustomerCommentDao;
import com.winxuan.ec.dao.ProductSalePerformanceDao;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerCommentReply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.points.CustomerPointsRule;
import com.winxuan.ec.service.customer.points.CustomerPointsRuleFactory;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 用户评论
 * 
 * @author HideHai
 * @version 1.0,2011-10-10
 */
@Service("customerCommentService")
@Transactional(rollbackFor = Exception.class)
public class CustomerCommentServiceImpl implements CustomerCommentService,
		Serializable {

	private static final long serialVersionUID = 7414409637085296182L;

	@InjectDao
	private CustomerCommentDao customerCommentDao;

	@InjectDao
	private ProductSalePerformanceDao productSalePerformanceDao;

	@Autowired
	private CustomerPointsRuleFactory customerPointsRuleFactory;

	@Override
	public CustomerComment get(Long id) {
		return customerCommentDao.get(id);
	}

	@Override
	public void save(CustomerComment customerComment, ProductSale productSale) {
		this.saveComment(customerComment, productSale);
	}
	
	

	@Override
	public List<CustomerComment> findByProductSale(ProductSale productSale,
			Pagination pagination) {

		return findByProductSale(productSale, pagination,
				Channel.DIRECT_CHANNEL);
	}

	@Override
	public List<CustomerComment> findByProductSale(ProductSale productSale,
			Pagination pagination, Channel channel) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSale", productSale);
		parameters.put("channel", channel);
		return customerCommentDao.find(parameters, pagination,
				Short.valueOf("0"));
	}

	@Override
	public List<CustomerComment> findByProductSale(ProductSale productSale,
			int size, short order, Channel channel) {

		return customerCommentDao.getByProductSale(productSale, channel, size,
				order);
	}

	@Override
	public List<CustomerComment> findByProductSale(ProductSale productSale,
			int size, short order) {

		return findByProductSale(productSale, size, order,
				Channel.DIRECT_CHANNEL);
	}

	@Override
	public long getCountByProductSale(ProductSale productSale) {

		return getCountByProductSale(productSale, Channel.DIRECT_CHANNEL);
	}

	@Override
	public long getCountByProductSale(ProductSale productSale, Channel channel) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSale", productSale);
		parameters.put("channel", channel);
		return customerCommentDao.findCount(parameters);
	}

	@Override
	public long getCountByProductSaleAndCustomer(ProductSale productSale,
			Customer customer) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSale", productSale);
		parameters.put("customer", customer);
		return customerCommentDao.findCount(parameters);
	}

	@Override
	public void updateCustomerComment(CustomerComment customerComment,
			Customer customer, boolean isUseful) {
		if (isUseful) {
			int usefulCount = customerComment.getUsefulCount();
			customerComment.setUsefulCount(usefulCount + 1);
		} else {
			int uselessCount = customerComment.getUselessCount();
			customerComment.setUselessCount(uselessCount + 1);
		}
		customerComment.addCommentRank(isUseful, customer);
		customerCommentDao.update(customerComment);
	}

	@Override
	public void saveReply(CustomerCommentReply reply) {
		customerCommentDao.saveReply(reply);
	}

	@Override
	public void delete(CustomerComment customerComment) {
		List<CustomerComment> comments = findByQuoteComment(customerComment);
		if(CollectionUtils.isNotEmpty(comments)){
			for(CustomerComment comment : comments){
				delete(comment);
			}
		}
		customerCommentDao.delete(customerComment);
	}

	@Override
	public void update(CustomerComment customerComment) {
		customerCommentDao.update(customerComment);
	}

	@Override
	public CustomerCommentReply findReply(Long id) {
		return customerCommentDao.findReply(id);
	}

	@Override
	public void updateReply(CustomerCommentReply re) {
		customerCommentDao.updateReply(re);
	}

	@Override
	public CustomerComment getMaxMergeByChannel(Channel channel) {
		List<CustomerComment> list = customerCommentDao.getMaxMergeByChannel(
				channel, new Pagination(MagicNumber.ONE));
		return list == null || list.size() == 0 ? new CustomerComment() : list
				.get(0);
	}

	@Override
	public void save(CustomerComment customerComment) {
		customerCommentDao.save(customerComment);
		ProductSalePerformance productSalePerformance = customerComment
				.getProductSale().getPerformance();
		productSalePerformance.setTotalComment(productSalePerformance
				.getTotalComment() + 1);
		productSalePerformanceDao.saveOrupdate(productSalePerformance);
	}

	@Override
	public int saveComment(CustomerComment customerComment, ProductSale productSale) {
		customerCommentDao.save(customerComment);
		ProductSalePerformance productSalePerformance = productSale
				.getPerformance();
		productSalePerformance.setTotalComment(productSalePerformance
				.getTotalComment() + 1);
		productSalePerformanceDao.saveOrupdate(productSalePerformance);
		CustomerPointsRule customerPointsRule = customerPointsRuleFactory
				.createCommentCustomerPointsRule(customerComment.getCustomer(),
						productSalePerformance);
		return customerPointsRule.addPoints();
	}

	@Override
	public List<CustomerComment> findByQuoteComment(CustomerComment customerComment) {
		return customerCommentDao.findByQuoteComment(customerComment);
	}

	@Override
	public List<CustomerComment> findByProductSale(Map<String, Object> params,
			Pagination pagination) {
		return  customerCommentDao.find(params, pagination, (short)1);
	}

}
