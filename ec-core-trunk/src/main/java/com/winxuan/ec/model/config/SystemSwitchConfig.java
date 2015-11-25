package com.winxuan.ec.model.config;

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

import com.winxuan.ec.model.user.Employee;

/**
 * 锁开关
 * 
 * @author liou 2014-10-14上午10:23:57
 */
@Entity
@Table(name = "system_switch_config")
public class SystemSwitchConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5912866843090460284L;

	// 批量开关类型-库存锁定开关
	public static final short TYPE_STOCK_TYPE = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 描述
	 */
	@Column
	private String description;

	@Column
	private short type;

	/**
	 * 是否有效
	 */
	@Column(name = "isopen")
	private boolean isOpen;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "updatetime")
	private Date updateTime;

	/**
	 * 修改人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updateoperator")
	private Employee updateOperator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Employee getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(Employee updateOperator) {
		this.updateOperator = updateOperator;
	}

}