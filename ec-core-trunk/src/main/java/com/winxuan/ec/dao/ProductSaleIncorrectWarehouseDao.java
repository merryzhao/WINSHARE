package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.product.ProductSaleIncorrectWarehouse;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @author HideHai
 *
 */
public interface ProductSaleIncorrectWarehouseDao {

	@Get
	ProductSaleIncorrectWarehouse get(Long id);
	
	@Save
	void save(ProductSaleIncorrectWarehouse incorrectWarehouse);
	
	/**
	 * 不准确记录是否存在指定商品的未处理信息
	 * @param productSaleId
	 * @return
	 */
	@Query("FROM ProductSaleIncorrectWarehouse pw WHERE pw.change = 0 AND pw.productSale.id = ?")
	boolean existNotProcessedByProductSale(Long productSaleId);
	
	@Query(sqlQuery=true,value="select productsale from product_incorrect_warehouse where ischanged=0 ")
	List<Long> findAll();
}
