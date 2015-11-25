/*
 * @(#)ecDaoImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.dao.ec;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.model.erp.ErpArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryCompany;
import com.winxuan.ec.task.model.erp.ErpOrder;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-11-27
 */
@Component("ecDao")
public class EcDaoImpl implements EcDao, Serializable {

	private static final long serialVersionUID = 410094693480354294L;
	// private static final Log LOG =
	// LogFactory.getLog(TransferOrderStatus.class);

	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;

	/**
	 * 使用API回传订单状态的渠道回传
	 * 
	 * @param order
	 */
	@Override
	public void sendChannelOrder(Order order) {
		jdbcTemplateEcCore.update(
				INSERT_EC2CHANNEL,
				new Object[] {
						order.getId(),
						order.getOuterId(),
						order.getChannel().getId(),
						order.getProcessStatus().getId(),
						order.getDeliveryCompany() == null ? null : order
								.getDeliveryCompany().getId(),
						order.getDeliveryCode() });
	}

	@Override
	public void sendChannelReturnOrder(ReturnOrder returnOrder) {
		jdbcTemplateEcCore.update(INSERT_RETURNORDER_EC2CHANNEL, new Object[] {
				returnOrder.getId(), returnOrder.getStatus().getId() });
	}

	@Override
	public void saveBlockOrder(ErpOrder erpOrder, int status) {
		int result = jdbcTemplateEcCore.queryForInt(CHECK_ERP_BLOCK,
				new Object[] { erpOrder.getOrderId() });
		Date createTime = erpOrder.getFhrq() == null ? new Date() : erpOrder
				.getFhrq();
		if (result == MagicNumber.ZERO) {
			jdbcTemplateEcCore.update(INSERT_ERP_BLOCK,
					new Object[] { erpOrder.getOrderId(), status, createTime,
							erpOrder.getCuser() });
		}
	}

	@Override
	public List<String> fetchNeedProcessBlockOrder() {
		return jdbcTemplateEcCore.queryForList(SELECT_ERP_BLOCK, String.class);
	}

	@Override
	public void updateBlockOrder(ErpOrder erpOrder, int status) {
		jdbcTemplateEcCore.update(UPDATE_ERP_BLOCK, new Object[] { status,
				erpOrder.getOrderId() });
	}

	@Override
	public void updateBlockOrder(String orderId, int status) {
		jdbcTemplateEcCore.update(UPDATE_ERP_BLOCK, new Object[] { status,
				orderId });
	}

	@Override
	public void saveDelivery(String orderId, String company, String code,
			Date deiveryTime) {
		jdbcTemplateEcCore.update(INSERT_DELIVERY_SPLIT, new Object[] {
				orderId, company, code, deiveryTime });
	}

	@Override
	public List<String> findReturnDelay() {
		return jdbcTemplateEcCore.queryForList(SELECT_RETURN_DELAY,
				String.class);
	}

	@Override
	public void saveOrderReturnMonitorResult(Integer delayNum) {
		jdbcTemplateEcCore.update(UPDATE_ORDER_RETURN_MONITOR, delayNum);
	}

	@Override
	public void updateOrderInvoiceDelivery(String deliveryCode,
			Integer deliveryCompany, Date deliveryTime, String id) {
		jdbcTemplateEcCore.update(UPDATE_ORDER_INVOICE_SQL, new Object[] {
				deliveryCode, deliveryCompany, deliveryTime, id });
	}

	@Override
	public void saveArea(ErpArea erpArea) {
		jdbcTemplateEcCore.update(INSERT_AREA, new Object[] { erpArea.getId(),
				erpArea.getParent(), erpArea.getName(), erpArea.getZt(),
				erpArea.getType() });
	}

	@Override
	public void saveDeliveryArea(ErpDeliveryArea erpDeliveryArea) {
		jdbcTemplateEcCore.update(INSERT_DELIVERY_AREA, new Object[] {
				erpDeliveryArea.getPqid(), erpDeliveryArea.getPsfs(),
				erpDeliveryArea.getCysid(), erpDeliveryArea.getType(),
				erpDeliveryArea.getPsfw(), erpDeliveryArea.getDc(),
				erpDeliveryArea.getMark() });
	}

	@Override
	public void saveDeliveryCompany(ErpDeliveryCompany erpDeliveryCompany) {
		jdbcTemplateEcCore.update(INSERT_DELIVERY_COMPANY, new Object[] {
				erpDeliveryCompany.getId(), erpDeliveryCompany.getName(),
				erpDeliveryCompany.getZt(), erpDeliveryCompany.getType() });
	}

	@Override
	public void executeProcedure(String name) {
		jdbcTemplateEcCore.execute("call " + name + "()");
	}

	@Override
	public boolean existErpBlock(String orderId) {
		int result = jdbcTemplateEcCore.queryForInt(GET_ERP_BLOCK,
				new Object[] { orderId });
		if (result == MagicNumber.ZERO) {
			return false;
		}
		return true;
	}

	@Override
	public List<String> findOrderNoTransfer() {
		return jdbcTemplateEcCore.queryForList(SELECT_ORDER_NOTRANSFER,
				String.class);
	}

	@Override
	public boolean isTransfer(String orderId) {
		int result = jdbcTemplateEcCore.queryForInt(GET_NOTRANS_NUM,
				new Object[] { orderId });
		if (result == MagicNumber.ZERO) {
			return true;
		}
		return  false;
	}
}
