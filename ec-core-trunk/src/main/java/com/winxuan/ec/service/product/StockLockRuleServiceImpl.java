package com.winxuan.ec.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.StockLockRuleDao;
import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author liou 2014-10-15上午11:48:56
 */
@Service("stockLockRuleService")
@Transactional(rollbackFor = Exception.class)
public class StockLockRuleServiceImpl implements StockLockRuleService {

	@InjectDao
	private StockLockRuleDao lockRuleDao;

	@Autowired
	private ProductSaleStockService productSaleStockService;

	@Override
	public StockLockRule get(Long id) {
		return lockRuleDao.get(id);
	}

	@Override
	public void save(StockLockRule lockRule) {
		lockRule.setRealLock(productSaleStockService.computeRealLock(lockRule));
		lockRuleDao.save(lockRule);
	}

	@Override
	public void update(StockLockRule lockRule) {
		lockRuleDao.update(lockRule);
	}



	@Override
	public int updateSales(int sales, long channelId, long productSaleId) {
		return lockRuleDao.updateSales(sales, sales, channelId, productSaleId);
	}

	@Override
	public int updateSales(int sales, long channelId, long productSaleId, long orderCustomerId) {
		return lockRuleDao.updateSales(sales, sales, channelId, productSaleId, orderCustomerId);
	}

	@Override
	public List<StockLockRule> find(Pagination pagination, Map<String, Object> parame, short sort) {
		return lockRuleDao.find(pagination, parame, sort);
	}

	@Override
	public StockLockRule getStockLockBycustomerNull(Long channelId, Long productSale) {
		return lockRuleDao.getStockLockBycustomerNull(channelId, productSale);
	}

	@Override
	public List<StockLockRule> findEffective(Map<String, Object> parameters) {
		return lockRuleDao.findEffective(parameters);
	}

	@Override
	public StockLockRule findEffective(long channelId, long productSaleId) {
		return lockRuleDao.findEffective(channelId, productSaleId);
	}

	@Override
	public StockLockRule findEffective(long channelId, long productSaleId, long orderCustomerId) {
		return lockRuleDao.findEffective(channelId, productSaleId, orderCustomerId);
	}

	@Override
	public long getLockedSumAvailableStock(long productSaleId) {
		return lockRuleDao.getLockedSumAvailableStock(productSaleId);
	}

}
