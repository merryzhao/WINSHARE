/*
 * @(#)ErpOrderRowMapper.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.erp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.task.model.erp.ErpOrderItem;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-29
 */
public class ErpOrderItemRowMapper implements RowMapper<ErpOrderItem>{

	@Override
	public ErpOrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		ErpOrderItem erpOrderItem = new ErpOrderItem();
		erpOrderItem.setOrderId(rs.getLong("orderid"));
		erpOrderItem.setCommodity(rs.getLong("commodity"));
		erpOrderItem.setQuantity(rs.getInt("quantity"));
		erpOrderItem.setOutOfStockQuantity(rs.getInt("outofstockquantity"));
		return erpOrderItem;
	}

}

