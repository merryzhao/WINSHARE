/**
 * 
 */
package com.winxuan.ec.service.product;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductSaleDao;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author john
 *
 */
@Service("productSaleService")
@Transactional(rollbackFor = Exception.class)
public class ProductSaleServiceImpl implements ProductSaleService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6048906209662449105L;
	
	@InjectDao
    private ProductSaleDao productSaleDao;

	@Override
	public ProductSale findProductSale(Long productSaleId) {
		// TODO Auto-generated method stub
		return this.productSaleDao.findProductSale(productSaleId); 
	}
	
	@Override
	public List<ProductSale> findProductSales(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination) {
		return this.productSaleDao.findProductSales(parameters, pagination);
	}

	@Override
	public ProductSale getPsByP(Product product) {
		// TODO Auto-generated method stub
		return productSaleDao.getPsByP(product);
	}
	
}
