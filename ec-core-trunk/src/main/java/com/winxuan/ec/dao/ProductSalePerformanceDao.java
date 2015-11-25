package com.winxuan.ec.dao;

import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-19
 */
public interface ProductSalePerformanceDao {
	
	@SaveOrUpdate
	void saveOrupdate(ProductSalePerformance productSalePerformance);
	
}
