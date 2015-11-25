package com.winxuan.ec.task.service.ebook;

import java.math.BigDecimal;

import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BusinessLog;

/**
 * 电子书商品
 * @author luosh
 *
 */
public interface ProductService {
	void validProduct(Book book,Book oldbook,BusinessLog businessLog, String discription);

	void createProduct(Book newbook, BigDecimal price);

	void updatePrice(BigDecimal price,Long bookId);
}
