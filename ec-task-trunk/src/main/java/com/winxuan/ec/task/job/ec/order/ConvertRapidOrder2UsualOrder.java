/**
 * 
 */
package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.task.service.ec.EcOrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * @author zhousl
 *
 * 2013-9-10
 */
public class ConvertRapidOrder2UsualOrder implements TaskAware, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EcOrderService ecOrderService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	@Override
	public String getName() {
		return "convertRapidOrder2UsualOrder";
	}

	@Override
	public String getDescription() {
		return "分拨订单转普通订单";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		hibernateLazyResolver.openSession();
		ecOrderService.convertRapidOrder2UsualOrder();
		hibernateLazyResolver.releaseSession();
	}

}
