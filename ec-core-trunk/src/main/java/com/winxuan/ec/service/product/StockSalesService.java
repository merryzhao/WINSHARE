/**
 * 
 */
package com.winxuan.ec.service.product;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.StockSales;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-8
 */
public interface StockSalesService {

	void save(StockSales stockSales);
	
	void update(StockSales stockSales);
	
	StockSales get(Long id );
	
	StockSales get(String orderId,Long productSaleId,Code dc);
		
}
