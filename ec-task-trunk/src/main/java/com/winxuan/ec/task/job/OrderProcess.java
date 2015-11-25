/*
 * @(#)AppTest.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.winxuan.ec.task.job.ec.order.ProcessFreeOrder;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-29
 */
public class OrderProcess {
	private static ApplicationContext applicationContext = null;
	private static final Log LOG = LogFactory.getLog(OrderProcess.class);

	public static void cancelOrder(){
		List<String> list = new ArrayList<String>();
		list.add("20120404481690");
		list.add("20120404481930");
		list.add("20120404482396");
		list.add("20120404481400");
		list.add("20120403478811");
		list.add("20120403480770");
		list.add("20120403481349");
		list.add("20120404481650");
		list.add("20120403479057");
		list.add("20120404481584");
		ProcessFreeOrder  freeOrder  = (ProcessFreeOrder)applicationContext.getBean("processFreeOrder");
		freeOrder.cancelOrder(list);
	}
	public static void createCredential(){
		ProcessFreeOrder  freeOrder  = (ProcessFreeOrder)applicationContext.getBean("processFreeOrder");
		List<String> returnOrders = new ArrayList<String>();
		returnOrders.add("30110519146781");
		returnOrders.add("30110519146763");
		freeOrder.createChannelCredential(returnOrders);
	}

	public static void orderDelivery(){
		List<String> list = new ArrayList<String>();
		list.add("20111223717139");
		list.add("20111223717140");
		list.add("20111223717141");
		list.add("20111223717142");
		list.add("20111223717143");
		list.add("20111223717144");
		list.add("20111223717145");
		list.add("20111223717146");
		ProcessFreeOrder  freeOrder  = (ProcessFreeOrder)applicationContext.getBean("processFreeOrder");
		freeOrder.orderDelivery(list);
	}
	
	public static void refundOrder(){
		List<String> list = new ArrayList<String>();
		list.add("20110520021291");
		ProcessFreeOrder  freeOrder  = (ProcessFreeOrder)applicationContext.getBean("processFreeOrder");
		freeOrder.orderRefund(list);
	}
	
	public static void updateReturnOrderException(){
		List<String> returnOrders = new ArrayList<String>();
		returnOrders.add("30110519145450");
		returnOrders.add("30110519145584");
		returnOrders.add("30110519145608");
		returnOrders.add("30110519145624");
		returnOrders.add("30110519145935");
		returnOrders.add("30110519146773");
		returnOrders.add("30110519149597");
		returnOrders.add("30110519149759");
		returnOrders.add("30110519149850");
		ProcessFreeOrder  freeOrder  = (ProcessFreeOrder)applicationContext.getBean("processFreeOrder");
		freeOrder.updateReturnOrderException(returnOrders);
	}

	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext("conf/commandContext.xml");
		LOG.error("ttt");
		System.exit(0);
	}

}

