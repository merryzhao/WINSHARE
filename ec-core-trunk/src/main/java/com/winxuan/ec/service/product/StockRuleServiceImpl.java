package com.winxuan.ec.service.product;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.dao.ProductSaleIncorrectStockDao;
import com.winxuan.ec.dao.StockChannelDao;
import com.winxuan.ec.dao.StockRuleDao;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.ec.model.product.StockRule;
import com.winxuan.ec.model.product.StockRuleDc;
import com.winxuan.ec.service.cache.BusinessCacheService;
import com.winxuan.ec.service.config.SystemConfigService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * @author liou
 * @version 2013-9-4:下午5:29:24
 * 
 */
@Service("stockRuleService")
@Transactional(rollbackFor = Exception.class)
public class StockRuleServiceImpl implements StockRuleService {
	private static final Log LOG = LogFactory.getLog(StockRuleServiceImpl.class);
	private static final float F_100 = 100.00F;
	public static final String ORGANIZE_SELL_PATH = "1000.1004.";
	public static final String SUPPLY_PATH = "1000.1005.";

	@InjectDao
	private StockRuleDao stockRuleDao;
	@InjectDao
	private StockChannelDao stockChannelDao;
	@InjectDao
	private ProductSaleIncorrectStockDao productSaleIncorrectStockDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private StockLockRuleService stockLockRuleService;
	@Autowired
	private BusinessCacheService businessCacheService;

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(StockRule stockRule) {
		stockRuleDao.save(stockRule);
	}

	@Override
	public void update(StockRule stockRule) {
		stockRuleDao.update(stockRule);
	}

	@Override
	public List<StockRule> find(Pagination pagination) {
		return stockRuleDao.find(pagination);
	}

	@Override
	public List<Long> findAll() {
		String sql = "select sr.id from stock_rule sr";
		return jdbcTemplate.queryForList(sql, Long.class);
	}

	@Override
	@Read
	public StockRule get(Long id) {
		return stockRuleDao.get(id);
	}

	@Override
	public void delete(StockRuleDc ruleDc) {
		stockRuleDao.delete(ruleDc);
	}

	@Override
	public void computeStock(ProductSale productSale) {
		Integer stock = null;
		if (systemConfigService.lockStockSwitchOpen()) {
			StockLockRule stockLockRule = stockLockRuleService.findEffective(Channel.CHANNEL_EC, productSale.getId());
			if (null != stockLockRule) {
				stock = stockLockRule.getRealLock();
			}
		}

		if (null == stock) {
			stock = computeStock(Channel.CHANNEL_EC, productSale);
		}

		productSale.setStockQuantity(stock);
		productService.onShelfOrOffShelf(productSale);
		productSale.setUpdateTime(new Date());
		productService.update(productSale);
	}

	@Override
	public int computeStock(long channelId, ProductSale productSale) {
		StockRule stockRule = getStockRule(channelId, productSale.getSupplyType().getId());
		if (null == stockRule) {
			return 0;
		}
		if (stockRule.getChannel().getPath().startsWith(SUPPLY_PATH) && productSale.getProduct().isComplex()) {
			return 0;
		}
		return getStock(productSale, stockRule);
	}

	@Override
	public StockRule getStockRule(long channelId, long supplyTypeId) {
		StockRule stockRule = getStockRuleByCache(channelId, supplyTypeId);
		if (null == stockRule) {
			stockRule = getStockRuleByCache(Channel.ROOT, supplyTypeId);
		}
		if (null == stockRule) {
			return null;
		}
		if (CollectionUtils.isEmpty(stockRule.getStockRuleDc())) {
			LOG.warn(stockRule.getId() + " 规则非法，没有库位规则");
			return null;
		}
		return stockRule;
	}

	private StockRule getStockRuleByCache(long channelId, long supplyTypeId) {
		StringBuilder sb = new StringBuilder();
		sb.append("StockRuleService");
		sb.append("_");
		sb.append(channelId);
		sb.append("_");
		sb.append(supplyTypeId);
		String key = sb.toString();
		Object keyObject = businessCacheService.get(key);
		Long stockRuleId = keyObject == null ? null : (Long)keyObject ;
		StockRule stockRule = null;
		if(stockRuleId == null) {
			stockRule = stockRuleDao.get(channelId, supplyTypeId); 
			if(stockRule != null) {
				businessCacheService.put(key,stockRule.getId());
			}
		}else {
			stockRule = stockRuleDao.get(stockRuleId);
		}
		return stockRule;
	}

	private int getStock(ProductSale productSale, StockRule stockRule) {
		int stock = 0;
		for (StockRuleDc srd : stockRule.getStockRuleDc()) {
			stock += getStockByType(productSale, stockRule.getStockType(), srd);
		}

		long tmpLong = productSaleIncorrectStockDao.getStockOccupancy(productSale.getId());
		if (systemConfigService.lockStockSwitchOpen()) {
			tmpLong += stockLockRuleService.getLockedSumAvailableStock(productSale.getId());
		}
		int occupancy = tmpLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) tmpLong;
		if (stock <= occupancy) {
			return 0;
		}

		stock -= occupancy;
		if (1 == stock) {
			if (systemConfigService.lockStockSwitchOpen()) {
				if(stockRule.getChannel().getPath().startsWith(SUPPLY_PATH) || stockRule.getChannel().getPath().startsWith(ORGANIZE_SELL_PATH)){
					return 0;
				}
			}
			return stock;
		}

		stock = (int) Math.floor(stock * stockRule.getPercent() / F_100);
		return stock;
	}

	private int getStockByType(ProductSale productSale, int stockType, StockRuleDc stockRuleDc) {
		if (null == stockRuleDc) {
			return 0;
		}
		ProductSaleStock productSaleStock = productSale.getStockByDc(stockRuleDc.getDc());
		if (null == productSaleStock) {
			return 0;
		}
		if (StockRule.STOCK_TYPE_ACTUAL == stockType) {
			return productSaleStock.existIncorrectStock() ? 0 : productSaleStock.getActualAvalibleQuantity();
		}
		if (StockRule.STOCK_TYPE_VIRTUAL == stockType) {
			return productSaleStock.getVirtualAvalibleQuantity();
		}
		return 0;
	}

	@Override
	public boolean isSupplyAndChannel(Long channelId, Long supplyTypeId) {
		return stockRuleDao.isSupplyAndChannel(channelId, supplyTypeId);
	}

}
