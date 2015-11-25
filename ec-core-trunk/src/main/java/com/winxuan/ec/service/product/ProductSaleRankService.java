package com.winxuan.ec.service.product;

import java.math.BigDecimal;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-17
 */
public interface ProductSaleRankService {
	 void save(ProductSaleRank productSaleRank);
	
	 long findRankCount(ProductSale productSale);
	 
	 BigDecimal getProductRankAverage(ProductSale productSale);
	 
	 ProductSaleRankRateBean getProductRankInfos(ProductSale productSale);	 
	 
}
