package com.winxuan.ec.task.service.union.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.service.order.UnionOrderService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.task.dao.union.UnionDao;
import com.winxuan.ec.task.job.ec.union.UpdateUnionOrder;
import com.winxuan.ec.task.model.union.ADUnionOrder;
import com.winxuan.ec.task.service.union.AdUnionOrderService;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-29
 */
@Service("adUnionOrderService")
public class AdUnionOrderServiceImpl implements AdUnionOrderService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8267731368637906616L;

	private static final Log LOG = LogFactory.getLog(UpdateUnionOrder.class);
	
	private static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final DateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String EARLY_TIME="00:00:00";
	
	private static final String LATE_TIME = "23:59:59";
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	@Autowired
	private UnionOrderService unionOrderService;
	
	@Autowired
	private ReturnOrderService returnOrderService;
	
	@Autowired
	private UnionDao unionDao;
	
	@Override
	public void updateDeliveryOrderListbyDate() {
		 LOG.info("更新联盟发货订单状态start...");	
		 hibernateLazyResolver.openSession();
		 Map<String,Object> parameters  = generateQueryMap();
 		 List<Long> processStatus = new ArrayList<Long>();
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_DELIVERIED);
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG);
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_COMPLETED);
 		 parameters.put("unionId", UnionOrder.UNION_AD);
 		 parameters.put("processStatus", processStatus);
 		 System.out.println(parameters);
		 Pagination pagination = new Pagination();
		 pagination.setPageSize(100);
		 List<UnionOrder> unionOrderList = null;
		 int currentPage = 1;
		while ((unionOrderList = unionOrderService.find(parameters, Short.parseShort("3"),
				pagination)) != null && unionOrderList.size() > 0) {
			for(UnionOrder unionOrder : unionOrderList){				
				unionDao.updateDeliveryUnionOrder(unionOrder);	
			}
			currentPage = currentPage +1;
			pagination.setCurrentPage(currentPage);
		}
		LOG.info("更新联盟发货订单状态end...");	
	}

	@Override
	public void addRefundOrderListbyDate() {
		LOG.info("添加联盟退货订单状态start...");	
		 hibernateLazyResolver.openSession();
		Map<String,Object> parameters  = generateQueryMap();
		 List<Long> processStatus = new ArrayList<Long>();
 		 processStatus.add(Code.RETURN_ORDER_STATUS_REFUNDED);
 		 processStatus.add(Code.RETURN_ORDER_STATUS_CHANGED);
 		 parameters.put("status", processStatus);
 		 List<ReturnOrder> returnOrderList = null;
 		 Pagination pagination = new Pagination();
 		 pagination.setPageSize(100);
 		 int currentPage = 1;
		while ((returnOrderList = returnOrderService.find(parameters,
				pagination)) != null && returnOrderList.size() > 0) {
			for(ReturnOrder returnOrder : returnOrderList){
				String orderId = returnOrder.getOriginalOrder().getId();			
				UnionOrder unionOrder = unionOrderService.getByOrderId(orderId);
				if(unionOrder == null){
					continue;
				}
				boolean blag = unionDao.isExistRefund(orderId);
				ADUnionOrder adUnionOrder = unionDao.get(orderId);				
				if(adUnionOrder != null && !blag){	
					BigDecimal refundMoney = getRefundMoney(returnOrder.getOriginalOrder());
					if(refundMoney.compareTo(BigDecimal.ZERO)>0){
						unionDao.saveRefundUnionOrder(adUnionOrder,refundMoney);
					}
				}
			}
			currentPage = currentPage +1;
			pagination.setCurrentPage(currentPage);
		}
			LOG.info("添加联盟退货订单状态end...");			
	}

	@Override
	public void updateCancelOrderListbyDate() {
		 LOG.info("更新联盟取消订单状态start...");
		 Map<String,Object> parameters  = generateQueryMap();
 		 List<Long> processStatus = new ArrayList<Long>();
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL);
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL);
 		 processStatus.add(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
 		 parameters.put("processStatus", processStatus);
 		 parameters.put("unionId", UnionOrder.UNION_AD);
		 Pagination pagination = new Pagination();
		 pagination.setPageSize(100);
		 List<UnionOrder> unionOrderList = null;
		 int currentPage = 1;
		while ((unionOrderList = unionOrderService.find(parameters, Short.parseShort("3"),
				pagination)) != null && unionOrderList.size() > 0) {
			for(UnionOrder unionOrder : unionOrderList){
				unionDao.updateCannelUnionOrder(unionOrder);	
			}
			currentPage = currentPage +1;
			pagination.setCurrentPage(currentPage);
		}
		 LOG.info("更新联盟取消订单状态end...");
	}
	
	
	/**
	 * 生成状态改变时间
	 * @return
	 */
	public Map<String,Object> generateQueryMap(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		Calendar c1 = Calendar.getInstance();
		 c1.add(Calendar.DAY_OF_MONTH, -3);
		 String startDateStr = SHORT_DATE_FORMAT.format(c1.getTime())+" "+EARLY_TIME;
		 Date startdate=null;
		try {
			startdate = LONG_DATE_FORMAT.parse(startDateStr);
		} catch (ParseException e) {
			LOG.info("日期转化错误");
		}
		 Calendar c = Calendar.getInstance();
		 c.add(Calendar.DAY_OF_MONTH, -1);
		 String endDateStr= SHORT_DATE_FORMAT.format(c.getTime())+" "+LATE_TIME;
		 Date enddate = null;
		try {
			enddate = LONG_DATE_FORMAT.parse(endDateStr);
		} catch (ParseException e) {
			LOG.info("日期转化错误");
		}
		 parameters.put("startLastProcessTime", startdate);
		 parameters.put("endLastProcessTime", enddate);	 
		 return parameters;
	}
	
	public BigDecimal getRefundMoney(Order order){
		Set<OrderItem> itemList =order.getItemList();
		BigDecimal returnMoney = BigDecimal.ZERO;
		for (OrderItem orderItem : itemList) {
			BigDecimal returnQuantity = new BigDecimal(
					orderItem.getReturnQuantity());
			returnMoney = returnMoney.add(orderItem.getBalancePrice().multiply(
					returnQuantity));
		}
		return returnMoney;
	}
	
	@Override
	public void updateOrder() {
		List<String> orderIds = unionDao.findNewUnionOrderId();  
		if(orderIds == null ||orderIds.size()<=0){
			return;
		}
		for(String orderId : orderIds){
			UnionOrder  unionOrder = unionOrderService.getByOrderId(orderId);
			if(unionOrder == null){
				LOG.info("null===>"+orderId);
			}else{
				if(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL.equals(unionOrder.getOrder().getProcessStatus().getId())
					||Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL.equals(unionOrder.getOrder().getProcessStatus().getId())
					||Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL.equals(unionOrder.getOrder().getProcessStatus().getId())
					||Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL.equals(unionOrder.getOrder().getProcessStatus().getId())
				    ||Code.ORDER_PROCESS_STATUS_ERP_CANCEL.equals(unionOrder.getOrder().getProcessStatus().getId())){
						LOG.info("取消:"+orderId);
						unionDao.updateCannelUnionOrder(unionOrder);
					}
				if(Code.ORDER_PROCESS_STATUS_DELIVERIED.equals(unionOrder.getOrder().getProcessStatus().getId())
					||Code.ORDER_PROCESS_STATUS_COMPLETED.equals(unionOrder.getOrder().getProcessStatus().getId())
					||Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG.equals(unionOrder.getOrder().getProcessStatus().getId())){	
					LOG.info("发货:"+orderId);
					unionDao.updateDeliveryUnionOrder(unionOrder);
				}
			}	
		}
	}
	
}
