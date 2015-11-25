//package com.winxuan.ec.service.customer;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.joda.time.DateTime;
//
//import com.winxuan.ec.model.customer.CustomerLock;
//import com.winxuan.ec.model.user.Customer;
//import com.winxuan.ec.model.user.User;
//import com.winxuan.ec.model.user.UserLockState;
//import com.winxuan.ec.util.AccountCacheKeyStrategy;
//import com.winxuan.framework.cache.CacheManager;
//
///**
// * ******************************
// * 
// * @author:cast911
// * @lastupdateTime:2013-4-17 下午12:02:42 --! 有后续需求的话 重写一个bydata的实现,
// ******************************** 
// */
//
//
//public class CustomerLockServiceByCacheImpl implements CustomerLockService {
//
//	
//	private CacheManager cacheManager;
//
//	public CacheManager getCacheManager() {
//		return cacheManager;
//	}
//
//	public void setCacheManager(CacheManager cacheManager) {
//		this.cacheManager = cacheManager;
//	}
//
//	@Override
//	public Integer getAccountErrorTimes(String userName) {
//		CustomerLock customerLock = this.getCustomerLock(userName);
//		if (customerLock == null) {
//			return 0;
//		}
//		return customerLock.getErrorTimes();
//	}
//
//	@SuppressWarnings("unchecked")
//	private CustomerLock getCustomerLock(String userName) {
//		ArrayList<CustomerLock> customerLockList = (ArrayList<CustomerLock>) this.cacheManager
//				.get(AccountCacheKeyStrategy.getUserLockGroupCacheKey());
//		if (CollectionUtils.isEmpty(customerLockList)) {
//			return null;
//		}
//		for (CustomerLock customerLock : customerLockList) {
//			if (userName.equals(customerLock.getUserName())) {
//				return customerLock;
//			}
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	private boolean updateOrSaveCustomerLock(CustomerLock customerLock) {
//		ArrayList<CustomerLock> customerLockList = (ArrayList<CustomerLock>) this.cacheManager
//				.get(AccountCacheKeyStrategy.getUserLockGroupCacheKey());
//		if (CollectionUtils.isEmpty(customerLockList)) {
//			customerLockList = new ArrayList<CustomerLock>();
//		}
//
//		ArrayList<CustomerLock> newCustomerLockList = new ArrayList<CustomerLock>();
//		for (int i = 0; i < customerLockList.size(); i++) {
//			CustomerLock oldCustomerLock = customerLockList.get(i);
//			if (!customerLock.getUserName().equals(
//					oldCustomerLock.getUserName())) {
//				newCustomerLockList.add(oldCustomerLock);
//			}
//		}
//		customerLockList.clear();
//		newCustomerLockList.add(customerLock);
//		return this.cacheManager.put(
//				AccountCacheKeyStrategy.getUserLockGroupCacheKey(),
//				newCustomerLockList, this.getSecondDayTime());
//
//	}
//
//	/**
//	 * 获取第二天的最早时间
//	 * 
//	 * @return
//	 */
//	private Date getSecondDayTime() {
//		DateTime nowTime = new DateTime();
//		nowTime = nowTime.withDayOfMonth(nowTime.getDayOfMonth() + 1);
//		nowTime = nowTime.withHourOfDay(0);
//		nowTime = nowTime.withMinuteOfHour(0);
//		nowTime = nowTime.withSecondOfMinute(0);
//		return nowTime.toDate();
//	}
//
//	public Integer lockTheCountdown(String userName, Integer max) {
//		Integer errorTimes = this.getAccountErrorTimes(userName);
//		return max - errorTimes;
//	}
//
//	@Override
//	public Boolean addAccountErrorTimes(String userName) {
//		Integer accountErrorTimes = this.getAccountErrorTimes(userName);
//		CustomerLock customerLock = this.getCustomerLock(userName);
//		if (customerLock == null) {
//			customerLock = new CustomerLock();
//		}
//		customerLock.setErrorTimes(++accountErrorTimes);
//		customerLock.setLockTime(new Date());
//		customerLock.setUserName(userName);
//		return this.updateOrSaveCustomerLock(customerLock);
//	}
//
//	@Override
//	public Boolean accountIsLock(String userName) {
//		return this.accountIsLock(userName,
//				AccountCacheKeyStrategy.DEFAULT_LOCK_LIMIT);
//	}
//
//	@Override
//	public Boolean accountIsLock(String userName, Integer lockLimit) {
//		Integer accountErrorTimes = this.getAccountErrorTimes(userName);
//		return accountErrorTimes >= lockLimit;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public Boolean accountUnLock(Customer customer,User operator) {
//		ArrayList<CustomerLock> customerLockList = (ArrayList<CustomerLock>) this.cacheManager
//				.get(AccountCacheKeyStrategy.getUserLockGroupCacheKey());
//		if (CollectionUtils.isEmpty(customerLockList)) {
//			return true;
//		}
//		CustomerLock customerLock = this.getCustomerLock(customer.getName());
//		customerLockList.remove(customerLock);
//		this.cacheManager.put(
//				AccountCacheKeyStrategy.getUserLockGroupCacheKey(),
//				customerLockList, this.getSecondDayTime());
//		return customerLockList.remove(customerLock);
//	}
//
//	@Override
//	public Boolean lockUser(Customer customer) {
//		return null;
//	}
//
//	@Override
//	public Boolean lockUser(Customer customer, User operator) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public UserLockState getUserLockState(Customer customer) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
