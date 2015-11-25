package com.winxuan.ec.service.delivery;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.model.order.OrderLogistics;

/**
 * 物流信息接口
 * @author Heyadong
 * @version 1.0 , 2012-1-12
 */
public interface DeliveryAPI extends Serializable {
	/**
	 * 获取订单物流信息
	 * @param deliveryCompany 物流公司
	 * @param deliveryCode 对应的运单号
	 * @return NULL 没有找到该运单号物流信息 或. 运单号过期(一般为3个月)
	 */
	List<OrderLogistics> getOrderLogistics(String deliveryCode) throws Exception;
	/**
	 * 物流公司号  @see{com.winxuan.ec.model.delivery.DeliveryCompany.Code}
	 * @return
	 */
	String getDeliverCompanyCode();
}
