package com.winxuan.ec.task.service.booktop;

import java.util.Date;
import java.util.List;

import com.winxuan.ec.task.model.booktop.BookTopCategory;
/**
 * 
 * @author sunflower
 *
 */
public interface BookTopService {

	/**
	 * 删除原有榜单数据
	 */
	void delete();

	/**
	 * 添加一级分类
	 * @param minNum
	 * @param saleNum 
	 */
	void addFirstCategory(int minNum, int saleNum);

	/**
	 * 新增二级分类
	 */
	void addSecondCategory();

	/**
	 * 新增一周商品
	 */
	void addAWeekProductSale();

	void addAMonthProductSale();

	void addThreeMonthProductSale();

	void addLastYearProductSale();

	void addNewFirstCategory(int minNum, int saleNum, Date date);

	/**
	 * 新增图书顶级分类
	 * @param topType 
	 */
	void addBookCategory(int topType);

	void addNewSecondCategory(Date date);

	void addAWeekProductNew(Date date);

	void addAMonthProductNew(Date date);

	void addThreeMonthProductNew(Date date);

	void addLastYearProductNew(Date date);

	List<BookTopCategory> getAllCategorys();

}
