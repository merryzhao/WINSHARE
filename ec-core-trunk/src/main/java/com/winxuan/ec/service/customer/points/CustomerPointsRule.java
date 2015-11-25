package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerPoints;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;

/**
 * 用户积分规则
 * 
 * @author sunflower
 * 
 */
public abstract class CustomerPointsRule {

	protected Customer customer;
	protected Order order;

	private CustomerAccountDao customerAccountDao;

	private CustomerPointsDao customerPointsDao;

	public CustomerPointsRule(Customer customer,
			CustomerAccountDao customerAccountDao,
			CustomerPointsDao customerPointsDao, Order order) {

		this(customer, customerAccountDao, customerPointsDao);
		this.order = order;
	}
	
	public CustomerPointsRule(){
		
	}

	public CustomerPointsRule(Customer customer,
			CustomerAccountDao customerAccountDao,
			CustomerPointsDao customerPointsDao) {

		this.customer = customer;
		this.customerAccountDao = customerAccountDao;
		this.customerPointsDao = customerPointsDao;
	}

	/**
	 * 添加积分处理
	 */
	public int addPoints() {
		int points = getPoints();
		String comment = getComment();
		Long codeType = getCodeType();
		if (customer != null) {
			CustomerAccount account = customer.getAccount();
			int accountPoints = account.getPoints();
			account.setPoints(accountPoints + points);
			customerAccountDao.update(account);
			CustomerPoints customerPoints = new CustomerPoints(customer,
					points, new Code(codeType), order, comment);
			customerPointsDao.save(customerPoints);
		}
		return points;
	}

	/**
	 * 获取code类型
	 * 
	 * @return
	 */
	protected abstract Long getCodeType();

	/**
	 * 获取备注
	 * 
	 * @return
	 */
	protected abstract String getComment();


	/**
	 * 产生需要添加的积分
	 * @param object
	 * 			如果需要修改它的内容的话，否则传任意值
	 * 			针对某些实现类会去修改object内容
	 * @return
	 */
	public abstract int generatePoints(Object object);
	
	/**
	 * 获取积分，默认情况下产生的积分和获取的积分是一样的.
	 * 但是像订单确认是异步处理积分的，可能会存在积分规则已经变化的情况。此时需要重载该方法
	 * 设置为public：在某种情况下会调用该方法：比如查看历史订单对应的积分
	 * @return
	 */
	public int getPoints(){
		return generatePoints(null);
	}

}