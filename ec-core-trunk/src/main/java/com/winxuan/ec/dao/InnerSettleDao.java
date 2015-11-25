package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.settle.SapOrderItem;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @description: TODO
 *
 * @createtime: 2014-2-13 下午2:28:00
 *
 * @author zenghua
 *
 * @version 1.0
 */

public interface InnerSettleDao {
	
	String DELIVERY_ORDERITEM_SQL = " select o.id outerorder, oi.id outeritem, o.customer customer, ps.outerid sapid, "
			+ " oi.purchasequantity quantity,if(c.name='D803','8A12',c.name) dc, o.channel channel, oi.deliveryquantity deliveryquantity, oi.listprice listprice, "
			+ " oi.deliveryquantity settle,if(p.complex=2,1,0) as complex from _order o "
			+ " inner join order_item oi on o.id = oi._order "
			+ " inner join order_distribution_center odc on o.id=odc._order "
			+ " inner join code c on c.id=odc.dcdest  "
			+ " INNER JOIN product_sale ps ON oi.productsale=ps.id "
			+ " INNER JOIN product p on p.id=ps.product "
			+ " left join interface_channel_settle ics on ics.outeritem = oi.id and " 
			+ "  ics.outerorder = o.id,channel ch "
			+ " where ics.id is null and o.createtime >= :startDate and o.createtime < :endDate and oi.deliveryquantity > 0 "
			+ " and o.transferresult != :transferresult "
			+ " AND o.processstatus NOT IN(8010,8013) and ch.id=o.channel and ch.issettle=1";//排除中启取消与等待拦截的订单
	
	String RETURN_ORDERITEM_SQL = "SELECT CONVERT(ri.returnorder , CHAR) outerorder, "
			+ " ri.id outeritem,o.channel,ps.outerid sapid,oi.listprice,oi.deliveryquantity,if(c.name='D803','8A12',c.name) dc,ri.realquantity settle ,"
			+ " oi.purchasequantity quantity,'X' as returnflag, o.id `order`, oi.id orderitem, o.customer,if(p.complex=2,1,0) as complex" 
			+ " FROM returnorder ro inner join returnorder_item ri on ro.id = ri.returnorder " 
			+ " INNER JOIN order_item oi ON ri.orderitem=oi.id "
			+ " INNER JOIN _order o ON o.id=oi._order "
			+ " inner join order_distribution_center odc on o.id=odc._order "
			+ " inner join code c on c.id=odc.dcdest  "
			+ " INNER JOIN product_sale ps ON ps.id=oi.productsale "
			+ " INNER JOIN product p on p.id=ps.product "
			+ " left join interface_channel_settle ics on ics.outeritem = ri.id and "
			+"  ics.outerorder = CONVERT(ro.id, CHAR),channel ch  "
			+ " WHERE ics.id is null  "
			+ " and o.transferresult != :transferresult "
			+ " AND o.createtime >= :startDate "
			+ " AND ro.refundtime < :endDate "
			+ " AND ri.realquantity > 0 and ch.id=o.channel and ch.issettle=1";
	
	String REJECT_ORDERITEM_SQL = "SELECT CONCAT(CONVERT(LEFT(oi._order,1),SIGNED) + 2 , RIGHT(o.id,LENGTH(oi._order) - 1)) outerorder, "
			+ " oi.id outeritem, 'X' as returnflag, o.customer customer, ps.outerid sapid, oi.purchasequantity quantity,if(c.name='D803','8A12',c.name) dc, o.id `order`, "
			+ " oi.id orderitem, o.channel channel, oi.deliveryquantity deliveryquantity, oi.listprice listprice, oi.deliveryquantity settle "
			+ " ,if(p.complex=2,1,0) as complex FROM _order o "
			+ " INNER JOIN order_item oi ON o.id=oi._order "
			+ " inner join order_distribution_center odc on o.id=odc._order "
			+ " inner join code c on c.id=odc.dcdest  "
			+ " INNER JOIN product_sale ps ON oi.productsale=ps.id "
			+ " INNER JOIN product p on p.id=ps.product "
			+ " left join interface_channel_settle ics on ics.outeritem = oi.id " 
			+ " AND ics.outerorder = Concat(CONVERT(LEFT(oi._order, 1), signed) + 2, RIGHT(o.id, Length(oi._order) - 1)) ,channel ch "  
			+ "WHERE ics.id is null  "
			+ " AND o.createtime>=:startDate AND o.createtime<:endDate AND o.transferresult != :transferresult"
			+ " AND o.processstatus= :processstatus "
			+ " AND oi.deliveryquantity > 0 and ch.id=o.channel and ch.issettle=1"; 
	
	@Query(value=DELIVERY_ORDERITEM_SQL, sqlQuery=true, entityClass=SapOrderItem.class)
	List<SapOrderItem> getDeliveryOrderItem(@ParameterMap Map<String, Object> params);
	
	@Query(value=RETURN_ORDERITEM_SQL, sqlQuery=true, entityClass=SapOrderItem.class)
	List<SapOrderItem> getReturnOrderItem(@ParameterMap Map<String, Object> params);
	
	@Query(value=REJECT_ORDERITEM_SQL, sqlQuery=true, entityClass=SapOrderItem.class)
	List<SapOrderItem> getRejectOrderItem(@ParameterMap Map<String, Object> params);
	
}
