package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.ec.EcOrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 
 * @author caoyouwen
 *
 */
@Component("transferBookingOrder2UsualOrder")
public class ConvertBookingOrder2UsualOrder implements TaskAware,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8492213019931999646L;

	private static final Log LOG = LogFactory.getLog(TransferOrderDelivery.class);
	
	@Autowired
	private EcOrderService ecOrderService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	@Override
	public String getName() {
		return "transferBookingOrder2UsualOrder";
	}

	@Override
	public String getDescription() {
		return "检查预售订单是否可转正式订单";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		hibernateLazyResolver.openSession();
		try {
			ecOrderService.convertBookingOrder2UsualOrder();
		} catch (Exception e) {
			LOG.error(String.format("Transfer error: %s",e.getMessage()), e);
		}
		hibernateLazyResolver.releaseSession();
	}
	
}
