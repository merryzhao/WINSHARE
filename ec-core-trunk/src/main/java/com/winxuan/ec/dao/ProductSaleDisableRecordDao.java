package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleDisableItem;
import com.winxuan.ec.model.product.ProductSaleDisableRecord;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * @description: TODO
 * 
 * @createtime: 2013-12-11 下午4:37:19
 * 
 * @author zenghua
 * 
 * @version 1.0
 */

public interface ProductSaleDisableRecordDao {

	@Get
	ProductSaleDisableRecord get(Long productSaleDisableRecordId);
	
	@Get
	ProductSaleDisableItem getItem(Long productSaleDisableItemId);
	
	@Update
	void update(ProductSaleDisableRecord productSaleDisableRecord);
	
	@Update
	void updateItem(ProductSaleDisableItem productSaleDisableItem);

	@Query("FROM ProductSaleDisableRecord psdr")
	@Conditions({ @Condition("psdr.createTime>= :startTime"),
			@Condition("psdr.createTime<= :endTime"),
			@Condition("psdr.status.id =:status"),
			@Condition("psdr.uploader.id =:userId") })
	@OrderBys({ @OrderBy("psdr.id DESC") })
	List<ProductSaleDisableRecord> find(
			@ParameterMap Map<String, Object> params,
			@Page Pagination pagination, @Order short index);

	@Query("FROM ProductSaleDisableItem psdi")
	@Conditions({
			@Condition("psdi.id in:itemIds"),
			@Condition("psdi.productSaleDisableRecord.id =:productSaleDisableRecordId"),
			@Condition("psdi.status.id =:status"),
			@Condition("psdi.synced =:synced") })
	List<ProductSaleDisableItem> findItems(
			@ParameterMap Map<String, Object> params,
			@Page Pagination pagination);

	@Save
	void save(ProductSaleDisableRecord record);

	@Save
	void saveItem(ProductSaleDisableItem item);

	@Query("from ProductSale where ps.outerId=?")
	List<ProductSale> getProductSale(String sapId);

	@Query(value = "UPDATE product_sale_disable_item SET status = 120004, is_sync = 0 " +
			"WHERE product_sale_disable_record =:recordId and status = 120002", sqlQuery = true, autoInfer = false, executeUpdate = true)
	int recoverItemsByRecord(@Parameter("recordId") Long recordId);
	
	@Query(value = "SELECT id is not null from product_sale_disable_item where product_sale_disable_record =:recordId and status = 120002", sqlQuery = true)
	boolean existStopedItem(@Parameter("recordId") Long recordId);

}
