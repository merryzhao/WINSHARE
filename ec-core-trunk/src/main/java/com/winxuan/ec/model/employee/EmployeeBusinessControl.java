package com.winxuan.ec.model.employee;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

/**
 * 
 * @author  bianlin
 */

@Entity
@Table(name = "employee_business_control")
public class EmployeeBusinessControl implements Serializable{
	private static final long serialVersionUID = 3578144476907596249L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user")
	private Employee employee;
	
	@Column(name = "createtime")
	private Date createTime;
	
	@Column
	private int status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business")
	private Code business;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Code getBusiness() {
		return business;
	}

	public void setBusiness(Code business) {
		business = business;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
