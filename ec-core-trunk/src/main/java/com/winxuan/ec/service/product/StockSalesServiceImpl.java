/**
 * 
 */
package com.winxuan.ec.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.StockSalesDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.StockSales;
import com.winxuan.ec.service.cache.BusinessCacheService;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-8
 */
@Transactional
@Service("stockSalesService")
public class StockSalesServiceImpl implements StockSalesService {

	@InjectDao
	private StockSalesDao stockSalesDao;

	@Autowired
	private BusinessCacheService businessCacheService;

	@Override
	public void save(StockSales stockSales) {
		stockSalesDao.save(stockSales);
	}

	@Override
	public void update(StockSales stockSales) {
		stockSalesDao.update(stockSales);
	}


	@Override
	public StockSales get(Long id) {
		return stockSalesDao.get(id);
	}

	@Override
	public StockSales get(String ordreId, Long productSaleId, Code dc) {
		String key = stockSaleCacheKey(ordreId, productSaleId, dc);
		StockSales stockSales = null;
		Object keyObject = businessCacheService.get(key);
		Long id =  keyObject == null ? null : (Long) keyObject;
		if(id == null) {
			stockSales = stockSalesDao.queryByOrderProductSaleAndDC(ordreId, productSaleId, dc);
			if(stockSales != null) {
				businessCacheService.put(key, stockSales.getId());
			}
		}else {
			stockSales = stockSalesDao.get(id);
		}
		return stockSales;
	}

	private String stockSaleCacheKey(String ordreId, Long productSaleId, Code dc) {
		StringBuilder sb = new StringBuilder();
		sb.append("StockSalesService");
		sb.append("_");
		sb.append(ordreId);
		sb.append("_");
		sb.append(productSaleId);
		sb.append("_");
		sb.append(dc.getId());
		sb.append("_");
		return sb.toString();
	}

}
