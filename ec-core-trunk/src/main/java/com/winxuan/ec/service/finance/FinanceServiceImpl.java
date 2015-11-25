package com.winxuan.ec.service.finance;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDeliveryFinance;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderReturnFinance;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;

/**
 * 财务发货、退货数据相关的记录
 * @author HideHai
 */
@Service("financeService")
@Transactional(rollbackFor = Exception.class)
public class FinanceServiceImpl implements FinanceService{

	private static final String INSERT_DELIVERY_FINANCE_SQL = "INSERT INTO order_delivery_finance (_order,deliveryquantity,deliverylistprice,deliverysaleprice,deliveryfee," +
	"savemoney,couponmoney,presentcardmoney,deliverytime,createtime) VALUES " +
	"(?,?,?,?,?,?,?,?,?,?)";
	private static final String INSERT_RETURN_FINANCE_SQL = "INSERT INTO order_return_finance (_order,business,status,returnquantity,returnlistprice,returnsaleprice,deliveryfee,savemoney," +
	"couponmoney,presentcardmoney,returntime,createtime) " +
	"VALUES " +
	"(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_EXIST_DELIVERY_SQL = "SELECT count(*) FROM order_delivery_finance WHERE _order = ?";
	private static final String SELECT_EXIST_RETURN_SQL = "SELECT count(*) FROM order_return_finance WHERE _order = ? AND business = ?";
	private static final String UPDATE_FINANCE_ONSHELF_SQL = "UPDATE order_return_finance SET status=?,returntime=? WHERE _order=? AND business=?";

	private static final String SELECT_DELIVERY_FINANCE = "SELECT * FROM order_delivery_finance WHERE _order = ?";
	private static final String SELECT_RETURN_FINANCE = "SELECT * FROM order_return_finance WHERE _order = ? AND business =?";

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public OrderDeliveryFinance get(String orderId) {
		OrderDeliveryFinance finance = jdbcTemplate.queryForObject(SELECT_DELIVERY_FINANCE, new Object[]{orderId},new RowMapper<OrderDeliveryFinance>(){
			@Override
			public OrderDeliveryFinance mapRow(ResultSet rs, int rowNum)
			throws SQLException {
				OrderDeliveryFinance finance = new OrderDeliveryFinance();
				finance.setOrder(rs.getString("_order"));
				finance.setDeliveryQuantity(rs.getInt("deliveryquantity"));
				finance.setDeliveryListPrice(rs.getBigDecimal("deliverylistprice"));
				finance.setDeliverySalePrice(rs.getBigDecimal("deliverysaleprice"));
				finance.setDeliveryFee(rs.getBigDecimal("deliveryfee"));
				finance.setSaveMoney(rs.getBigDecimal("savemoney"));
				finance.setCouponMoney(rs.getBigDecimal("couponmoney"));
				finance.setPresentCardMoney(rs.getBigDecimal("presentcardmoney"));
				finance.setDeliveryTime(rs.getDate("deliverytime"));
				finance.setCreateTime(rs.getDate("createtime"));
				return finance;
			}
		});
		return finance;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public OrderReturnFinance get(String orderId, String business) {
		OrderReturnFinance finance = jdbcTemplate.queryForObject(SELECT_RETURN_FINANCE, new Object[]{orderId,business},new RowMapper<OrderReturnFinance>(){
			@Override
			public OrderReturnFinance mapRow(ResultSet rs, int rowNum)
			throws SQLException {
				OrderReturnFinance finance = new OrderReturnFinance();
				finance.setOrder(rs.getString("_order"));
				finance.setBussiness(rs.getString("business"));
				finance.setStatus(rs.getLong("status"));
				finance.setReturnQuantity(rs.getInt("returnquantity"));
				finance.setReturnListPrice(rs.getBigDecimal("returnlistprice"));
				finance.setReturnSalePrice(rs.getBigDecimal("returnsaleprice"));
				finance.setDeliveryFee(rs.getBigDecimal("deliveryfee"));
				finance.setSaveMoney(rs.getBigDecimal("savemoney"));
				finance.setCouponMoney(rs.getBigDecimal("couponmoney"));
				finance.setPresentCardMoney(rs.getBigDecimal("presentcardmoney"));
				finance.setReturntime(rs.getDate("returntime"));
				finance.setCreateTime(rs.getDate("createtime"));
				return finance;
			}
		});
		return finance;
	}

	@Override
	public boolean processDelvieryFinance(Order order,Date returnTime) {
		Long statusId = order.getProcessStatus().getId();
		if(!(order.isDeliveried() || 
				statusId.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) || 
				statusId.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL))){
			throw new RuntimeException(String.format("order id : %s status 091 ignore!", order.getId()));
		}

		BigDecimal saveMoney = order.getSaveMoney();
		if(order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)){
			Set<OrderItem> orderItems = order.getItemList();
			for(OrderItem item : orderItems){
				saveMoney.add((item.getSalePrice().subtract(item.getBalancePrice())).multiply(new BigDecimal(item.getDeliveryQuantity())));
			}
		}

		OrderDeliveryFinance deliveryFinance = new OrderDeliveryFinance();
		deliveryFinance.setOrder(order.getId());
		deliveryFinance.setCouponMoney(order.getCouponMoney());
		deliveryFinance.setPresentCardMoney(order.getGiftCardMoney());
		deliveryFinance.setCreateTime(new Date());
		deliveryFinance.setDeliveryFee(order.getDeliveryFee());
		deliveryFinance.setDeliveryListPrice(order.getDeliveryListPrice());
		deliveryFinance.setDeliverySalePrice(order.getDeliverySalePrice());
		deliveryFinance.setDeliveryQuantity(order.getDeliveryQuantity());
		deliveryFinance.setDeliveryTime(returnTime==null?order.getDeliveryTime():returnTime);
		deliveryFinance.setSaveMoney(saveMoney);
		saveDeliveryFinance(deliveryFinance);
		return true;
	}

	@Override
	public boolean processReuturnFinance(Order order,Date returnTime) {
		Long statusId = order.getProcessStatus().getId();
		if(!(statusId.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) || 
				statusId.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL))){
			throw new RuntimeException(String.format("order id : %s status 091 ignore!", order.getId()));
		}
		OrderReturnFinance returnFinance = new OrderReturnFinance();
		returnFinance.setOrder(order.getId());
		returnFinance.setBussiness(order.getId());
		returnFinance.setStatus(Code.RETURN_ONSHELF_NO);
		returnFinance.setCouponMoney(order.getCouponMoney());
		returnFinance.setCreateTime(new Date());
		returnFinance.setDeliveryFee(order.getDeliveryFee());
		returnFinance.setReturnListPrice(order.getDeliveryListPrice()==null?order.getListPrice():order.getDeliveryListPrice());
		returnFinance.setReturnQuantity(order.getDeliveryQuantity());
		returnFinance.setReturnSalePrice(order.getDeliverySalePrice()==null?order.getSalePrice():order.getDeliverySalePrice());
		returnFinance.setReturntime(returnTime==null?order.getLastProcessTime():returnTime);
		returnFinance.setPresentCardMoney(order.getGiftCardMoney());
		returnFinance.setSaveMoney(order.getSaveMoney());
		saveReturnFinance(returnFinance);
		return true;
	}

	@Override
	public boolean processReturnFinance(ReturnOrder returnOrder,Date returnTime) {
		Long statusID = returnOrder.getStatus().getId();
		if(!(statusID.equals(Code.RETURN_ORDER_STATUS_REFUNDED) || statusID.equals(Code.RETURN_ORDER_STATUS_CHANGED))){
			return false;
		}
		Order order = returnOrder.getOriginalOrder();
		OrderReturnFinance returnFinance = new OrderReturnFinance();

		Set<ReturnOrderItem> returnOrderItems = returnOrder.getItemList();
		BigDecimal returnListPrice = BigDecimal.ZERO;
		BigDecimal returnSalePrice = BigDecimal.ZERO;
		BigDecimal returnSaveMoney = BigDecimal.ZERO;

		for(ReturnOrderItem returnItem : returnOrderItems){
			OrderItem item = returnItem.getOrderItem();
			returnListPrice.add(returnItem.getTotalListPrice());
			returnSalePrice.add(returnItem.getTotalSalePrice());
			returnSaveMoney.add((item.getSalePrice().subtract(item.getBalancePrice())).multiply(new BigDecimal(returnItem.getRealQuantity())));
		}

		returnFinance.setOrder(order.getId());
		returnFinance.setBussiness(returnOrder.getId().toString());
		returnFinance.setStatus(Code.RETURN_ONSHELF_NO);
		returnFinance.setCouponMoney(order.getCouponMoney());
		returnFinance.setCreateTime(new Date());
		returnFinance.setDeliveryFee(returnOrder.getRefundDeliveryFee());
		returnFinance.setReturnQuantity(returnOrder.getTotalQuantity());
		returnFinance.setReturntime(returnTime == null ? returnOrder.getRefundTime() : returnTime);
		returnFinance.setPresentCardMoney(returnOrder.getGiftValue());
		returnFinance.setSaveMoney(returnSaveMoney);
		returnFinance.setReturnListPrice(returnListPrice);
		returnFinance.setReturnSalePrice(returnListPrice);

		saveReturnFinance(returnFinance);
		return true;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean existDeliveryFinance(Order order) {
		int count = jdbcTemplate.queryForInt(SELECT_EXIST_DELIVERY_SQL, new Object[]{order.getId()});
		return count!=0;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean existReturnFinance(Order order) {
		int count = jdbcTemplate.queryForInt(SELECT_EXIST_RETURN_SQL, new Object[]{order.getId(),order.getId()});
		return count!=0;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean existReturnFinance(ReturnOrder returnOrder) {
		int count = jdbcTemplate.queryForInt(SELECT_EXIST_RETURN_SQL, new Object[]{returnOrder.getOriginalOrder().getId(),returnOrder.getId()});
		return count!=0;
	}

	@Override
	public void updateReturnFinance(Order order, Date returnTime) {
		if(!order.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NEW)){
			throw new RuntimeException(String.format("%s:order type error to update finance status!", order.getId()));
		}
		Long statusId = order.getProcessStatus().getId();
		if(!(order.isDeliveried() || 
				statusId.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) || 
				statusId.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL) || 
				statusId.equals(Code.ORDER_PROCESS_STATUS_ARCHIVE))){
			throw new RuntimeException(String.format("order id : %s status 091 ignore!", order.getId()));
		}
		updateFinanceStatusAndReturnTime(order.getId(), order.getId(), returnTime);
	}

	@Override
	public void updateReturnFinance(ReturnOrder returnOrder, Date returnTime) {
		if(!(returnOrder.isNeedtransfers() || returnOrder.isTransferred())){
			throw new RuntimeException(String.format("%s:returnOrder type error to update finance status!", returnOrder.getId()));
		}
		updateFinanceStatusAndReturnTime(returnOrder.getOriginalOrder().getId(),returnOrder.getId().toString(), returnTime);
	}

	private void updateFinanceStatusAndReturnTime(String orderId,String business,Date returnTime) {
		jdbcTemplate.update(UPDATE_FINANCE_ONSHELF_SQL, new Object[]{
				Code.RETURN_ONSHELF_YES,returnTime,orderId,business});
	}

	private void saveDeliveryFinance(OrderDeliveryFinance deliveryFinance){
		jdbcTemplate.update(INSERT_DELIVERY_FINANCE_SQL,new Object[]{
				deliveryFinance.getOrder(),
				deliveryFinance.getDeliveryQuantity(),
				deliveryFinance.getDeliveryListPrice(),
				deliveryFinance.getDeliverySalePrice(),
				deliveryFinance.getDeliveryFee(),
				deliveryFinance.getSaveMoney(),
				deliveryFinance.getCouponMoney(),
				deliveryFinance.getPresentCardMoney(),
				deliveryFinance.getDeliveryTime(),
				deliveryFinance.getCreateTime()
		});
	}

	private void saveReturnFinance(OrderReturnFinance returnFinance){
		jdbcTemplate.update(INSERT_RETURN_FINANCE_SQL,new Object[]{
				returnFinance.getOrder(),
				returnFinance.getBussiness(),
				returnFinance.getStatus(),
				returnFinance.getReturnQuantity(),
				returnFinance.getReturnListPrice(),
				returnFinance.getReturnSalePrice(),
				returnFinance.getDeliveryFee(),
				returnFinance.getSaveMoney(),
				returnFinance.getCouponMoney(),
				returnFinance.getPresentCardMoney(),
				returnFinance.getReturntime(),
				returnFinance.getCreateTime()
		});
	}
}
