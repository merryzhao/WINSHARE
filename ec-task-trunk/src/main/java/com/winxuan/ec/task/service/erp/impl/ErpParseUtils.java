/*
 * @(#)ErpParseUtils.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.erp.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.framework.util.StringUtils;

/**
 * EQ 订单信息转换工具类
 * 
 * @author HideHai
 * @version 1.0,2011-12-22
 */
public class ErpParseUtils {

	private static final Map<Long, String> DELIVERY_TYPE_MAP = new HashMap<Long, String>();
	private static final Map<Long, String> CHANNEL_TYPE = new HashMap<Long, String>();
	private static final Map<Long, String> PRODUCT_SORT = new HashMap<Long, String>();

	static {
		DELIVERY_TYPE_MAP.put(DeliveryType.FLAT_POST, "1");// 平邮
		DELIVERY_TYPE_MAP.put(DeliveryType.EMS, "2"); // EMS
		DELIVERY_TYPE_MAP.put(DeliveryType.DIY, "4"); // 自提
		DELIVERY_TYPE_MAP.put(DeliveryType.FLAT_POST_SMALL, "6");// 平邮小包

		CHANNEL_TYPE.put(Code.CHANNEL_TYPE_NORMAL_SALE, "2");
		CHANNEL_TYPE.put(Code.CHANNEL_TYPE_NORMAL, "3");
		CHANNEL_TYPE.put(Code.CHANNEL_TYPE_PROFESSION_SALE, "4");
		CHANNEL_TYPE.put(Code.CHANNEL_TYPE_PROFESSION, "5");
		CHANNEL_TYPE.put(Code.CHANNEL_TYPE_UNION, "6");
		CHANNEL_TYPE.put(Code.CHANNEL_TYPE_OTHER, "7");

		PRODUCT_SORT.put(Code.PRODUCT_SORT_BOOK, "1");
		PRODUCT_SORT.put(Code.PRODUCT_SORT_VIDEO, "2");
		PRODUCT_SORT.put(Code.PRODUCT_SORT_MERCHANDISE, "3");
	}


	/**
	 * 处理订单地址
	 * 
	 * @param country
	 * @param provice
	 * @param city
	 * @param district
	 * @param addr
	 * @return
	 */
	protected static String parseAddress(Area country, Area provice, Area city,
			Area district, Area town,String addr) {
		StringBuffer result = new StringBuffer();
		StringBuffer prefixAddr = new StringBuffer();
		result.append(country.getName());
		prefixAddr.append(provice.getName());
		prefixAddr.append(city.getName());
		prefixAddr.append(district.getName());
		prefixAddr.append(town.getName());
		if (!StringUtils.isNullOrEmpty(addr)) {
			result.append(" ");
			if (addr.startsWith(prefixAddr.toString())) {
				result.append(addr);
			} else {
				result.append(prefixAddr);
				result.append(" ");
				result.append(addr);
			}
		}
		return result.toString();
	}

	/**
	 * 订单的缺货处理方式
	 * 
	 * @param consignee
	 * @return 1: 缺货整单取消 0:发送有的商品
	 */
	protected static String parseOutOfStockOption(OrderConsignee consignee) {
		Code outOption = consignee.getOutOfStockOption();
		if (outOption.getId().equals(Code.OUT_OF_STOCK_OPTION_CANCEL)) {
			return "1";
		}
		return "0";
	}

	/**
	 * 获取ERP支持的配送方式
	 * D818订单直接设定为快递
	 * @param order
	 * @return
	 */
	protected static String parseDeliveryType(Order order) {
		Long dc = order.getDistributionCenter().getDcOriginal().getId();
		if(dc.equals(Code.DC_D818)){
			return "3";
		}
		String deliveryType = DELIVERY_TYPE_MAP.get(order.getDeliveryType()
				.getId());
		if (deliveryType == null) {
			deliveryType = "3";// 快递
		}
		return deliveryType;
	}

	/**
	 * 得到商品分类
	 * 
	 * @param sort
	 * @return
	 */
	protected static String parseProductSort(Product product) {
		return PRODUCT_SORT.get(product.getSort().getId());
	}

	/**
	 * 得到下传ERP对应的卖家编号
	 * 
	 * @param shop
	 * @return
	 */
	protected static String parseErpShopId(Shop shop) {
		if (shop.getId().equals(Shop.WINXUAN_SHOP)) {
			return "1035";
		} else {
			return shop.getId().toString();
		}
	}

	/**
	 * 渠道类型
	 * 
	 * @param order
	 * @return
	 */
	protected static String parseChannelType(Order order) {
		Code typeCode = order.getChannel().getType();
		return CHANNEL_TYPE.get(typeCode.getId());
	}

	/**
	 * 送货时间
	 * 
	 * @param consignee
	 * @return
	 */
	protected static String parseDeliveryTimeDesc(OrderConsignee consignee) {
		if (consignee.getDeliveryOption().getId() == Code.DELIVERY_OPTION_WORK_DAY) {
			return "周一至周五";
		}
		if (consignee.getDeliveryOption().getId() == Code.DELIVERY_OPTION_WEEKEND) {
			return "周六周日";
		}
		return "";
	}

	/**
	 * 获取ERP支持的退换货方式
	 * 
	 * @param returnOrder
	 * @return
	 */
	protected static String parseReturnGoodsMeans(ReturnOrder returnOrder) {
		Code pickUpCode = returnOrder.getPickup();
		if (pickUpCode.getId().equals(Code.RETURN_ORDER_STYLE_DOOR)) {
			return "2";
		}
		return "3";
	}

	/**
	 * 得到对应的支付状态
	 * 
	 * @param order
	 * @return
	 */
	protected static String parsePaymentStatus(Order order) {
		Code paymentStatusCode = order.getPaymentStatus();
		if (paymentStatusCode.getId().equals(Code.ORDER_PAY_STATUS_COMPLETED)) {
			return "1";
		}
		return "2";
	}

	/**
	 * 获取对应区域
	 * 
	 * @param consignee
	 * @return
	 */
	protected static String parseArea(OrderConsignee consignee) {
		Area area = consignee.getTown();
		if (area == null) {
			area = consignee.getDistrict();
		}
		if (area != null) {
			return area.getId().toString();
		}
		return null;
	}

	/**
	 * 订单运单是否已付
	 * 
	 * @param order
	 * @return
	 */
	protected static String parsePayDeliveryFee(Order order) {
		Long payTypeId = order.getPayType().getId();
		if (payTypeId.equals(Code.ORDER_PAY_TYPE_FIRST_DELIVERY)) {
			return "false";
		} else {
			BigDecimal fee = order.getDeliveryFee();
			if (fee.compareTo(BigDecimal.ZERO) != 0) {
				return "true";
			}
			return "false";
		}
	}

}
