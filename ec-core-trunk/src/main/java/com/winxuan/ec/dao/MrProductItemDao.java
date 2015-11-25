/*
 * @(#)MrProductItemDao.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.framework.dynamicdao.annotation.Delete;
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
 * @author yangxinyi
 * @version 1.0,2013-8-22
 */
public interface MrProductItemDao {

	@Get
	MrProductItem get(Long id);
	
	@Save
	void save(MrProductItem mrProductItem);

	@SaveOrUpdate
	void saveOrUpdate(MrProductItem mrProductItem);
	
	/**
	 * 商品自编码，商品名称，商品条形码（ISBN），EC分类，供应商名称，出版社名称,码洋（区间），repQuantityMin:补货数量
	 * @description
	 * @author wangbiao
	 * @date 2013-8-28 下午3:00:54
	 *
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	@Query("FROM MrProductItem mpi")
	@Conditions({
		@Condition("mpi.productSale.id=:productSale"),
		@Condition("mpi.productSale.outerId like :outerId"),
		@Condition("mpi.productSale.product.name like :productName"),
		@Condition("mpi.productSale.product.barcode=:productBarcode"),
		@Condition("mpi.productSale.product.vendor like :productVendor"),
		@Condition("mpi.productSale.product.manufacturer like :productManufacturer"),
		@Condition("mpi.productSale.product.mcCategory like :productMcCategory"),
		@Condition("mpi.productSale.basicPrice>=:basicPriceMin"),
		@Condition("mpi.productSale.basicPrice<=:basicPriceMax"),
		@Condition("mpi.productSale.stockQuantity>=:stockQuantityMin"),
		@Condition("mpi.productSale.stockQuantity<=:stockQuantityMax"),
		
		@Condition("mpi.productSale.product.mcCategory in :mcCategorys"),
		@Condition("mpi.model.id=:model"),
		@Condition("mpi.dc.name like :dc"),
		@Condition("mpi.num=:num"), 
		@Condition("mpi.replenishmentCycle>=:replenishmentCycleMin"), 
		@Condition("mpi.replenishmentCycle<=:replenishmentCycleMax"), 
		@Condition("mpi.deliveryQuantity>=:deliveryQuantityMin"), 
		@Condition("mpi.deliveryQuantity<=:deliveryQuantityMax"), 
		@Condition("mpi.safeQuantity>=:safeQuantityMin"), 
		@Condition("mpi.safeQuantity<=:safeQuantityMax"), 
		@Condition("mpi.forecastQuantity>=:forecastQuantityMin"), 
		@Condition("mpi.forecastQuantity<=:forecastQuantityMax"), 
		@Condition("mpi.availableQuantity>=:availableQuantityMin"), 
		@Condition("mpi.availableQuantity<=:availableQuantityMax"), 
		@Condition("mpi.replenishmentQuantity>=:repQuantityMin"), 
		@Condition("mpi.replenishmentQuantity<=:repQuantityMax"), 
		@Condition("mpi.check=0"), 
		@Condition("mpi.createTime>=:createStartTime"),
		@Condition("mpi.createTime<=:createEndTime"),
		@Condition("mpi.auditTime>=:auditStartTime"),
		@Condition("mpi.auditTime<=:auditEndTime"),
		@Condition("mpi.grade in :grades"),
		@Condition("mpi.type=:type")
		})
	@OrderBys({
		@OrderBy("mpi.replenishmentQuantity desc")
	})
	List<MrProductItem> findMrProductItemsByType(
			@ParameterMap Map<String, Object> parameters,
			@Order Short sort,
			@Page Pagination pagination);
	
	@Update
	void update(MrProductItem mrProductItem);
	
	@Query(value = "UPDATE mr_product_item SET status = 1, audittime = NOW() WHERE id = ?", sqlQuery = true, executeUpdate = true)
	void updateCheckStatus(Long id);
	
	@Delete
	void delete(MrProductItem mrProductItem);
	
	//根据productsale，dc，type和status选择对应的补货记录
	@Query("from MrProductItem where productsale=? and dc=? and type=? and status=?")
	MrProductItem findArtificialProductItem(ProductSale productSale, Code dc, Code type, boolean status);

}
