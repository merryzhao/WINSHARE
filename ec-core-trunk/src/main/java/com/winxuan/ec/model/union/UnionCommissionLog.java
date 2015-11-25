package com.winxuan.ec.model.union;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.User;

/**
 * 联盟佣金日志
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
@Entity
@Table(name="union_commission_log")
public class UnionCommissionLog implements Serializable{
	

	/**
	 * 修改佣金
	 */
	public static final short LOG_COMMISSION_TYPE = 1;
	
	/**
	 *修改支付 
	 */
	public static final short LOG_PAY_TYPE = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3428349940083276281L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "createdate")
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="unioncommission")
	private UnionCommission unionCommission;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="operator")
	private User operator;
	
	@Column
	private short type;
	
	@Column(name="oldcommission")
	private BigDecimal oldCommission =BigDecimal.ZERO;
	
	@Column(name="newcommission")
	private BigDecimal newCommission =BigDecimal.ZERO;
	
	@Column(name="oldpay")
	private boolean oldPay;
	
	@Column(name="newpay")
	private boolean newPay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UnionCommission getUnionCommission() {
		return unionCommission;
	}

	public void setUnionCommission(UnionCommission unionCommission) {
		this.unionCommission = unionCommission;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public BigDecimal getOldCommission() {
		return oldCommission;
	}

	public void setOldCommission(BigDecimal oldCommission) {
		this.oldCommission = oldCommission;
	}

	public BigDecimal getNewCommission() {
		return newCommission;
	}

	public void setNewCommission(BigDecimal newCommission) {
		this.newCommission = newCommission;
	}

	public boolean isOldPay() {
		return oldPay;
	}

	public void setOldPay(boolean oldPay) {
		this.oldPay = oldPay;
	}

	public boolean isNewPay() {
		return newPay;
	}

	public void setNewPay(boolean newPay) {
		this.newPay = newPay;
	}
	
	
	
}
