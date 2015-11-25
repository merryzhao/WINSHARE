package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Customer;

/**
 * 添加喜欢积分规则
 * @author sunflower
 *
 */
public class FavoriteCustomerPointsRule extends CustomerPointsRule {
	
	private static final int FAVORITE_POINTS_AWARD = 5;

	public FavoriteCustomerPointsRule(Customer customer, CustomerAccountDao customerAccountDao, CustomerPointsDao customerPointsDao) {
		super(customer, customerAccountDao, customerPointsDao);
	}

	@Override
	protected Long getCodeType() {
		return Code.CUSTOMER_POINTS_TYPE_FAVORITE_AWARD;
	}

	@Override
	protected String getComment() {
		
		return "添加喜欢奖励积分";
	}

	@Override
	public int generatePoints(Object object) {
		
		return FAVORITE_POINTS_AWARD;
	}

}
