/*
 * @(#)User.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.user;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.validator.Principal;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable, Principal {

	public static final boolean ENABLE = true;
	public static final boolean DISABLE = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3601229674108492925L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String password;

	@Column(name = "realname")
	private String realName;

	@Column
	private String email;

	@Column
	private String phone;

	@Column
	private String mobile;

	@Column
	private Date birthday;

	@Column
	private Short gender;

	@Column(name = "registertime")
	private Date registerTime;

	@Column(name = "lastlogintime")
	private Date lastLoginTime;

	@Column
	private boolean available;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source")
	private Code source;

	@Column(name = "emailactive")
	private short emailActive;

	@OneToOne(mappedBy = "user")
	private UserLockState userLockState;

	public User() {

	}

	public User(Long id) {
		this.id = id;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			return "";
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Serializable getIdentity() {
		return getId();
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Code getSource() {
		return source;
	}

	public void setSource(Code source) {
		this.source = source;
	}

	public Long getLastLoginSeconds() {
		try {
			Date date = getLastLoginTime();
			String now = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return format.parse(now).getTime();
		} catch (ParseException e) {
			return getLastLoginTime().getTime();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	public short getEmailActive() {
		return emailActive;
	}

	public void setEmailActive(short emailActive) {
		this.emailActive = emailActive;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [getId()=" + getId() + ", getName()=" + getName() + "]";
	}

	@Override
	public String getDisplayName() {
		return name;
	}

	/**
	 * 验证邮箱格式真否正确
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email) {
		if (!email.matches(ControllerConstant.EMAIL_EXP)) {
			return false;
		}
		return true;
	}

	public UserLockState getUserLockState() {
		return userLockState;
	}

	public void setUserLockState(UserLockState userLockState) {
		this.userLockState = userLockState;
	}

	/**
	 * 获取有效邮箱
	 * 
	 * @return
	 */
	public String getValidEmail() {
		if (this.checkEmail(this.email)) {
			return this.email;
		}
		return null;
	}

	public String getProtectionName() {
		return null;
	}

	/**
	 * 是否长时间未登陆(180天)
	 * 
	 * @return
	 */
	public Boolean getLongTimeNotLogin() {
		if(this.getUserLockState() == null){
			return null;
		}
		DateTime afterTime = new DateTime(this.getUserLockState()
				.getLastUpdateTime());
		afterTime =afterTime.plusDays(180);
		DateTime nowTime = new DateTime(this.lastLoginTime);
		return afterTime.isBefore(nowTime.getMillis());
	}
}
