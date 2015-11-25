/**
 * 
 */
package com.winxuan.ec.service.product;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author john
 *
 */
public interface ProductSaleService {

	ProductSale findProductSale(Long productSaleId);

	List<ProductSale> findProductSales(Map<String, Object> parameters,
			Pagination pagination);
	
	ProductSale getPsByP(Product product);
}
