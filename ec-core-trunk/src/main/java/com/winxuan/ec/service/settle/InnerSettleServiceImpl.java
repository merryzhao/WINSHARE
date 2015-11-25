package com.winxuan.ec.service.settle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.BillDao;
import com.winxuan.ec.dao.EmployeeDao;
import com.winxuan.ec.dao.InnerSettleDao;
import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.settle.SapOrderItem;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @description: TODO
 *
 * @createtime: 2014-2-10 下午2:30:33
 *
 * @author zenghua
 *
 * @version 1.0
 */
@Service("innerSettleService")
@Transactional(rollbackFor=Exception.class)
public class InnerSettleServiceImpl implements InnerSettleService {
	
	private static final Log LOG = LogFactory.getLog(InnerSettleServiceImpl.class);
	
	private static final String SEND_SAPORDERITEM_SQL = "INSERT INTO interface_channel_settle ( "
			+ "outerorder, outeritem, warehouse, returnflag, customer, sapid, quantity,"
			+ "_order, orderitem, channel, deliveryquantity, settle, type, listprice, bill, historysettle, createtime"
			+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,'I',?,?,?,?)";
	
	private static final String FINDCOMPLEX_SQL="SELECT i.item ,p.listprice,i.childnum,i.quantiy "+
			"from interface_sap_complex i,product p,product_sale ps where i.item =ps.outerid and p.id=ps.product and p.complex=0 and ps.storagetype = 6001 and  ps.shop=1 and i.complex=?";
	@InjectDao
	private InnerSettleDao innerSettleDao;
	
	@InjectDao
	private BillDao billDao;
	
	@InjectDao
	private EmployeeDao employeeDao;
	
	private JdbcTemplate jdbctemplateSap;
	@Override
	public List<SapOrderItem> getDeliveryOrderItem(String startDate,
			String endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("transferresult", Code.EC2ERP_STATUS_CANCEL);
		List<SapOrderItem> sapOrderItems = innerSettleDao.getDeliveryOrderItem(params);
		return sapOrderItems;
	}

	@Override
	public List<SapOrderItem> getReturnOrderItem(String startDate,
			String endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("transferresult", Code.EC2ERP_STATUS_CANCEL);
		return innerSettleDao.getReturnOrderItem(params);
	}

	@Override
	public List<SapOrderItem> getRejectOrderItem(String startDate,
			String endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("transferresult", Code.EC2ERP_STATUS_CANCEL);
		params.put("processstatus", Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
		return innerSettleDao.getRejectOrderItem(params);
	}

	@Override
	public List<SapOrderItem> getAllOrderItem() {
		Date date = DateUtils.addMonths(new Date(), -3);
		Date date1 = DateUtils.addMonths(new Date(), -2);
		String startDate = DateFormatUtils.format(date, "yyyy-MM") + "-01";
//		String startDate = "2013-06-27";
		String endDate = DateFormatUtils.format(date1, "yyyy-MM") + "-01";
		List<SapOrderItem> sapList = new ArrayList<SapOrderItem>();
		List<SapOrderItem> deliveryOrderItems = this.getDeliveryOrderItem(startDate, endDate);
		if (CollectionUtils.isNotEmpty(deliveryOrderItems)) {
			sapList.addAll(deliveryOrderItems);
			LOG.info("deliveryOrderItems's size is " +  deliveryOrderItems.size());
		}
		List<SapOrderItem> returnOrderItems = this.getReturnOrderItem(startDate, endDate);
		if (CollectionUtils.isNotEmpty(returnOrderItems)) {
			sapList.addAll(returnOrderItems);
			LOG.info("returnOrderItems's size is " + returnOrderItems.size());
		}
		List<SapOrderItem> rejectOrderItems = this.getRejectOrderItem(startDate, endDate);
		if (CollectionUtils.isNotEmpty(rejectOrderItems)) {
			sapList.addAll(rejectOrderItems);
			LOG.info("rejectOrderItems's size is " + rejectOrderItems.size());
		}
		
		return this.chackOrderItemComplex(sapList);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Bill createBill(SapOrderItem sapOrderItem) {
		Bill bill = new Bill();
		bill.setBillAccount(billDao.getBillAccount(6L));//TODO 绑定一个固定的账号
		bill.setUpdatetime(new Date());
		bill.setCreatetime(new Date());
		bill.setEmployee(employeeDao.get(6L));
		bill.setStatus(new Code(Code.BILL_PROCESSED));
		bill.setInvoice(sapOrderItem.getOuterOrder());
		bill.setList(sapOrderItem.getOuterOrder());
		billDao.save(bill);
		LOG.info("create bill successfully!!!");
		return bill;
	}

	@Override
	public void sendSapOrderItem(Object[] params) {
		jdbctemplateSap.update(SEND_SAPORDERITEM_SQL, params);
	}

	public void setJdbctemplateSap(JdbcTemplate jdbctemplateSap) {
		this.jdbctemplateSap = jdbctemplateSap;
	}

	public List<SapOrderItem> chackOrderItemComplex(List<SapOrderItem> itemList) {
		List<SapOrderItem> sapList = new ArrayList<SapOrderItem>();
		for(SapOrderItem item:itemList){
			if(item.isComplex()){
				String isUnderSql="select COUNT(1) total from  interface_channel_settle s where s.outeritem=? and s.outerorder=? ";
				List<Map<String,Object>> complexitem= jdbctemplateSap.queryForList(FINDCOMPLEX_SQL,item.getSapid());
				if(complexitem!=null&&complexitem.size()>0
						&&complexitem.size()==Integer.parseInt(complexitem.get(0).get("childnum").toString())){//子商品
		     		for(Map<String,Object> vo:complexitem){
		     			SapOrderItem orderItemNew=new SapOrderItem();
						try {
							BeanUtils.copyProperties(orderItemNew,item);
						} catch (Exception e) {
							LOG.info("chackOrderItemComplex  exception",e);
							break;
						}
		     			orderItemNew.setListPrice(new BigDecimal(vo.get("listprice").toString()));
		     			orderItemNew.setSapid(vo.get("item").toString());
		     			//发货单2/退货单3/拒收单4+订单行项+sapid+X
		     			String outerOrder=String.valueOf(item.getOuterOrder().charAt(0))+(item.getOuterItem()+Long.parseLong(orderItemNew.getSapid()))+"X";
		     			orderItemNew.setOuterOrder(outerOrder);
		     			orderItemNew.setQuantity(item.getQuantity()*(Integer)vo.get("quantiy"));
		     			orderItemNew.setDeliveryQuantity(item.getDeliveryQuantity()*(Integer)vo.get("quantiy"));
		     			if(item.getReturnFlag()!=null&&"X".equals(item.getReturnFlag())){
		     				String order=String.valueOf(item.getOrder().charAt(0))+(item.getOrderItem()+Long.parseLong(orderItemNew.getSapid()))+"X";
		     				orderItemNew.setOrder(order);
		     			}
		     			orderItemNew.setDc(item.getDc());
		     			int total=jdbctemplateSap.queryForInt(isUnderSql,orderItemNew.getOuterItem(),orderItemNew.getOuterOrder());
		     			if(total==0){
		     				sapList.add(orderItemNew);
		     			}
		     		}
				}
			}else{
	     		sapList.add(item);
			}
		}
		return sapList;
	}
}
