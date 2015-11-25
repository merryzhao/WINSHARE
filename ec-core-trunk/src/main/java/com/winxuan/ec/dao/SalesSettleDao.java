package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.settle.DailySalesItem;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 今日销售
 * 
 * @author wenchx
 * @version 1.0, 2014-8-4 上午9:43:25
 */
public interface SalesSettleDao {

	String DELIVERY_ITEM_SQL = "SELECT o.id as outerorder,oi.id as outeritem,if(c.name='D803','8A12',c.name) as warehouse, "
			+ " o.customer as customer, ps.outerid as sapid, oi.deliveryquantity as quantity, "
			+ "oi.listprice*oi.deliveryquantity as listprice, o.deliverytime as deliverytime, o.channel as channel,"
			+ " if(p.complex=2,1,0) as complex FROM _order o  "
			+ " inner join order_distribution_center odc on o.id=odc._order "
			+ " inner join code c on c.id=odc.dcdest  "
			+ "	INNER JOIN order_item oi ON (oi._order = o.id) "
			+ "	INNER JOIN product_sale ps ON (ps.id = oi.productsale) "
			+ "   INNER JOIN product p ON (ps.product = p.id) "
			+ "WHERE oi.deliveryquantity > 0 and o.id in:orderlist";// 发货订单项

	String REJECT_ITEM_SQL = "SELECT  CONCAT(CONVERT(LEFT(oi._order,1),SIGNED) + 2 , RIGHT(o.id,LENGTH(oi._order) - 1)) outerorder"
			+ ",oi.id outeritem, if(c.name='D803','8A12',c.name) warehouse, 'X' returnflag, o.customer customer, ps.outerid sapid, "
			+ " oi.deliveryquantity quantity, oi.deliveryquantity*oi.listprice listprice, o.lastprocesstime deliverytime, "
			+ "o.id 'order', oi.id orderitem, o.channel channel,"
			+ " if(p.complex=2,1,0) as complex FROM _order o "
			+ " inner join order_distribution_center odc on o.id=odc._order "
			+ " inner join code c on c.id=odc.dcdest  "
			+ "	INNER JOIN order_item oi ON (oi._order = o.id) "
			+ "	INNER JOIN product_sale ps ON (ps.id = oi.productsale) "
			+ "   INNER JOIN product p ON (ps.product = p.id) "
			+ "WHERE oi.deliveryquantity > 0 and o.id in:orderlist";// 拒收订单项

	String RETURN_ITEM_SQL = "SELECT CONVERT(ro.id, CHAR) as outerorder,ri.id outeritem, if(c.name='D803','8A12',c.name) warehouse, 'X' returnflag, o.customer customer,"
			+ " ps.outerid sapid, ri.realquantity quantity, oi.listprice*ri.realquantity listprice, ro.refundtime deliverytime,"
			+ " o.id 'order', oi.id orderitem, "
			+ "IF(o.deliverytime < '2013-03-22' AND o.channel IN (42,45,8095,8087,40,8090,8085,41,8093,8091), NULL, o.channel) AS 'channel', "
			+ "if(p.complex=2,1,0) as complex FROM  returnorder ro  "
			+ "INNER JOIN returnorder_item ri ON (ri.returnorder = ro.id) "
			+ "INNER JOIN order_item oi ON (ri.orderitem = oi.id) "
			+ "INNER JOIN product_sale ps ON (ps.id = oi.productsale) "
			+ "   INNER JOIN product p ON (ps.product = p.id) "
			+ "INNER JOIN _order o ON (o.id = ro.originalorder) "
			+ " inner join order_distribution_center odc on o.id=odc._order "
			+ " inner join code c on c.id=odc.dcdest  "
			+ "WHERE ri.realquantity > 0  and ro.id in:orderlist";

	@Query(value = DELIVERY_ITEM_SQL, sqlQuery = true, entityClass = DailySalesItem.class)
	List<DailySalesItem> getDeliveryOrderItem(
			@ParameterMap Map<String, Object> params);

	/**
	 * 获取退货订单项
	 * 
	 * @return
	 */
	@Query(value = RETURN_ITEM_SQL, sqlQuery = true, entityClass = DailySalesItem.class)
	List<DailySalesItem> getReturnSalesItem(
			@ParameterMap Map<String, Object> params);

	/**
	 * 获取拒收订单项
	 * 
	 * @return
	 */
	@Query(value = REJECT_ITEM_SQL, sqlQuery = true, entityClass = DailySalesItem.class)
	List<DailySalesItem> getRejectSalesItem(
			@ParameterMap Map<String, Object> params);

}
