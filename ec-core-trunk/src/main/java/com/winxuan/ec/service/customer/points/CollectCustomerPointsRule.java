package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Customer;

/**
 * 添加收藏积分规则
 * @author sunflower
 *
 */
public class CollectCustomerPointsRule extends CustomerPointsRule {
	
	private static final int COLLECT_POINTS_AWARD = 5;

	public CollectCustomerPointsRule(Customer customer, CustomerAccountDao customerAccountDao, CustomerPointsDao customerPointsDao) {
		super(customer, customerAccountDao, customerPointsDao);
	}

	@Override
	protected Long getCodeType() {
		return Code.CUSTOMER_POINTS_TYPE_COLLECT_AWARD;
	}

	@Override
	protected String getComment() {
		return "添加收藏奖励积分";
	}

	@Override
	public int generatePoints(Object object) {
		return COLLECT_POINTS_AWARD;
	}

}
