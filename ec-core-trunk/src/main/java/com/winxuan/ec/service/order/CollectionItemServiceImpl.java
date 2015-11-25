/**
 * 
 */
package com.winxuan.ec.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CollectionItemDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcPriority;
import com.winxuan.ec.model.order.CollectionItem;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-11
 */
@Service("collectionItemService")
@Transactional(rollbackFor = Exception.class)
public class CollectionItemServiceImpl implements CollectionItemService {
	
	@Autowired
	private ProductSaleStockService productSaleStockService;
	
	@InjectDao
	private CollectionItemDao collectionItemDao;
	@Override
	public List<CollectionItem> getCollectionItems(Long orderItem,
			ProductSale productSale, Code status) {
		return null;
	}
	
	@Override
	public CollectionItem get(Long id) {
		return collectionItemDao.get(id);
	}
	
	@Override
	public void save(CollectionItem collectionItem) {
		collectionItemDao.save(collectionItem);		
	}
	
	@Override
	public void update(CollectionItem collectionItem) {
		collectionItemDao.update(collectionItem);		
	}

	
	
	@Override
	public void createCollectionItem(Order order,OrderDistributionCenter odc) {
		Map<ProductSale,Integer> map  =  collExculdeDestDc(order,odc);
		if(order.isCollection() && odc.isCanColl() && odc.isNeedColl()){ //能不能集货
			List<DcPriority> dps = odc.getDps(); 
			for(DcPriority dp :dps){
				boolean needColl = true;
				for(OrderItem item : order.getItemList()){
					ProductSale productSale  = item.getProductSale();
					int dcCollStock = item.getProductSaleStockVoAvailableQuantity(dp.getLocation());
					int needStock = map.get(productSale)!=null ? map.get(productSale) : MagicNumber.ZERO; 
					if(needStock > MagicNumber.ZERO){
					int canCollStock = needStock - dcCollStock >= MagicNumber.ZERO ? dcCollStock : needStock - dcCollStock;
					if(canCollStock != 0){
						needStock = needStock - canCollStock;
						CollectionItem collectionItem = new CollectionItem();
						map.put(productSale, needStock);
						collectionItem.setCollectQuantity(canCollStock);
						collectionItem.setToDc(odc.getDcDest());
						collectionItem.setFromDc(new Code(dp.getLocation()));
						collectionItem.setOrderId(order.getId());
						collectionItem.setProductSale(productSale);
						collectionItem.setOrderItem(item);
						collectionItem.setStatus(new Code(Code.ORDER_COLLECT_WAITING));
						collectionItemDao.save(collectionItem);
					}
						if(needStock > 0){
							needColl = false;
						}					
					}
				}
				if(needColl){
					break;
				}
			}
		}
	}
	
	/**
	 * 除开目标仓还需要集货数量
	 * @param o
	 * @param odc
	 * @return  Map<ProductSale,Integer> Integer为还需要集货数量
	 */
	private Map<ProductSale,Integer> collExculdeDestDc(Order order,OrderDistributionCenter odc){
		Map<ProductSale,Integer> map  =  new HashMap<ProductSale,Integer> ();
		for(OrderItem item : order.getItemList()){
			int dcDest = item.getProductSaleStockVoAvailableQuantity(odc.getDcDest().getId());
			int needCollNum = item.getPurchaseQuantity() - dcDest > 0 ? item.getPurchaseQuantity() - dcDest : 0;
			map.put(item.getProductSale(), needCollNum);
		}
		return map;
	}

}
