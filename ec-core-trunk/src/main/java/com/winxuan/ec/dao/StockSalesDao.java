/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.StockSales;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-8
 */
public interface StockSalesDao {

	@Save
	void save(StockSales stockSales);
	
	@Get
	StockSales get(Long id);
	
	@Update
	void update(StockSales stockSales);
	
	@Query("from StockSales where orderId =? and productSaleId =? and dc = ? ")
	StockSales queryByOrderProductSaleAndDC(String ordreId,
			Long productSaleId, Code dc);
}
