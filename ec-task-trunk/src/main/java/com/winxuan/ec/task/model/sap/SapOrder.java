package com.winxuan.ec.task.model.sap;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author yangxinyi
 *
 */
public class SapOrder implements Serializable {

	private static final long serialVersionUID = -4650771080660299898L;

	private Long interfaceId;
	private String orderId;
	private String orderItemId;
	private int deliveryQuantity;
	private Date deliveryTime;
	private String deliveryCode;
	private String iflag;
	
	public Long getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDeliveryCode() {
		return deliveryCode;
	}
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	public String getIflag() {
		return iflag;
	}
	public void setIflag(String iflag) {
		this.iflag = iflag;
	}
}