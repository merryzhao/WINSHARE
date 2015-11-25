package com.winxuan.ec.model.customer;

import java.io.Serializable;
import java.util.Date;


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-4-17 下午3:07:50  --!
 * 
 ********************************
 */
public class CustomerLock implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private Integer errorTimes;
	
	private Date lockTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	
	
	
	

	@Override
	public String toString() {
		return "CustomerLock [userName=" + userName + ", errorTimes="
				+ errorTimes +"]";
	}
	
}
