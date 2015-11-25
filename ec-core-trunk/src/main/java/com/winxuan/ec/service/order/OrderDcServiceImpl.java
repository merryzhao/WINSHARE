/*
 * @(#)OrderDcService.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.OrderDistributionCenterDao;
import com.winxuan.ec.dao.OrderItemStockDao;
import com.winxuan.ec.dao.OrderUpdateLogDao;
import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.exception.OrderDcMatchException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcPriority;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderItemStock;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.OrderUpdateLog;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 上午11:29:36 --!
 * @description:
 ******************************** 
 */
@Service("orderDcService")
@Transactional(rollbackFor = Exception.class)
public class OrderDcServiceImpl implements OrderDcService {

	private static final Log LOG = LogFactory.getLog(OrderDcServiceImpl.class);

	@InjectDao
	private OrderUpdateLogDao orderUpdateLogDao;

	@InjectDao
	private OrderItemStockDao orderItemStockDao;

	@Autowired
	private DcService dcService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private ProductSaleStockService productSaleStockService;	

	@InjectDao
	private OrderDistributionCenterDao orderDistributionCenterDao;
	
	@Override
	public OrderDistributionCenter createOrderDcNew(Order order) throws OrderDcMatchException {
		boolean isWinXuan = Shop.WINXUAN_SHOP.equals(order.getShop().getId());
		boolean isWinXuanVirtual = order.isVirtual()&&isWinXuan;
		if (!isWinXuan||isWinXuanVirtual) {
			OrderDistributionCenter orderDistributionCenter = order.getDistributionCenter();
			orderDistributionCenter.setOrder(order);
			orderDistributionCenter.setDcOriginal(new Code(Code.DC_MALL));
			orderDistributionCenter.setDcDest(new Code(Code.DC_MALL));
			orderDistributionCenter.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_COMPUTE));
			order.setDistributionCenter(orderDistributionCenter);
			return orderDistributionCenter;
		}
		OrderDistributionCenter orderDistributionCenter = getDefaultDcDestByOrder(order); //得到默认ODc
		List<DcPriority> dps = dcService.findDcPriorityByArea(order.getConsignee().getProvince());//按区域优先级查出可用的dc
		excludeDps(order,dps);//当当cod除去D819
		getDcSatisfactionRate(order,dps,null);//得到各仓的总量	
		if(orderDistributionCenter.getDcDest() != null){  //已确定目标
			appointedDestOdc(orderDistributionCenter,dps,order);
		}else{
			Code destDc = completeFillOdc(dps, order); // 完全满足，不需要集货
			if (destDc != null) {
				orderDistributionCenter.setNeedColl(false); 
			} else {
				destDc = partFillOdc(order, dps, orderDistributionCenter);// 不完全满足
			}
			orderDistributionCenter.setDcDest(destDc);
			orderDistributionCenter.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_COMPUTE));
		}
		if (orderDistributionCenter.getDcDest() == null) {		
			LOG.info("order dc match failure");
			throw new OrderDcMatchException(order, "order dc match failure.");
		}
		orderDistributionCenter.setDcOriginal(orderDistributionCenter.getDcDest());
		if(StringUtils.isBlank(orderDistributionCenter.getRemark())){
			orderDistributionCenter.setRemark(orderDistributionCenter.getDcDest().getName());
		}
		orderDistributionCenter.setOrder(order);
		order.setDistributionCenter(orderDistributionCenter);
		return orderDistributionCenter; 
	}
	
	/**
	 * 当当cod，不走D819
	 * @param order
	 * @param dps
	 */
	private void excludeDps(Order order,List<DcPriority> dps){
		if(isDangDangCod(order)){
			DcPriority dcPriority = null;
			for(DcPriority dp : dps){
				if(Code.DC_D819.equals(dp.getLocation())){
					dcPriority = dp;
				}
			}
			dps.remove(dcPriority);
		}
	}
	
	/**
	 * 确定是否默认指定目标仓
	 * @param order
	 * @return
	 */
	private  OrderDistributionCenter getDefaultDcDestByOrder(Order order){
		OrderDistributionCenter orderDistributionCenter = order.getDistributionCenter();
		orderDistributionCenter.setStrategy(new Code(Code.ORDER_STRATEGY_OUTER_APPOINT));
		if(orderDistributionCenter.getDcDest() == null && isDangDangCod(order) && order.getConsignee().getProvince().isSouthWest()) {
			orderDistributionCenter.setDcDest(new Code(Code.DC_8A17));
			orderDistributionCenter.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_COMPUTE));
			return orderDistributionCenter;
		}	
		return orderDistributionCenter;
	}
	@Override
	public void getDcSatisfactionRate(Order o,List<DcPriority> dps,List<Long> exculdeDcs){
		if(CollectionUtils.isEmpty(dps)){
			return;
		}
		if(CollectionUtils.isEmpty(exculdeDcs)){
			exculdeDcs = new ArrayList<Long>();
		}
		int num;
		for(DcPriority dp : dps){
			num = 0;
			if(dp.isAvailable()){
				for(OrderItem item : o.getItemList()){
					int collNum = item.getPurchaseQuantity();
					int exculdeNum = MagicNumber.ZERO;
					for(Long excludeDc : exculdeDcs){
						exculdeNum += item.getProductSaleStockVoAvailableQuantity(excludeDc);
					}
					collNum = item.getPurchaseQuantity() - exculdeNum > MagicNumber.ZERO ? item.getPurchaseQuantity() - exculdeNum : MagicNumber.ZERO;
					int available = item.getProductSaleStockVoAvailableQuantity(dp.getLocation());
					num += collNum >= available ? available : collNum;
				}
			}
			dp.setNumber(num);		
		}
		
	}
	
	/**
	 *已经指定dc的情况，生成集货仓的满足优先级排序
	 */
	private  OrderDistributionCenter  appointedDestOdc(OrderDistributionCenter orderDistributionCenter,List<DcPriority> dps,Order order){
		if (orderDistributionCenter.getDcDest() != null) {
			if (!order.isCollection()) {
				return orderDistributionCenter;
			}
			List<Long> excludDcs = new ArrayList<Long>();
			excludDcs.add(orderDistributionCenter.getDcDest().getId());
			getDcSatisfactionRate(order, dps, excludDcs);
			satisfactionRateSort(dps);
			orderDistributionCenter.setDps(dps);
			return orderDistributionCenter;
		}
		return null;
	}
	/**
	 * 更加满足率从大到小排序,满足率相等比较区域优先级
	 * @param dps
	 */
	@Override
	public void satisfactionRateSort(List<DcPriority> dps){
		Collections.sort(dps,new Comparator<DcPriority>(){
			public int compare(DcPriority dp1,DcPriority dp2){
				if (dp2.getNumber().compareTo(dp1.getNumber())==0){
					return dp1.getPriority().compareTo(dp2.getPriority());
				}
				return dp2.getNumber().compareTo(dp1.getNumber());
			}
		});
	}

	/**
	 * 完全满足
	 * @param orderDistributionCenter
	 * @param list
	 * @param order
	 * @return
	 */
	private Code completeFillOdc(List<DcPriority> dps,Order order){
		for(DcPriority dp : dps){
			if(dp.getNumber() == order.getPurchaseQuantity()){
				return new Code(dp.getLocation());
			}
		}	
		return null;
	}

	/**
	 * 部分满足、都不满足确定目标DC
	 * @param order
	 * @param dps
	 * @param orderDistributionCenter
	 * @param dcs
	 * @return
	 */
	private Code partFillOdc(Order order ,List<DcPriority> dps,OrderDistributionCenter orderDistributionCenter){
		if(CollectionUtils.isEmpty(dps)){
			return null;
		}
		DcPriority dp = dps.get(0);
		Code destDc = new Code (dp.getLocation());
		if(!order.isCollection()){//不集货确定目标dc
			satisfactionRateSort(dps);
			dp = dps.get(0);
			return new Code(dp.getLocation());
		}	
		if(dp.getNumber() != 0){//只要区域优先的情况不为0就是目标dc
			orderDistributionCenter.setDcDest(destDc);
			appointedDestOdc(orderDistributionCenter,dps,order);
		}else{
			dps.remove(dp);
			satisfactionRateSort(dps);
			dp = dps.get(0);
			if (dp.getNumber() == 0) { // 满足率最大的都为0的情况，所有仓库都是0
				orderDistributionCenter.setCanColl(false);
			} else {
				destDc = new Code(dp.getLocation());
				dps.remove(dp);
				orderDistributionCenter.setDps(dps);
			}
			}
		return destDc;
	}

	
	private boolean isDangDangCod(Order order) {
		if(8097L == order.getChannel().getId()) {
			for (OrderPayment orderPayment : order.getPaymentList()) {
				Payment payment = paymentService.get(orderPayment.getPayment().getId());
				if (Code.PAYMENT_TYPE_COD.equals(payment.getType().getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isJd(Order order){
		return 8096L == order.getChannel().getId();
	}


	@Override
	public Boolean updateOrderDcOriginal(Order order, Code dc, User user)
			throws BusinessException {

		OrderDistributionCenter od = orderDistributionCenterDao.get(order
				.getId());
		//移动之前的库位信息
		Code oldDc = od.getDcOriginal();
		od.setDcOriginal(dc);
		Set<OrderItem> orderItemList = order.getItemList();
		updateOrderItemStock(order);
		for (OrderItem orderItem : orderItemList) {
			ProductSale ps = orderItem.getProductSale();
			productSaleStockService.updateQuantity(dc, ps, 0, orderItem.getPurchaseQuantity());
			productSaleStockService.updateQuantity(oldDc, ps, 0, -orderItem.getPurchaseQuantity());
			orderService.increaseStockLockQuantity(orderItem);
		}
		orderDistributionCenterDao.update(od);
		OrderUpdateLog log = new OrderUpdateLog(user, order, OrderUpdateLog.ORIGINAL_DC, oldDc.getName(), dc.getName());
		orderUpdateLogDao.save(log);
		return true;
	}


	@Override
	public void saveOrderItemStock(Order order) throws OrderStockException {
		Set<OrderItem> orderItems = order.getItemList();
		Set<OrderItemStock> stocks = new HashSet<OrderItemStock>();
		for(OrderItem orderItem : orderItems){
			ProductSale productSale = orderItem.getProductSale();
			OrderItemStock orderItemStock = new OrderItemStock();
			orderItemStock.setOrder(order);
			orderItemStock.setProductSale(productSale);
			orderItemStock.setStock(productSale.getStockQuantity());
			orderItemStock.setStockInfo(productSaleStockService.parseStockInfo4Json(orderItem.getProductSaleStockVos()));
			stocks.add(orderItemStock);
		}
		order.setOrderItemStocks(stocks);
	}

	@Override
	public void updateOrderItemStock(Order order) throws OrderStockException {
		Set<OrderItemStock> stocks = order.getOrderItemStocks();
		if(stocks != null){
			for(OrderItemStock stock : stocks){
				ProductSale productSale = stock.getProductSale();
				stock.setStock(productSale.getStockQuantity());
//				stock.setStockInfo(productSaleStockService.parseStockInfo4Json(productSale));
			}
		}else{
			saveOrderItemStock(order);
		}
	}


	@Override
	public OrderItemStock getOrderItemStock(OrderItem orderItem) {
		return orderItemStockDao.getByOrderItem(orderItem);
	}
}
