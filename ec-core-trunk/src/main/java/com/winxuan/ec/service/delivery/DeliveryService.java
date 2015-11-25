/*
 * @(#)DeliveryService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.delivery;

import java.math.BigDecimal;
import java.util.List;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.user.Employee;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface DeliveryService { 

	/**
	 * 获得配送方式
	 * 
	 * @param id
	 *            配送方式id
	 * @return 存在返回对应的配送方式，不存在返回null
	 */
	DeliveryType getDeliveryType(Long id);

	/**
	 * 获得配送方式列表
	 * 
	 * @param onlyAvailable
	 *            是否仅返回有效的配送方式
	 * @return 配送方式列表
	 */
	List<DeliveryType> findDeliveryType(boolean onlyAvailable);

	/**
	 * 
	 * @param area
	 * @param deliveryType
	 * @param address
	 * @param employee
	 * @return
	 */
	List<DeliveryInfo> findDeliveryInfoForEmployee(Area area, DeliveryType deliveryType, 
			String address, Employee employee);
	
	/**
	 * 获得配送公司
	 * 
	 * @param id
	 *            配送公司id
	 * @return 存在返回对应的配送公司，不存在返回null
	 */
	DeliveryCompany getDeliveryCompany(Long id);

	/**
	 * 获得配送公司列表
	 * 
	 * @param onlyAvailable
	 *            是否仅返回有效的配送公司
	 * @return 配送公司列表
	 */
	List<DeliveryCompany> findDeliveryCompany(boolean onlyAvailable);


	/**
	 * 
	 * @param area
	 * @param deliveryType
	 * @param address
	 * @return
	 */
	List<DeliveryInfo> findDeliveryInfo(Area area,
			DeliveryType deliveryType, String address ) ;
	
	/**
	 * 获得配送信息
	 * 
	 * @param area
	 *            区域。查找该区域及该区域所有叶子节点区域。如果不存入则忽略区域查询条件。
	 * @param deliveryType
	 *            配送方式。如果不传入则忽略配送方式查询条件。
	 * @param hasDiy
	 *            是否需要有自提选项，由于现在无法从IP信息中获取，是否有自提交由上层调用显示决定。
	 * @return
	 */
	List<DeliveryInfo> findDeliveryInfo(Area area, DeliveryType deliveryType,
			boolean hasDiy);

	/**
	 * 更新配送方式
	 * 
	 * @param deliveryType
	 */
	void update(DeliveryType deliveryType);

	/**
	 * 更新配送公司
	 * 
	 * @param deliveryCompany
	 */
	void update(DeliveryCompany deliveryCompany);

	/**
	 * 更新配送信息
	 * 
	 * @param deliveryInfo
	 */
	void update(DeliveryInfo deliveryInfo);

	/**
	 * 得到配送信息
	 * 
	 * @param id
	 */
	DeliveryInfo getDeliveryInfo(Long id);

	/**
	 * 删除配送信息
	 * 
	 * @param deliveryInfo
	 */
	void delete(DeliveryInfo deliveryInfo);

	/**
	 * 根据配送方式、区域、码洋计算运费
	 * 
	 * @param deliveryType
	 *            配送方式
	 * @param area
	 *            区域
	 * @param listPrice
	 *            码洋
	 * @return
	 */
	BigDecimal getDeliveryFee(DeliveryType deliveryType, Area area,
			BigDecimal listPrice);
	
	/**
	 * 根据渠道查找默认配送方式
	 * 默认从缓存中加载
	 * @param channel
	 * @param district
	 * @return
	 */
	DeliveryType findDefaultDeliveryType(Channel channel,Area district);
}
