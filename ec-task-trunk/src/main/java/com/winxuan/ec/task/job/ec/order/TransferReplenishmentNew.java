/*
] * @(#)TransferReplenishment.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
@Component("transferReplenishmentNew")
public class TransferReplenishmentNew implements TaskAware, TaskConfigure {

	private static final Log LOG = LogFactory.getLog(TransferReplenishmentNew.class);
	/**
	 * 2014.04.04之前，新的定时任务一次性向接口表写入1000条补货记录
	 * 调整之后，修改为每次写入100条补货记录
	 */
	private static final int NUMBER_PER_TRANSFER = 100;
	
	private NamedParameterJdbcTemplate dd;
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
		return "transferReplenishmentNew";
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
		LOG.info("应该下传的补货申请总数为：" + freezedProducts.size());

		int count = 0;
		
		while (count < freezedProducts.size()){
			final List<MrProductFreeze> subFreezedProducts = freezedProducts.subList(count, freezedProducts.size() - count >= NUMBER_PER_TRANSFER ?
					count + NUMBER_PER_TRANSFER:freezedProducts.size());
			count += NUMBER_PER_TRANSFER;
			sapReplenishmentService.transferReplenishmentItems(subFreezedProducts);
		}
		
		/**
		 * 当所有应该下传到SAP的补货记录都写到接口表之后，再一次性将记录的标志位由"T"改为"C"
		 */
		sapReplenishmentService.updateReplenishmentItems();
		
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
