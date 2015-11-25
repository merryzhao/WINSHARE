package com.winxuan.ec.service.booktop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.BookTopDao;
import com.winxuan.ec.model.booktop.BookTopCategory;
import com.winxuan.ec.model.booktop.BookTopProductSale;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 *
 */
@Service("bookTopService")
@Transactional(rollbackFor = Exception.class)
public class BookTopServiceImpl implements BookTopService {

	private static final int MAX_PAGE_SIZE = 20;
	
	@InjectDao
	private BookTopDao bookTopDao;

	@Override
	public List<BookTopProductSale> queryTopProducts(int topType,
			Long category, int timeType, int page) {

		Pagination pagination = new Pagination();
		pagination.setCurrentPage(page);
		pagination.setPageSize(MAX_PAGE_SIZE);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("topType", Integer.valueOf(topType));
		params.put("timeType", Integer.valueOf(timeType));
		params.put("category", category);

		return bookTopDao.queryTopProducts(pagination, params,(short)0);
		
	}

	@Override
	public List<BookTopCategory> queryCategorys(int topType, Long parent) {
		BookTopCategory btc = bookTopDao.queryCategory(topType, parent);
		return bookTopDao.queryBookTopCategorys(topType,btc.getId());
	}

	@Override
	public long queryTopProductsNum(int topType, Long category, int timeType) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("topType", Integer.valueOf(topType));
		params.put("timeType", Integer.valueOf(timeType));
		params.put("category", category);
		return bookTopDao.queryTopProductsNum(params);
	}

	@Override
	public boolean isBookTop(int timeType, Long category, int topSize, String productsale) {
		 List<String> psid =  bookTopDao.isBookTop(timeType,category,topSize);
		 return psid.contains(productsale);
	}

}
