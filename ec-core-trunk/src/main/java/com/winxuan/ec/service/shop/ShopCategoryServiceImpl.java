package com.winxuan.ec.service.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.dao.ShopCategoryDao;
import com.winxuan.ec.dao.ShopDao;
import com.winxuan.ec.exception.UpdateShopCategoryException;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.ProductCount;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopCategory;
import com.winxuan.ec.model.shop.ShopColumn;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author xuan jiu dong
 * 
 */
@Service("shopCategoryService")
@Transactional(rollbackFor = Exception.class)
public class ShopCategoryServiceImpl implements ShopCategoryService {

	/**
	 * 分类标识
	 */
	private static final String FLAG ="flag";
	
	/**
	 * 错误信息
	 */
	private static final String ERROR_INFO = "分类或其子分类下有商品，不能直接删除！";
	
	
	/**
	 * 查询分类下的商品数量
	 */
	private static final String QUERY_CATEGORY_PRODUCTNUM_SQL="select count(1) from product_sale_shopcategory  pss where pss.shopcategory = ?";

	/**
	 * 查询指定分类,上架的商品数量
	 */
	private static final String QUERY_CATEGORY_ONSHELFPRODUCTNUM_SQL = 
			"Select count(1) from product_sale_shopcategory pss,product_sale ps where pss.productsale=ps.id and ps.salestatus=13002 and pss.shopcategory=?";

	/**
	 * 查询商品的店铺分类数量
	 */
	private static final String QUERY_PRODUCT_CATEGORYNUM_SQL="select count(1) from product_sale_shopcategory as pss where pss.productsale=?";

	
	
	/**
	 * 店铺未分类标示
	 */
	private static final int DEFAULT_FLAG = 0;
	
	@InjectDao
	private ShopCategoryDao shopCategoryDao;
	
	@InjectDao
	private ShopDao shopDao;
	
	private JdbcTemplate jdbcTemplate;

	@Override
	public ShopCategory get(Shop shop) {
		synchronized (shop) {
			ShopCategory result = shopCategoryDao.findByShop(shop);
			if (result == null) {
				return initUserTree(shop);
			} else {
				return result;
			}
		}
	}

	/**
	 * 初始化用户独立节点.
	 * 
	 * @param shop
	 */
	private ShopCategory initUserTree(Shop shop) {
		ShopCategory sc = ShopCategory.getShopRootCategory();
		save(sc,shop);
		return sc;
	}

	@Override
	public void update(ShopCategory shopCategory) {

		shopCategory.setProductNum(getPrductCount(shopCategory));
		this.shopCategoryDao.update(shopCategory);
	}

	@Override
	public void delete(ShopCategory shopCategory) throws UpdateShopCategoryException{
		if(checkCategoryHasProduct(shopCategory)){
			throw new UpdateShopCategoryException(shopCategory.getName()+ERROR_INFO);
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("scId", shopCategory.getId());
		Short sort=0;
		List<ShopColumn> columns = shopDao.findShopColumnList(param,sort);
		if(!CollectionUtils.isEmpty(columns)){
			for(ShopColumn column : columns){
				shopDao.delete(column);
			}
		}
		this.shopCategoryDao.delete(shopCategory);
	}
	
	/**
	 * 检查shopCategory下的子分类是否包含有商品
	 * @param shopCategory
	 * @return
	 */
	private boolean checkCategoryHasProduct(ShopCategory shopCategory){
		if(shopCategory.hasChildren()){
			for(ShopCategory child : shopCategory.getChildren()){
				return checkCategoryHasProduct(child);
			}
		}
		return getPrductCount(shopCategory)>0;
	}

	@Override
	public long getPrductCount(ShopCategory shopCategory) {
		return jdbcTemplate.queryForLong(QUERY_CATEGORY_PRODUCTNUM_SQL,new Object[]{shopCategory.getId()});
	}

	@Override
	public long getOnshelfProductCount(ShopCategory shopCategory) {
		return jdbcTemplate.queryForLong(QUERY_CATEGORY_ONSHELFPRODUCTNUM_SQL,new Object[]{shopCategory.getId()});
	}

	@Override
	public long getPrductTypeCount(ProductSale productSale) {
		return jdbcTemplate.queryForLong(QUERY_PRODUCT_CATEGORYNUM_SQL,new Object[]{productSale.getId()});
	}

	@Override
	public ProductCount getShopCount(ShopCategory shopCategory) {
		long prductCount = this.getPrductCount(shopCategory);
		long onshelfProductCount = this.getOnshelfProductCount(shopCategory);
		return new ProductCount(onshelfProductCount, prductCount);
	}

	@Override
	public void save(ShopCategory shopCategory,Shop shop) {
		shopCategory.setShop(shop);
		shopCategoryDao.save(shopCategory);
		shopCategory.setMaintainId(shopCategory.getId());
		shopCategoryDao.update(shopCategory);
		if (!CollectionUtils.isEmpty(shopCategory.getChildren())) {
			for(ShopCategory child : shopCategory.getChildren()){
				child.setParent(shopCategory);
				save(child,shop);
			}
		}
	}

	@Override
	public ShopCategory get(Long id) {
		return this.shopCategoryDao.get(id);
	}

	@Override
	public List<ProductSale> getUnCategory(Map<String, Object> parameters,
			Pagination pagination) {
		if (parameters.get(FLAG) == null) {
			parameters.put(FLAG, ShopCategoryServiceImpl.DEFAULT_FLAG);
		}

		return this.shopCategoryDao.getUnCategory(parameters, pagination);
	}

	@Override
	public void update(Shop shop, List<ShopCategory> addList,
			List<ShopCategory> updateList, List<ShopCategory> removeList)
			throws UpdateShopCategoryException {
		if(!CollectionUtils.isEmpty(removeList)){
			for(ShopCategory category : removeList){
				delete(category);
			}
		}
		if(!CollectionUtils.isEmpty(addList)){
			for(ShopCategory category : addList){
				save(category,shop);
			}
		}
		if(!CollectionUtils.isEmpty(updateList)){
			for(ShopCategory category : updateList){
				update(category);
			}
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
