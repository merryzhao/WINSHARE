/*
 * @(#)ErpOrderRowMapper.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.erp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.service.order.OrderServiceImpl;
import com.winxuan.ec.task.model.erp.ErpOrder;
import com.winxuan.framework.util.StringUtils;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-29
 */
public class ErpOrderDeliveryRowMapper implements RowMapper<ErpOrder>{
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	@Override
	public ErpOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		ErpOrder erpOrder = new ErpOrder();
		erpOrder.setCode(rs.getString("code"));
		erpOrder.setDeliveryCompany(rs.getString("deliveryCompany"));
		erpOrder.setOrderId(rs.getString("orderId"));
		erpOrder.setState(rs.getString("state"));
		erpOrder.setDdlxid(rs.getString("ddlxid"));
		erpOrder.setDc(rs.getString("dc"));
		try {
			if(!StringUtils.isNullOrEmpty(rs.getString("FHRQ"))){
				erpOrder.setFhrq(DATE_FORMAT.parse(rs.getString("FHRQ")));
			}else{
				erpOrder.setFhrq(null);
			}
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e);
		}
		return erpOrder;
	}

}

