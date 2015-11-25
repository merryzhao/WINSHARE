package com.winxuan.ec.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopColumn;
import com.winxuan.ec.model.shop.ShopColumnItem;
import com.winxuan.ec.model.shop.ShopLog;
import com.winxuan.ec.model.shop.ShopUsualCategory;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author xuan jiu dong
 * 
 */
public interface ShopDao {

	@Save
	void save(Shop shop);

	@Save
	void save(ShopLog shopLog);

	@Save
	void save(ShopUsualCategory shopUsualCategory);

	@Save
	void save(ShopColumn shopColumn);

	@Save
	void save(ShopColumnItem shopColumnItem);

	@SaveOrUpdate
	void saveOrUpdate(ShopColumn shopColumn);

	@Update
	void update(Shop shop);

	@Update
	void update(ShopColumnItem shopColumnItem);

	@Delete
	void delete(ShopColumnItem shopColumnItem);

	@Delete
	void delete(ShopColumn shopColumn);

	@Get
	Shop getById(Long id);

	@Get
	ShopColumn getShopColumn(Long id);

	@Get
	ShopColumnItem getShopColumnItem(Long id);

	@Query("select distinct s from Shop s ")
	@Conditions({ @Condition("s.id  =:sId"), @Condition("s.state in :state"),
			@Condition("s.state not in :noStates") })
	List<Shop> find(@ParameterMap Map<String, Object> params,
			@Page Pagination pagination);

	@Query("select distinct s from Shop s ")
	@Conditions({ @Condition("s.id  =:sId"),
			@Condition("s.endDate >=:limitedEndDate"),
			@Condition("s.state in :state"),
			@Condition("s.state not in :noStates") })
	List<Shop> find(@ParameterMap Map<String, Object> params);

	@Query("from ShopColumn as sc where sc.type.id <> 47004 ")
	@Conditions({ @Condition("sc.shop.id  =:sId"),
			@Condition("sc.shopcategory.id  =:scId") })
	@OrderBys({
		@OrderBy("sc.type.id asc,sc.index asc"),
		@OrderBy("sc.index desc")
	})
	List<ShopColumn> findShopColumnList(@ParameterMap Map<String, Object> params,@Order Short sort);

	@Query("from ProductSale as ps ")
	@Conditions({ @Condition("ps.shop.id=:shop"),
			@Condition("ps.saleStatus.id in :saleStatuses")})
	long shopProductCount(@ParameterMap Map<String, Object> parameters);

	// where and
	@Query("select distinct cc from CustomerComment cc left join cc.productSale ps")
	@Conditions({ @Condition("cc.productSale = ps.id"),
			@Condition("ps.shop.id =:shopId") })
	List<CustomerComment> findShopComment(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);

	@Query("from CustomerComment cc left join cc.productSale ps where cc.productSale = ps.id and ps.shop.id=?")
	long getCommentCount(long id);

	@Query(value = "select psr.rank from product_sale as ps,product_sale_rank as psr where psr.productsale=ps.id and ps.shop = ?", sqlQuery = true)
	List<Integer> getShopAllRank(Long id);

	/**
	 * 指定星级评分次数
	 * 解决shop=1慢查询的临时解决办法，shop=1不需要处理什么业务逻辑
	 * @param id
	 * @return
	 */
	@Query(value = "select ifnull(sum(psr.rank),0) as sumrank,count(psr.rank) as countrank,ifnull(sum(psr.rank)/count(psr.rank),0) as avgrank  " +
			" from product_sale as ps,product_sale_rank as psr where psr.productsale=ps.id and ps.shop =? and ps.shop !=1", sqlQuery = true)
	List<HashMap<String, Object>> getRank(Long id);

	/**
	 * 指定星级评分次数
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select sum(psr.rank) as sumrank,count(psr.rank) as countrank,sum(psr.rank)/count(psr.rank) as avgrank from" +
			" product_sale as ps,product_sale_rank as psr where psr.productsale=ps.id and ps.shop = ? and psr.rank = ?", sqlQuery = true)
	List<HashMap<String, Object>> getRank(Long id, int rank);

	@Query("select distinct p from ProductSale p left join p.shopCategoryList ps")
	@Conditions({ @Condition("p.shop.id=:shop"),
			@Condition("p.saleStatus.id in :saleStatuses"),
			@Condition("ps.id=:shopCategoryId") })
	List<ProductSale> findProductSaleByCategory(
			@ParameterMap Map<String, Object> parameters,@Page Pagination pagination);

	@Query("from ProductSale p left join p.shopCategoryList ps")
	@Conditions({ @Condition("p.shop.id=:shop"),
		@Condition("p.saleStatus.id in :saleStatuses"),
		@Condition("ps.id=:shopCategoryId") })
	long findProductSaleCount(@ParameterMap Map<String, Object> parameters);
	
	@Query("select distinct p from ProductSale p left join p.shopCategoryList ps")
	@Conditions({ @Condition("p.shop.id=:shop"),
			@Condition("ps.id=:shopCategoryId") })
	List<ProductSale> findProductSaleByCategory(
			@ParameterMap Map<String, Object> parameters,@MaxResults int size);

	@Query("from ShopColumn sc  where sc.shop.id =? order by sc.index desc limit 1")
	ShopColumn findMaxIndexShopColumnByShopId(Long id);

	@Query("from ShopColumn sc  where sc.shop.id =? and sc.index< ? order by sc.index desc limit 1")
	ShopColumn findUpShopColumn(Long shopId, int index);

	@Query("from ShopColumn sc  where sc.shop.id =? and sc.index> ? order by sc.index desc limit 1")
	ShopColumn findDownShopColumn(Long id, int index);

	@Query("from ShopColumn sc  where sc.shop.id =? and sc.type.id=?")
	ShopColumn findSubject(Long shopId, Long shopColumnTypeImg);

	
}
