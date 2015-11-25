/*
 * @(#)BatchPayDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.OrderBatchPay;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-11
 */
public interface BatchPayDao {

	@Get
	BatchPay get(String id);
	
	@Query("from BatchPay b where b.digest = ?")
	BatchPay getByDigest(String digest);

	@Update
	void update(BatchPay batchPay);

	@Save
	void save(BatchPay batchPay);
	
	@Query(value="from OrderBatchPay ob where ob.orderId=?")
	OrderBatchPay getByOrderId(String orderId);
}
