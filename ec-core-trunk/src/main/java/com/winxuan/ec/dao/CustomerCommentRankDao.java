package com.winxuan.ec.dao;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-24
 */
public interface CustomerCommentRankDao {
	@Query("SELECT count(*) FROM CustomerCommentRank c WHERE c.productSale=?")
	long findRankCount(ProductSale productSale);
}
