/*
 * @(#)Bank.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.winxuan.ec.support.component.refund.Refunder;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-7-26
 */
public abstract class Bank {

	private Long id;
	/**
	 * 支付成功后，后台接受通知URL
	 */
	private String notifyUrl;
	/**
	 * 支付成功后，前台显示调用URL
	 */
	private String returnUrl;
	/**
	 * 接口网址
	 */
	private String action;
	/**
	 * 商户标识
	 */
	private String partner;

	private String returnServerInfo;
	
	private String notifyServerInfo;
	
	private String refundNotifyServerInfo;
	
	private String viewName;
	
	/**
	 * 退款网关
	 */
	private String refundAction;
	/**
	 * 退款成功异步调用url
	 */
	private String refundNotifyUrl;
	
	private Refunder refunder;

	
	public Refunder getRefunder() {
		return refunder;
	}
	public void setRefunder(Refunder refunder) {
		this.refunder = refunder;
	}
	public boolean canRefund(){
		return !StringUtils.isBlank(refundAction) 
				&& refunder != null; 
	}
	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}
	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}
	public String getRefundAction() {
		return refundAction;
	}
	public void setRefundAction(String refundAction) {
		this.refundAction = refundAction;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReturnServerInfo() {
		return returnServerInfo;
	}

	public void setReturnServerInfo(String returnServerInfo) {
		this.returnServerInfo = returnServerInfo;
	}

	
	public String getNotifyServerInfo() {
		return notifyServerInfo;
	}
	public void setNotifyServerInfo(String notifyServerInfo) {
		this.notifyServerInfo = notifyServerInfo;
	}



	protected abstract void initTrade(String tradeId, BigDecimal amount);
	
	public  void init(String tradeId, BigDecimal amount, String code){
		if (this instanceof Tenpay){
			Tenpay tenpay = (Tenpay) this;
			HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			if(request != null){
				tenpay.setSpbillCreateIp(request.getRemoteAddr());
			}
		}
		else if(this instanceof AlipayNetBank){
			AlipayNetBank alipayNetBank = (AlipayNetBank) this;
			alipayNetBank.setDefaultBank(code);
		}
		else if(this instanceof AlipayWildCard){
			AlipayWildCard alipayWildCard = (AlipayWildCard) this;
			alipayWildCard.setDefaultBank(code);
		}
		initTrade(tradeId, amount);
	}
	
	protected void initCallBack(){
		if(StringUtils.isNotBlank(returnServerInfo)  ){
			returnUrl = returnServerInfo + returnUrl;
		}else{
			returnUrl = getUrlPrefix() + returnUrl;
		}
		if(StringUtils.isNotBlank(notifyServerInfo) ){
			notifyUrl = notifyServerInfo + notifyUrl;
		}else{
			notifyUrl = getUrlPrefix() + notifyUrl;
		}
		
		if(StringUtils.isNotBlank(refundNotifyServerInfo) ){
			refundNotifyUrl = refundNotifyServerInfo + refundNotifyUrl;
		}
		else{
			refundNotifyUrl = getUrlPrefix() + refundNotifyUrl;
		}
		if(!(returnServerInfo.startsWith("http://") && notifyServerInfo.startsWith("http://"))){
			throw new RuntimeException("error callback ");
		}
	}
	
	private String getUrlPrefix(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		}
		return "";
	}
	public String getRefundNotifyServerInfo() {
		return refundNotifyServerInfo;
	}
	public void setRefundNotifyServerInfo(String refundNotifyServerInfo) {
		this.refundNotifyServerInfo = refundNotifyServerInfo;
	}
	

}
