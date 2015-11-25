/*
 * @(#)ReturnOrderService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.returnorder;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderPackage;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageItem;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageRelate;
import com.winxuan.ec.model.returnorder.ReturnOrderTag;
import com.winxuan.ec.model.returnorder.ReturnOrderTrack;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-13
 */
public interface ReturnOrderService {
	/**
	 * 根据原销售订单号,创建退货单.
	 * @param originalOrderId
	 * 			原销售订单号
	 * @param creator
	 * 			创建人
	 * @return
	 */
	ReturnOrder create(String originalOrderId, User creator) throws ReturnOrderException;

	/**
	 * 创建退换货单<br/>
	 * 设置如下属性：<br/>
	 * returnOrder.type<br/>
	 * returnOrder.responsible<br/>
	 * returnOrder.holder<br/>
	 * returnOrder.pickup<br/>
	 * returnOrder.reason<br/>
	 * returnOrder.refundDeliveryFee 可选，默认0<br/>
	 * returnOrder.refundCompensating 可选，默认0<br/>
	 * returnOrder.refundCouponValue 可选，默认0<br/>
	 * returnOrder.remark 可选，默认null<br/>
	 * returnOrder.itemList 类型为退货、换货必填，以下同，使用returnOrder.addItem()方法<br/>
	 * returnOrder.itemList[\d].returnOrder<br/>
	 * returnOrder.itemList[\d].orderItem<br/>
	 * returnOrder.itemList[\d].appQuantity<br/>
	 * 
	 * @param returnOrder
	 *            退换货单
	 * @param originalOrder
	 *            原始订单
	 * @param creator
	 *            创建人
	 */
	void create(ReturnOrder returnOrder, Order originalOrder, User creator) throws ReturnOrderException;

	/**
	 * 审核<br/>
	 * 
	 * @param returnOrder
	 * @param auditor
	 */
	void audit(ReturnOrder returnOrder, User auditor)  throws ReturnOrderException;
	
	/**
	 * 取消（审核不通过）
	 * @param returnOrder
	 * @param auditor
	 */
	void cancel(ReturnOrder returnOrder, User auditor);

	/**
	 * 退货
	 * 
	 * @param returnOrder
	 */
	void startReturn(ReturnOrder returnOrder, User operator);

	/**
	 * 实物入库<br/>
	 * 需设置returnOrder.itemList[\d].realQuantity 实际退货数量
	 * 
	 * @param returnOrder
	 */
	List<OrderPayment> completeReturn(ReturnOrder returnOrder, User operator)  throws ReturnOrderException;
	
	void createOrder(ReturnOrder returnOrder, List<OrderPayment> orderPaymentList, User operator) throws ReturnOrderException;
	
	/**
	 * 实物入库<br/>
	 * 需设置returnOrder.itemList[\d].realQuantity 实际退货数量
	 * 
	 * @param returnOrder
	 * @param refund 是否退款
	 */
	List<OrderPayment> completeReturn(ReturnOrder returnOrder,boolean refund, User operator)  throws ReturnOrderException;

	/**
	 * 退款<br/>
	 * 
	 * @param returnOrder
	 */
	List<OrderPayment> refund(ReturnOrder returnOrder, User operator)  throws ReturnOrderException;

	/**
	 * 获得退换货单
	 * 
	 * @param id
	 * @return
	 */
	ReturnOrder get(Long id);
	
	/**
	 * 修改退换货单<br/>
	 * 仅当已提交状态才能修改
	 * @param returnOrder
	 */
	void update(ReturnOrder returnOrder);
	
	/**
	 * 增加跟踪信息
	 * @param returnOrder
	 * @param track
	 */
	void addTrack(ReturnOrder returnOrder,ReturnOrderTrack track);

	/**
	 * 查询退换货单
	 * 
	 * @param parameters
	 * <br/>
	 *            String[] orderId:订单号<br/>
	 *            Long[] returnOrderId:退换货单号<br/>
	 *            Long type:类型<br/>
	 *            Long status:状态<br/>
	 *            String consignee:收货人<br/>
	 *            String creator:创建人<br/>
	 *            Date startCreateTime:退换货单创建开始时间<br/>
	 *            Date endCreateTime:退换货单创建截止时间<br/>
	 *            Date startDeliveryTime:原订单发货开始时间<br/>
	 *            Date endDeliveryTime:原订单发货截止时间<br/>
	 * @param pagination
	 * @return
	 */
	List<ReturnOrder> find(Map<String, Object> parameters, Pagination pagination);
	
	List<Object[]> findReturnOrderCollectByShop(Shop shop,Map<String, Object> param);
	
	/**
	 * 根据退货订单id获取退货订单标签列表
	 * @param id
	 * @return
	 */
	List<ReturnOrderTag> findReturnOrderTagByReturnOrderId(Long id);
	
	/**
	 * 保存退货订单标签，如：普通退货订单（570001），原包非整退（570002）
	 * @param tagId
	 */
	void saveReturnOrderTag(ReturnOrder returnOrder, Long tagId) throws ReturnOrderException;
	
	/**
	 * 执行不退款操作
	 */
	void notRefund(ReturnOrder returnOrder, User operator);
	
	/**
	 * 保存包件主数据
	 * @param returnPackage
	 * @throws ReturnOrderException 
	 */
	void saveReturnOrderPackage(ReturnOrderPackage returnPackage) throws ReturnOrderException;
	
	/**
	 * 保存包件子数据
	 * @param packageItem
	 * @throws ReturnOrderException 
	 */
	void saveReturnOrderPackageItem(ReturnOrderPackageItem packageItem) throws ReturnOrderException;
	
	/**
	 * 保存包件子数据（关系）
	 * @param packageRelate
	 */
	void saveReturnOrderPackageRelate(ReturnOrderPackageRelate packageRelate) throws ReturnOrderException;
	
	/**
	 * 根据输入条件查询包件信息
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<ReturnOrderPackage> findReturnOrderPackage(Map<String, Object> parameters, Pagination pagination);
	
	/**
	 * 根据id查寻包件商品详情
	 * @param id
	 * @return
	 */
	List<ReturnOrderPackageItem> getPackageItem(Long id);
	
	/**
	 * 根据订单号，返回退货包件的运单号
	 * @return
	 */
	String getReturnExpressId(String orderid);
	
	/**
	 * 根据包件id获取包件对象
	 * @param packageid
	 * @return
	 */
	ReturnOrderPackage getPackage(Long packageid);
	
	/**
	 * 更新包件信息
	 * @param returnOrderPackage
	 */
	void update(ReturnOrderPackage returnOrderPackage);
	
	/**
	 * 更新包件关系表
	 * @param ropr
	 */
	void update(ReturnOrderPackageRelate ropr);
	
}
