package com.winxuan.ec.front.controller.customer;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.framework.util.AcceptHashMap;

/**
 * 查询订单
 * 
 * @author zhoujun
 * @version 1.0,2011-8-6
 */
public class OrderForm { 
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 订单状态
	 */
	private Long processStatus;
	/**
	 * 支付方式
	 */
	private Long paymentType;
	/**
	 * 支付方式, 见我的订单中的支付方式列, 以及http://code.winxuan.info/issues/764
	 */
	private Long payment;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 下单开始时间
	 */
	private Date startCreateTime;

	/**
	 * 下单结束时间
	 */
	private Date endCreateTime;
	
	/**
	 * 是否已开发票
	 */
	private Long invoiceType;

	public Long getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Long invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}


	public Long getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Long processStatus) {
		this.processStatus = processStatus;
	}

	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Long getPayment() {
		return payment;
	}

	public void setPayment(Long payment) {
		this.payment = payment;
	}

	public Map<String,Object> generateQueryMap()   {	
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()
		 .acceptIf("orderId", orderId, !StringUtils.isEmpty(orderId))
		 .acceptIf("processStatus", processStatus, processStatus!=null && processStatus != -1)
		 .acceptIf("consignee", consignee, !StringUtils.isEmpty(consignee))
		 .acceptIf("startCreateTime", startCreateTime, startCreateTime != null)
		 .acceptIf("endCreateTime", endCreateTime, endCreateTime != null)
		 .acceptIf("paymentType", paymentType, paymentType!=null && paymentType != -1)
		 .acceptIf("payment", payment, payment!=null && payment != -1)
		 .acceptIf("invoiceType", invoiceType, invoiceType!=null && invoiceType != -1);
		return parameters;
   }
}
