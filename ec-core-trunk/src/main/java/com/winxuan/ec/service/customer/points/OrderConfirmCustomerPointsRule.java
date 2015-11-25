package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;

/**
 * 订单确认积分规则
 * @author sunflower
 *
 */
public class OrderConfirmCustomerPointsRule extends CustomerPointsRule {

	private static final int CONFIRM_ORDER_POINTS_AWARD = 5;
	
	public OrderConfirmCustomerPointsRule(Customer customer, CustomerAccountDao customerAccountDao, CustomerPointsDao customerPointsDao,Order order) {
		super(customer, customerAccountDao, customerPointsDao,order);
	}

	@Override
	protected Long getCodeType() {
		return Code.CUSTOMER_POINTS_TYPE_CONFIRM_ORDER_AWARD;
	}

	@Override
	protected String getComment() {
		
		return "确认收货奖励积分";
	}

	@Override
	public int generatePoints(Object object) {
		
		return CONFIRM_ORDER_POINTS_AWARD;
	}

}
