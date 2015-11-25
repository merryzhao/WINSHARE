/*
 * @(#)OrderDcService.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.order;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderCourierReceipt;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.erp.adapter.ErpService;
import com.winxuan.erp.adapter.WmsService;

/**
 * 
 * @author yangxinyi
 * 
 */
@Service("orderInterfaceService")
@Transactional(rollbackFor = Exception.class)
public class OrderInterfaceServiceImpl implements OrderInterfaceService {

	private static final Log LOG = LogFactory.getLog(OrderInterfaceServiceImpl.class);

	@Autowired
	public OrderService orderService;

	public ErpService erpService;

	public WmsService wmsService;

	public void setErpService(ErpService erpService) {
		this.erpService = erpService;
	}

	public void setWmsService(WmsService wmsService) {
		this.wmsService = wmsService;
	}

	public String getStatusByErp(String orderId) {
		return erpService.getStatusByOrder(orderId);
	}

	public String getStatusByWms(String orderId) {
		return wmsService.getStatusByOrder(orderId);
	}

	public String getStatusByWmsBj(String orderId) {
		return wmsService.getStatusByOrderBj(orderId);
	}

	public String getStatusByWmsHd(String orderId) {
		return wmsService.getStatusByOrderHd(orderId);
	}

	public String erpInsertIntercept(String orderId) {
		return wmsService.insertIntercept(orderId);
	}

	@Override
	public String getStatusByInterface(Order order) {
		String erpStatus = ErpService.STATUS_NULL_STR;
		if (order.getDistributionCenter().getDcDest().getId().equals(Code.DC_8A17)) {
			 erpStatus = getStatusByWms(order.getId());
		} else if (order.getDistributionCenter().getDcDest().getId().equals(Code.DC_D818)) {
			erpStatus = getStatusByWmsBj(order.getId());
		} else if (order.getDistributionCenter().getDcDest().getId().equals(Code.DC_D819)) {
			// 华东仓
			erpStatus = getStatusByWmsHd(order.getId());
		}
		return erpStatus;
	}

	@Override
	public void saveCodWarehouseExtend(Order order) {
		if (order.isCODOrder()) {
			OrderExtend orderExtend = new OrderExtend();
			orderExtend.setOrder(order);
			orderExtend.setMeta(OrderExtend.getCODWarehouse(order));
			/** 因为收货仓渠道之间定义不同,所以根据渠道判断 */
			if (order.getChannel().equals(Channel.CHANNEL_ID_360BUY)) {
				orderExtend.setValue(OrderExtend.JINGDONG_COD_WAREHOUSE_CHENGDU);
			} else if (order.getChannel().equals(Channel.CHANNEL_ID_DANGDANG) || order.getChannel().equals(Channel.CHANNEL_DANGDANG_JYZ)) {
				orderExtend.setValue(OrderExtend.DANGDANG_COD_WAREHOUSE_BEIJING);
				if (Code.DC_8A17.equals(order.getDistributionCenter().getDcOriginal().getId()) && order.getConsignee().getProvince().isSouthWest()) {
					orderExtend.setValue(OrderExtend.DANGDANG_COD_WAREHOUSE_CHENGDU);
				}
			}
			orderService.saveOrderExtend(orderExtend);
		}
	}

	/**
	 * 解析当当COD订单的电子面单
	 */
	public OrderCourierReceipt parseOrderCodJson(Order order) {
		for (OrderExtend orderExtend : order.getOrderExtends()) {
			if (StringUtils.isNotBlank(OrderExtend.getExtendValueByKey(order))) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				try {
					return mapper.readValue(orderExtend.getValue(), OrderCourierReceipt.class);
				} catch (Exception e) {
					LOG.error("json parsing excepiton");
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

}
