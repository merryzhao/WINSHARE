package com.winxuan.ec.model.user;

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


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-4-19 下午2:43:10  --!
 * 
 ********************************
 */
@Entity
@Table(name = "user_login_log")
public class UserLoginLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7868421098302369343L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Column(name = "ipaddress")
	private String ipAddress;

	@Column(name = "islogin")
	private boolean isLogin;

	@Column(name = "useragent")
	private String userAgent;
	
	
	@Column(name = "logintime")
	private Date loginTime;
	
	@Column
	private String address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logintype")
	private Code loginType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;
	
	
	
	
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Code getLoginType() {
		return loginType;
	}

	public void setLoginType(Code loginType) {
		this.loginType = loginType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "UserLoginLog [name=" + name + ", isLogin=" + isLogin
				+ ", loginTime=" + loginTime + "]";
	}



}
