/*
 * @(#)SubmitOrderForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import com.winxuan.ec.model.shoppingcart.ShoppingcartProm;


/**
 * 订单提交表单
 * @author  HideHai
 * @version 1.0,2011-8-9
 */
public class CheckoutForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4767586837035552330L;
	
	/**
	 * 卖家编号
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class,CheckoutShoppingcart.class})
	@Min(value=0,groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class,CheckoutShoppingcart.class})
	private Long shopId;
	
	
	/**
	 * 供应类型id
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class,CheckoutShoppingcart.class})
	@Min(value=0,groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class,CheckoutShoppingcart.class})
	private Long supplyTypeId;
	
	/**
	 * 订单地址编号
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class})
	@Min(value=0,groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class})
	private Long addressId;
	/**
	 * 订单地址最后一级的区域编号
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class})
	@Min(value=0,groups={Default.class,CheckoutInfo.class})
	private Long townId;
	/**
	 * 配送方式ID
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class})
	private Long deliveryTypeId;
	/**
	 * 配送时间
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class})
	private Long deliveryOption;
	/**
	 * 支付方式ID
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class})
	private Long paymentId;
	/**
	 * 是否使用暂存款
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class})
	private Boolean useAccount;
	
	/**
	 * 暂存款支付密码
	 */
	private String payPassword;
	
	/**
	 * 是否使用了礼券
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class})
	private Boolean usePresent;
	
	/**
	 * 礼券ID
	 */
	private Long presentId;
	
	/**
	 * 是否使用了礼品卡
	 */
	@NotNull(groups={Default.class,CheckoutInfo.class,CheckoutSubmit.class})
	private Boolean usePresentCard;
	
	private String[] presentCardId;
	
	private String[] presentCardPass;
	/**
	 * 是否需要发票
	 */
	@NotNull(groups={Default.class,CheckoutSubmit.class})
	private Boolean needInvoice;

	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	/**
	 * 发票类型
	 */
	private Long invoiceType;

	/**
	 * 代理商发票金额
	 */
	private BigDecimal invoiceValue;
	/**
	 * 返回展示-运费
	 */
	private BigDecimal deliveryFee;
	/**
	 * 返回展示-订单金额
	 */
	private BigDecimal salePrice;
	/**
	 * 返回展示-暂存款金额
	 */
	private BigDecimal accountMoney = BigDecimal.ZERO;
	/**
	 * 返回展示-礼券金额
	 */
	private BigDecimal presentMoney = BigDecimal.ZERO;
	/**
	 * 返回展示-礼品卡金额
	 */
	private BigDecimal presentCardMoney = BigDecimal.ZERO;
	/**
	 * 返回展示-优惠金额
	 */
	private BigDecimal saveMoney = BigDecimal.ZERO;

	/**
	 * 返回展示-订单还需支付
	 */
	private BigDecimal needPay;
	/**
	 * 返回展示-促销信息
	 */
	private List<ShoppingcartProm> proms;

	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getSupplyTypeId() {
		return supplyTypeId;
	}
	public void setSupplyTypeId(Long supplyTypeId) {
		this.supplyTypeId = supplyTypeId;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public Long getDeliveryTypeId() {
		return deliveryTypeId;
	}
	public void setDeliveryTypeId(Long deliveryTypeId) {
		this.deliveryTypeId = deliveryTypeId;
	}
	public Long getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(Long deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Boolean getUseAccount() {
		return useAccount;
	}
	public void setUseAccount(Boolean useAccount) {
		this.useAccount = useAccount;
	}
	public Boolean getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public Long getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Long invoiceType) {
		this.invoiceType = invoiceType;
	}
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getAccountMoney() {
		return accountMoney;
	}
	public void setAccountMoney(BigDecimal accountMoney) {
		this.accountMoney = accountMoney;
	}
	public BigDecimal getSaveMoney() {
		return saveMoney;
	}
	public void setSaveMoney(BigDecimal saveMoney) {
		this.saveMoney = saveMoney;
	}
	public BigDecimal getNeedPay() {
		return needPay;
	}
	public void setNeedPay(BigDecimal needPay) {
		this.needPay = needPay;
	}
	public List<ShoppingcartProm> getProms() {
		return proms;
	}
	public void setProms(List<ShoppingcartProm> proms) {
		this.proms = proms;
	}
	public Long getTownId() {
		return townId;
	}
	public void setTownId(Long townId) {
		this.townId = townId;
	}
	public Boolean getUsePresent() {
		return usePresent;
	}
	public void setUsePresent(Boolean usePresent) {
		this.usePresent = usePresent;
	}
	public Boolean getUsePresentCard() {
		return usePresentCard;
	}
	public void setUsePresentCard(Boolean usePresentCard) {
		this.usePresentCard = usePresentCard;
	}
	public Long getPresentId() {
		return presentId;
	}
	public void setPresentId(Long presentId) {
		this.presentId = presentId;
	}
	public String[] getPresentCardId() {
		return presentCardId;
	}
	public void setPresentCardId(String[] presentCardId) {
		this.presentCardId = presentCardId;
	}
	public BigDecimal getPresentMoney() {
		return presentMoney;
	}
	public void setPresentMoney(BigDecimal presentMoney) {
		this.presentMoney = presentMoney;
	}
	public BigDecimal getPresentCardMoney() {
		return presentCardMoney;
	}
	public void setPresentCardMoney(BigDecimal presentCardMoney) {
		this.presentCardMoney = presentCardMoney;
	}
	public String[] getPresentCardPass() {
		return presentCardPass;
	}
	public void setPresentCardPass(String[] presentCardPass) {
		this.presentCardPass = presentCardPass;
	}
	public BigDecimal getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(BigDecimal invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	/**
	 * 实际支付金额 =实洋-礼券-优惠金额
	 */
	public BigDecimal getActualMoney() {
		return salePrice.subtract(saveMoney).subtract(presentMoney);
	}
	
	/**
	 * 添加优惠金额
	 * @param saveMoney 为正数表示添加优惠金额，如果为负数表示从优惠金额中减去一定金额
	 */
	public void addSaveMoney(BigDecimal saveMoney){
		this.saveMoney = this.saveMoney.add(saveMoney);
	}
	
	/**
	 * 添加一个优惠活动信息
	 * @param prom
	 */
	public void add(ShoppingcartProm prom) {
		if (proms == null) {
			proms = new ArrayList<ShoppingcartProm>();
		}
		proms.add(prom);
	}

	/**
	 * 分组验证-订单结算信息
	 * @author hidehai
	 *
	 */
	public interface CheckoutInfo{};
	
	/**
	 * 分组验证-提交订单信息
	 * @author hidehai
	 *
	 */
	public interface CheckoutSubmit{};
	
	/**
	 * 购物车关键属性验证
	 * @author hidehai
	 *
	 */
	public interface CheckoutShoppingcart{};

}

