/*
 * @(#)CustomerDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.ec.model.customer.CustomerExtension;
import com.winxuan.ec.model.customer.CustomerIP;
import com.winxuan.ec.model.customer.CustomerThirdParty;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-13
 */
public interface CustomerDao {

	@Get
	Customer get(Long id);

	@Save
	void save(Customer customer);

	@Update
	void update(Customer customer);
	
	@SaveOrUpdate
	void saveOrUpdate(Customer customer);

	@Query("from Customer c where c.source=? AND c.name=?")
	Customer getByName(Code source, String name);

	@Query("from Customer c where c.source=? AND c.email=?")
	Customer getByEmail(Code source, String email);

	@Query("from Customer c where c.source=? AND c.name=? and c.password=?")
	Customer getByNameAndPassword(Code source, String name, String password);

	@Query("from Customer c where c.source=? AND c.email=? and c.password=?")
	Customer getByEmailAndPassword(Code source, String email,String password);

	@Query("from Customer c where c.source=? AND c.name=?")
	boolean nameIsExisted(Code source, String name);

	@Query("from Customer c where c.source=? AND c.email=?")
	boolean emailIsExisted(Code source, String email);

	@Query("from Customer c")
	@Conditions({ @Condition("c.name =:name"), @Condition("c.name in:names"),
			@Condition("c.registerTime >=:registerTimeStart"),
			@Condition("c.registerTime <=:registerTimeEnd"),
			@Condition("c.lastLoginTime >=:lastLoginTimeStart"),
			@Condition("c.lastLoginTime <=:lastLoginTimeEnd"),
			@Condition("c.lastTradeTime >=:lastTradeTimeStart"),
			@Condition("c.lastTradeTime <=:lastTradeTimeEnd"),
			@Condition("c.channel = :channel"),
			@Condition("c.channel in :channels"),
			@Condition("c.channel.id in :channelIds"),
			@Condition("c.grade =:grade"), @Condition("c.grade in:grades"),
			@Condition("c.source.id =:source") })
	List<Customer> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);

	@SaveOrUpdate
	void saveOrUpdateCustomerExtension(CustomerExtension customerExtension);
	
	@SaveOrUpdate
	void saveOrUpdateCustomerExtend(CustomerExtend customerExtend);
	
	@Query(sqlQuery=true,value="select max(id) from" +
			" (select count(*) as id from `user` c where c.source in(40001,40010,40021) AND (c.name=?) and c.`available`=1 "+
			" union all "+
			" select count(*) as id from `user` c where c.source in(40001,40010,40021) AND ( c.email=?) and c.`available`=1 )  b")
	long nameOrEmailIsDouble(String name,String email);
	
	@SaveOrUpdate
	void saveCustomerThirdParty(CustomerThirdParty customerThirdParty);
	
	@Query("from CustomerThirdParty c where c.thirdparty.id = ? ")
	CustomerThirdParty getByThirdPartyId(Long thirdPartyId);

	@Query("from CustomerThirdParty c where c.thirdparty.name = ? ")
	CustomerThirdParty getByThirdPartyOutId(String outId);
	
	@SaveOrUpdate
	void saveorupdate(CustomerIP customerIP);
	
	@Query("from CustomerIP ci")
	@Conditions({
		@Condition("ci.id =:cid"),
		@Condition("ci.customer =:customer"),
		@Condition("ci.customerCookie =:customercookie")
	})
	@OrderBys({
		@OrderBy("ci.createTime desc")
	})
	List<CustomerIP> getCustomerIPByCustomer(@ParameterMap Map<String, Object> params,@Page Pagination pagination
			,@Order short orderIndex);
	
}
