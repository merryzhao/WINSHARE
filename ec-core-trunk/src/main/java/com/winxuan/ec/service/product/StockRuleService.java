package com.winxuan.ec.service.product;

import java.util.List;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.StockRule;
import com.winxuan.ec.model.product.StockRuleDc;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author liou
 * @version 2013-9-4:下午5:22:19
 * 
 */
public interface StockRuleService {

	/**
	 * 添加
	 * 
	 * @param stockRule
	 */
	void save(StockRule stockRule);

	/**
	 * 修改
	 * 
	 * @param stockRule
	 */
	void update(StockRule stockRule);

	/**
	 * 查询所有
	 * 
	 * @param pagination
	 * @return
	 */
	List<StockRule> find(Pagination pagination);

	List<Long> findAll();

	/**
	 * 根据id查询
	 * 
	 * @param id
	 */
	StockRule get(Long id);

	/**
	 * 删除StockRuleDc
	 * 
	 * @param ruleDc
	 */
	void delete(StockRuleDc ruleDc);

	/**
	 * 根据库存规则计算直销渠道库存 注意：此处如果渠道规则设置的太多，需要考虑性能问题（还未考虑, 未考虑规则太多问题）
	 * 
	 * @param productSale
	 */
	void computeStock(ProductSale productSale);

	/**
	 * 
	 * @param channelId
	 * @param productSale
	 * @return
	 */
	int computeStock(long channelId, ProductSale productSale);

	/**
	 * 查看该库存和渠道是否已有相同的
	 * 
	 * @param channelId
	 * @param supplyTypeId
	 * @return
	 */
	boolean isSupplyAndChannel(Long channelId, Long supplyTypeId);
	
	StockRule getStockRule(long channelId, long supplyTypeId);

}
