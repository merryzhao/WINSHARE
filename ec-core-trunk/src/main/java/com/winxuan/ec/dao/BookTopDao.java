package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.booktop.BookTopCategory;
import com.winxuan.ec.model.booktop.BookTopProductSale;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 *
 */
public interface BookTopDao {

	@Query("SELECT btps FROM BookTopProductSale btps")
	@Conditions({ @Condition(" btps.bookTopCategory.topType = :topType"),
			@Condition(" btps.bookTopCategory.category.id = :category"),
			@Condition(" btps.timeType = :timeType") })
	@OrderBys({ @OrderBy(" btps.sale desc") })
	List<BookTopProductSale> queryTopProducts(@Page Pagination pagination,
			@ParameterMap Map<String, Object> params, @Order short index);

	@Query("select btc from BookTopCategory btc where btc.topType=? and btc.parent.id=? order by btc.num desc")
	List<BookTopCategory> queryBookTopCategorys(int topType, Long parent);

	@Query("select btc from BookTopCategory btc where btc.topType=? and btc.category.id=? order by btc.num desc")
	BookTopCategory queryCategory(int topType, Long parent);

	@Query("SELECT btps FROM BookTopProductSale btps")
	@Conditions({ @Condition(" btps.bookTopCategory.topType = :topType"),
			@Condition(" btps.bookTopCategory.category.id = :category"),
			@Condition(" btps.timeType = :timeType") })
	long queryTopProductsNum(@ParameterMap Map<String, Object> params);
	
	@Query(sqlQuery=true,value="select bs.productsale from book_top_product_sale bs," +
			"book_top_category bc where bc.id = bs.category and bs.time_type = ? and bc.category = ? "+
			"ORDER BY sale desc limit ?")
	List<String> isBookTop(int timeType, Long category, int topSize);

}
