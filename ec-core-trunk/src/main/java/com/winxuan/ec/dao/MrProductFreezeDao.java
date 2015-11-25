/*
 * @(#)MrProductFreezeDao.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-8-26
 */
public interface MrProductFreezeDao {
	@Save
	void save(MrProductFreeze mrProductFreeze);

	@Update
	void update(MrProductFreeze mrProductFreeze);

	@Query("from MrProductFreeze mpf")
	@Conditions({ 
		@Condition("mpf.productSale.id =:productSaleId"),
		@Condition("mpf.type.id =:type"),
		@Condition("mpf.dc.id =:dc"),
		@Condition("mpf.productSale.product.vendor =:vendor"),
		@Condition("mpf.flag = '0'") })
	@OrderBys({ @OrderBy("mpf.createTime desc") })
	List<MrProductFreeze> findFreezedProducts(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);

	@SaveOrUpdate
	void saveOrUpdateFreeze(MrProductFreeze mrProductFreeze);

	@Get
	MrProductFreeze get(Long id);

	@Delete
	void delete(MrProductFreeze mrProductFreeze);

	@Query("from MrProductFreeze mpf where mpf.productSale=? and mpf.type=? and mpf.flag = 0")
	boolean isExistByProductSaleAndType(ProductSale productSale, Code type);

	@Query("from MrProductFreeze mpf where mpf.productSale=? and mpf.type=? and mpf.flag = 0")
	MrProductFreeze findFreezeByProductSaleAndType(ProductSale productSale,
			Code type);

	@Query("from MrProductFreeze mpf where mpf.productSale=? and mpf.type=? and mpf.dc=? and mpf.flag = 0")
	boolean isExistByProductSaleAndType(ProductSale productSale, Code type,
			Code dc);
	
	@Query("from MrProductFreeze mpf where mpf.productSale=? and mpf.type=? and mpf.dc=? and mpf.flag = 0")
	MrProductFreeze findFreezeByProductSaleAndType(ProductSale productSale,
			Code type, Code dc); 
	
	@Query("from MrProductFreeze mpf where mpf.productSale=? and mpf.type=? and mpf.dc=? and mpf.flag != 1")
	boolean isExistByProductSaleAndTypeNew(ProductSale productSale, Code type,
			Code dc);
	
	@Query("from MrProductFreeze mpf where mpf.productSale=? and mpf.type=? and mpf.dc=? and mpf.flag != 1")
	MrProductFreeze findFreezeByProductSaleAndTypeNew(ProductSale productSale,
			Code type, Code dc); 

}
