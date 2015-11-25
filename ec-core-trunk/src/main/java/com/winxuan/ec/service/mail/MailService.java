/*
 * @(#)MailService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.mail;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerNotify;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 邮件发送封装
 * @author  HideHai
 * @version 1.0,2011-11-3
 */
public interface MailService {
	
	 void sendMailByOrderStatus(Order order);
	
	 void sendMail(String to, String subject, String templateName,Map<String, Object> model);
	 
	 void sendArrivalMail(List<CustomerNotify> customerNotifies , List<ProductSale> productRecommendation);
	 
	 void sendPriceReduce(List<CustomerNotify> customerNotifies , List<ProductSale> productRecommendation);

}

