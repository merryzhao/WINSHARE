package com.winxuan.ec.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author  周斯礼
 * @version 2013-7-11
 */

@Entity
@Table(name="order_batchpay")
public class OrderBatchPay {
	@Id
	@Column(name="batchpay")
	private String batchPayId;
	
	@Column(name="_order")
	private String orderId;

	public String getBatchPayId() {
		return batchPayId;
	}

	public void setBatchPayId(String batchPayId) {
		this.batchPayId = batchPayId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}


