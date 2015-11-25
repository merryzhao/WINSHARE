package com.winxuan.ec.front.controller.booktop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.booktop.BookTopCategory;
import com.winxuan.ec.model.booktop.BookTopProductSale;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.service.booktop.BookTopService;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.product.ProductSaleRankService;

/**
 * 
 * @author sunflower
 * 
 */
@Controller
@RequestMapping(value = "/booktop")
public class BookTopController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	BookTopService bookTopService;
	
	@Autowired
	CustomerCommentService commentService;
	
	@Autowired
	ProductSaleRankService productSaleRankService;

	@RequestMapping(value = "/{topType}/{category}/{timeType}/{page}", method = RequestMethod.GET)
	public ModelAndView bookNewTop(@PathVariable("topType") int topType,
			@PathVariable("category") Long category,
			@PathVariable("timeType") int timeType,
			@PathVariable("page") int page) {

		ModelAndView modelAndView = new ModelAndView("/booktop/index");
		modelAndView.addObject("location", getLocation(category, topType));
		long pageNum = bookTopService.queryTopProductsNum(topType, category, timeType);
		pageNum = (long) Math.ceil(pageNum*1.0/20);
		List<BookTopProductSale> bookTopProductSales = bookTopService
				.queryTopProducts(topType, category, timeType, page);
		//取评论条数，不能通过list.length的方式
		if(CollectionUtils.isNotEmpty(bookTopProductSales)){
			for(BookTopProductSale topProductSale : bookTopProductSales){
				topProductSale.setCommentCount(commentService.getCountByProductSale(topProductSale.getProductsale()));
				topProductSale.setAvgRank(productSaleRankService.getProductRankAverage(topProductSale.getProductsale()));
			}
		}
		modelAndView.addObject("bookTopProductSales", bookTopProductSales);
		modelAndView.addObject("topType", topType);
		modelAndView.addObject("timeType", timeType);
		modelAndView.addObject("page", page);
		modelAndView.addObject("pageNum", pageNum);
		List<BookTopCategory> bookTopCategorys = bookTopService.queryCategorys(
				topType, BookTopCategory.CATEGORY_BOOK);
		modelAndView.addObject("bookTopCategorys", bookTopCategorys);
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy");
		// 取得时分
		int thisYear = Integer.parseInt(df.format(date));
		int lastYear = thisYear-1;
		modelAndView.addObject("thisYear", thisYear);
		modelAndView.addObject("lastYear", lastYear);
		return modelAndView;
	}

	private String getLocation(Long category, int type) {

		StringBuffer location = new StringBuffer();
		location.append("<a href=\"http://www.winxuan.com\">文轩网</a> &gt;   <a href=\"http://www.winxuan.com/book\">图书</a>");
		if (type == BookTopCategory.TOP_TYPE_SALE) {
			if (category == 1) {
				location.append(" &gt;   ").append("图书畅销榜");
			} else {
				Category c = categoryService.get(category);
				location.append(" &gt;   ")
						.append("<a href=\"http://www.winxuan.com/booktop/0/1/1/1\">图书畅销榜</a>");
				Category parent = c.getParent();
				if (parent.getId() != 1) {
					location.append(" &gt;   ")
							.append("<a href=\"http://www.winxuan.com/booktop/0/")
							.append(parent.getId()).append("/1/1\">")
							.append(parent.getName()).append("</a>");
				}
				location.append(" &gt;   ").append(c.getName());
			}
		} else if (type == BookTopCategory.TOP_TYPE_NEW) {

			if (category == 1) {
				location.append(" &gt;   ").append("新书榜");
			} else {
				Category c = categoryService.get(category);
				location.append(" &gt;   ")
						.append("<a href=\"http://www.winxuan.com/booktop/1/1/1/1\">新书榜</a>");
				Category parent = c.getParent();
				if (parent.getId() != 1) {
					location.append(" &gt;   ")
							.append("<a href=\"http://www.winxuan.com/booktop/1/")
							.append(parent.getId()).append("/1/1\">")
							.append(parent.getName()).append("</a>");
				}
				location.append(" &gt;   ").append(c.getName());
			}
		}

		return location.toString();
	}

}
