package com.winxuan.ec.service.booktop;

import java.util.List;

import com.winxuan.ec.model.booktop.BookTopCategory;
import com.winxuan.ec.model.booktop.BookTopProductSale;

/**
 * 
 * @author sunflower
 *
 */
public interface BookTopService {

	List<BookTopProductSale> queryTopProducts(int topType, Long category, int timeType,
			int page);

	List<BookTopCategory> queryCategorys(int topType, Long parent);

	long queryTopProductsNum(int topType, Long category, int timeType);

	boolean isBookTop(int timeType, Long category, int topSize, String productsale);

}
