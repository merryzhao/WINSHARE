package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderLogistics;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/*******************************************
* @ClassName: OrderLogisticsDao 
* @Description: TODO
* @author:cast911
* @date:2014年9月13日 下午12:57:13 
*********************************************/
public interface OrderLogisticsDao extends BaseDao<OrderLogistics> {

	@Query("from OrderLogistics logis")
	@Conditions({ @Condition("logis.order=:order")})
	List<OrderLogistics> find(@ParameterMap Map<String, Object> parameters);

}
