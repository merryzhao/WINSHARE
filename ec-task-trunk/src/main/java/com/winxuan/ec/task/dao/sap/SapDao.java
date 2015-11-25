package com.winxuan.ec.task.dao.sap;

import java.util.List;

import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.task.model.sap.SapOrder;
/**
 * 
 * @author yangxinyi
 * 
 */
public interface SapDao {

	String INSERT_SAP_ORDER = "INSERT INTO interface_sap_sales_order ( "
			+ "_order,outerorderid,orderitem,warehousefrom,warehouse,sapcustomer,outerid,purchasequantity,"
			+ "saleprice,listprice,operator,createtime,channel,deliveryoption,iflag"
			+ ") VALUES ("
			+ "?,?,?,?,'8A12',?,?,?,"
			+ "?,?,?,now(),?,?,'C') ";
	/**
	 * 写入interface_replenishment接口表里面的记录标志位设置为T
	 */	
	String INSERT_INTERFACE_REPLENISHMENT = "INSERT INTO interface_replenishment(outerorder, outeritem, dc, sapid, quantity, returnmsg)"
			+ " VALUES(concat(date_format(curdate(), '%Y%m%d'), ?), ?, ?, ?, ?, 'T') ";
	
	/**
	 * 接口表写入完成后，将标志位更新为C
	 */
	String UPDATE_INTERFACE_REPLENISHMENT = "UPDATE interface_replenishment set returnmsg = 'C' where returnmsg = 'T'";

	String INSERT_BILL_SETTLE = "INSERT INTO interface_channel_settle ( "
			+ "outerorder, outeritem, bill, warehouse, returnflag, customer, sapid, quantity,"
			+ "_order, orderitem, channel, deliveryquantity, settle, historysettle, type, listprice"
			+ ") VALUES ("
			+ "?,?,?,'8A12',?,?,?,?,"
			+ "?,?,?,?,?,?,'I',?)";
	
	String FIND_SAP_DELIVERY = "SELECT _order FROM interface_sap_order_delivery WHERE iflag = 'C' GROUP BY _order";
	
	String FIND_SAP_ORDER = "SELECT id, _order, orderitem, deliveryquantity, deliverycode, deliverytime, iflag " +
			" FROM interface_sap_order_delivery WHERE _order = ? AND iflag = 'C' ";
	
	String FIND_SAP_STOCK = "SELECT count(*) FROM interface_sap_order_delivery WHERE _order = ? AND deliveryquantity > 0 AND iflag = 'C' ";
	
	String UPDATE_ORDERITEM_DELIVERYQUANTITY = "UPDATE order_item SET deliveryquantity = ? WHERE id = ?";
	
	String UPDATE_SAP_DELIVERY_IFLAG = "UPDATE interface_sap_order_delivery SET iflag = 'X' WHERE _order = ?"; 
	
	String UPDATE_MR_FREEZE_FLAG = "UPDATE mr_product_freeze set flag = 2 WHERE id = ?";
	
	String NEW_INSERT_INTERFACE_REPLENISHMENT = "INSERT INTO interface_replenishment(outerorder, outeritem, dc, sapid, quantity, returnmsg) VALUES";
	
	/**
	 * 旧版，逐条写入interface_replenishment接口表里面的记录标志位设置为T
	 * @param params
	 */
	void sendReplenishmentItems(Object[] params);
	
	/**
	 * 新版，批量写入interface_replenishment接口表里面的记录标志位设置为T
	 * @param params
	 */
	int sendReplenishmentItemsNew(List<MrProductFreeze> mrProductFreezes);
	
	/**
	 * 补货数据下传到SAP之后，修改商品在冻结表中的状态
	 */
	void batchUpdateMrProductFreezeFlag(final List<MrProductFreeze> mrProductFreezes);
	
	/**
	 * 接口表写入完成后，将标志位更新为C
	 */
	void updateReplenishmentItems();
	
	void sendOrderItems(Object[] params);
	
	void sendSapBillItems(Object[] params);
	
	List<String> listSapOrderDelivery();
	
	List<SapOrder> listSapOrderItemDelivery(String orderId);

	boolean sapDoCancel(String orderId);

	void updateOrderItem(Long orderItemId, int deliveryQuantity);

	void updateSapOrderIflag(String orderId);
	
}