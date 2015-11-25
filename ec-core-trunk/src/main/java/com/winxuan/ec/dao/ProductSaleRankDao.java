/*
 * @(#)CustomerComment.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.math.BigDecimal;
import java.util.List;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.ec.service.product.ProductSaleRankInfo;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 用户评分
 * @author  zhoujun
 * @version 1.0,2011-10-17
 */
public interface ProductSaleRankDao {

	@Save
	void save(ProductSaleRank productSaleRank);
	
	@Query("SELECT count(*) FROM ProductSaleRank c WHERE c.productSale=?")
	long findRankCount(ProductSale productSale);
	
	@Query(value="select avg(psr.rank) rankavg from product_sale_rank as psr where psr.productsale = ?",sqlQuery=true)
    BigDecimal getProductRankAverage(Long id);
	
	@Query(value="select new com.winxuan.ec.service.product.ProductSaleRankInfo(p.rank, COUNT(p.rank)) from ProductSaleRank p where p.productSale.id = ? group by p.rank")
	List<ProductSaleRankInfo> getProductRankInfos(Long id);
}

