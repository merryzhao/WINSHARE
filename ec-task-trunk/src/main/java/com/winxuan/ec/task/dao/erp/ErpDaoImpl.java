/*
 * @(#)ErpDaoImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.dao.erp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.erp.ErpArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryCompany;
import com.winxuan.ec.task.model.erp.ErpOrder;
import com.winxuan.ec.task.model.erp.ErpOrderInvoice;
import com.winxuan.ec.task.model.erp.ErpOrderItem;
import com.winxuan.ec.task.model.erp.ErpProductStock;
import com.winxuan.ec.task.model.erp.ErpReturnOrder;
import com.winxuan.ec.task.service.erp.impl.ErpOrderDeliveryRowMapper;
import com.winxuan.ec.task.service.erp.impl.ErpOrderItemRowMapper;
import com.winxuan.ec.task.service.erp.impl.ErpOrderRowMapper;
import com.winxuan.ec.task.service.erp.impl.ErpProductStockRowMapper;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-11-25
 */
@Component("erpDao")
public class ErpDaoImpl implements ErpDao, Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(ErpDaoImpl.class);

	private static final int PAGINATION_DEFAULT = 0;

	private static final int PAGINATION_COEFFICIENT = 1;
	
	private static final int PAGINATION_SIZE_DEFAULT = 1000;

	private static final long serialVersionUID = -6963944289940036027L;

	@Autowired
	private JdbcTemplate jdbcTemplateErp;

	@Override
	public boolean erpDoCancel(ErpOrder erpOrder) {
		return jdbcTemplateErp.queryForInt(FIND_ERP_CANCEL_ORDER,
				new Object[] { erpOrder.getOrderId() }) > 0 ? true : false;
	}

	@Override
	public boolean erpCanceled(ErpOrder erpOrder) {
		return jdbcTemplateErp.queryForInt(FIND_ERP_CANCEL_SUCCESS,
				new Object[] { erpOrder.getOrderId() }) > 0 ? true : false;

	}

	@Override
	public ErpReturnOrder getByErpReturnOrderId(ErpOrder erpOrder) {
		return jdbcTemplateErp.query(FIND_EC_RETURNORDER,
				new Object[] { erpOrder.getOrderId() },
				new ResultSetExtractor<ErpReturnOrder>() {
					@Override
					public ErpReturnOrder extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							ErpReturnOrder erpReturnOrder = new ErpReturnOrder();
							erpReturnOrder.setErpReturnOrderId(rs
									.getString("erpReturnOrder"));
							erpReturnOrder.setOrderId(rs.getString("ecOrder"));
							erpReturnOrder.setReturnOrderId(rs
									.getString("ecReturnOrder"));
							return erpReturnOrder;
						}
						return null;
					}

				});
	}

	@Override
	public void updateErpProcessStatus(String id, String status) {
		jdbcTemplateErp.update(UPDATE_ERP_FLAG, new Object[] { id, status });
		jdbcTemplateErp.update(UPDATE_ERP_MX_FLAG, new Object[] { id });
		updateErpDeliverytatus(id, status);
	}

	@Override
	public void updateErpDeliverytatus(String id, String status) {
		jdbcTemplateErp.update(UPDATE_ERP_YSDH_FLAG, new Object[] { id });
	}

	@Override
	public List<ErpOrderItem> fetchOrderStateItem(String orderId) {
		return jdbcTemplateErp.query(FIND_ORDER_STATE_ITEM,
				new Object[] { orderId }, new ErpOrderItemRowMapper());
	}

	@Override
	public void sendOrder(Object[] params) {
		jdbcTemplateErp.update(INSERT_ORDER, params);
	}

	@Override
	public void sendOrderItems(Object[] params) {
		jdbcTemplateErp.update(INSERT_ORDER_INTEM, params);
	}

	@Override
	public void sendUserInovice(Object[] params) {
		jdbcTemplateErp.update(INSERT_INVOICE, params);
	}

	@Override
	public void sendInoviceOrder(Object[] params) {
		jdbcTemplateErp.update(INSERT_ORDER, params);
	}

	@Override
	public void sendInoviceOrderItem(Object[] params) {
		jdbcTemplateErp.update(INSERT_ORDER_INTEM, params);
	}
	
	@Override
	public void sendDangDangCod(Object[] params) {
		jdbcTemplateErp.update(INSERT_DANGDANG_COD, params);
	}

	@Override
	public void sendReturnOrder(Object[] params) {
		jdbcTemplateErp.update(INSERT_RETURNORDER, params);
	}

	@Override
	public void sendReturnOrderItem(Object[] params) {
		jdbcTemplateErp.update(INSERT_RETURNORDER_ITEM, params);
	}

	@Override
	public void sendReturnOrder4Main(Object[] params) {
		jdbcTemplateErp.update(INSERT_CHARGEOFF_ORDER, params);
	}

	@Override
	public void sendReturnOrderItem4Main(Object[] params) {
		jdbcTemplateErp.update(INSERT_CHARGEOFF_ORDER_ITEM, params);
	}

	@Override
	public List<ErpOrder> fetchOrderState() {
		return jdbcTemplateErp.query(FIND_ORDER_STATE, new ErpOrderRowMapper());
	}

	@Override
	public List<ErpOrder> fetchOrderState(String orderId) {
		return jdbcTemplateErp.query(FIND_ORDER_STATE_ONE,
				new Object[] { orderId }, new ErpOrderRowMapper());
	}

	@Override
	public List<ErpOrder> fetchOrderDelivery(String orderid) {
		return jdbcTemplateErp.query(FIND_ORDER_DELIVERY,
				new Object[] { orderid }, new ErpOrderDeliveryRowMapper());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> fetchDistinctOrderDelivery() {
		List<String> list = jdbcTemplateErp.query(FIND_ORDER_DELIVERY_LIST,
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("orderId");
					}
				});
		return list == null ? Collections.EMPTY_LIST : list;
	}

	@Override
	public boolean checkDelivery(String orderid) {
		return jdbcTemplateErp.queryForInt(FIND_ERP_ZT_ORDER,
				new Object[] { orderid }) > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ErpOrder> fetchOrderReject() {
		List<ErpOrder> list = jdbcTemplateErp.query(FIND_ERP_ORDER_REJECT,
				new RowMapper<ErpOrder>() {
					@Override
					public ErpOrder mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ErpOrder order = new ErpOrder();
						order.setOrderId(rs.getString("KHDDID"));	
						order.setCuser(rs.getString("CUSER"));
						return order;
					}
				});
		return list == null ? Collections.EMPTY_LIST : list;
	}

	@Override
	public int countErpProductStock() {
		return jdbcTemplateErp.queryForInt(COUNT_ERP_PRODUCT_STOCK);
	}

	@Override
	public List<ErpProductStock> needUpdateProductStock(int pageSize,
			int pagination) {
		if (pagination < PAGINATION_DEFAULT) {
			pagination = PAGINATION_DEFAULT;
		}
		int startRow = (pagination - PAGINATION_COEFFICIENT) * pageSize;
		int maxRow = PAGINATION_COEFFICIENT * pagination * pageSize;
		List<ErpProductStock> result = jdbcTemplateErp.query(
				NEED_EPRODUCT_STOCK, new Object[] { maxRow, startRow },
				new ErpProductStockRowMapper());
		if (CollectionUtils.isEmpty(result)) {
			LOG.warn(String.format("result is empty,maxRow :%s,startRow:%s",
					maxRow, startRow));
		}
		return result;
	}

	@Override
	public void updateErpProductStockState(long merchid) {
		DateTime now = new DateTime();
		jdbcTemplateErp.update(UPDATE_ERP_PRODUCT_STATE,
				new Object[] { now.toString("yyyy-MM-dd HH:mm:ss"), merchid });
	}

	@Override
	public List<ErpOrderInvoice> fetchOrderInvoice() {
		return jdbcTemplateErp.query(FIND_ORDER_INVOICE_SQL, new RowMapper<ErpOrderInvoice>(){
			@Override
			public ErpOrderInvoice mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ErpOrderInvoice eoi = new ErpOrderInvoice();
				eoi.setId(rs.getString("KHDDH"));
				eoi.setDeliveryCode(rs.getString("YSDH"));
				eoi.setDeliveryCompany(rs.getString("CYSID"));
				eoi.setDeliveryTime(rs.getTimestamp("FDATE"));
				return eoi;
			}
		});
	}

	@Override
	public void updateOrderInvoiceStatus(String id) {
		jdbcTemplateErp.update(UPDATE_ORDER_INVOICE_STATE_SQL, new Object[] { id });
	}

	@Override
	public List<ErpArea> fetchArea(int pageNum) {
		pageNum = pageNum < PAGINATION_DEFAULT ? PAGINATION_DEFAULT : pageNum;
		int startRow = (pageNum - PAGINATION_COEFFICIENT) * PAGINATION_SIZE_DEFAULT;
		int maxRow = pageNum * PAGINATION_SIZE_DEFAULT;
		return jdbcTemplateErp.query(FIND_AREA_SQL, new Object[] {maxRow, startRow}, new RowMapper<ErpArea>(){
			@Override
			public ErpArea mapRow(ResultSet rs, int rowNum) throws SQLException {
				ErpArea ea = new ErpArea();
				ea.setId(rs.getString("id"));
				ea.setParent(rs.getString("parent"));
				ea.setName(rs.getString("name"));
				ea.setType(rs.getString("type"));
				ea.setZt(rs.getString("zt"));
				return ea;
			}
		});
	}

	@Override
	public void updateAreaStatus(String id, String type, String zt) {
		jdbcTemplateErp.update(UPDATE_AREA_STATE_SQL, new Object[] {id, type, zt});
	}

	@Override
	public void deleteArea() {
		jdbcTemplateErp.execute(DELETE_AREA_SQL);
	}

	@Override
	public List<ErpDeliveryArea> fetchDeliveryArea(int pageNum) {
		pageNum = pageNum < PAGINATION_DEFAULT ? PAGINATION_DEFAULT : pageNum;
		int startRow = (pageNum - PAGINATION_COEFFICIENT) * PAGINATION_SIZE_DEFAULT;
		int maxRow = pageNum * PAGINATION_SIZE_DEFAULT;
		return jdbcTemplateErp.query(FIND_DELIVERY_AREA_SQL, new Object[] {maxRow, startRow}, new RowMapper<ErpDeliveryArea>(){
			@Override
			public ErpDeliveryArea mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ErpDeliveryArea eda = new ErpDeliveryArea();
				eda.setPqid(rs.getString("pqid"));
				eda.setPsfs(rs.getString("psfs"));
				eda.setCysid(rs.getString("cysid"));
				eda.setType(rs.getString("type"));
				eda.setPsfw(rs.getString("psfw"));
				eda.setDc(rs.getString("ccdid"));
				eda.setMark(rs.getString("mark"));
				return eda;
			}
		});
	}

	@Override
	public void updateDeliveryAreaStatus(String pqid, 
			String cysid, String type, String mark) {
		jdbcTemplateErp.update(UPDATE_DELIVERY_AREA_STATE_SQL, new Object[] {pqid, cysid, type, mark});
	}

	@Override
	public void deleteDeliveryArea() {
		jdbcTemplateErp.execute(DELETE_DELIVERY_AREA_SQL);
	}

	@Override
	public List<ErpDeliveryCompany> fetchDeliveryCompany(int pageNum) {
		pageNum = pageNum < PAGINATION_DEFAULT ? PAGINATION_DEFAULT : pageNum;
		int startRow = (pageNum - PAGINATION_COEFFICIENT) * PAGINATION_SIZE_DEFAULT;
		int maxRow = pageNum * PAGINATION_SIZE_DEFAULT;
		return jdbcTemplateErp.query(FIND_DELIVERY_COMPANY_SQL, new Object[] {maxRow, startRow}, new RowMapper<ErpDeliveryCompany>(){
			@Override
			public ErpDeliveryCompany mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ErpDeliveryCompany edc = new ErpDeliveryCompany();
				edc.setId(rs.getString("id"));
				edc.setName(rs.getString("name"));
				edc.setType(rs.getString("type"));
				edc.setZt(rs.getString("zt"));
				return edc;
			}
		});
	}

	@Override
	public void updateDeliveryCompanyStatus(String id, String type, String zt) {
		jdbcTemplateErp.update(UPDATE_DELIVERY_COMPANY_STATE_SQL, new Object[] {id, type, zt});
	}

	@Override
	public void deleteDeliveryCompany() {
		jdbcTemplateErp.execute(DELETE_DELIVERY_COMPANY_SQL);
	}

}
