/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrCumulativeReceive;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @author monica
 *
 */
public interface MrCumulativeReceiveDao {
	
	@Merge
	void update(MrCumulativeReceive mrCumulativeReceive);

	@Query("from MrCumulativeReceive mcr where mcr.productSale=? and mcr.dc=?")
	boolean isExistByProductSaleAndDc(ProductSale productSale, Code dc);
	
	@Query("from MrCumulativeReceive mcr where mcr.productSale=? and mcr.dc=?")
	MrCumulativeReceive findByProductSaleAndDc(ProductSale productSale, Code dc);
	
	
}
