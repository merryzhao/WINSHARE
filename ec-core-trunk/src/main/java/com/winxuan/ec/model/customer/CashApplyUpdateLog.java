package com.winxuan.ec.model.customer;

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

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.User;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-31
 */
@Entity
@Table(name="customer_cashapply_updatelog")
public class CashApplyUpdateLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cashapply")
	private CustomerCashApply customerCashApply;
	
	@Column(name="tradeno")
	private String tradeNo;
	
	@Column
	private String remark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status")
	private Code status;
	
    @Column (name="updatetime")  
	private Date operateTime;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerCashApply getCustomerCashApply() {
		return customerCashApply;
	}

	public void setCustomerCashApply(CustomerCashApply customerCashApply) {
		this.customerCashApply = customerCashApply;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}	
	
	
}
