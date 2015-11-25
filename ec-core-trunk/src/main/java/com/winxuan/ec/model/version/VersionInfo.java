package com.winxuan.ec.model.version;

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
 * 版本信息实体
 * 
 * @author zhousili
 * 
 */
@Entity
@Table(name="mobile_version")
public class VersionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String version;

	@Column
	private int system;

	@Column(name="updateaddress")
	private String updateAddress;
	
	@Column(name="updateinfo")
	private String updateInfo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator")
	private Employee creator;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="updator")
	private Employee updator;

	@Column(name="createtime")
	private Date createTime;

	@Column(name="updatetime")
	private Date updateTime;

	@Column
	private boolean latest;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getSystem() {
		return system;
	}
	public void setSystem(int system) {
		this.system = system;
	}
	public String getUpdateAddress() {
		return updateAddress;
	}
	public void setUpdateAddress(String updateAddress) {
		this.updateAddress = updateAddress;
	}
	public String getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}
	public Employee getCreator() {
		return creator;
	}
	public void setCreator(Employee creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isLatest() {
		return latest;
	}
	public void setLatest(boolean latest) {
		this.latest = latest;
	}
	public Employee getUpdator() {
		return updator;
	}
	public void setUpdator(Employee updator) {
		this.updator = updator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
