/*
 * @(#)RefundCredentialDao.java
 *
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.model.refund.RefundMessage;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  huangyixiang
 * @version 2013-5-16
 */
public interface RefundCredentialDao {
	
	@Get
	RefundCredential get(String id);
	
	@Save
	void save(RefundCredential refundCredential);
	
	@Update
	void update(RefundCredential refundCredential);
	
	@SaveOrUpdate
	void saveOrUpdate(RefundCredential refundCredential);
	
	@Query("from RefundCredential r where r.order = ?  ")
	List<RefundCredential> findByOrder(Order order);

	@Query("from RefundCredential r where r.order = ?  and r.processStatus = ? ")
	List<RefundCredential> findByOrder(Order order,Code processStatus);
	
	@Query("from RefundCredential r")
	@Conditions({
		@Condition("r.payment.id = :paymentId"),
		@Condition("r.status.id in :status"),
		@Condition("r.outerId = :outerId"),
		@Condition("r.refundTime >= :startTime"),
		@Condition("r.refundTime <= :endTime"),
		@Condition("r.order.id in :orderIds")
	})
	@OrderBys({
		@OrderBy("r.createTime desc"),
		@OrderBy("r.refundTime desc"),
		@OrderBy("r.payment.id desc"),
		@OrderBy("r.status desc")
	})
	List<RefundCredential> find(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination, @com.winxuan.framework.dynamicdao.annotation.query.Order short sort);
	
	@Query("from RefundCredential r where r.status = ?")
	List<RefundCredential> findByStatus(Code status, @MaxResults int size);
	
	@Query(value="select DISTINCT rc.payment from refund_credential rc", sqlQuery=true)
	List<Object> getPaymentId();
	
	@Query("from RefundMessage r where r.payment.id = ? and r.available = ?")
	List<RefundMessage> find(Long payment,boolean available);
	
	@SaveOrUpdate
	void saveorupdate(RefundMessage refundMessage);
	
	@Query("from RefundMessage r")
	@Conditions({
		@Condition("r.available = :available"),
		@Condition("r.payment.id in :paymentId")
	})
	@OrderBys({
		@OrderBy("r.id asc")
	})
	List<RefundMessage> findRefundMessage(@ParameterMap Map<String, Object> params,
			 @Page Pagination pagination,@com.winxuan.framework.dynamicdao.annotation.query.Order short sort);
	
	@Query("from RefundMessage r where r.id = ?")
	RefundMessage get(Long id);
	
}
