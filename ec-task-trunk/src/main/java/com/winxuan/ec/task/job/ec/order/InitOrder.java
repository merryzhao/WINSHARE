/**
 * 
 */
package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.order.OrderStockService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.pagination.Pagination;

/**
 * 定时获取为初始化的订单进行初始化
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-20
 */
@Component("initOrder")
public class InitOrder implements TaskAware, Serializable{

	private static final Log LOG = LogFactory.getLog(InitOrder.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 661570523113210428L;

	@Autowired
	private OrderStockService orderInit;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public String getName() {
		return "initOrder";
	}

	@Override
	public String getDescription() {
		return "定时获取为初始化的订单进行初始化";
	}
	
	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		LOG.info("开始执行初始化订单task");
		List<String> orders = null;
		int  pageNo = 1;
		while((orders = orderService.getNeedInitOrders(new Pagination(20, pageNo++))) != null && !orders.isEmpty()){
			for(String order :orders){
				orderInit.broadcastInitOrderStock(order);
				LOG.info(order);
			}
		}	
		LOG.info("执行初始化订单task结束");
	}

}
