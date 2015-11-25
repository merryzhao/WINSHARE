/*
 * @(#)TransferReplenishment.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.service.replenishment.MrProductFreezeService;
import com.winxuan.ec.task.service.sap.SapReplenishmentService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-8-30
 */
@Component("transferReplenishment")
public class TransferReplenishment implements TaskAware, TaskConfigure {

	private static final Log LOG = LogFactory.getLog(TransferReplenishment.class);
	
	@Autowired
	private MrProductFreezeService mrProductFreezeService;

	@Autowired
	private SapReplenishmentService sapReplenishmentService;

	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	@Autowired
	private NotifyService notifyService;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "transferReplenishment";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "智能补货商品冻结审核后下传SAP";
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		hibernateLazyResolver.openSession();
		List<MrProductFreeze> freezedProducts = mrProductFreezeService
				.findFreezedProducts();
		/**
		 * 获取应该下传的补货申请的总数total
		 */
		int total = freezedProducts.size();
		
		/**
		 * 获取当前已经成功下传的补货申请的数量num
		 */
		int num = 0;
		
		for (MrProductFreeze mrProductFreeze : freezedProducts) {
			try{
				/**
				 * 如果商品的补货数量不大于0，则不下传到SAP
				 */
				if(mrProductFreeze.getQuantity() > 0){
					
					/**
					 * 写入到inteface_replenishment接口表，此时标志位为"T"
					 */
					sapReplenishmentService.sendReplenishmentItems(new Object[] {
							mrProductFreeze.getDc().getId(),
							mrProductFreeze.getProductSale().getId(),
							mrProductFreeze.getDc().getName(),
							mrProductFreeze.getProductSale().getOuterId(),
							mrProductFreeze.getQuantity() });				
					mrProductFreeze.setFlag(MrProductFreeze.FLAG_SEND_SAP);
					mrProductFreezeService.save(mrProductFreeze);
					num++;
					/**
					 * 打印正确的日志信息
					 */
					LOG.info("productSaleId为:" + mrProductFreeze.getProductSale().getId()
							+ ",dc为:" + mrProductFreeze.getDc().getId()
							+ ",补货数量为:" + mrProductFreeze.getQuantity()
							+ "的补货申请: 下传SAP成功");
				}
			}catch(Exception e){
				/**
				 * 打印错误的日志信息
				 */
				LOG.error("productSaleId为:" + mrProductFreeze.getProductSale().getId()
						+ ",dc为:" + mrProductFreeze.getDc().getId()
						+ ",补货数量为:" + mrProductFreeze.getQuantity()
						+ "的补货申请: 下传SAP异常:" + e.getMessage(),e);
			}
		}

		/**
		 * 将mr_product_freeze表中所有满足下传SAP调整的补货记录都以标识位"T"写入interface_replenishment
		 * 后，再一次性将表中所有标志位为"T"的记录更新为"C"
		 */
		sapReplenishmentService.updateReplenishmentItems();
		
		/**
		 * 打印概括日志信息
		 */
		LOG.info("应下传补货申请的总数为:" + total + ",已成功下传的补货申请数为:" + num);
		hibernateLazyResolver.releaseSession();
	}

	@Override//XDict.exe/word.html###
	public int getNotifyLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void doNotify(String... msg) {
		// TODO Auto-generated method stub
		notifyService.notify(this, String.format("%s 由于过多异常中断!", getName()));
	}

}

