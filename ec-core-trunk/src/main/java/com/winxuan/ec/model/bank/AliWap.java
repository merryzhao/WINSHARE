package com.winxuan.ec.model.bank;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 支付宝平台支付
 * @author youwen
 *
 */
public class AliWap extends Bank {

	/**
	 * 签名方式
	 */
	public static final String SIGN_TYPE = "MD5";
	/**
	 * 字符编码格式 utf-8
	 */
	public static final String INPUT_CHARSET = "utf-8";
	/**
	 * 商户账户
	 */
	private String sellerEmail;
	/**
	 * 商户账户
	 */
	private String sellerUserId;
	/**
	 * 密钥
	 */
	private String key;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 接口名称
	 */
	private String service;
	/**
	 * 合作者身份ID (签约的支付宝账号对应的支付宝唯一用户号)
	 * 
	 */
	private String partner;
	/**
	 * 通知地址
	 */
	private String notifyUrl;
	/**
	 * 退款请求的当前时
	 */
	private Date refundDate;
	/**
	 * 退款批次号
	 */
	private String batchNo;
	/**
	 * 单笔数据集
	 */
	private String detailData;
	
	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getDetailData() {
		return detailData;
	}

	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}

	public String getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		refundDate = new Date();
		batchNo = tradeId;
	}

}
