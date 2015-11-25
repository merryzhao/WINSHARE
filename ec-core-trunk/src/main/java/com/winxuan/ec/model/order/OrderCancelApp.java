package com.winxuan.ec.model.order;

import java.io.Serializable;
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
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.Seller;

/**
 * @author yuhu
 * @version 1.0, 2011-10-17下午04:21:20
 */
@Entity
@Table(name = "order_cancelapp")
public class OrderCancelApp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 789558490470684643L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;
	
	@Column
	private String remark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="createuser")
	private Seller createUser;
	
	@Column(name = "createtime")
	private Date createTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="audituser")
	private Employee auditUser;
	
	@Column(name = "audittime")
	private Date auditTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}


	public Seller getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Seller createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Employee getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(Employee auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	
}
