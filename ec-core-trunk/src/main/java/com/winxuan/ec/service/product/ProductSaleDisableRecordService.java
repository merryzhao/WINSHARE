package com.winxuan.ec.service.product;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleDisableItem;
import com.winxuan.ec.model.product.ProductSaleDisableRecord;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * @description: TODO
 *
 * @createtime: 2013-12-10 下午5:34:54
 *
 * @author zenghua
 *
 * @version 1.0
 */

public interface ProductSaleDisableRecordService {
	
	List<ProductSaleDisableRecord> find(@ParameterMap Map<String,Object> params, @Page Pagination pagination, @Order short index);
	
	void save(ProductSaleDisableRecord record);
	
	void saveItem(ProductSaleDisableItem item);

	ProductSaleDisableRecord upload(List<ProductSale> productSales, Employee operator, String remark);
	
	List<ProductSaleDisableRecord> findByUserAndStatus(Employee operator,
			Code status);

	List<ProductSaleDisableItem> findItems(Map<String, Object> params,
			Pagination pagination);
	
	/**
	 * 批量上传停用商品后，异步的方式更新商品商品销售状态
	 * @param productSaleDisableRecord
	 * @param operator
	 * @return 
	 */
	void asyncProductSaleStop(final ProductSaleDisableRecord productSaleDisableRecord, final Employee operator);
	
	/**
	 * 根据recordid批量释放商品，异步方式更新商品销售状态
	 * @param productSaleDisableRecord
	 * @param operator
	 */
	void asyncProductSaleRecover(final ProductSaleDisableRecord productSaleDisableRecord, final Employee operator);
	
	ProductSaleDisableRecord get(Long id);

	int recoverItemsByRecord(ProductSaleDisableRecord record);

	void update(ProductSaleDisableRecord record);

	ProductSaleDisableItem getItem(Long itemId);

	void updateItem(ProductSaleDisableItem item);

	boolean existStopedItem(ProductSaleDisableRecord record);

}
