/*
 * @(#)MrProductFreezeService.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.ReplenishmentArtificialLimitException;
import com.winxuan.ec.exception.ReplenishmentRestrictLimitException;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-8-26
 */
public interface MrProductFreezeService {

	void save(MrProductFreeze mrProductFreeze);
	
	void update(MrProductFreeze mrProductFreeze);
	
	/**
	 * 由于限制补货表和人工冻结表的添加逻辑有区别，所以将限制补货表和人工冻结表的添加功能分开写
	 * @param mrProductFreeze
	 * @throws ReplenishmentArtificialLimitException
	 */
	void saveOrUpdateArtificial(MrProductFreeze mrProductFreeze) throws ReplenishmentArtificialLimitException; 
	
	void saveOrUpdateRestrict(MrProductFreeze mrProductFreeze)throws ReplenishmentRestrictLimitException;

	/**
	 * 定时任务MrDeliveryRecordJob中调用，将冻结时间超过（包括）45天的商品解冻
	 */
	void updateFreezeFlag();

	/**
	 * 获取当前通过审核的冻结商品，暂用于下传sap
	 */
	List<MrProductFreeze> findFreezedProducts();
	
	List<MrProductFreeze> findFreezedProducts(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	List<MrProductFreeze> fetchData(InputStream in) throws IOException;
	
	List<MrProductFreeze> fetchArtificialFreezeData(InputStream in) throws IOException, ReplenishmentArtificialLimitException;
	
	void saveAll(List<MrProductFreeze> datas);
	
	void saveOrUpdateFreeze(MrProductFreeze mrProductFreeze);

	MrProductFreeze get(Long id);
	
	void delete(MrProductFreeze mrProductFreeze);
	
	/**
	 * 根据productsale查找对应的商品是否被限制补货
	 * @param productSale
	 * @return
	 */
	boolean existSingleLimitFreezedProduct(ProductSale productSale);
	
	/**
	 * 根据productsale查找被限制补货的商品
	 */
	MrProductFreeze findSingleLimitFreezedProduct(ProductSale productSale);
	
	void updateProductFreeze(Long productFreezeId);
	/**
	 * 获取所有应该解冻的商品列表
	 */
	List<Long> getFreezed();

}
