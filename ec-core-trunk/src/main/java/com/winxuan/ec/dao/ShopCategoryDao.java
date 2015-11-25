package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopCategory;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author xuan jiu dong
 * 
 */
public interface ShopCategoryDao {

	@Query("from ShopCategory s where s.shop =? and s.parent is null")
	ShopCategory findByShop(Shop shop);

	@Get
	ShopCategory get(Long id);

	@Save
	void save(ShopCategory shopCategory);

	@Update
	void update(ShopCategory shopCategory);

	@Delete
	void delete(ShopCategory shopCategory);

	/**
	 * 查询未分类的商品
	 * 
	 * 
	 * @return
	 */
	@Query("Select distinct ps from ProductSale ps")
	@Conditions({ @Condition("ps.seller.shop.id=:shopid"),
			@Condition("ps.hasShopCategory=:flag")
	})
	List<ProductSale> getUnCategory(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	
	

}
