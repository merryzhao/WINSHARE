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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * ******************************
 * 用户锁定状态,
 * @author:cast911
 * @lastupdateTime:2013-4-19 下午2:43:17 --!
 * 
 ******************************** 
 */
@Entity
@Table(name = "user_lock_state")
public class UserLockState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7868421098302369343L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "islock")
	private boolean isLock;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;

	@Column(name = "lastupdatetime")
	private Date lastUpdateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;

	@Column(name = "errortimes")
	private int errorTimes;

	public Long getId() {
		return id;
	}

	
	public int getErrorTimes() {
		return errorTimes;
	}


	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "UserLockLog [id=" + id + ", isLock=" + isLock + ", user="
				+ user + ", lastUpdateTime=" + lastUpdateTime + ", operator="
				+ operator + "]";
	}

}
