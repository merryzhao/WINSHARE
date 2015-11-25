package com.winxuan.ec.model.version;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 客户端信息实体
 * 
 * @author zhousili
 * 
 */
@Entity
@Table(name="mobile_request_log")
public class ClientInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String sign;

	@Column
	private String name;

	@Column
	private String size;
	
	@Column(name="appversion")
	private String appVersion;

	@Column
	private int system;
	
	@Column(name="systemversion")
	private String systemVersion;

	@Column
	private String network;

	@Column
	private String operators;
	
	@Column(name="createtime")
	private Date createTime;

	@Column(name="needupdate")
	private boolean needUpdate = false;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public int getSystem() {
		return system;
	}
	public void setSystem(int system) {
		this.system = system;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	
	
}
