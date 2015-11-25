package com.winxuan.ec.service.shop;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopServiceNo;
import com.winxuan.ec.model.shop.ShopServiceTime;
import com.winxuan.framework.pagination.Pagination;
/**
 * 
 * @author df.rsy
 *
 */
public interface ShopServiceNoService {
	
 
	/**
	 * 获取单个ShopServiceNo对象
	 * @param  
	 * @return
	 */
	ShopServiceNo get(Long id);

	/**
	 * 保存或更新ShopServiceNo
	 * @param shopServiceNo
 	 */
	void saveOrupdate(ShopServiceNo shopServiceNo);
	
	/**
	 * 保存ShopServiceNo
	 * @param shopServiceNo
 	 */
	void save(ShopServiceNo shopServiceNo);
   	
	/**
	 * 删除
	 * @param shopServiceNo
	 */
	void delete(ShopServiceNo shopServiceNo);
	
	/**
	 * 查询
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<ShopServiceNo> find(Map<String, Object> parameters,Pagination pagination);

	/**
	 * 查询店铺服务时间信息
	 * @param shop
	 * @return
	 */
	List<ShopServiceTime> find(Shop shop);

	/**
	 * 保存
	 * @param shopServiceTime
	 */
	void save(ShopServiceTime shopServiceTime);

	/**
	 * 更新
	 * @param shopServiceTime
	 */
	void update(ShopServiceTime shopServiceTime);
	
}
