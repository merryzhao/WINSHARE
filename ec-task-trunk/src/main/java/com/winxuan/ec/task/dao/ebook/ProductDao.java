package com.winxuan.ec.task.dao.ebook;

import java.math.BigDecimal;
import java.util.List;

import com.winxuan.ec.task.model.ebook.EProduct;
import com.winxuan.ec.task.model.ebook.ProductItem;
import com.winxuan.ec.task.model.ebook.ProductProcessItem;

/**
 * 电子书商品DAO接口
 * @author luosh
 *
 */
public interface ProductDao {

	List<EProduct> findProduct(Long bookId);
	void save(ProductProcessItem productProcessItem);
	void save(EProduct product);
	void save(ProductItem productItem);
	void update(EProduct product);
	void updatePrice(BigDecimal price,EProduct product);

}
