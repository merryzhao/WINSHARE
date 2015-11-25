/*
 * @(#)OrderShipperInfoServiceImpl.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winxuan.ec.dao.OrderShipperInfoDao;
import com.winxuan.ec.exception.OrderShipperInfoException;
import com.winxuan.ec.exception.OrderShipperInfoMatchException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderCourierReceipt;
import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.order.OrderShipperInfo;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.services.dms.service.DmsShipperCodeService;
import com.winxuan.services.dms.vo.parameter.DmsOrderVo;
import com.winxuan.services.dms.vo.parameter.ShipperCodeStatusVo;
import com.winxuan.services.dms.vo.result.ResultBooleanVo;
import com.winxuan.services.dms.vo.result.ShipperCodeVo;
import com.winxuan.services.support.exception.RemoteBusinessException;

/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2015-1-15
 */
@Service("orderShipperInfoService")
@Transactional(rollbackFor = Exception.class)
public class OrderShipperInfoServiceImpl implements OrderShipperInfoService {

	/**
	 * 匹配DMS的配送方式
	 */
	private static final Map<Long, Long> DELIVERY_TYPE = Maps.newHashMap();
	
	/**
	 * DMS运单号的key
	 */
	private static final String DELIVERY_CODE = "DELIVERY_CODE";

	static {
		DELIVERY_TYPE.put(DeliveryType.FLAT_POST, 104L);
		DELIVERY_TYPE.put(DeliveryType.FLAT_POST_SMALL, 104L);
		DELIVERY_TYPE.put(DeliveryType.EMS, 101L);
		DELIVERY_TYPE.put(DeliveryType.EXPRESS, 102L);
		DELIVERY_TYPE.put(DeliveryType.DIY, 103L);
	}

	@Autowired
	private DmsShipperCodeService dmsShipperCodeService;

	@InjectDao
	private OrderShipperInfoDao orderShipperInfoDao;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DeliveryService deliveryService;

	@Override
	public void matchingOrderShipperInfo(Order order) throws OrderShipperInfoMatchException {
		Code processStatus = order.getProcessStatus();
		long status = processStatus.getId();
		if (!Code.ORDER_PROCESS_STATUS_NEW.equals(status) && !Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(status)) {
			throw new OrderShipperInfoMatchException(order, "订单状态应该为已提交状态或者等待配货状态!");
		}
		DmsOrderVo dmsOrderVo = buildDmsOrderVo(order);
		try {
			ShipperCodeVo shipperCodeVo = this.dmsShipperCodeService.getShipperCodeByOrder(dmsOrderVo);
			validateShipperCodeVo(order, shipperCodeVo);
			processDeliveryCompany(order, shipperCodeVo);
			// 判断是否已经存在承运商信息，存在就修改，不存在就新增
			OrderShipperInfo orderShipperInfo = this.orderShipperInfoDao.get(order.getId());
			if (null != orderShipperInfo) {
				buildOrderShipperInfo(shipperCodeVo, orderShipperInfo);
				this.orderShipperInfoDao.update(orderShipperInfo);
			} else {
				orderShipperInfo = new OrderShipperInfo();
				buildOrderShipperInfo(shipperCodeVo, orderShipperInfo);
				orderShipperInfo.setOrderId(order.getId());
				orderShipperInfo.setCreateTime(new Date());
				this.orderShipperInfoDao.save(orderShipperInfo);
			}
		} catch (RemoteBusinessException e) {
			throw new OrderShipperInfoMatchException(order, e.getMessage());
		}
	}

	/**
	 * 处理订单承运商和运单号回填
	 * @param order
	 * @param shipperCodeVo
	 * @throws OrderShipperInfoMatchException
	 */
	private void processDeliveryCompany(Order order, ShipperCodeVo shipperCodeVo)
			throws OrderShipperInfoMatchException {
		DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(shipperCodeVo.getShipperId());
		if (null == deliveryCompany) {
			throw new OrderShipperInfoMatchException(order, "DMS系统匹配了一个EC系统不存在的承运商，请及时同步EC系统承运商信息！");
		} else {
			order.setDeliveryCompany(deliveryCompany);
		}
		order.setDeliveryCode(shipperCodeVo.getShipperCode());
		try {
			this.orderService.auditShipperInfo(order, new Employee(Employee.SYSTEM));
		} catch (OrderStatusException e) {
			throw new OrderShipperInfoMatchException(order, e.getMessage());
		}
	}

	/**
	 * 承运商信息回填
	 * @param shipperCodeVo
	 * @param orderShipperInfo
	 */
	private void buildOrderShipperInfo(ShipperCodeVo shipperCodeVo,
			OrderShipperInfo orderShipperInfo) {
		orderShipperInfo.setCarriageType(shipperCodeVo.getTransportType());
		orderShipperInfo.setJobType(shipperCodeVo.getJobType());
		orderShipperInfo.setClientId(shipperCodeVo.getSpecialClientId());
		orderShipperInfo.setDeliveryPlace(shipperCodeVo.getDeliveryPlaceId());
		orderShipperInfo.setDryline(shipperCodeVo.getTransportCompanyId());
		orderShipperInfo.setStatus(OrderShipperInfo.MACHING_STATUS);
	}

	/**
	 * 校验DMS返回的承运商信息的有效性
	 * 
	 * @param order
	 * @param shipperCodeVo
	 * @throws OrderShipperInfoMatchException
	 */
	private void validateShipperCodeVo(Order order, ShipperCodeVo shipperCodeVo) throws OrderShipperInfoMatchException {
		if (null == shipperCodeVo) {
			throw new OrderShipperInfoMatchException(order, "匹配订单承运商信息ShipperCodeVo is null！");
		}
		if (null == shipperCodeVo.getShipperId()) {
			throw new OrderShipperInfoMatchException(order, "匹配订单承运商信息返回承运商id is null！");
		}
		if (null == shipperCodeVo.getShipperCode()) {
			throw new OrderShipperInfoMatchException(order, "匹配订单承运商信息返回承运商运单号 is null！");
		}
		if (null == shipperCodeVo.getJobType()) {
			throw new OrderShipperInfoMatchException(order, "匹配订单承运商信息返回承运作业类型 is null！");
		}
		if (null == shipperCodeVo.getTransportType()) {
			throw new OrderShipperInfoMatchException(order, "匹配订单承运商信息返回承运运输类型 is null！");
		}
	}

	/**
	 * 组建DmsOrderVo对象
	 * 
	 * @param order
	 *            EC订单
	 * @return
	 * @throws IllegalArgumentException
	 *             if order is null
	 */
	private DmsOrderVo buildDmsOrderVo(Order order) {
		long status = order.getProcessStatus().getId();
		OrderConsignee consignee = order.getConsignee();
		DmsOrderVo dmsOrderVo = new DmsOrderVo();
		dmsOrderVo.setOrderId(order.getId());
		dmsOrderVo.setOrderStatus(status);
		// 最末一节Area的id
		dmsOrderVo.setLastAreaId(consignee.getTown().getId());
		dmsOrderVo.setAddress(consignee.getAddress());
		dmsOrderVo.setZipCode(consignee.getZipCode());
		dmsOrderVo.setConsignee(consignee.getConsignee());
		String mobilePhone = consignee.getMobile();
		dmsOrderVo.setPhone(StringUtils.isNotBlank(mobilePhone) ? mobilePhone : consignee.getPhone());
		// 重量单位为kg
		BigDecimal weight = new BigDecimal(order.getOrderWeight(Order.PURCHASE_QUANTITY)).divide(new BigDecimal(
				MagicNumber.THOUSAND), 2, BigDecimal.ROUND_HALF_UP);
		dmsOrderVo.setWeight(weight);
		dmsOrderVo.setListPrice(order.getListPrice());
		dmsOrderVo.setSalePrice(order.getSalePrice());
		dmsOrderVo.setEcOrderCreateTime(order.getCreateTime());
		dmsOrderVo.setRequidPayMoney(order.getRequidPayMoney());
		dmsOrderVo.setDeliveryType(DELIVERY_TYPE.get(order.getDeliveryType().getId()));
		// 已经设置了承运商的直接回传DMS
		DeliveryCompany deliveryCompany = order.getDeliveryCompany();
		if (null != deliveryCompany && null != deliveryCompany.getId()) {
			dmsOrderVo.setSelectShipper(Boolean.TRUE);
			dmsOrderVo.setShipperId(deliveryCompany.getId());
		} else {
			dmsOrderVo.setSelectShipper(Boolean.FALSE);
			dmsOrderVo.setShipperId(null);
		}
		dmsOrderVo.setChannleId(order.getChannel().getId());
		dmsOrderVo.setPayType(order.getPayType().getId());
		// 发货dc
		dmsOrderVo.setDc(order.getDistributionCenter().getDcOriginal().getId());
		// 针对不同的渠道传入特殊的匹配规则
		dmsOrderVo.setPlaceStrategyPara(processSpecificShipper(order));
		return dmsOrderVo;
	}

	/**
	 * 处理可能存在的特殊的承运商信息，比如当当COD对应不同的交货地
	 * 
	 * @param order
	 * @return
	 */
	private Map<String, String> processSpecificShipper(Order order) {
		Map<String, String> map = Maps.newHashMap();
		// 当当COD不同的交货地对应的是key是渠道id，value是承运商id
		if (Channel.CHANNEL_ID_DANGDANG.equals(order.getChannel().getId()) && order.isCODOrder()) {
			String warehouse = OrderExtend.getCodWarehouseData(order);
			// 101-当当成都仓,100-当当北京仓
			String value = warehouse.equals(OrderExtend.DANGDANG_COD_WAREHOUSE_CHENGDU) ? "101" : "100";
			map.put(Channel.CHANNEL_ID_DANGDANG + "", value);
			// 当当cod运单号就是外部订单号
			map.put(DELIVERY_CODE, order.getOuterId());
		}
		// 京东COD运单号
		if(Channel.CHANNEL_ID_360BUY.equals(order.getChannel().getId()) && order.isCODOrder()) {
			String value = OrderExtend.getExtendValueByKey(order);
			map.put(DELIVERY_CODE, getDeliveryCode(value));
		}
		return map;
	}

	/**
	 * 获取京东COD运单号
	 * @param value
	 * @return
	 */
	private String getDeliveryCode(String value) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		try {
			OrderCourierReceipt orderCourierReceipt = mapper.readValue(value, OrderCourierReceipt.class);
			if (null != orderCourierReceipt) {
				return orderCourierReceipt.getWaybillNumber();
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("简析京东面单信息报错！");
		}
	}

	@Override
	public void backfillOrderShipperInfo(Order order) throws OrderShipperInfoException {
		Preconditions.checkArgument(null != order, "参数 order is null!");
		Code processStatus = order.getProcessStatus();
		long status = processStatus.getId();
		Preconditions.checkState((!Code.ORDER_PROCESS_STATUS_DELIVERIED.equals(status)
				|| !Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG.equals(status) || !order.isCanceled()),
				"订单状态应该为发货状态或者取消状态!");
		ShipperCodeStatusVo shipperCodeStatusVo = new ShipperCodeStatusVo();
		List<String> shipperCodeList = Lists.newArrayList();
		DeliveryCompany deliveryCompany = order.getDeliveryCompany();
		shipperCodeStatusVo.setOrderId(order.getId());
		shipperCodeStatusVo.setStatus(status);
		shipperCodeStatusVo.setName(processStatus.getName());
		shipperCodeStatusVo.setTimestamp(System.currentTimeMillis());
		shipperCodeStatusVo.setShipperId(null == deliveryCompany ? null : deliveryCompany.getId());
		Set<OrderDeliverySplit> deliverySplits = order.getDeliverySplits();
		if (CollectionUtils.isNotEmpty(deliverySplits)) {
			for (OrderDeliverySplit orderDeliverySplit : deliverySplits) {
				shipperCodeList.add(orderDeliverySplit.getCode());
			}
		}
		shipperCodeStatusVo.setShipperCodeList(shipperCodeList);
		try {
			ResultBooleanVo result = this.dmsShipperCodeService.updateShipperCodeStatus(shipperCodeStatusVo);
			if (!result.getResult()) {
				throw new OrderShipperInfoException(order, "回传DMS状态失败!");
			}
		} catch (RemoteBusinessException e) {
			throw new OrderShipperInfoException(order, e.getMessage());
		}

	}

	@Override
	public OrderShipperInfo getOrderShipperInfo(String orderId) {
		return this.orderShipperInfoDao.get(orderId);
	}

	@Override
	public void save(OrderShipperInfo orderShipperInfo) {
		this.orderShipperInfoDao.save(orderShipperInfo);
	}

	@Override
	public List<OrderShipperInfo> find(Pagination pagination) {
		List<OrderShipperInfo> orderShipperInfos = Lists.newArrayList();
		List<Map<String, String>> find = this.orderShipperInfoDao.find(pagination);
		for (Map<String, String> map : find) {
			OrderShipperInfo orderShipperInfo = new OrderShipperInfo();
			orderShipperInfo.setOrderId(map.get("_order"));
			orderShipperInfo.setErrorMessage(map.get("errormessage"));
			orderShipperInfos.add(orderShipperInfo);
		}
		return orderShipperInfos;
	}

	@Override
	public long countNeedAuditShipperInfo() {
		return this.orderShipperInfoDao.countNeedAuditShipperInfo();
	}

	@Override
	public void update(OrderShipperInfo orderShipperInfo) {
		this.orderShipperInfoDao.update(orderShipperInfo);
	}

}
