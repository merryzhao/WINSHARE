package com.winxuan.ec.task.dao.union;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.task.model.union.ADUnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-29
 */
@Component("unionDao")
public class UnionDaoImpl implements UnionDao,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3178675382894073925L;
	private static final  Log LOG =LogFactory.getLog(UnionDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplateUnion;

	@Override
	public void updateDeliveryUnionOrder(UnionOrder unionOrder) {
		int count = jdbcTemplateUnion.update(
				UPDATE_UNION_DELIVERYORDER,
				new Object[] { unionOrder.getOrder().getEffiveMoney(),
						unionOrder.getOrder().getSalePrice(), 
						unionOrder.getOrder().getEffiveMoney(),
						unionOrder.getOrder().getId()
						});
		if(count>0){
			LOG.info("订单号："+unionOrder.getOrder().getId()+"更新发货记录成功");
		}
	}

	@Override
	public void updateCannelUnionOrder(UnionOrder unionOrder) {
		int count = jdbcTemplateUnion.update(
				UPDATE_UNION_CANCELORDER,
				new Object[] {
					unionOrder.getOrder().getId()	
				});
		if(count>0){
			LOG.info("订单号："+unionOrder.getOrder().getId()+"更新取消记录成功");
		}
	}

	@Override
	public ADUnionOrder get(String orderId) {
		return jdbcTemplateUnion.query(SELECT_UNION_ORDER,
				new Object[] {orderId},
				new ResultSetExtractor<ADUnionOrder>(){
					@Override
					public ADUnionOrder extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if(rs.next()){
							ADUnionOrder adUnionOrder = new ADUnionOrder();
							adUnionOrder.setMerchantId(rs.getLong("merchantId"));
							adUnionOrder.setCreatetime(rs.getDate("create_timestamp"));
							adUnionOrder.setNumber(rs.getString("number"));
							adUnionOrder.setAmount(rs.getBigDecimal("amount"));
							adUnionOrder.setBookPrice(rs.getBigDecimal("book_price"));
							adUnionOrder.setRealPrice(rs.getBigDecimal("real_price"));
							adUnionOrder.setSaleBookPrice(rs.getBigDecimal("sale_book_price"));
							adUnionOrder.setSaleRealPrice(rs.getBigDecimal("sale_real_price"));
							adUnionOrder.setStan(rs.getString("stan"));
							return adUnionOrder;
						}
						return null;
					}
				});
	}

	@Override
	public boolean isExistRefund(String orderId) {
		return jdbcTemplateUnion.queryForInt(SELECT_UNION_REFUNDORDER,
				new Object[] {orderId}) > 0 ? true : false;
	}

	@Override
	public void saveRefundUnionOrder(ADUnionOrder adUnionOrder,BigDecimal returnMoney) {
		jdbcTemplateUnion.update(INSERT_UNION_REFUNDORDER, new Object[]{
				adUnionOrder.getMerchantId(),
				adUnionOrder.getCreatetime(),
				adUnionOrder.getNumber(),
				returnMoney,
				adUnionOrder.getBookPrice(),
				adUnionOrder.getRealPrice(),
				adUnionOrder.getSaleBookPrice(),
				adUnionOrder.getSaleRealPrice(),
				adUnionOrder.getSaleBookPrice(),
				returnMoney,
				adUnionOrder.getStan()
		});	
		LOG.info("订单号："+adUnionOrder.getNumber()+"添加退货记录成功");
	}

	@Override
	public List<String> findNewUnionOrderId() {
		return jdbcTemplateUnion.queryForList(SELECT_NEW_UNION_ORDER,String.class);
	}
}
