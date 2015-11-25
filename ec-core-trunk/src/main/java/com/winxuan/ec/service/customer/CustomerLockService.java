package com.winxuan.ec.service.customer;

import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.model.user.UserLockState;


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-4-17 下午3:09:09  --!
 * 
 ********************************
 */
public interface CustomerLockService {

	
	void update(UserLockState userLockState);
	
	
	/**
	 * 获取账号当天的错误次数,当天的凌晨至23:59:59
	 * @param userName
	 * @return
	 */
	int getAccountErrorTimes(String userName);

	
	/**
	 * 添加用户锁定次数
	 * @param userName
	 * @return
	 */
	void addAccountErrorTimes(String userName);
	
	/**
	 * 账户是否被锁定,默认错误次数为5次
	 * @param userName
	 * @return true:锁定
	 */
	boolean accountIsLock(String userName);
	
	
	/**
	 * 账户是否被锁定,指定错误次数内是否被锁定,
	 * @param userName
	 * @return true:锁定
	 */
	boolean accountIsLock(String userName,Integer lockLimit);
	
	/**
	 * 解锁
	 * @param customer
	 * @param operator
	 * @return
	 */
	boolean accountUnLock(Customer customer,User operator);
	
	
	/**
	 * 锁定倒数,账户距离指定错误还剩余多少次
	 * @param userName
	 * @param max
	 * @return
	 */
    int lockTheCountdown(String userName,Integer max);
    
    
    /**
     * 锁定用户
     * @param user
     * @return
     */
    boolean lockUser(Customer customer);
    
    
    /**
     * 锁定用户
     * @param user
     * @return
     */
    boolean lockUser(Customer customer,User operator);
    
    /**
     * 获取用户的锁定状态.没有锁定状态,就创建一条.
     * @param customer
     * @return
     */
    UserLockState getUserLockState(Customer customer);
	
}
