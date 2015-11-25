/*
 * @(#)ecDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.dao.ec;

import java.util.Date;
import java.util.List;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.task.model.erp.ErpArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryCompany;
import com.winxuan.ec.task.model.erp.ErpOrder;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-27
 */
public interface EcDao {
	/**
	 * 检查渠道订单是否已经存在
	 */
	String CHECK_EC2CHANNEL = "SELECT count(*) FROM order_ec2channel WHERE outerid=? AND channel=?";
	/**
	 * ERP回传的发货订单如果是渠道订单则插入到渠道状态回传表
	 * size: 5
	 */
	String INSERT_EC2CHANNEL = "INSERT INTO order_ec2channel " +
			"(_order,outerid,channel,processstatus,deliverycompany,deliverycode,status,createtime) " +
			"VALUES " +
			"(?,?,?,?,?,?,0,now());";
	
	String INSERT_RETURNORDER_EC2CHANNEL = "INSERT INTO returnorder_ec2channel" +
			"(returnorder,processstatus,status,createtime) " +
			"VALUES" +
			"(?,?,0,now());";
	
	
	String GET_NOTRANS_NUM = "select count(DISTINCT od.id) " +
			"from _order od left join order_item oi on od.id = oi._order LEFT JOIN erp_product_notransfer pn on oi.productsale = pn.productsale " +
			"where od.id = ? and pn.productsale is not null and  pn.status = 0;";
	
	String SELECT_ORDER_NOTRANSFER = "SELECT _order FROM erp_order_notransfer WHERE status = 0";
	
	/**
	 * ERP拦截订单保存
	 */
	String INSERT_ERP_BLOCK = "INSERT INTO erp_block (_order,status,createtime,cuser) VALUES (?,?,?,?)";
	String CHECK_ERP_BLOCK = "SELECT count(*) FROM erp_block WHERE _order = ?";
	/**
	 * 更新ERP拦截订单的状态
	 */
	Integer BLOCK_INIT = 0;
	Integer BLOCK_SUCCESS = 1;
	Integer BLOCK_DELIVERY = 2;
	String UPDATE_ERP_BLOCK = "UPDATE erp_block SET status=? WHERE _order=?";
	
	String GET_ERP_BLOCK = "select count(*) from erp_block where _order=?";
	
	/**
	 * 查询需要二次处理的ERP拦截订单
	 */
	String SELECT_ERP_BLOCK = "SELECT _order FROM erp_block WHERE `status` = 0 AND createtime<=DATE_ADD(now(),INTERVAL -20 HOUR)";
	
	/**
	 * 插入发货信息
	 */
	String SELECT_SPLIT = "SELECT count(*) FROM order_delivery_split WHERE _order = ? AND company=? AND code = ? ";
	
	String INSERT_DELIVERY_SPLIT = "INSERT INTO order_delivery_split (_order,company,code,createtime) VALUES (?,?,?,?)";
	
	String SELECT_RETURN_DELAY = "select o.id from _order o left join erp_block eb on o.id=eb._order " +
		",channel c where o.processstatus=8003 and o.transferresult=35002 and o.shop=1 and o.channel=c.id and eb.id is null " +
		"and ((o.lastprocesstime<date_sub(now(),INTERVAL 2 DAY) and o.channel not in (40,41,42,43)) " +
		"or (o.lastprocesstime<date_sub(now(),INTERVAL 3 DAY) and o.channel  in (40,41,42,43)))";
	
	String UPDATE_ORDER_RETURN_MONITOR = "update interface_monitor set num=?,lasttime=now() where `procedure`='check_order_return'";
	
	String UPDATE_ORDER_INVOICE_SQL = "update order_invoice oi set oi.deliverycode=?, oi.deliverycompany=?, oi.deliverytime=? where oi.id=?";
	
	String INSERT_AREA = "insert into interface_area (id, parent, name, zt, type) values(?, ?, ?, ?, ?)";
	
	String INSERT_DELIVERY_AREA = "insert into interface_delivery_area (area,deliverytype,deliverycompany,type,description,dc,supportcod) values(?, ?, ?, ?, ?, ?, ?)";
	
	String INSERT_DELIVERY_COMPANY = "insert into interface_deliverycompany (id, name, zt, type) values(?, ?, ?, ?)";

	/**
	 * 渠道订单回传
	 * @param order
	 */
	void sendChannelOrder(Order order);
	
	
	/**
	 * 渠道退货单回传
	 * @param returnOrder
	 */
	void sendChannelReturnOrder(ReturnOrder returnOrder);
	
	/**
	 * 保存ERP做了销退的订单
	 * @param erpOrder
	 */
	void saveBlockOrder(ErpOrder erpOrder,int status);
	
	/**
	 *  查询需要处理的未拦截成功的订单
	 * @return
	 */
	List<String> fetchNeedProcessBlockOrder();
	
	/**
	 * 订单拦截成功或者未成功的后续状态更新
	 * @param orderId		订单号
	 * @param status	 0 已拦截 1拦截成功 2拦截未处理已发货
	 */
	void updateBlockOrder(ErpOrder erpOrder,int status);
	
	/**
	 * 订单拦截成功或者未成功的后续状态更新
	 * @param orderId		订单号
	 * @param status	 0 已拦截 1拦截成功 2拦截未处理已发货
	 */
	void updateBlockOrder(String orderId,int status);
	
	/**
	 * 是否存在拦截记录
	 * @param orderId
	 * @param status
	 * @return
	 */
	boolean existErpBlock(String orderId);
	
	/**
	 *  保存ERP发货信息
	 *  运单信息和订单对应关系表
	 * @param erpOrder
	 */
	void saveDelivery(String orderId,String company,String code,Date deiveryTime);
	
	/**
	 * 查询回传延迟的订单
	 */
	List<String> findReturnDelay();
	
	void saveOrderReturnMonitorResult(Integer delayNum);

	/**
	 * 修改发票的发货信息
	 */
	void updateOrderInvoiceDelivery(String deliveryCode, Integer deliveryCompany, Date deliveryTime, String id);
	
	/**
	 * 保存ERP回传的区域
	 */
	void saveArea(ErpArea erpArea);
	
	/**
	 * 保存ERP回传的配送公司和区域关系
	 */
	void saveDeliveryArea(ErpDeliveryArea erpDeliveryArea);
	
	/**
	 * 保存ERP回传的配送公司
	 */
	void saveDeliveryCompany(ErpDeliveryCompany erpDeliveryCompany);
	
	/**
	 * 执行无参存储过程
	 */
	void executeProcedure(String name);
	
	/**
	 * 判断该订单是否下传
	 * @return
	 */
	boolean isTransfer(String orderId);

	/**
	 * 获取不下传的订单
	 * @return
	 */
	List<String> findOrderNoTransfer();
}

