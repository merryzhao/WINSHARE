package com.winxuan.ec.task.dao.ec;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 
 * @author caoyouwen
 *
 */
public interface EcOrderDao {
	
	String SELECT_PRODUCTSALE_TOTEL = "SELECT if(SUM(oi.purchasequantity) is null,0,SUM(oi.purchasequantity))sum FROM _order o," +
			"order_item oi,order_distribution_center odc where oi.productsale= ? AND oi._order = o.id AND o.id = odc._order " +
			" AND o.processstatus in (?,?) AND  odc.dcoriginal = ? and supplytype = 13101";
	
	String SELECT_PRODUCT_STOCK = "SELECT stock FROM product_sale_stock where productsale = ? and dc = ?";
	
	String UPDATE_PRODUCT_STOCK = "UPDATE _order SET supplytype = 13101 where id = ?";
	
	String SELECT_RAPID_ORDER_SALES_COUNT = "select sum(oi.purchasequantity) from "
		+ "order_item as oi left join _order as o on oi._order=o.id left join order_distribution_center as odc on o.id=odc._order"
		+ "where o.processstatus in(8002, 8003) and o.supplytype=13101 and odc.dcoriginal=110005 and oi.productsale=?";
	
	String SELECT_ETL_ORDER_ITEM_STOCK = "select id from etl_order_item_stock where order_item = ? and dc = ?";
	
	/**
	 * 查询订单正在配送和等待发货的商品数量
	 * @param productSale (需要查询的商品)
	 * @return int 商品数量
	 */
	int getProductSalePurchaseQuantityTotle(ProductSale productSale,Code dc);
	
	/**
	 * 查询商品实际库存量
	 * @param productSale (需要查询的商品)
	 * @param dc (北京仓库和青白江仓库)
	 * @return int 商品数量
	 */
	int getProductDcStock(ProductSale productSale,Code dc);
	
	/**
	 * 预售订单状态更改销售订单
	 * @param supplyTypeUsual
	 * @param order (需更改的订单编号)
	 */
	void convertBookingOrder2UsualOrder(String order);
	
	/**
	 * 快速分拨订单转普通订单
	 * @param order 订单号
	 */
	void convertRapidOrder2UsualOrder(String order);
	
	/**
	 * 查询快速分拨订单的占用量
	 * @param productSaleId
	 * @return
	 */
	int countRapidOrderSales(Long productSaleId);
	
	/**
	 * 查询库存数据是否已存在
	 * @param orderItem
	 * @param dc
	 * @return
	 */
	Long findEtlOrderItemStock(Long orderItemId, Long dc);
}
