/*
 * @(#)AppTest.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.winxuan.ec.task.job.channel.ChannelSalesToSapJob;
import com.winxuan.ec.task.job.ec.TransferOrderSap;
import com.winxuan.ec.task.job.ec.admin.dic.fetch.DicFetchTask;
import com.winxuan.ec.task.job.ec.front.NotifyArrival;
import com.winxuan.ec.task.job.ec.order.AuditingOrder;
import com.winxuan.ec.task.job.ec.order.CancelOverduePayOrder;
import com.winxuan.ec.task.job.ec.order.CheckOrderReturnBack;
import com.winxuan.ec.task.job.ec.order.CloneOrder;
import com.winxuan.ec.task.job.ec.order.MrDeliveryRecordJob;
import com.winxuan.ec.task.job.ec.order.OrderSettleJob;
import com.winxuan.ec.task.job.ec.order.ParseOrderStock;
import com.winxuan.ec.task.job.ec.order.TransferNewOrder;
import com.winxuan.ec.task.job.ec.order.TransferOrderDelivery;
import com.winxuan.ec.task.job.ec.order.TransferOrderInvoice;
import com.winxuan.ec.task.job.ec.order.TransferOrderStatus;
import com.winxuan.ec.task.job.ec.order.TransferReplenishment;
import com.winxuan.ec.task.job.ec.order.TransferReplenishmentNew;
import com.winxuan.ec.task.job.ec.order.TransferReturnOrder;
import com.winxuan.ec.task.job.ec.order.TransferSapOrderDelivery;
import com.winxuan.ec.task.job.ec.product.ErpAllStockSync;
import com.winxuan.ec.task.job.ec.product.ProductWareHouseJob;
import com.winxuan.ec.task.job.feed.CreateFullIndex;
import com.winxuan.ec.task.job.feed.CreateIncrementIndex;
import com.winxuan.ec.task.job.feed.douban.CreateFullIndexForDoubanJob;
import com.winxuan.ec.task.job.image.CompressImage;
import com.winxuan.ec.task.job.search.SearchUpdate;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-29
 */
public class AppTest {
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("conf/commandContext.xml");

	public static void transferReplenishment() {
		TransferReplenishment transferReplenishment = (TransferReplenishment) applicationContext.getBean("transferReplenishment");
		transferReplenishment.start();
	}
	
	public static void transferReplenishmentNew() {
		TransferReplenishmentNew transferReplenishmentNew = (TransferReplenishmentNew) applicationContext.getBean("transferReplenishmentNew");
		transferReplenishmentNew.start();
	}
	
	public static void transferNewOrder(){
		TransferNewOrder transferNewOrder =  (TransferNewOrder) applicationContext.getBean("transferNewOrder");
		transferNewOrder.start();
	}
	
	public static void transferOrderSap(){
		TransferOrderSap transferOrderSap =  (TransferOrderSap) applicationContext.getBean("transferOrderSap");
		transferOrderSap.start();
	}

	public static void transferSapOrderDelivery(){
		TransferSapOrderDelivery transferSapOrderDelivery =  (TransferSapOrderDelivery) applicationContext.getBean("transferSapOrderDelivery");
		transferSapOrderDelivery.start();
	}
	
	public static void transferReturnOrder(){
		TransferReturnOrder transferReturnOrder =  (TransferReturnOrder) applicationContext.getBean("transferReturnOrder");
		transferReturnOrder.start();
	}

	public static void fetchOrder(){
		TransferOrderStatus transferOrderStatus =  (TransferOrderStatus) applicationContext.getBean("transferOrderStatus");
		transferOrderStatus.start();
	}

	public static void auditingOrder(){
		AuditingOrder auditingOrder =  (AuditingOrder) applicationContext.getBean("auditingOrder");
		auditingOrder.start();
	}

	public static void fetchOrderInvoice(){
		TransferOrderInvoice transferOrderInvoice =  (TransferOrderInvoice) applicationContext.getBean("transferOrderInvoice");
		transferOrderInvoice.start();
	}

	public static void notifyArrival(){
		NotifyArrival notifyArrival =  (NotifyArrival) applicationContext.getBean("notifyArrival");
		notifyArrival.start();
	}

	public static void recommend(){
//		RecommendService recommendService =  (RecommendService) applicationContext.getBean("recommendService");
//		recommendService.start(0);
	}
	
	public static void compressImage(){
		CompressImage compressImage = (CompressImage)applicationContext.getBean("compressImage");
		compressImage.start();
	}
	
	public static void payOrder(){
		CancelOverduePayOrder  cancelOverduePayOrder  = (CancelOverduePayOrder)applicationContext.getBean("cancelOverduePayOrder");
		cancelOverduePayOrder.start();
	}
	
	public static void dicFetch(){
		DicFetchTask  dicFetchTask  = (DicFetchTask)applicationContext.getBean("dicFetchTask");
		dicFetchTask.start();
	}
	
	public static void cloneOrder(){
		CloneOrder cloneOrder= (CloneOrder)applicationContext.getBean("cloneOrder");
		cloneOrder.start();
	}
	
	public static void transferOrderDelivery(){
		TransferOrderDelivery orderDelivery= (TransferOrderDelivery)applicationContext.getBean("transferOrderDelivery");
		orderDelivery.start();
	}

	public static void calculateCommission(){
//		FranchiseeOrderCommission franchiseeOrderCommission = (FranchiseeOrderCommission)applicationContext.getBean("franchiseeOrderCommission");
//		franchiseeOrderCommission.start();
	}
	
	public static void checkOrderReturnBack(){
		CheckOrderReturnBack checkOrderReturnBack= (CheckOrderReturnBack)applicationContext.getBean("checkOrderReturnBack");
		checkOrderReturnBack.start();
	}
	
	public static void mrDeliveryRecordJob() {
		MrDeliveryRecordJob mrDeliveryRecordJob = (MrDeliveryRecordJob)applicationContext.getBean("mrDeliveryRecordJob");
		mrDeliveryRecordJob.start();
	}
	public static void checkProductWarehouse(){
		ProductWareHouseJob productWareHouseJob =(ProductWareHouseJob)applicationContext.getBean("ProductWareHouseJob");
		productWareHouseJob.start();
	}
	
	public static void stockSync(){
		ErpAllStockSync erpAllStockSync = (ErpAllStockSync) applicationContext.getBean("erpAllStockSync");
		erpAllStockSync.start();
	}
	
	public static void orderSettleJob() {
		OrderSettleJob orderSettleJob = (OrderSettleJob) applicationContext.getBean("orderSettleJob");
		orderSettleJob.start();
	}
	
	public static void updatequery(){
		SearchUpdate searchUpdate = (SearchUpdate) applicationContext.getBean("searchUpdate");
		searchUpdate.start();
	}
	
	public static void parseOrderStock() {
		ParseOrderStock parseOrderStock = (ParseOrderStock) applicationContext.getBean("parseOrderStock");
		parseOrderStock.start();
	}
	
	public static void channelSalesToSapJob() {
		ChannelSalesToSapJob channelSalesToSapJob = (ChannelSalesToSapJob) applicationContext.getBean("channelSalesToSapJob");
		channelSalesToSapJob.start();
	}
	

	public static void createFullIndexJob(){
		CreateFullIndex createFullIndexJob = (CreateFullIndex) applicationContext.getBean("feedFullIndex");
		createFullIndexJob.start();
	}
	
	public static void createFullIndexJonForDouban(){
		CreateFullIndexForDoubanJob createFullIndexForDoubanJob = (CreateFullIndexForDoubanJob) applicationContext.getBean("createFullIndexForDoubanJob");
		createFullIndexForDoubanJob.start();
	}
	
	
	
	public static void createIncreamentIndexForEtao(){
		CreateIncrementIndex createIncrementIndex = (CreateIncrementIndex) applicationContext.getBean("feedIncrementIndex");
		createIncrementIndex.start();
	}
	
	public static void main(String[] args) {
//		updatequery();
//		stockSync();
//		checkProductWarehouse();
//		stockSync();
//		checkProductWarehouse();
//		RobotCategoryDao robotCategoryDao = (RobotCategoryDao) applicationContext.getBean("robotCategoryDao");
//		
//		try {
//			int size = robotCategoryDao.getNewCategories().size(); //输出size
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			
//		}
//		transferNewOrder();
//		checkOrderReturnBack();
//		transferReturnOrder();
//		fetchOrderInvoice();
//		payOrder();
		fetchOrder();
//		cloneOrder();
//		auditingOrder();
//		NotifyArrival();
//		recommend();
//		transferNewOrder();
//		recommend();
//		System.exit(0);
//		dicFetch();
//		transferUnionOrder();	
//		calculateCommission();
//		transferOrderSap();
//		transferSapOrderDelivery();
//		mrDeliveryRecordJob();
//		transferReplenishment() ;
//		transferReplenishmentNew();
//		transferOrderDelivery();
//		transferNewOrder();
//		orderSettleJob();
		//parseOrderStock(); 
		//checkProductWarehouse();
		//channelSalesToSapJob();
		//FeedProductSaleMapper feedProductSaleMapper =  (FeedProductSaleMapper) applicationContext.getBean("feedProductSaleMapper");
//		createFullIndexJob();
		//createIncreamentIndexForEtao();
		//createFullIndexJonForDouban();
//		parseOrderStock();
//		channelSalesToSapJob();
	}
	
}

