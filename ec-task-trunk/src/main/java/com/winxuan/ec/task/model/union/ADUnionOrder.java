package com.winxuan.ec.task.model.union;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-29
 */
public class ADUnionOrder {
	/**
	 * 订单编号
	 */
	
	private long orderid;

	/**
	 * 联盟id
	 */
	private long merchantId;

	/**
	 * 下单时间
	 */
	private Date createtime;

	/**
	 * 修改时间
	 */
	private Date updatetime;

	/**
	 * 创建者
	 */
	private String creator;
	/**
	 * 更新者
	 */
	private String updator;

	/**
	 * 订单状态
	 */
	private short status;

	/**
	 * 订单号
	 */
	private String number;
	/**
	 * 订单金额
	 */
	private BigDecimal amount =BigDecimal.ZERO;
	/**
	 * 订单码洋
	 */
	private BigDecimal bookPrice=BigDecimal.ZERO;
	/**
	 * 订单实洋
	 */
	private BigDecimal realPrice=BigDecimal.ZERO;
	/**
	 * 发货码洋
	 */
	private BigDecimal saleBookPrice=BigDecimal.ZERO;
	/**
	 * 发货实洋
	 */
	private BigDecimal saleRealPrice=BigDecimal.ZERO;
	/**
	 * 退货码洋
	 */
	private BigDecimal refundBookPrice=BigDecimal.ZERO;
	/**
	 * 退货实洋
	 */
	private BigDecimal refundRealPrice=BigDecimal.ZERO;
	/**
	 * 访问唯一标识码
	 */
	private String stan;
	/**
	 * 订单结算状态
	 */
	private short ispay;
	/**
	 * 所对应的佣金外键
	 */
	private Long commissionId;

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	
	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(BigDecimal bookPrice) {
		this.bookPrice = bookPrice;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public BigDecimal getSaleBookPrice() {
		return saleBookPrice;
	}

	public void setSaleBookPrice(BigDecimal saleBookPrice) {
		this.saleBookPrice = saleBookPrice;
	}

	public BigDecimal getSaleRealPrice() {
		return saleRealPrice;
	}

	public void setSaleRealPrice(BigDecimal saleRealPrice) {
		this.saleRealPrice = saleRealPrice;
	}

	public BigDecimal getRefundBookPrice() {
		return refundBookPrice;
	}

	public void setRefundBookPrice(BigDecimal refundBookPrice) {
		this.refundBookPrice = refundBookPrice;
	}

	public BigDecimal getRefundRealPrice() {
		return refundRealPrice;
	}

	public void setRefundRealPrice(BigDecimal refundRealPrice) {
		this.refundRealPrice = refundRealPrice;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public short getIspay() {
		return ispay;
	}

	public void setIspay(short ispay) {
		this.ispay = ispay;
	}

	

	public Long getCommissionId() {
		return commissionId;
	}

	public void setCommissionId(Long commissionId) {
		this.commissionId = commissionId;
	}

	public String toString() {
		return "orderid" + orderid + "merchantId" + merchantId + "createtime"
				+ createtime + "updatetime" + createtime + "number" + number
				+ "amount" + amount;
	}
}
