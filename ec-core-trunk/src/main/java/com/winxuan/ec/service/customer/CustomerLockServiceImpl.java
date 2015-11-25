package com.winxuan.ec.service.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.UserLockStateDao;
import com.winxuan.ec.dao.UserLoginLogDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.model.user.UserLockState;
import com.winxuan.ec.util.AccountCacheKeyStrategy;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * ******************************
 * 
 * @author:cast911
 * @lastupdateTime:2013-4-18 下午3:31:38 --! 根据数据库记录锁定用户
 ******************************** 
 */

@Service(value = "customerLockService")
@Transactional(rollbackFor = Exception.class)
public class CustomerLockServiceImpl implements CustomerLockService {

	@InjectDao
	UserLoginLogDao userLoginLogDao;

	@InjectDao
	UserLockStateDao userLockStateDao;

	@Autowired
	CustomerService customerService;

	@Override
	public int getAccountErrorTimes(String userName) {
		UserLockState userLockState = null;
		Customer customer = this.getCustomerByName(userName);

		if (customer == null) {
			// 不存在的用户查看登录日志
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", userName);
			parameters.put("isLogin", false);
			parameters.put("startTime", this.morningTime());
			parameters.put("endTime", this.latestTime());
			Long count = userLoginLogDao.countfind(parameters);
			return Integer.valueOf(count + "");
		}
		userLockState = getUserLockState(customer);
		return userLockState.getErrorTimes();
	}

	@Override
	public void addAccountErrorTimes(String userName) {
		Customer customer = this.getCustomerByName(userName);
		if (customer != null) {
			UserLockState userLockState = this.getUserLockState(customer);
			userLockState.setErrorTimes(userLockState.getErrorTimes() + 1);
		}
	}

	@Override
	public boolean accountIsLock(String userName) {
		return this.accountIsLock(userName,
				AccountCacheKeyStrategy.DEFAULT_LOCK_LIMIT);
	}

	private Customer getCustomerByName(String name) {
		Customer customer = null;
		if (customer == null) {
			customer = customerService.getByName(new Code(
					Code.USER_SOURCE_EC_FRONT), name);
		}
		if (customer == null) {
			customer = customerService.getByName(new Code(
					Code.USER_SOURCE_ANONYMITY), name);
		}
		return customer;
	}

	@Override
	public boolean accountIsLock(String userName, Integer lockLimit) {
		Customer customer = this.getCustomerByName(userName);
		// 不存在的用户 不需要锁定
		if (customer == null) {
			return false;
		}

		if (!customer.isAvailable()) {
			return true;
		}
		// 是否有锁定记录
		boolean result = this.findUserIsLock(customer);
		if (result) {
			return true;
		}

		Integer accountErrorTimes = this.getAccountErrorTimes(customer
				.getName());
		//
		result = (accountErrorTimes >= lockLimit);
		if (result) {
			this.lockUser(customer);
		}
		return result;
	}

	@Override
	public boolean accountUnLock(Customer customer, User operator) {
		// 设置用户为可用状态
		customer.setAvailable(true);
		this.customerService.update(customer);
		// 更新 用户锁定 表
		UserLockState userLockState = this.getUserLockState(customer);
		userLockState.setLastUpdateTime(new Date());
		userLockState.setErrorTimes(0);
		userLockState.setUser(customer);
		userLockState.setLock(false);
		userLockState.setOperator(operator);
		this.userLockStateDao.update(userLockState);
		return true;
	}

	@Override
	public int lockTheCountdown(String userName, Integer max) {
		Integer errorTimes = this.getAccountErrorTimes(userName);
		return max - errorTimes;
	}

	private Boolean findUserIsLock(User user) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("user", user);
		parameters.put("isLock", true);
		long result = userLockStateDao.countFind(parameters);
		return result > 0;
	}

	@Override
	public UserLockState getUserLockState(Customer customer) {
		UserLockState userLockState = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("user", customer);
		List<UserLockState> userLockStateList = userLockStateDao
				.find(parameters);
		if (CollectionUtils.isEmpty(userLockStateList)) {
			userLockState = new UserLockState();
			userLockState.setUser(customer);
			userLockState.setLock(false);
			userLockState.setLastUpdateTime(customer.getLastLoginTime());
			userLockState.setOperator(customer);
			userLockStateDao.save(userLockState);
			return userLockState;
		}
		// 重复项数据 删除除第一个以外的所有数据
		if (userLockStateList.size() > 0) {
			for (int i = 1; i < userLockStateList.size(); i++) {
				userLockStateDao.delelte(userLockStateList.get(i));
			}
		}
		userLockState = userLockStateList.get(0);
		return userLockState;
	}

	/**
	 * 当天的最早时间
	 * 
	 * @return
	 */
	private Date morningTime() {
		DateTime nowTime = new DateTime();
		nowTime = nowTime.withHourOfDay(0);
		nowTime = nowTime.withMinuteOfHour(0);
		nowTime = nowTime.withSecondOfMinute(0);
		return nowTime.toDate();
	}

	/**
	 * 当天的最晚时间
	 * 
	 * @return
	 */
	private Date latestTime() {
		DateTime afterTime = new DateTime();
		afterTime = afterTime.withHourOfDay(23);
		afterTime = afterTime.withMinuteOfHour(59);
		afterTime = afterTime.withSecondOfMinute(59);
		return afterTime.toDate();
	}

	@Override
	public boolean lockUser(Customer customer, User operator) {
		UserLockState userLockState = this.getUserLockState(customer);
		userLockState.setUser(customer);
		userLockState.setLock(true);
		userLockState.setLastUpdateTime(new Date());
		userLockState.setOperator(operator);
		userLockStateDao.update(userLockState);
		customer.setAvailable(false);
		customerService.update(customer);
		return true;
	}

	@Override
	public boolean lockUser(Customer customer) {
		return this.lockUser(customer, customer);
	}

	@Override
	public void update(UserLockState userLockState) {
   	 this.userLockStateDao.update(userLockState);
	}

}
