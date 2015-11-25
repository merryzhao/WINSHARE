package com.winxuan.ec.task.service.booktop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.dao.booktop.BookTopDao;
import com.winxuan.ec.task.model.booktop.BookTopCategory;
/**
 * 
 * @author sunflower
 *
 */
@Service("booktopService")
public class BookTopServiceImpl implements BookTopService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private BookTopDao bookTopDao;

	@Override
	public void delete() {
		bookTopDao.deleteProducts();
		bookTopDao.deleteCategory();
	}

	@Override
	public void addFirstCategory(int minNum, int saleNum) {
		bookTopDao.addFirstCategory(minNum, saleNum);
	}

	@Override
	public void addSecondCategory() {
		List<BookTopCategory> firstCategorys = bookTopDao
				.getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				bookTopDao.addSecondCategory(firstCategory);
			}
		}
	}

	@Override
	public void addAWeekProductSale() {
		// 新增图书分类的一周畅销
		addAWeekProductSaleOfBook();
		// 新增图书一级分类的一周畅销
		addAWeekProductSaleOfFirstCategory();
		// 新增图书二级分类的一周畅销
		addAWeekProductSaleOfSecondCategory();
	}

	private void addAWeekProductSaleOfSecondCategory() {
		bookTopDao.addAWeekProductSaleOfSecondCategory();
	}

	private void addAWeekProductSaleOfFirstCategory() {

		bookTopDao.addAWeekProductSaleOfFirstCategory();
	}

	private void addAWeekProductSaleOfBook() {
		bookTopDao.addAWeekProductSaleOfBook();
	}

	@Override
	public void addAMonthProductSale() {
		// 新增图书分类的一月畅销
		addAMonthProductSaleOfBook();
		// 新增图书一级分类的一月畅销
		addAMonthProductSaleOfFirstCategory();
		// 新增图书二级分类的一月畅销
		addAMonthProductSaleOfSecondCategory();
	}

	private void addAMonthProductSaleOfSecondCategory() {
		bookTopDao.addAMonthProductSaleOfSecondCategory();
	}

	private void addAMonthProductSaleOfFirstCategory() {
		bookTopDao.addAMonthProductSaleOfFirstCategory();
	}

	private void addAMonthProductSaleOfBook() {
		bookTopDao.addAMonthProductSaleOfBook();
	}

	@Override
	public void addThreeMonthProductSale() {
		List<String> months = lastMonths(3);
		// 新增图书分类的三月畅销
		addThreeMonthProductSaleOfBook(months);
		// 新增图书一级分类的三月畅销
		addThreeMonthProductSaleOfFirstCategory(months);
		// 新增图书二级分类的三月畅销
		addThreeMonthProductSaleOfSecondCategory(months);
	}

	private void addThreeMonthProductSaleOfSecondCategory(List<String> months) {
		bookTopDao.addThreeMonthProductSaleOfSecondCategory(months);
	}

	private void addThreeMonthProductSaleOfFirstCategory(List<String> months) {
		bookTopDao.addThreeMonthProductSaleOfFirstCategory(months);
	}

	private List<String> lastMonths(int lastMonthNum) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		List<String> months = new ArrayList<String>();
		String s = new String(String.valueOf(year));
		if (month < 10) {
			s = s + "-0" + String.valueOf(month);
		} else {
			s = s + "-" + String.valueOf(month);
		}
		month--;
		months.add(s);
		lastMonthNum--;
		while (lastMonthNum > 0) {
			StringBuffer m = new StringBuffer();
			if (month < 1) {
				year = year - 1;
				month = 12;
				m.append(String.valueOf(year)).append("-")
						.append(String.valueOf(month));
			} else {
				m.append(String.valueOf(year));
				if (month < 10) {
					m.append("-0").append(String.valueOf(month));
				} else {
					m.append("-").append(String.valueOf(month));
				}
				month--;
			}
			months.add(m.toString());
			lastMonthNum--;
		}
		return months;
	}

	private void addThreeMonthProductSaleOfBook(List<String> months) {
		bookTopDao.addThreeMonthProductSaleOfBook(months);
	}

	@Override
	public void addLastYearProductSale() {
		List<String> months = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		year = year - 1;
		for (int i = 1; i <= 12; i++) {
			StringBuffer m = new StringBuffer();
			m.append(String.valueOf(year)).append("-");
			if (i < 10) {
				m.append("0");
			}
			m.append(String.valueOf(i));
			months.add(m.toString());
		}
		// 新增图书分类的头年畅销
		addLastYearProductSaleOfBook(months);
		// 新增图书一级分类的头年畅销
		addLastYearProductSaleOfFirstCategory(months);
		// 新增图书二级分类的头年畅销
		addLastYearProductSaleOfSecondCategory(months);
	}

	private void addLastYearProductSaleOfSecondCategory(List<String> months) {
		bookTopDao.addLastYearProductSaleOfSecondCategory(months);
	}

	private void addLastYearProductSaleOfFirstCategory(List<String> months) {
		bookTopDao.addLastYearProductSaleOfFirstCategory(months);
	}

	private void addLastYearProductSaleOfBook(List<String> months) {
		bookTopDao.addLastYearProductSaleOfBook(months);
	}

	@Override
	public void addNewFirstCategory(int minNum, int saleNum, Date date) {
		bookTopDao.addNewFirstCategory(minNum, saleNum, date);
	}

	@Override
	public void addBookCategory(int topType) {
		bookTopDao.addBookCategory(topType);
	}

	@Override
	public void addNewSecondCategory(Date date) {
		List<BookTopCategory> firstCategorys = bookTopDao
				.getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				bookTopDao.addNewSecondCategory(firstCategory, date);
			}
		}
	}

	@Override
	public void addAWeekProductNew(Date date) {
		// 新增图书分类的一周畅销
		addAWeekProductNewOfBook(date);
		// 新增图书一级分类的一周畅销
		addAWeekProductNewOfFirstCategory(date);
		// 新增图书二级分类的一周畅销
		addAWeekProductNewOfSecondCategory(date);
	}

	private void addAWeekProductNewOfSecondCategory(Date date) {
		bookTopDao.addAWeekProductNewOfSecondCategory(date);
	}

	private void addAWeekProductNewOfFirstCategory(Date date) {
		bookTopDao.addAWeekProductNewOfFirstCategory(date);
	}

	private void addAWeekProductNewOfBook(Date date) {
		bookTopDao.addAWeekProductNewOfBook(date);
	}

	@Override
	public void addAMonthProductNew(Date date) {
		// 新增图书分类的一月畅销
		addAMonthProductNewOfBook(date);
		// 新增图书一级分类的一月畅销
		addAMonthProductNewOfFirstCategory(date);
		// 新增图书二级分类的一月畅销
		addAMonthProductSaleOfSecondCategory(date);
	}

	private void addAMonthProductSaleOfSecondCategory(Date date) {
		bookTopDao.addAMonthProductSaleOfSecondCategory(date);
	}

	private void addAMonthProductNewOfFirstCategory(Date date) {
		bookTopDao.addAMonthProductNewOfFirstCategory(date);
	}

	private void addAMonthProductNewOfBook(Date date) {
		bookTopDao.addAMonthProductNewOfBook(date);
	}

	@Override
	public void addThreeMonthProductNew(Date date) {
		List<String> months = lastMonths(3);
		// 新增图书分类的三月畅销
		addThreeMonthProductNewOfBook(months, date);
		// 新增图书一级分类的三月畅销
		addThreeMonthProductNewOfFirstCategory(months, date);
		// 新增图书二级分类的三月畅销
		addThreeMonthProductNewOfSecondCategory(months, date);
	}

	private void addThreeMonthProductNewOfSecondCategory(List<String> months,
			Date date) {
		bookTopDao.addThreeMonthProductNewOfSecondCategory(months, date);
	}

	private void addThreeMonthProductNewOfFirstCategory(List<String> months,
			Date date) {
		bookTopDao.addThreeMonthProductNewOfFirstCategory(months, date);
	}

	private void addThreeMonthProductNewOfBook(List<String> months, Date date) {

		bookTopDao.addThreeMonthProductNewOfBook(months, date);
	}

	@Override
	public void addLastYearProductNew(Date date) {
		List<String> months = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		year = year - 1;
		for (int i = 1; i <= 12; i++) {
			StringBuffer m = new StringBuffer();
			m.append(String.valueOf(year)).append("-");
			if (i < 10) {
				m.append("0");
			}
			m.append(String.valueOf(i));
			months.add(m.toString());
		}
		// 新增图书分类的头年畅销
		addLastYearProductNewOfBook(months, date);
		// 新增图书一级分类的头年畅销
		addLastYearProductNewOfFirstCategory(months, date);
		// 新增图书二级分类的头年畅销
		addLastYearProductNewOfSecondCategory(months, date);
	}

	private void addLastYearProductNewOfSecondCategory(List<String> months,
			Date date) {
		bookTopDao.addLastYearProductNewOfSecondCategory(months, date);
	}

	private void addLastYearProductNewOfFirstCategory(List<String> months,
			Date date) {
		bookTopDao.addLastYearProductNewOfFirstCategory(months, date);
	}

	private void addLastYearProductNewOfBook(List<String> months, Date date) {
		bookTopDao.addLastYearProductNewOfBook(months, date);
	}

	@Override
	public List<BookTopCategory> getAllCategorys() {
		return bookTopDao.getAllCategorys();
	}

}
