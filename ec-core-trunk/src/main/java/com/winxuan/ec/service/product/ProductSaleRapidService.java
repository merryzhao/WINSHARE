/**
 * 
 */
package com.winxuan.ec.service.product;

import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRapid;

/**
 * @author zhousl
 *
 * 2013-9-3
 */
public interface ProductSaleRapidService {

	void save(ProductSaleRapid productSaleRapid) throws ProductSaleStockException;
	
	void update(ProductSaleRapid productSaleRapid) throws ProductSaleStockException;
	 
	ProductSaleRapid getByProductSale(ProductSale productSale);
}
