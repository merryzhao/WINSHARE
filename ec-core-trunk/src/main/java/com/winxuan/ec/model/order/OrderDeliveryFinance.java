package com.winxuan.ec.model.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单发货财务记录
 * @author HideHai
 */
public class OrderDeliveryFinance {
	
	private String order;
	
	private Integer deliveryQuantity;
	
	private BigDecimal deliveryListPrice;
	
	private BigDecimal deliverySalePrice;
	
	private BigDecimal deliveryFee;
	
	private BigDecimal saveMoney;
	
	private BigDecimal couponMoney;
	
	private BigDecimal presentCardMoney;
	
	private Date deliveryTime;
	
	private Date createTime;
	
	

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public BigDecimal getDeliveryListPrice() {
		return deliveryListPrice;
	}

	public void setDeliveryListPrice(BigDecimal deliveryListPrice) {
		this.deliveryListPrice = deliveryListPrice;
	}

	public BigDecimal getDeliverySalePrice() {
		return deliverySalePrice;
	}

	public void setDeliverySalePrice(BigDecimal deliverySalePrice) {
		this.deliverySalePrice = deliverySalePrice;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(BigDecimal saveMoney) {
		this.saveMoney = saveMoney;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public BigDecimal getPresentCardMoney() {
		return presentCardMoney;
	}

	public void setPresentCardMoney(BigDecimal presentCardMoney) {
		this.presentCardMoney = presentCardMoney;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
