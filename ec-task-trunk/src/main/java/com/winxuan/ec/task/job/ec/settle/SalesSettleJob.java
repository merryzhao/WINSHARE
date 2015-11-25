package com.winxuan.ec.task.job.ec.settle;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.service.settle.SalesSettleService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 * 每日销售任务
 * @author wenchx
 * @version 1.0, 2014-8-1 上午9:57:31
 */
@Component("salesSettleJob")
public class SalesSettleJob implements TaskAware, Serializable, TaskConfigure {

	private static final long serialVersionUID = -7831165685439479155L;

	private static final Log LOG = LogFactory.getLog(SalesSettleJob.class);
	private static final int LIMIT = 2000;//一次处理多少
	@Autowired
	private SalesSettleService salesSettleService;
	

	@Override
	public int getNotifyLevel() {
		return 0;
	}

	@Override
	public void doNotify(String... msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "salesSettleJob";
	}

	@Override
	public String getDescription() {
		return "每日销售";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		LOG.info("SalesSettleJob is start !!!");
		while(true){
			int n =salesSettleService.start(LIMIT);
			LOG.info("当前销售个数:" + n);
			if (n < LIMIT){
				break;
			}
		}
		
		while(true){
			int n =salesSettleService.startReturnSales(LIMIT);
			LOG.info("当前退货订单个数:" + n);
			if (n < LIMIT){
				break;
			}
		}
		
		while(true){
			int n =	salesSettleService.startRejectSales(LIMIT); 
			LOG.info("当前拒收数据订单个数:" + n);
			if (n < LIMIT){
				break;
			}
		}
		
	
		 
		LOG.info("SalesSettleJob is finished!!!");
	}

	 
}
