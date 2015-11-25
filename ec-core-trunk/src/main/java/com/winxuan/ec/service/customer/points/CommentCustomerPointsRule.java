package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.user.Customer;

/**
 * 添加评论积分规则
 * 
 * @author sunflower
 * 
 */
public class CommentCustomerPointsRule extends CustomerPointsRule {

	private static final int COMMENT_POINTS_AWARD = 10;

	private static final int COMMENT_POINTS_AHEAD_AWARD = 20;

	private static final int COMMENT_AHEAD_FIVE = 5;

	ProductSalePerformance productSalePerformance;

	public CommentCustomerPointsRule(Customer customer,
			CustomerAccountDao customerAccountDao,
			CustomerPointsDao customerPointsDao,
			ProductSalePerformance productSalePerformance) {
		super(customer, customerAccountDao, customerPointsDao);
		this.productSalePerformance = productSalePerformance;
	}

	@Override
	protected Long getCodeType() {
		return Code.CUSTOMER_POINTS_TYPE_COMMENT_AWARD;
	}

	@Override
	protected String getComment() {
		return "提交评论奖励积分";
	}

	@Override
	public int generatePoints(Object object) {

		if (productSalePerformance.getTotalComment() <= COMMENT_AHEAD_FIVE) {
			return COMMENT_POINTS_AHEAD_AWARD;
		}
		return COMMENT_POINTS_AWARD;
	}

}
