package com.winxuan.ec.task.dao.ec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 
 * @author caoyouwen
 *
 */
@Component("ecOrderDao")
public class EcOrderDaoImpl implements EcOrderDao {
	
	private static final Log LOG = LogFactory.getLog(EcOrderDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	@Override
	public int getProductSalePurchaseQuantityTotle(ProductSale productSale,Code dc) {
		return jdbcTemplateEcCore.queryForInt(SELECT_PRODUCTSALE_TOTEL,new Object[]{
				productSale.getId(),
				Code.ORDER_PROCESS_STATUS_PICKING,Code.ORDER_PROCESS_STATUS_WAITING_PICKING,dc.getId()
			});
	}

	@Override
	public int getProductDcStock(ProductSale productSale, Code dc){
		try {
			return jdbcTemplateEcCore.queryForInt(SELECT_PRODUCT_STOCK,new Object[]{
					productSale.getId(),
					dc.getId()
			});
		} catch (EmptyResultDataAccessException e) {
			LOG.info(String.format("error:<商品ID：%s>在<仓库：%s>-----未找到库存!", productSale,dc));
		}
		return -1;
	}

	@Override
	public void convertBookingOrder2UsualOrder(String order) {
		jdbcTemplateEcCore.update(UPDATE_PRODUCT_STOCK,new Object[]{
				order
			});
	}

	@Override
	public Long findEtlOrderItemStock(Long orderItemId, Long dc) {
		try {
			return jdbcTemplateEcCore.queryForLong(SELECT_ETL_ORDER_ITEM_STOCK, new Object[]{
					orderItemId, dc
			});
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public void convertRapidOrder2UsualOrder(String order) {
		jdbcTemplateEcCore.update(UPDATE_PRODUCT_STOCK, new Object[]{order});
	}

	@Override
	public int countRapidOrderSales(Long productSaleId) {
		return jdbcTemplateEcCore.queryForInt(SELECT_RAPID_ORDER_SALES_COUNT, productSaleId);
	}

}
