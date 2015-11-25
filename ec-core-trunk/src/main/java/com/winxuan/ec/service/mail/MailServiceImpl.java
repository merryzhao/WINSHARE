/*
 * @(#)MailServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.mail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerNotify;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.order.OrderServiceImpl;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.Constant;
import com.winxuan.framework.util.StringUtils;
import com.winxuan.framework.util.jms.JmsInvokerProxyFactoryBean;
import com.winxuan.framework.util.mail.MailSender;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-8-9
 */
@Service("mailService")
public class MailServiceImpl implements MailService, Serializable {

	private static final long serialVersionUID = 1023481680818478012L;

	private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Value("${core.mailQueneName}")
	private String mailQueneName;

	@Value("${core.mailQueneAddress}")
	private String mailQueneAddress;

	private MailSender mailSender;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public void sendMail(String to, String subject, String templateName,
			Map<String, Object> model) {
		if (to == null) {
			return;
		}
		mailSenderInit();
		if (!StringUtils.isNullOrEmpty(subject)
				&& subject.length() > MagicNumber.SIXTY) {
			subject = subject.substring(MagicNumber.ZERO, MagicNumber.SIXTY);
		}
		mailSender.send(to, subject, templateName, model);
	}

	@Override
	public void sendMailByOrderStatus(Order order) {
		mailSenderInit();
		Long status = order.getProcessStatus().getId();
		String orderId = order.getId();
		// String to = order.getConsignee().getEmail();
		String to = order.getCustomer().getEmail();// 修改为发给下单人
		order.getCustomer().getDisplayName();
		String subjectPrefix = "文轩网提醒：订单";
		String subject = null;
		String template = null;
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		if (status.equals(Code.ORDER_PROCESS_STATUS_NEW)) {
			subject = subjectPrefix + orderId + "已提交";
			template = Constant.MAIL_ORDER_NEW;
			Customer customer = order.getCustomer();
			boolean isAnonym = false;
			if (Code.USER_SOURCE_ANONYMITY.equals(customer.getSource().getId())
					&& customer.isFirstOrder()) {
				// 匿名用户会话内下单，邮件内容中提示用户信息
				isAnonym = true;
			}
			if(customer.isFirstOrder()){
				customer.setFirstOrder(false);
				customerService.update(customer);
			}
			model.put("isAnonym", isAnonym);
		} else if (status.equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING) && order.getAdvanceMoney().compareTo(order.getTotalPayMoney()) == 0) {
			subject = subjectPrefix + orderId + "款项已收到";
			template = Constant.MAIL_ORDER_PAYED;
		} else if (status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED)
				|| status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)) {
			subject = subjectPrefix + orderId + "已发货";
			template = Constant.MAIL_ORDER_DELIVERY;
		} else if (Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL.equals(status)) {
			subject = subjectPrefix + orderId + "缺货取消";
			template = Constant.MAIL_ORDER_LACKED;
		}
		// 以下暂不提供邮件功能
		/*
		 * else if (status.equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
		 * subject = subjectPrefix + orderId + "准备配货"; template =
		 * Constant.MAIL_ORDER_EFFECTIVE; }else
		 * if(status.equals(Code.ORDER_PROCESS_STATUS_PICKING)){ subject =
		 * subjectPrefix + orderId + "正在配货"; template =
		 * Constant.MAIL_ORDER_EFFECTIVE; } else if (order.isCanceled()) {
		 * subject = subjectPrefix + orderId + "已取消"; template =
		 * Constant.MAIL_ORDER_CANCEL; }
		 */
		if (subject != null && template != null) {
			mailSender.send(to, subject, template, model);
		}
	}

	@Override
	public void sendArrivalMail(List<CustomerNotify> customerNotifies,
			List<ProductSale> productRecommendation) {
		mailSenderInit();
		String subjectPrefix = "文轩网提醒：到货通知";
		Map<String, Object> map = new HashMap<String, Object>();
		if (productRecommendation == null || productRecommendation.size() == 0) {
			productRecommendation = null;
		}
		map.put("customerNotifies", customerNotifies);
		map.put("productRecommendation", productRecommendation);
		String email = customerNotifies.get(0).getCustomer().getEmail();
		String displayName = customerNotifies.get(0).getCustomer()
				.getDisplayName();
		log.info("发送邮件到：" + email + "-" + displayName);
		mailSender
				.send(email, subjectPrefix, Constant.MAIL_NOTIFY_ARRIVAL, map);
	}

	@Override
	public void sendPriceReduce(List<CustomerNotify> customerNotifies,
			List<ProductSale> productRecommendation) {
		mailSenderInit();
		String subjectPrefix = "文轩网提醒：降价通知";
		Map<String, Object> map = new HashMap<String, Object>();
		if (productRecommendation == null || productRecommendation.size() == 0) {
			productRecommendation = null;
		}
		map.put("customerNotifies", customerNotifies);
		map.put("productRecommendation", productRecommendation);
		String email = customerNotifies.get(0).getCustomer().getEmail();
		String displayName = customerNotifies.get(0).getCustomer()
				.getDisplayName();
		log.info("发送邮件到：" + email + "-" + displayName);
		mailSender.send(email, subjectPrefix,
				Constant.MAIL_NOTIFY_PRICE_REDUCE, map);
	}

	private void mailSenderInit() {
		if (StringUtils.isNullOrEmpty(mailQueneAddress)
				|| StringUtils.isNullOrEmpty(mailQueneName)) {
			log.warn("未加载邮件配置路径!");
			return;
		}
		log.debug("mailService: " + mailSender);
		if (mailSender == null || mailSender.toString() == null) {
			ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(
					mailQueneAddress);
			ActiveMQQueue mqQueue = new ActiveMQQueue(mailQueneName);

			JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
			factoryBean.setServiceInterface(MailSender.class);
			factoryBean.setDestination(mqQueue);
			factoryBean.setConnectionFactory(mqConnectionFactory);
			factoryBean.afterPropertiesSet();
			mailSender = (MailSender) factoryBean.getObject();
			mqConnectionFactory = null;
			mqQueue = null;
			factoryBean = null;
		}
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}
