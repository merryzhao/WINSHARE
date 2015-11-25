package com.winxuan.ec.service.product;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author liou 2014-10-15上午11:48:09
 */
public interface StockLockRuleService {

	StockLockRule get(Long id);

	void save(StockLockRule lockRule);

	void update(StockLockRule lockRule);
	
	int updateSales(int sales, long channelId, long productSaleId);
	
	int updateSales(int sales, long channelId, long productSaleId, long orderCustomerId);

	List<StockLockRule> find(Pagination pagination, Map<String, Object> parame, short sort);

	List<StockLockRule> findEffective(Map<String, Object> parameters);

	StockLockRule findEffective(long channelId, long productSaleId);

	StockLockRule findEffective(long channelId, long productSaleId, long orderCustomerId);

	long getLockedSumAvailableStock(long productSaleId);

	StockLockRule getStockLockBycustomerNull(Long channelId, Long productSale);
}
