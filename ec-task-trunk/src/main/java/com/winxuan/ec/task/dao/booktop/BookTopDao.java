package com.winxuan.ec.task.dao.booktop;

import java.util.Date;
import java.util.List;

import com.winxuan.ec.task.model.booktop.BookTopCategory;

/**
 * 
 * @author sunflower
 *
 */
public interface BookTopDao {

	void deleteProducts();

	void deleteCategory();

	void addFirstCategory(int minNum, int saleNum);

	List<BookTopCategory> getFirstCategory(int topType);
	
	List<BookTopCategory> getSecondCategory(BookTopCategory firstCategory, int topType);
	
	BookTopCategory getBookTopCategory(long category, int topType);

	void addSecondCategory(BookTopCategory firstCategory);

	void addAWeekProductSaleOfBook();

	void addAWeekProductSaleOfFirstCategory();

	void addAWeekProductSaleOfSecondCategory();

	void addAMonthProductSaleOfBook();

	void addAMonthProductSaleOfFirstCategory();

	void addAMonthProductSaleOfSecondCategory();

	void addThreeMonthProductSaleOfBook(List<String> months);

	void addThreeMonthProductSaleOfFirstCategory(List<String> months);

	void addThreeMonthProductSaleOfSecondCategory(List<String> months);

	void addLastYearProductSaleOfBook(List<String> months);

	void addLastYearProductSaleOfFirstCategory(List<String> months);

	void addLastYearProductSaleOfSecondCategory(List<String> months);

	void addBookCategory(int topType);

	void addNewFirstCategory(int minNum, int saleNum, Date date);

	void addNewSecondCategory(BookTopCategory firstCategory, Date date);

	void addAWeekProductNewOfBook(Date date);

	void addAWeekProductNewOfFirstCategory(Date date);

	void addAWeekProductNewOfSecondCategory(Date date);

	void addAMonthProductNewOfBook(Date date);

	void addAMonthProductNewOfFirstCategory(Date date);

	void addAMonthProductSaleOfSecondCategory(Date date);

	void addThreeMonthProductNewOfBook(List<String> months, Date date);

	void addThreeMonthProductNewOfFirstCategory(List<String> months, Date date);

	void addThreeMonthProductNewOfSecondCategory(List<String> months, Date date);

	void addLastYearProductNewOfBook(List<String> months, Date date);

	void addLastYearProductNewOfFirstCategory(List<String> months, Date date);

	void addLastYearProductNewOfSecondCategory(List<String> months, Date date);

	List<BookTopCategory> getAllCategorys();

}
