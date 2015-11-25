package com.winxuan.ec.service.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopColumn;
import com.winxuan.ec.model.shop.ShopColumnItem;
import com.winxuan.ec.model.shop.ShopRank;
import com.winxuan.ec.model.shop.ShopUsualCategory;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author xuan jiu dong
 * 
 */
public interface ShopService {

	/**
	 * 获取店铺信息
	 * 
	 * @param id
	 * @return
	 */
	Shop get(Long id);

	/**
	 * 获取店铺栏目的单个产品信息
	 * 
	 * @param scp
	 * @return
	 */
	ShopColumnItem getShopColumnItem(long id);

	/**
	 * 保存店铺信息
	 * 
	 * @param shop
	 * @param operator
	 */
	void save(Shop shop, User operator);

	/**
	 * 保存店铺的常用分类
	 * 
	 * @param uc
	 */
	void saveUsualCagtegory(ShopUsualCategory uc);

	/**
	 * 保存店铺陈列信息
	 * 
	 * @param shopColumn
	 */
	void save(ShopColumn shopColumn);

	/**
	 * 保存店铺栏目
	 * 
	 * @param shopColumn
	 */
	void save(ShopColumnItem shopColumnItem);

	/**
	 * 修改店铺陈列信息
	 * 
	 * @param shopColumn
	 */
	void update(ShopColumnItem shopColumnItem);

	/**
	 * 删除店铺商品
	 * 
	 * @param shopColumn
	 */
	void delete(ShopColumnItem shopColumnItem);

	/**
	 * 修改店铺信息
	 * 
	 * @param shop
	 * @param operator
	 */
	void update(Shop shop, User operator);

	/**
	 * 查询店铺
	 * 
	 * @param params
	 * @param pagination
	 * @return
	 */
	List<Shop> find(Map<String, Object> params, Pagination pagination);

	/**
	 * 获取店铺栏目
	 * 
	 * @param shop
	 * 
	 * @return list
	 */
	List<ShopColumn> find(Map<String, Object> params);

	/**
	 * 查询所有有效时间大于当前时间且为非注销状态的店铺
	 * @return
	 */
	List<Shop> findAll();
	
	void saveOrUpdate(ShopColumn shopColumn);
	
	/**
	 * 删除店铺栏目
	 * @param id
	 */
	void deleteShopColumn(Long id);
	
	/**
	 * 上移
	 * @param shopColumn
	 */
	void moveUp(ShopColumn shopColumn);
	
	/**
	 * 下移
	 * @param shopColumn
	 */
	void moveDown(ShopColumn shopColumn);
	
	/**
	 * 根据id查询shopColumn
	 * @param id
	 * @return
	 */
	ShopColumn getShopColumn(Long id);
	
	/**
	 * 获取店铺下所有商品数量
	 * @return 
	 */
	long shopProductCount(Map<String, Object> parameters);
	
	/**
	 * 获取店铺下所有的评论
	 * @param shop
	 * @return
	 */
	List<CustomerComment> findAllShopComment(Map<String, Object> parameters,Pagination pagination);
	
	/**
	 * 获取店铺下所有评论的数量
	 * @param shop
	 * @return
	 */
	long getCommentCount(Shop shop);
	
	/**
	 * 获取店铺下所有的评论
	 * @param shop
	 * @return
	 */
	List<Integer> getShopRank(Shop shop);
	
	/**
	 * 指定星级评分
	 * @param id
	 * @return sumrank 总分  countrank总人数	avgrank平均分
	 */
	@Deprecated
	List<HashMap<String, Object>> getRank(Shop shop);
	
	/**
	 * 获取店铺评分：总分、平均分、评分数
	 * @param shop
	 * @return
	 */
	ShopRank convertToShopRank(Shop shop);
	
	/**
	 * 指定星级评分次数
	 * @param shop rank
	 * @return sumrank 总分  countrank总人数	avgrank平均分
	 */
	@Deprecated
	List<HashMap<String, Object>> getRank(Shop shop,int rank);

	/**
	 * 指定星级评分次数
	 * @param shop rank
	 * @return sumrank 总分  countrank总人数	avgrank平均分
	 */
	ShopRank convertToShopRank(Shop shop, int rank);
	
	/**
	 * 获取店铺分类下的所有商品
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<ProductSale> findProductSaleByCategory(Map<String, Object> parameters,int size);
	/**
	 * 获取店铺分类下的所有商品
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<ProductSale> findProductSaleByCategory(Map<String, Object> parameters,Pagination pagination);

	ShopColumn findSubject(Long shopId);
	
	Map<String, Object> serviceInfo(Shop shop);
	
	/**
	 * 根据店铺id、商品销售状态和商品分类id查询商品数量
	 * @param parameters
	 * @return
	 */
	long findProductSaleCount(Map<String, Object> parameters);
	
}
