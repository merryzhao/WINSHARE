package com.winxuan.ec.task.service.ec.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.task.job.ec.product.ErpAllStockSync;
import com.winxuan.ec.task.model.ec.StockSyncInfo;
import com.winxuan.ec.task.model.ec.convert.StockSyncInfoMapper;
import com.winxuan.ec.task.service.ec.StockSyncService;

/**
 * 
 * @author: HideHai
 * @date: 2013-10-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StockSyncServiceImpl implements StockSyncService {

	public static String selectProducts = "SELECT * FROM product_warehouse WHERE ischanged=1 LIMIT 2000";
	public static String updateProcess = "UPDATE product_warehouse SET ischanged = 0 WHERE id = ?";
	public static String updateIngore = "UPDATE product_warehouse SET ischanged = 2 WHERE id = ?";
	public static String changeCount = "SELECT count(*) " +
			"FROM product_sale_stock pss " +
			"LEFT JOIN product_warehouse pw ON pss.productsale=pw.productsale " +
			"LEFT JOIN `code` c ON c.id=pss.dc " +
			"WHERE pss.stock!=pw.stock AND pw.ischanged=0 AND c.`name`=pw.location " +
			" AND pw.stock>=0";
	public static String updateIsChange = "update product_sale_stock pss " +
			"LEFT JOIN product_warehouse pw ON pss.productsale=pw.productsale " +
			"LEFT JOIN `code` c ON c.id=pss.dc " +
			"SET pw.ischanged=1 " +
			"WHERE pss.stock!=pw.stock AND pw.ischanged=0 AND c.`name`=pw.location " +
			"AND pw.stock>=0";

	private static final Log LOG = LogFactory.getLog(ErpAllStockSync.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ProductSaleStockService productSaleStockService;
	@Autowired
	private ProductService productService;

	public StockSyncInfo getNeedProcessInfo(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StockSyncInfo> findNeedProcessInfo() {
		return jdbcTemplate.query(selectProducts, new Object[] {},
				new StockSyncInfoMapper());
	}

	public void processStockSyncInfo(StockSyncInfo stockSyncInfo)
			throws Exception {
		Code dc = getDcByLocation(stockSyncInfo.getLocation());
		// List<Long> psIds = findPsIdByOuterid(stockSyncInfo.getOuterId());
		Long psId = stockSyncInfo.getProductsale();
		// for(Long psid : psIds){
		if (psId != null) {
			long s = System.currentTimeMillis();
			ProductSale productSale = productService.getProductSale(psId);
			productSaleStockService.updateQuantityByWms(dc, productSale,
					stockSyncInfo.getStock());
			if(stockSyncInfo.getChangeqty() > 1){
				productSaleStockService.afreshComputeRealLockByFactor(productSale);
			}
			LOG.info(String.format("process time: %s",
					(System.currentTimeMillis() - s)));
		} else {
			LOG.error(String.format("stock info: %s not Found Ec ProductSale!",
					stockSyncInfo.getId()));
		}
		// }
		markInfoProcess(stockSyncInfo.getId());
	}

	/**
	 * 获取库存信息的DC
	 * 
	 * @param location
	 * @return
	 */
	private Code getDcByLocation(String location) {
		long dcId = Code.DC_8A17;
		if ("8A17".equals(location)) {
			dcId = Code.DC_8A17;
		} else if ("D818".equals(location)) {
			dcId = Code.DC_D818;
		} else if ("8A19".equals(location)) {
			dcId = Code.DC_8A19;
		} else if ("D803".equals(location)) {
			dcId = Code.DC_D803;
		} else if ("D819".equals(location)) {
			dcId = Code.DC_D819;
		}
		return new Code(dcId);
	}

	/**
	 * 标记数据已处理
	 * 
	 * @param id
	 */
	private void markInfoProcess(long id) {
		jdbcTemplate.update(updateProcess, new Object[] { id });
	}

	/**
	 * 标记数据忽略
	 * 
	 * @param id
	 */
	public void markInfoIgnore(long id) {
		jdbcTemplate.update(updateIngore, new Object[] { id });
	}

	/**
	 * 根据Sap编码获取EC商品编号
	 * 
	 * @param outerid
	 * @returnon
	 */
	private List<Long> findPsIdByOuterid(String outerid) {
		String selectPsSql = "SELECT id FROM product_sale WHERE outerid = ?";
		return jdbcTemplate.queryForList(selectPsSql, Long.class,
				new Object[] { outerid });
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForInt(changeCount);
	}

	@Override
	public void updateDifStockIsChanege() {
		jdbcTemplate.update(updateIsChange);
	}

}
