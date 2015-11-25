package com.winxuan.ec.task.dao.union;

import java.math.BigDecimal;
import java.util.List;

import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.task.model.union.ADUnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-29
 */
public interface UnionDao {
	
	static final String UPDATE_UNION_DELIVERYORDER = "UPDATE t_order o SET o.update_timestamp = now(),o.status =1,o.sale_book_price=?,o.sale_real_price=?,o.amount=? WHERE  o.status =0 AND o.number =? AND ispay=0";
	
	static final String UPDATE_UNION_CANCELORDER = "UPDATE t_order o SET o.status =-1 WHERE  o.status =0 AND o.number =? AND ispay=0";
	
	static final String SELECT_UNION_ORDER = "SELECT merchantid,create_timestamp,number,amount,book_price,real_price,sale_book_price,sale_real_price,stan FROM t_order WHERE status =1 AND number=?";
	
	static final String SELECT_UNION_REFUNDORDER = "SELECT count(*) FROM t_order WHERE number =? AND status =-2";
	
	static final String INSERT_UNION_REFUNDORDER = "INSERT INTO t_order(merchantid,create_timestamp,update_timestamp,status ,number ,amount,book_price,real_price,sale_book_price,sale_real_price,refund_book_price,refund_real_price,stan,ispay)  values(?,?,now(),-2,?,?,?,?,?,?,?,?,?,0)";
	
	static final String SELECT_NEW_UNION_ORDER = "SELECT number FROM t_order where status = 0";
	
	void updateDeliveryUnionOrder(UnionOrder unionOrder);
	
	void updateCannelUnionOrder(UnionOrder unionOrder);
	
	ADUnionOrder get(String orderId);
	
	boolean isExistRefund(String orderId);
	
	void saveRefundUnionOrder(ADUnionOrder adUnionOrder,BigDecimal refundMoney);
	
	List<String> findNewUnionOrderId();
		
}
