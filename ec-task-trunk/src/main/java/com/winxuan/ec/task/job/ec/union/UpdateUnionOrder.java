package com.winxuan.ec.task.job.ec.union;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.union.AdUnionOrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-29
 */
@Component("updateUnionOrder")
public class UpdateUnionOrder implements Serializable,TaskAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2891959212570897191L;
	@Autowired
	AdUnionOrderService adUnionOrderService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	@Override	
	public String getName() {
		return "updateUnionOrder";
	}

	@Override
	public String getDescription() {
		return "更新联盟订单状态";
	}

	@Override
	public String getGroup() {		
		return TaskAware.GROUP_EC_FRONT;
	}

	@Override
	public void start() {
		hibernateLazyResolver.openSession();
		adUnionOrderService.updateDeliveryOrderListbyDate();
		adUnionOrderService.updateCancelOrderListbyDate();
		adUnionOrderService.addRefundOrderListbyDate();
		hibernateLazyResolver.releaseSession();
	}	

}
