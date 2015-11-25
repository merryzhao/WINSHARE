package com.winxuan.ec.service.shop;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.UpdateShopCategoryException;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.ProductCount;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopCategory;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author xuan jiu dong 
 *
 */
public interface ShopCategoryService {
	
	
	/**
	 * 获取店铺的分类树
	 * @param shop 店铺
	 * @return 
	 */
	ShopCategory get(Shop shop);
	
	/**
	 * 获取单个ShopCategory对象
	 * @param ShopCateGoryId
	 * @return
	 */
	ShopCategory get(Long id);

	/**
	 * 添加
	 * @param shopCategory
	 * @param shop
	 */
	void save(ShopCategory shopCategory,Shop shop);
	
	/**
	 * 更新
	 * @param shopCategory
	 */
	void update(ShopCategory shopCategory);
	
	/**
	 * 修改店铺shop的店铺分类
	 * @param shop
	 * @param addList  新增的分类节点
	 * @param updateList  修改过的分类节点
	 * @param removeList  已经删除的分类节点
	 * @throws UpdateShopCategoryException
	 */
	void update(Shop shop,List<ShopCategory> addList,List<ShopCategory> updateList,List<ShopCategory> removeList) throws UpdateShopCategoryException;
	
	/**
	 * 删除
	 * @param shopCategory
	 */
	void delete(ShopCategory shopCategory) throws UpdateShopCategoryException;
	
	
	/**
	 * 获取商品的分类数量
	 * 
	 * @param scid
	 *            商品id
	 * @return
	 */
	long getPrductTypeCount(ProductSale productSale);
	
	/**
	 * 获取指定类别下的商品数量
	 * @return 
	 */
	long getPrductCount(ShopCategory shopCategory);
	
	/**
	 * 获取上架的商品数量
	 * @return 
	 */
	long getOnshelfProductCount(ShopCategory shopCategory);
	
	
	/**
	 * 商品数
	 * 上架的商品数量/分类的商品数量
	 * @return productCount
	 */
	ProductCount getShopCount(ShopCategory shopCategory);
	
	/**
	 * 获取未分类的商品
	 * @param parameters<br/>
	 * 店铺id shopid<br/>
	 * 
	 * @param pagination
	 * @return
	 */
	List<ProductSale> getUnCategory(Map<String, Object> parameters,Pagination pagination);
	
}
