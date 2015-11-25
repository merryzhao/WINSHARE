package com.winxuan.ec.model.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单退货财务记录
 * @author HideHai
 *
 */
public class OrderReturnFinance {

	private String order;
	
	private String bussiness;
	
	private Long status;
	
	private Integer returnQuantity;
	
	private BigDecimal returnListPrice;
	
	private BigDecimal returnSalePrice;
	
	private BigDecimal deliveryFee;
	
	private BigDecimal saveMoney;
	
	private BigDecimal couponMoney;
	
	private BigDecimal presentCardMoney;
	
	private Date returntime;
	
	private Date createTime;
	
	

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public BigDecimal getReturnListPrice() {
		return returnListPrice;
	}

	public void setReturnListPrice(BigDecimal returnListPrice) {
		this.returnListPrice = returnListPrice;
	}

	public BigDecimal getReturnSalePrice() {
		return returnSalePrice;
	}

	public void setReturnSalePrice(BigDecimal returnSalePrice) {
		this.returnSalePrice = returnSalePrice;
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
	public Date getReturntime() {
		return returntime;
	}

	public void setReturntime(Date returntime) {
		this.returntime = returntime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	
}
