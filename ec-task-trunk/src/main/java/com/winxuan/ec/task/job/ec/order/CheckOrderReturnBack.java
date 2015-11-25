package com.winxuan.ec.task.job.ec.order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * @author yuhu
 * @version 1.0,2012-9-29上午11:05:28
 */
@Component("checkOrderReturnBack")
public class CheckOrderReturnBack  implements TaskAware{
	private static final Log LOG = LogFactory.getLog(CheckOrderReturnBack.class);
	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	@Override
	public String getName() {
		return "checkOrderReturnBack";
	}

	@Override
	public String getDescription() {
		return "检查超时回传的订单在中启中是否发货";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		hibernateLazyResolver.openSession();
		try {
			erpOrderService.checkOrderReturnBack();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		hibernateLazyResolver.releaseSession();
	}

}
