package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;



/**
 * @author  周斯礼
 * @version 2012-11-8
 */

public interface CustomerExtendDao {

	@Query("from CustomerExtend ce")
	@Conditions({
		@Condition("ce.payMobile = :payMobile"),
		@Condition("ce.payEmail = :payEmail")
	})
	List<CustomerExtend> find(@ParameterMap Map<String, Object> parameters);
}


