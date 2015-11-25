package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 退货积分扣减规则
 * @author sunflower
 *
 */
public class ReturnOrderCustomerPointsRule extends CustomerPointsRule {

	public ReturnOrderCustomerPointsRule(Customer customer,
			CustomerAccountDao customerAccountDao,
			CustomerPointsDao customerPointsDao, Order order) {
		super(customer,customerAccountDao,customerPointsDao,order);
	}

	@Override
	protected Long getCodeType() {
		return Code.CUSTOMER_POINTS_TYPE_RETURN_ORDER;
	}

	@Override
	protected String getComment() {
		return "退货扣减原先添加的积分";
	}

	@Override
	public int generatePoints(Object object) {
		if(order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_COMPLETED)){
			int returnPoints = order.getReturnTotalPoints();
			int ownerPoints = customer.getAccount().getPoints();
			if(ownerPoints <= returnPoints){//当前用户拥有的积分小于需扣减的积分
				return ownerPoints*MagicNumber.NEGATIVE_ONE;
			}else{
				return returnPoints*MagicNumber.NEGATIVE_ONE;
			}
		}else{
			return 0;
		}
		
	}

}
