/*
 * @(#)RefundCredential.java
 *
 */

package com.winxuan.ec.model.refund;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;

/**
 * description
 * @author  huangyixiang
 * @version 2013-5-13
 */
@Entity
@Table(name = "refund_credential")
public class RefundCredential {
	
	/**
	 * 退款状态 - 等待系统退款
	 */
	public static final Long STATUS_SYS_WAIT = 461013L;
	/**
	 * 退款状态 - 等待第三方退款
	 */
	public static final Long STATUS_OTHER_WAIT = 461014L;
	/**
	 * 退款状态 - 退款成功
	 */
	public static final Long STATUS_SUCCESS = 461015L;
	/**
	 * 退款状态 - 退款失败
	 */
	public static final Long STATUS_FAILED = 461016L;
	/**
	 * 退款状态 - 作废
	 */
	public static final Long STATUS_DISUSE = 461019L;
	/**
	 * 退款状态 
	 */
	public static final Long STATUS = 461017L;
	
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.winxuan.ec.support.util.DatePrefixSequenceIdentifierGenerator", 
		parameters = {
			@Parameter(name = "table", value = "serializable"),
			@Parameter(name = "column", value = "maxid"),
			@Parameter(name = "" +
					"target_name", value = "tablename"),
			@Parameter(name = "target_value", value = "refund_credential"),
			@Parameter(name = "length", value = "8")
		}
	)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment")
	private Payment payment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer")
	private Customer customer;
	
	@Column(name = "outerid")
	private String outerId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;
	
	@Column
	private BigDecimal money;
	
	@Column(name = "refundtime")
	private Date refundTime;
	
	@Column(name = "createtime")
	private Date createTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;
	
	@Column
	private String message;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="operator")
	private User operator;
	
	@Version
	private int version; 
	
	@Column(name="refundfee_money")
	private BigDecimal refundfreemoney;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processstatus")
	private Code processStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private RefundCredential parent;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = RefundCredential.class)
	@OrderBy("createTime")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<RefundCredential> children;
	

	public String getId() {
		return id;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public Code getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Code processStatus) {
		this.processStatus = processStatus;
	}
	

	public BigDecimal getRefundfreemoney() {
		return refundfreemoney;
	}

	public void setRefundfreemoney(BigDecimal refundfreemoney) {
		this.refundfreemoney = refundfreemoney;
	}

	public RefundCredential getParent() {
		return parent;
	}

	public void setParent(RefundCredential parent) {
		this.parent = parent;
	}

	public Set<RefundCredential> getChildren() {
		return children;
	}

	public void setChildren(Set<RefundCredential> children) {
		this.children = children;
	}
	
}
