/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRapid;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @author zhousl
 *
 * 2013-9-3
 */
public interface ProductSaleRapidDao {

	@Save
	void save(ProductSaleRapid productSaleRapid);
	
	@Update
	void update(ProductSaleRapid productSaleRapid);
	
	@Query("from ProductSaleRapid psr")
	@Conditions({ 
		@Condition("psr.productSale=:productSale")
		})
	ProductSaleRapid getByProductSale(@Parameter("productSale")ProductSale productSale);
}
