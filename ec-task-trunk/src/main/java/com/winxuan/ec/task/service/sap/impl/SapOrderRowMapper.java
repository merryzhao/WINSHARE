/*
 * @(#)ErpOrderRowMapper.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.sap.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.service.order.OrderServiceImpl;
import com.winxuan.ec.task.model.sap.SapOrder;
import com.winxuan.framework.util.StringUtils;

/**
 * description
 * @author  yangxinyi
 * 
 */
public class SapOrderRowMapper implements RowMapper<SapOrder>{
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Override
	public SapOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		SapOrder sapOrder = new SapOrder();
		sapOrder.setInterfaceId(rs.getLong("id"));
		sapOrder.setOrderId(rs.getString("_order"));
		sapOrder.setOrderItemId(rs.getString("orderitem"));
		sapOrder.setDeliveryQuantity(rs.getInt("deliveryQuantity"));
		sapOrder.setDeliveryCode(rs.getString("deliverycode"));
		sapOrder.setIflag(rs.getString("iflag"));
		try {
			if(!StringUtils.isNullOrEmpty(rs.getString("deliverytime"))){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				sapOrder.setDeliveryTime(dateFormat.parse(rs.getString("deliverytime")));
			}else{
				sapOrder.setDeliveryTime(null);
			}
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e);
		}
		return sapOrder;
	}

}

