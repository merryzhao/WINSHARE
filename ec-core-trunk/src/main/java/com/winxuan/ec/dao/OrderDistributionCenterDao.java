package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-29 下午4:40:16 --!
 * @description:
 ******************************** 
 */
public interface OrderDistributionCenterDao {

	@Query("from OrderDistributionCenter odc where odc.id = ?" )
	OrderDistributionCenter get(String id);
	
	@Update
	OrderDistributionCenter update(OrderDistributionCenter orderDistributionCenter);

}
