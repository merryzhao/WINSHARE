/*
 * @(#)ProductSaleIncorrectStockDao.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.ProductSaleIncorrectStock;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-7-31
 */
public interface ProductSaleIncorrectStockDao {
	@Save
	void save(ProductSaleIncorrectStock productSaleIncorrectStock);
	
	@Get
	ProductSaleIncorrectStock get(Long id);
	
	@Query(value="select count(1) from product_sale_incorrectstock psi where psi.changed=0 and psi.dc=? and psi.productsale=?", sqlQuery=true)
	boolean existIncorrectStock(Long dcId, Long productSaleId);
	
	@Query(value="select psi.id from product_sale_incorrectstock psi where psi.changed=0 and psi.dc=? and psi.productsale=?", sqlQuery=true)
	List<Map<String, Integer>> findIncorrectStock(Long dcId, Long productSaleId);
	
	@Query(value="update product_sale_incorrectstock psi set psi.changed=1 where psi.dc=? and psi.productsale=?", sqlQuery=true, executeUpdate=true)
	void updateIncorrectStockChanged(Long dcId, Long productSaleId);
	
	@Query(value="select ifnull(sum(o.quantity),0) num from product_sale_stock_occupancy o where o.status=1 and o.productsale=?", sqlQuery=true)
	long getStockOccupancy(long productSaleId);
	
	@Query(value="select o.productsale,sum(o.quantity) num from product_sale_stock_occupancy o where o.status=1 and o.productsale in :productSaleIds group by o.productsale", sqlQuery=true)
	List<Map<String, Object>> getOccupancyStocks(@Parameter("productSaleIds")List<Long> productSaleIds);
}
