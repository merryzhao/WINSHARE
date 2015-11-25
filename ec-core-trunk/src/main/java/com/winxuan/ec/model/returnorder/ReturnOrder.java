/*
 * @(#)ReturnOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.returnorder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 退换货订单
 * 
 * @author liuan
 * @version 1.0,2011-9-13
 */
@Entity
@Table(name = "returnorder")
public class ReturnOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5541926856089856104L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "originalorder")
	private Order originalOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "neworder")
	private Order newOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transfertype")
	private Code transferType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsible")
	private Code responsible;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "holder")
	private Code holder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pickup")
	private Code pickup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason")
	private Code reason;

	@Column(name = "refunddeliveryfee")
	private BigDecimal refundDeliveryFee = BigDecimal.ZERO;

	@Column(name = "refundcompensating")
	private BigDecimal refundCompensating = BigDecimal.ZERO;

	@Column(name = "refundcouponvalue")
	private BigDecimal refundCouponValue = BigDecimal.ZERO;

	@Column(name = "refundgoodsvalue")
	private BigDecimal refundGoodsValue = BigDecimal.ZERO;

	@Column(name = "remark")
	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator")
	private User creator;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor")
	private User auditor;

	@Column(name = "audittime")
	private Date auditTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refunder")
	private User refunder;

	@Column(name = "refundtime")
	private Date refundTime;

	@Version
	private int version;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "returnOrder", targetEntity = ReturnOrderItem.class)
	@OrderBy("id")
	private Set<ReturnOrderItem> itemList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "returnOrder", targetEntity = ReturnOrderRefund.class)
	@OrderBy("id")
	private Set<ReturnOrderRefund> refundList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "returnOrder", targetEntity = ReturnOrderLog.class)
	@OrderBy("id")
	private Set<ReturnOrderLog> logList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "returnOrder", targetEntity = ReturnOrderTrack.class)
	@OrderBy("id")
	private Set<ReturnOrderTrack> trackList;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "returnOrder", targetEntity = ReturnOrderDc.class)
	@OrderBy("id")
	private ReturnOrderDc returnOrderDc;

	@Column(name = "istransferred")
	private boolean transferred;

	@Column
	private boolean needtransfers;

	@Column(name = "cause")
	private String cause;

	/**
	 * 是否应该退款
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "isshouldrefund")
	private Code shouldrefund;
	
	/**
	 * 是否已退款
	 */
	@Column(name = "isrefunded")
	private boolean refunded;
	
	/**
	 * 退货运单号
	 */
	@Column(name = "expressid")
	private String expressid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Order getOriginalOrder() {
		return originalOrder;
	}

	public void setOriginalOrder(Order originalOrder) {
		this.originalOrder = originalOrder;
	}

	public Order getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(Order newOrder) {
		this.newOrder = newOrder;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Code getTransferType() {
		return transferType;
	}

	public void setTransferType(Code transferType) {
		this.transferType = transferType;
	}

	public Code getResponsible() {
		return responsible;
	}

	public void setResponsible(Code responsible) {
		this.responsible = responsible;
	}

	public Code getHolder() {
		return holder;
	}

	public void setHolder(Code holder) {
		this.holder = holder;
	}

	public Code getPickup() {
		return pickup;
	}

	public void setPickup(Code pickup) {
		this.pickup = pickup;
	}

	public BigDecimal getRefundDeliveryFee() {
		return refundDeliveryFee;
	}

	public void setRefundDeliveryFee(BigDecimal refundDeliveryFee) {
		this.refundDeliveryFee = refundDeliveryFee;
	}

	public BigDecimal getRefundCompensating() {
		return refundCompensating;
	}

	public void setRefundCompensating(BigDecimal refundCompensating) {
		this.refundCompensating = refundCompensating;
	}

	public BigDecimal getRefundCouponValue() {
		return refundCouponValue;
	}

	public void setRefundCouponValue(BigDecimal refundCouponValue) {
		this.refundCouponValue = refundCouponValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Set<ReturnOrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(Set<ReturnOrderItem> itemList) {
		this.itemList = itemList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	public User getRefunder() {
		return refunder;
	}

	public void setRefunder(User refunder) {
		this.refunder = refunder;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Set<ReturnOrderRefund> getRefundList() {
		return refundList;
	}

	public void setRefundList(Set<ReturnOrderRefund> refundList) {
		this.refundList = refundList;
	}

	public BigDecimal getRefundGoodsValue() {
		return refundGoodsValue;
	}

	public void setRefundGoodsValue(BigDecimal refundGoodsValue) {
		this.refundGoodsValue = refundGoodsValue;
	}

	public Set<ReturnOrderLog> getLogList() {
		return logList;
	}

	public void setLogList(Set<ReturnOrderLog> logList) {
		this.logList = logList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Code getReason() {
		return reason;
	}

	public void setReason(Code reason) {
		this.reason = reason;
	}

	public Set<ReturnOrderTrack> getTrackList() {
		return trackList;
	}

	public void setTrackList(Set<ReturnOrderTrack> trackList) {
		this.trackList = trackList;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

	public boolean isNeedtransfers() {
		return needtransfers;
	}

	public void setNeedtransfers(boolean needtransfers) {
		this.needtransfers = needtransfers;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public ReturnOrderDc getReturnOrderDc() {
		return returnOrderDc;
	}

	public void setReturnOrderDc(ReturnOrderDc returnOrderDc) {
		this.returnOrderDc = returnOrderDc;
	}
	
	
	public int getDesireKindQuantity() {
		if (itemList == null || itemList.isEmpty()) {
			return MagicNumber.ZERO;
		}
		return itemList.size();
	}
	
	public Code getShouldrefund() {
		return shouldrefund;
	}

	public void setShouldrefund(Code shouldrefund) {
		this.shouldrefund = shouldrefund;
	}

	public boolean isRefunded() {
		return refunded;
	}

	public void setRefunded(boolean refunded) {
		this.refunded = refunded;
	}
	
	public String getExpressid() {
		return expressid;
	}

	public void setExpressid(String expressid) {
		this.expressid = expressid;
	}

	/**
	 * 申请退换货的商品总数量
	 * 
	 * @return
	 */
	public int getDesireTotalQuantity() {
		if (itemList == null || itemList.isEmpty()) {
			return MagicNumber.ZERO;
		}
		int quantity = MagicNumber.ZERO;
		for (ReturnOrderItem returnOrderItem : itemList) {
			quantity += returnOrderItem.getAppQuantity();
		}
		return quantity;
	}

	/**
	 * 申请的退换货总码洋
	 * 
	 * @return
	 */
	public BigDecimal getDesireTotalListPrice() {
		if (itemList == null || itemList.isEmpty()) {
			return BigDecimal.ZERO.setScale(2);
		}
		BigDecimal totalListPrice = BigDecimal.ZERO;
		for (ReturnOrderItem returnOrderItem : itemList) {
			totalListPrice = totalListPrice.add(returnOrderItem
					.getDesireTotalListPrice());
		}
		return totalListPrice;
	}

	/**
	 * 申请的退换货总实洋
	 * 
	 * @return
	 */
	public BigDecimal getDesireTotalSalePrice() {
		if (itemList == null || itemList.isEmpty()) {
			return BigDecimal.ZERO.setScale(2);
		}
		BigDecimal totalSalePrice = BigDecimal.ZERO;
		for (ReturnOrderItem returnOrderItem : itemList) {
			totalSalePrice = totalSalePrice.add(returnOrderItem
					.getDesireTotalSalePrice());
		}
		return totalSalePrice;
	}

	/**
	 * 实际退换货的商品总数量
	 * 
	 * @return
	 */
	public int getTotalQuantity() {
		if (itemList == null || itemList.isEmpty()) {
			return MagicNumber.ZERO;
		}
		int quantity = MagicNumber.ZERO;
		for (ReturnOrderItem returnOrderItem : itemList) {
			quantity += returnOrderItem.getRealQuantity();
		}
		return quantity;
	}

	public void addItem(OrderItem orderItem, int appQuantity) {
		if (appQuantity <= 0 || appQuantity > orderItem.getDeliveryQuantity()) {
			throw new RuntimeException(
					"appQuantity must be greater than 0 and less than orderItem.deliveryQuantity");
		}
		if (itemList == null) {
			itemList = new HashSet<ReturnOrderItem>();
		}
		ReturnOrderItem returnOrderItem = new ReturnOrderItem();
		returnOrderItem.setOrderItem(orderItem);
		returnOrderItem.setAppQuantity(appQuantity);
		returnOrderItem.setReturnOrder(this);
		itemList.add(returnOrderItem);
	}

	public void addLog(Code status, User operator) {
		if (logList == null) {
			logList = new HashSet<ReturnOrderLog>();
		}
		ReturnOrderLog log = new ReturnOrderLog();
		log.setLogTime(new Date());
		log.setOperator(operator);
		log.setReturnOrder(this);
		log.setStatus(status);
		logList.add(log);
		this.setStatus(status);
	}

	public void addRefund(Payment payment, BigDecimal refundValue,
			String refundObjectId) {
		if (refundList == null) {
			refundList = new HashSet<ReturnOrderRefund>();
		}
		ReturnOrderRefund refund = new ReturnOrderRefund();
		refund.setReturnOrder(this);
		refund.setPayment(payment);
		refund.setRefundObjectId(refundObjectId);
		refund.setRefundValue(refundValue);
		refundList.add(refund);
	}

	/**
	 * 得到退货单实际退的礼品卡金额
	 * 
	 * @return
	 */
	public BigDecimal getGiftValue() {
		if (refundList != null) {
			for (ReturnOrderRefund refund : refundList) {
				if (refund.getPayment().getId().equals(Payment.GIFT_CARD)) {
					return refund.getRefundValue();
				}
			}
		}
		return BigDecimal.ZERO;
	}

	public void addTrack(ReturnOrderTrack track) {
		if (trackList == null) {
			trackList = new HashSet<ReturnOrderTrack>();
		}
		trackList.add(track);
		track.setReturnOrder(this);
		track.setCreateTime(new Date());
	}

	@Override
	public String toString() {
		return "ReturnOrder [id=" + id +"]";
	}
}
