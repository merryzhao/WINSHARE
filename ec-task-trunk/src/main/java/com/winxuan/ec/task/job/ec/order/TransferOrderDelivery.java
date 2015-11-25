package com.winxuan.ec.task.job.ec.order;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 从JKXX_ZD_ZQ_EC_YSDH读取MSGTY is null的数据
 * 更新EC对应的订单运单信息
 * @author HideHai
 * @version 0.1 ,2012-7-13
 **/
@Component("transferOrderDelivery")
public class TransferOrderDelivery implements TaskAware {

	private static final Log LOG = LogFactory.getLog(TransferOrderDelivery.class);
	private static final int ERRORTIMES = MagicNumber.HUNDRED;

	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;

	@Override
	public String getName() {
		return "transferOrderDelivery";
	}

	@Override
	public String getDescription() {
		return "更新48小时内的运单修改信息";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		int errorCount = 0;
		List<String> orders = erpOrderService.fetchDistinctOrderDelivery();
		for(String id : orders){
			hibernateLazyResolver.openSession();
			try {
				erpOrderService.updateDeliveryInfoByOrderId(id);
			} catch (Exception e) {
				errorCount++;
				LOG.error(String.format("%s case error: %s",id,e.getMessage()), e);
			}
			hibernateLazyResolver.releaseSession();
			if(errorCount > ERRORTIMES){
				notifyService.notify(this,"订单运单更新因为异常次数过多中断! "+ERRORTIMES);
				break;
			}
		}
	}

}

