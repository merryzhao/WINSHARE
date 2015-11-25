package com.winxuan.ec.model.union;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 联盟
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
@Table(name="_union")
@Entity
public class Union implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2976677183344154261L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	@Column
	private String name;
	
	@Column
	private String url;
	
	@Column
	private  BigDecimal rate;
	
	@Column(name="createtime")
	private Date createTime;
	
	@Column(name="username")
	private String userName;
	
	@Column
	private String phone;
	
	@Column
	private String email;
	
	@Column
	private boolean available;
	
	@Column(name="isshow")
	private boolean show;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
	
}
