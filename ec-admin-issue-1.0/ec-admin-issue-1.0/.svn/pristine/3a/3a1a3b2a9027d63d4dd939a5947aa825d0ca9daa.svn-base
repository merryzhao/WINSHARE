package com.winxuan.ec.admin.controller.order;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 暂存款支付订单验证表单
 *
 * @author YangJun
 * @version 1.0,2011-12-7
 */
public class AdvanceaccountPaymentFrom {
	@NotBlank(message="请输入订单号")
	private String orderId;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		if ("".equals(orderId)) {
			this.orderId = null;
		} else {
			this.orderId = orderId;
		}
	}

}
