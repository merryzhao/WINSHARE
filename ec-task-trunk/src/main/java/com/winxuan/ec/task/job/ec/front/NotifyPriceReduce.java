/*
 * @(#)NotifyPriceReduce
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.front;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.customer.CustomerNotify;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.service.product.ProductRecommendationService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * @author  huangyixiang
 * @version 1.0,2011-11-15
 */
@Component("notifyPriceReduce")
public class NotifyPriceReduce implements Serializable,TaskAware{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 327899875836231288L;
	private static final Integer PAGE_SIZE = Integer.valueOf(100);
	private static final Log LOG = LogFactory.getLog(NotifyPriceReduce.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductRecommendationService productRecommendationService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	
	public void start(){
		LOG.info("start NotifyPriceReduce----------------");
		int currentPage = MagicNumber.ONE;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(PAGE_SIZE);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("flagPriceReduce", true);
		parameters.put("isNotified", false);
		List<CustomerNotify> list = null;
		Map<Long , List<CustomerNotify>> map = null;
		
		hibernateLazyResolver.openSession();
		while((list = customerService.findPriceReduce(parameters, (short)0, pagination)) !=  null && !list.isEmpty()){
			map = new HashMap<Long, List<CustomerNotify>>();
			for(CustomerNotify customerNotify : list){
				if(map.get(customerNotify.getCustomer().getId()) == null){
					List<CustomerNotify> notifyList = new ArrayList<CustomerNotify>();
					notifyList.add(customerNotify);
					map.put(customerNotify.getCustomer().getId(), notifyList);
				}
				else {
					map.get(customerNotify.getCustomer().getId()).add(customerNotify);
				}
				
				//初始化
				customerNotify.getUrl();
				customerNotify.getProductSale().getIntegerDiscount();
				customerNotify.getProductSale().getImageUrl();
				customerNotify.getProductSale().getProduct().getSaleList();
			}
			//发邮件
			for (Entry<Long, List<CustomerNotify>> e : map.entrySet()) {
				//推荐商品
				List<ProductSale> productSales = new ArrayList<ProductSale>();
				for(CustomerNotify customerNotify : e.getValue()){
					productSales.add(customerNotify.getProductSale());
				}
				Set<ProductSale> productRecommendations = productRecommendationService.findRecommendByProductSales(productSales,RecommendMode.BUY, MagicNumber.FOUR);
				List<ProductSale> products = new ArrayList<ProductSale>();
				for(ProductSale productRecommendation : productRecommendations){
					//初始化
					productRecommendation.getSellName();
					productRecommendation.getUrl();
					productRecommendation.getImageUrl();
					productRecommendation.getProduct().getSaleList();
					
					products.add(productRecommendation);
				}
				mailService.sendPriceReduce(e.getValue() , products);
				customerService.updateNotifies(e.getValue(), true);
			}
			pagination.setCurrentPage(++currentPage);
		}
		hibernateLazyResolver.releaseSession();
	}

	@Override
	public String getName() {
		return "notifyPriceReduce";
	}

	@Override
	public String getDescription() {
		return "降价通知邮件发送";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	}

}

