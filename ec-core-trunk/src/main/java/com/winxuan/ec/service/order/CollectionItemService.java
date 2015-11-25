/**
 * 
 */
package com.winxuan.ec.service.order;

import java.util.List;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.CollectionItem;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.product.ProductSale;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-11
 */
public interface CollectionItemService {

	List<CollectionItem> getCollectionItems(Long orderItem,ProductSale productSale,Code status);
	
	CollectionItem get(Long id);
	
	void save(CollectionItem collectionItem);
	
	void update(CollectionItem collectionItem);
	
	void createCollectionItem(Order order,OrderDistributionCenter odc);
}
