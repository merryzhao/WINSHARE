/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.CollectionItem;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-6
 */
public interface CollectionItemDao {

	@Get
	CollectionItem get(Long id);
	
	@Update
	void update(CollectionItem collectionItem);
	
	@Save
	void save(CollectionItem collectionItem);
	
	@Query("from CollectionItem c where c.orderItem =? and c.productSale =? and p.status = ?")
	List<CollectionItem> getCollectionItems(Long orderItem,
			ProductSale productSale, Code status);
}
