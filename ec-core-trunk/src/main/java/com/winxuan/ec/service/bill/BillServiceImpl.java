package com.winxuan.ec.service.bill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.BillDao;
import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.exception.BillException;
import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.bill.BillAccount;
import com.winxuan.ec.model.bill.BillItem;
import com.winxuan.ec.model.bill.BillItemLog;
import com.winxuan.ec.model.bill.BillItemSap;
import com.winxuan.ec.model.bill.BillReceiptRecord;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 帐单服务实现
 * 主流程
 * 1.建立帐户绑定渠道(可选步骤)
 * 2.选帐户,勾选结算渠道, 输入发票,清单,抽单金额,确认抽单 (必须步骤)
 * 3.修改系统抽单结果,删除或添加 结算项. (可选步骤)
 * 4.锁定抽单结果 lock, 禁止修改订单项的操作; (必须)
 * 5.确认抽单结果 (必须)
 * 
 * 6.定时任务自动将确认的抽单项,下传SAP
 * 
 * 其他:
 * 用户可以反复抽单.系统只认最近一次抽单,历史抽单只要不到确认抽单结果都将被忽略
 * 
 * @author heyadong
 *
 */
@Service("billService")
@Transactional(rollbackFor=Exception.class)
public class BillServiceImpl implements BillService, InitializingBean {
	private static final Log LOG = LogFactory
			.getLog(BillServiceImpl.class);
	@Autowired
	ApplicationContext applicationContext;
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,60L,TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
	
	private BillServiceImpl billService;
	
	
	private HibernateLazyResolver hibernateLazyResolver;
	

	@InjectDao
	private BillDao billDao;
	@InjectDao
	private OrderDao orderDao; 
	
	private JdbcTemplate jdbcTemplate;
	
	private String formatChannel(List<Channel> channels){
		if (channels == null || channels.isEmpty()){
			throw new IllegalArgumentException("channel参数错误!!,");
		}
		StringBuilder sb = new StringBuilder();
		for (Channel c : channels) {
			sb.append(c.getId()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return " (" + sb.toString() + ") ";
	}
	


	@Override
	public void saveBillAccount(BillAccount billAccount) {
		billDao.saveBillAccount(billAccount);
		
	}
	
	@Override
	public void saveBillReceiptRecord(BillReceiptRecord billReceiptRecord) {
		billDao.saveBillReceiptRecord(billReceiptRecord);
	}

	@Override
	public void appendToBillAccount(BillAccount billAccount,
			List<Channel> channels) throws BillException {
		String channelIds = formatChannel(channels);
		String sql = "SELECT ba.name FROM bill_account ba"
				+	" INNER JOIN billaccount_channel bc ON (ba.id = bc.billaccount)"
				+	" WHERE bc.billaccount IN" + channelIds
				;
		List<String> names = jdbcTemplate.queryForList(sql, String.class);
		if (!names.isEmpty()){
			throw new BillException(String.format("%s,已经存在渠道帐户了",StringUtils.join(names,","))); 
		} else {
			for (Channel c : channels){
				billAccount.getChannels().add(c);
			}
			
			billDao.updateBillAccount(billAccount);
		}
	}

	@Override
	public void removeFromBillAccount(Long billAccountId, Long channelId)
			throws BillException {
		jdbcTemplate.update("DELETE FROM billaccount_channel WHERE billaccount = ? AND channel = ?", billAccountId ,channelId);
		
	}

	@Override
	public void updateTolerance(BillAccount billAccount, BigDecimal money,
			Employee employee) {
		billAccount.setTolerance(money.abs());
		billAccount.setLastEmployee(employee);
		billDao.updateBillAccount(billAccount);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void sync(List<Channel> channels) {
		String channleIds = formatChannel(channels);
		//同步最新渠道已发货的商品项
		String sqlDelivery = "INSERT INTO bill_item_statistics (orderitem,deliveryquantity,settlementquantity,refoundquantity,channel)"
					+ " SELECT oi.id AS 'orderitem', oi.deliveryquantity, 0 AS 'settlementquantity', 0 AS 'refoundquantity', o.channel FROM _order o"
					+ "		INNER JOIN order_item oi ON (oi._order = o.id)"
					+ " 	LEFT JOIN bill_item_statistics bis ON (bis.orderitem = oi.id)"
					+ " WHERE bis.orderitem IS NULL AND oi.deliveryquantity > 0 AND o.channel IN " + channleIds
					;
		//同步最新渠道退货单,默认状态为未确认
		String sqlReturn = "INSERT INTO bill_returnorder_item(returnorderitem,channel,status)"
					+	" SELECT ri.id AS 'returnorderitem', o.channel, ? AS 'status' FROM returnorder ro"
					+	"	INNER JOIN returnorder_item ri ON (ri.returnorder = ro.id)"
					+	"	INNER JOIN order_item oi ON (oi.id = ri.orderitem)"
					+	"	INNER JOIN _order o ON (o.id = oi._order)"
					+	" 	LEFT JOIN bill_returnorder_item bri ON (bri.returnorderitem = ri.id)"
					+	" WHERE bri.returnorderitem IS NULL AND ri.realquantity > 0 AND o.channel IN " + channleIds
					;
		
		//同步最新客户拒收订单项,默认状态为未确认.--据收单无退货订单.但记录销售与退货
		String sqlReject = " INSERT INTO bill_rejectorder_item(orderitem, channel, status)"
						+	" SELECT oi.id, o.channel,? FROM _order o"
						+	" 	INNER JOIN order_item oi ON (oi._order = o.id)"
						+	" 	LEFT JOIN bill_rejectorder_item bri ON (bri.orderitem = oi.id)"
						+	"	WHERE bri.orderitem IS NULL AND o.processstatus = ? AND o.channel IN " + channleIds
						;
		
		jdbcTemplate.update(sqlDelivery);
		jdbcTemplate.update(sqlReturn, Code.BILL_UNCONFIRMED);
		jdbcTemplate.update(sqlReject, Code.BILL_UNCONFIRMED, Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
		
	}

	@Override
	public Bill get(Long id) {
		return billDao.get(id);
	}
	
	@Override
	public void update(Bill bill) {
		billDao.update(bill);
	}

	@Override
	public BillAccount getBillAccount(Long billAccountId) {
		
		return billDao.getBillAccount(billAccountId);
	}
	
	@Override
	public BillReceiptRecord getBillReceiptRecord(Long billReceiptRecordId) {
		return billDao.getBillReceiptRecord(billReceiptRecordId);
	}

	@Override
	public Bill lastBill(Long billAccountId) {
		List<Long> list = jdbcTemplate.queryForList("SELECT id FROM bill WHERE billaccount = ? ORDER BY id DESC LIMIT 1", Long.class, billAccountId);
		return list.isEmpty() ? null : billDao.get(list.get(0));
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public Bill allotment(String invoice, String list, final BillAccount billAccount, List<Channel> channels,
			final BigDecimal money, Employee employee) throws BillException {
		
		//数据同步
		billService.sync(channels);
		
		Bill bill = new Bill();
		bill.setList(list);
		bill.setInvoice(invoice);
		bill.setUpdatetime(new Date());
		bill.setCreatetime(new Date());
		bill.setAllotment(money);
		bill.setChannels(channels);
		bill.setEmployee(employee);
		bill.setStatus(new Code(Code.BILL_ALLOC_UNFINISHED));
		bill.setBillAccount(billAccount);
		bill.setLastBalance(billAccount.getBalance());
		bill = billService.createBill(bill);
		
		final Long billId = bill.getId();
		final Long billAccountId = billAccount.getId();
		final Long[] channelIds = new Long[channels.size()];
		for(int i = 0; i < channelIds.length; i++){
			channelIds[i] = channels.get(i).getId();
		}
		//异步作业分配
		pool.execute(new Runnable() {
			@Override
			public void run() {
				asyncJob(billAccountId, channelIds , billId, money);
				
			}
		});
		return bill;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Bill createBill(Bill bill){

		billDao.save(bill);
		return bill;
	}
	
	private void asyncJob(Long billAccountId, Long[] channels, Long billId, BigDecimal total) {
		hibernateLazyResolver.openSession();

		try{
			BillAccount billAccount = billDao.getBillAccount(billAccountId);
			Bill bill = billDao.get(billId);
			String channelIds = "(" + StringUtils.join(channels, ",") + ")";
			
			//优先处理结算退货的订单项  -- 参数,流水号ID, 未确认状态ID, 渠道IDS,   未确认状态ID
			String sqlReturn = "INSERT INTO bill_item (bill,orderitem,settlementquantity,refoundquantity,status,historysettlement,historyrefound)"
					+ " SELECT ? AS 'bill', bis.orderitem, 0 AS 'settlementquantity', SUM(ri.realquantity) AS 'refoundquantity', ? AS 'status'"
					+ " , bis.settlementquantity AS 'historySettlement', bis.refoundquantity AS 'historyRefound'"
					+ " 	FROM bill_returnorder_item bri"
					+ " 	INNER JOIN returnorder_item ri ON (ri.id = bri.returnorderitem)"
					+ " 	INNER JOIN bill_item_statistics bis ON (bis.orderitem = ri.orderitem AND bis.channel IN " + channelIds + ")"
					+ " WHERE bri.status = ? AND bis.deliveryquantity = bis.settlementquantity"
					+ " GROUP BY bis.orderitem"
					;
			jdbcTemplate.update(sqlReturn, bill.getId(), Code.BILL_UNCONFIRMED, Code.BILL_UNCONFIRMED);
			
			//优先处理已结算的拒收订单,--参数 流水号ID, 未确认状态ID, 渠道IDs,   未确认状态ID
			String sqlReject = "INSERT INTO bill_item (bill,orderitem,settlementquantity,refoundquantity,status,historysettlement,historyrefound)"
						+	" SELECT ? AS 'bill', bis.orderitem, 0 AS 'settlementquantity', oi.deliveryquantity AS 'refoundquantity', ? AS 'status'"
						+	" , bis.settlementquantity AS 'historySettlement', bis.refoundquantity AS 'historyRefound'"
						+	"		FROM bill_rejectorder_item bri"
						+	"		INNER JOIN order_item oi ON (oi.id = bri.orderitem)"
						+	"		INNER JOIN bill_item_statistics bis ON (bis.orderitem = oi.id AND bis.channel IN  " + channelIds + ")"
						+	"	WHERE bri.status = ? AND bis.deliveryquantity = bis.settlementquantity"
						;
			jdbcTemplate.update(sqlReject, bill.getId(), Code.BILL_UNCONFIRMED, Code.BILL_UNCONFIRMED);
			
			//获取优先结算退货订单项(包含退货,拒收)的退款 --  1个参数 bill ID
			String sqlGetRefound = "SELECT IFNULL(SUM(bi.refoundquantity * oi.saleprice),0) AS 'refound' FROM bill_item bi"
								+ " 	INNER JOIN order_item oi ON (oi.id = bi.orderitem)"
								+ " WHERE bi.bill = ?"
								;
			BigDecimal refound = jdbcTemplate.queryForObject(sqlGetRefound,new Object[]{bill.getId()} , BigDecimal.class);
			bill.setRefound(refound);
			
	
			//抽单金额为: 渠道余额 + 订单分配金额 + 优先结算的退货退款
			total = total.add(billAccount.getBalance()).add(refound);
			
			int limit = 1000;
			Long lastOrderItem = 0L;
			//渠道容差,	默认:50
			BigDecimal tolerance = billAccount.getTolerance().abs();
			//最低余额为 ,  默认:-50
			BigDecimal lowest = tolerance.negate();
			
			//日期限制
			//如果出现 订单项不够结算的情况 ,只能在当前订单项的当月挑选订单
			// flag控制,只设置一次
			boolean flag = false;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.YEAR, 1000);
			Date maxDate = calendar.getTime();
			
			
			//查找-指定渠道-  可结算数量大0,或者出现有退货的订单项,根据订单项orderItem结算
			//辅助条件, orderItem(查询加速)  ,  saleprice (订单项的实洋需要小于剩余结算数量)
			//参数： 1.退货单未确认状态ID,  2.渠道ID,	3.orderItemId(加速)	4.saleprice	 5.订单创建最大时间
			String sql = "SELECT "
				+	" bis.orderitem, (bis.deliveryquantity - bis.settlementquantity) AS 'availableQuantity'"
				+	" ,SUM(IF(bri.returnorderitem IS NULL,0,ri.realquantity)) AS 'returnQuantity'"
				+	" ,IF(brji.orderitem != null, oi.deliveryquantity, 0) AS 'rejectQuantity'"
				+	" ,oi.saleprice AS 'saleprice',o.deliverytime, bis.settlementquantity AS 'historySettlement', bis.refoundquantity AS 'historyRefound'"
				+	" FROM bill_item_statistics bis"
				+	" INNER JOIN order_item oi ON (oi.id = bis.orderitem)"
				+	" INNER JOIN _order o ON (o.id = oi._order)"
				+	" LEFT JOIN bill_rejectorder_item brji ON (brji.orderitem = bis.orderitem)"
				+	" LEFT JOIN returnorder_item ri ON (ri.orderitem = bis.orderitem)"
				+	" LEFT JOIN bill_returnorder_item bri ON (bri.returnorderitem = ri.id AND bri.status = ?)"
				+	" WHERE bis.channel IN " + channelIds
				+	" AND bis.orderitem > ? AND oi.saleprice < ?"
				+	" AND bis.deliveryquantity > bis.settlementquantity"
				+	" AND o.deliverytime < ?"
				+	" GROUP BY bis.orderitem"
				+	" ORDER BY bis.orderitem"
				+	" LIMIT " + limit
				;
		
			List<OrderItemTemp> list;
			List<BillItemTemp> items = new ArrayList<BillItemTemp>(limit);
			do{
				
				list = jdbcTemplate.query(sql
							,new Object[]{Code.BILL_UNCONFIRMED, lastOrderItem, total.add(tolerance), maxDate}
							,new BeanPropertyRowMapper<OrderItemTemp>(OrderItemTemp.class));
				int len = list.size();
				L20:for (int i=0; i < len; i++ ) {
					
					OrderItemTemp o = list.get(i);
					lastOrderItem = o.getOrderitem();
					BillItemTemp item = new BillItemTemp();
					item.orderitem = o.orderitem;
					item.historySettlement = o.historySettlement;
					item.historyRefound = o.historyRefound;
					
					//本次结算商品数量
					int n = o.availableQuantity;
					
					//总金额 =总金额 -实洋 *(结算数量  -退货数量 - 拒收数量),(PS:由于业务特殊性 有退货,拒收数量为0.的拒收退货数量为0)
					BigDecimal tempTotal = total.subtract(o.saleprice.multiply(new BigDecimal(n - o.returnQuantity - o.rejectQuantity)));
					
					
					//如果本次结算数量>0 ,但是超过了容差, 则该则需要减少该商品的结算数量,并且启用  “只在当月订单挑选原则”
					while (n > 0 && tempTotal.compareTo(lowest) < 0){
						if (!flag) {
							flag = true;
							calendar.setTime(o.getDeliverytime());
							calendar.add(Calendar.MONTH, 1);
							calendar.set(Calendar.DATE, 1);
							calendar.set(Calendar.HOUR_OF_DAY, 0);
							calendar.set(Calendar.MINUTE, 0);
							calendar.set(Calendar.SECOND, 0);
							maxDate = calendar.getTime();
						}
						n--;
						//如果商品数量已经为0仍不满足..则不结算该商品项
						if (n == 0){
							continue L20;
						} else {
							tempTotal = total.subtract(o.saleprice.multiply(new BigDecimal(n - o.returnQuantity - o.rejectQuantity)));
						}
					}
					
					//总金额大于 -50 则继续
					if (tempTotal.compareTo(lowest) >= 0){
						total = tempTotal;
						item.settlementQuantity = n;
						item.refoundQuantity = o.returnQuantity + o.rejectQuantity;
						
						//流水号记帐
						bill.addSettlement(o.saleprice.multiply(new BigDecimal(item.settlementQuantity)));
						bill.addRefound(o.saleprice.multiply(new BigDecimal(item.refoundQuantity)));
					
						items.add(item);
					}
					
					// 金额在内差范围内结束
					if (total.abs().compareTo(tolerance) <= 0) {
						break;
					}
				}
				//批量添加
				if(!items.isEmpty()){
					String s = "INSERT INTO bill_item(bill,orderitem,settlementquantity,refoundquantity,status,historysettlement,historyrefound) VALUES ";
					StringBuilder sb = new StringBuilder(s);
					for (BillItemTemp i : items){
						sb.append(String.format("(%s,%s,%s,%s,%s,%s,%s),",bill.getId()
								,i.orderitem, i.settlementQuantity, i.refoundQuantity
								, Code.BILL_UNCONFIRMED,i.historySettlement,i.historyRefound));
					}
					sb.deleteCharAt(sb.length() - 1);
					jdbcTemplate.update(sb.toString());
					items.clear();
				}
			} while(!list.isEmpty() && total.compareTo(tolerance) > 0);
			
			
			
			//余额
			bill.setBalance(total);
			bill.setEndtime(new Date());
			bill.setStatus(new Code(Code.BILL_UNCONFIRMED));
			billDao.update(bill);
		} finally {
			hibernateLazyResolver.releaseSession();
		}
	}

	@Override
	public void appendAllocatment(List<OrderItem> orderitems, Long billId,
			Employee employee) throws BillException {
		if (orderitems == null || orderitems.isEmpty()){
			throw new BillException("订单项为空!");
		} 
		
		
		
		Bill bill = billDao.get(billId);
		if (!Code.BILL_UNCONFIRMED.equals(bill.getStatus().getId())) {
			throw new BillException(String.format("帐单状态不确认,当前状态%s", bill.getStatus().getName()));
		}
		
		Order order = orderitems.get(0).getOrder();
		Long[] itemIds = new Long[orderitems.size()];
		for (int i = 0 ; i < orderitems.size(); i++){
			OrderItem item = orderitems.get(i);
			
			if (!bill.containChannel(item.getOrder().getChannel())){
				throw new BillException(String.format("%s商品渠道与帐单不匹配", item.getProductSale().getSellName()));
			} else if (!order.getId().equals(item.getOrder().getId())){
				throw new BillException(String.format("%s不在同一个订单[%s]内", item.getProductSale().getSellName(), order.getId()));
			}
			
			itemIds[i] = item.getId();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bill", billId);
		params.put("orderItems", itemIds);
		Pagination pagination = new Pagination();
		pagination.setPageSize(orderitems.size());
		List<BillItem> list =  billDao.query(params, pagination, (short)0);
		if (!list.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (BillItem bi : list){
				sb.append(String.format("%s[%s];" ,
							bi.getOrderItem().getProductSale().getSellName(), 
							bi.getOrderItem().getProductSale().getId()));
			}
			throw new BillException("以下商品已经结算：" + sb.toString());
		}
		
		
		String itemStr = StringUtils.join(itemIds, ",");
		
		//--追加到bill_item --参数  billID,   未确认状态ID  orderitem(已拼接SQL中)  -- 拒收订单
		if (Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL.equals(order.getProcessStatus().getId())){
			String sql = "INSERT INTO bill_item (bill,orderitem,settlementquantity,refoundquantity,status,historysettlement,historyrefound)"
					+	" SELECT ? AS 'bill',oi.id,bis.deliveryquantity - bis.settlementquantity AS 'settlementquantity'"
					+	" ,oi.deliveryquantity AS 'refundquantity'"
					+	" ,? AS 'status'"
					+	" ,bis.settlementquantity AS 'historySettlement', bis.refoundquantity AS 'historyRefound'"
					+	" FROM bill_item_statistics bis"
					+	" INNER JOIN order_item oi ON (oi.id = bis.orderitem)"
					+	" INNER JOIN bill_rejectorder_item brji ON (brji.orderitem = oi.id AND brji.status = ?)"
					+	" WHERE bis.orderitem IN (" + itemStr + ")"
					;
			jdbcTemplate.update(sql, billId, Code.BILL_UNCONFIRMED, Code.BILL_UNCONFIRMED);
		} else {
			String sql = "INSERT INTO bill_item (bill,orderitem,settlementquantity,refoundquantity,status,historysettlement,historyrefound)"
				+	" SELECT ? AS 'bill',oi.id,bis.deliveryquantity - bis.settlementquantity AS 'settlementquantity'"
				+	" ,SUM(IF(bri.returnorderitem IS NULL,0,ri.realquantity)) AS 'refundquantity'"
				+	" ,? AS 'status'"
				+	" ,bis.settlementquantity AS 'historySettlement', bis.refoundquantity AS 'historyRefound'"
				+	" FROM bill_item_statistics bis"
				+	" INNER JOIN order_item oi ON (oi.id = bis.orderitem)"
				+	" LEFT JOIN returnorder_item ri ON (ri.orderitem = oi.id)"
				+	" LEFT JOIN bill_returnorder_item bri ON (bri.returnorderitem = ri.id AND bri.status = ?)"
				+	" WHERE bis.orderitem IN (" + itemStr + ")"
				+	" GROUP BY bis.orderitem"
				;
		
			jdbcTemplate.update(sql, billId, Code.BILL_UNCONFIRMED, Code.BILL_UNCONFIRMED);
		}
		
		
		//查询追加订单项的, 结算金额以及退款金额
		String sqlMoney = "SELECT SUM(oi.saleprice * bi.settlementquantity) AS settlement, SUM(oi.saleprice * bi.refoundquantity) AS 'refound'"
						+ " FROM bill_item bi"
						+ " INNER JOIN order_item oi ON (oi.id = bi.orderitem)"
						+ " WHERE bi.bill = ? AND bi.orderitem IN (" + itemStr + ")"
						;
		
		Map<String,Object> map = jdbcTemplate.queryForMap(sqlMoney, billId);
		BigDecimal settlement = new BigDecimal(map.get("settlement").toString());
		BigDecimal refound = new BigDecimal(map.get("refound").toString());
		bill.addRefound(refound);
		bill.addSettlement(settlement);
		bill.addBalance(refound);
		bill.addBalance(settlement.negate());
		bill.setLastEmployee(employee);
		bill.setUpdatetime(new Date());
		billDao.update(bill);
		
		//添加日志 --  参数  操作人ID ,   bill ID
		String sqlLog = "INSERT INTO bill_item_log(bill,orderitem,settlementquantity,refoundquantity,employee,log,updatetime)"
					+	" SELECT bi.bill, bi.orderitem,bi.settlementquantity,bi.refoundquantity, ? AS 'emplyee','add' AS 'log',NOW() AS 'updatetime' FROM bill_item bi"
					+	" WHERE bi.bill = ? AND bi.orderitem IN (" + itemStr + ")"
					;
		jdbcTemplate.update(sqlLog,employee.getId(), billId);
		
	}

	@Override
	public void removeAllocatment(OrderItem item, Long billId, Employee employee)
			throws BillException {
		Bill bill = billDao.get(billId);
		if (!Code.BILL_UNCONFIRMED.equals(bill.getStatus().getId())) {
			throw new BillException(String.format("帐单状态不确认,当前状态%s", bill.getStatus().getName()));
		}
		BillItem billItem = billDao.find(billId, item.getId());
		if (billItem == null) {
			throw new BillException(String.format("流水号:%s,不存在商品项:%s", billId, item.getProductSale().getSellName()));
		} else {
			BigDecimal saleprice = billItem.getOrderItem().getSalePrice();
			BigDecimal settlement = saleprice.multiply(new BigDecimal(billItem.getSettlementQuantity()));
			BigDecimal refound = saleprice.multiply(new BigDecimal(billItem.getRefoundQuantity()));
			
			
			//帐单退款金额与结算金额 减少,余额相应变化
			bill.addSettlement(settlement.negate());
			bill.addRefound(refound.negate());
			bill.addBalance(settlement);
			bill.addBalance(refound.negate());
			
			bill.setLastEmployee(employee);
			bill.setUpdatetime(new Date());
			
			BillItemLog log = new BillItemLog(bill,billItem.getOrderItem(), employee, new Date());
			log.setSettlementQuantity(billItem.getSettlementQuantity());
			log.setRefoundQuantity(billItem.getRefoundQuantity());
			log.setLog("remove");
			
			billDao.saveLog(log);
			billDao.remove(billItem);
			billDao.update(bill);
			
			
		}
		
	}

	@Override
	public void confirm(Bill bill, Employee employee) throws BillException {
		Bill lastBill = this.lastBill(bill.getBillAccount().getId());
		if (!lastBill.getId().equals(bill.getId())){
			throw new BillException(String.format("当前帐户被%s[%s]重新抽单!" 
					,lastBill.getEmployee().getName(), lastBill.getEmployee().getId()));
		} else if(!Code.BILL_LOCK.equals(bill.getStatus().getId())) {
			throw new BillException(String.format("当前帐单状态不正确!!当前状态:%s" 
					,bill.getStatus().getName()));
		}

//		//修改历史数据的结算，退货，冲销数量,  流水号状态,以及,流水单状态 ,渠道帐户余额
//		//参数  已确认状态 2个,  未确认状态ID
		String sql = "UPDATE bill_item_statistics bis, bill_item bi,bill b,bill_account ba SET "
					+ " bis.settlementquantity = bis.settlementquantity + bi.settlementquantity ,"
					+ " bis.refoundquantity = bis.refoundquantity + bi.refoundquantity,"
					+ " bi.status = ?, b.status = ? , ba.balance = b.balance"
					+ " WHERE bi.bill = b.id AND bi.orderitem = bis.orderitem AND b.billaccount = ba.id"
					+ " AND bi.status = ? AND b.id = ? "
					;
		jdbcTemplate.update(sql,Code.BILL_CONFIRMED, Code.BILL_CONFIRMED, Code.BILL_UNCONFIRMED, bill.getId());
//		
//		// 修改退货单状态,关联的billitem -- 参数  已确认状态ID, 未确认状态ID,  流水号ID
		String sqlReturn = "UPDATE bill_returnorder_item bri, bill_item bi, bill b, returnorder_item ri"
					+ " SET bri.status = ?, bri.billitem = bi.id, bri.updatetime = NOW()"
					+ " WHERE "
					+ " b.id = bi.bill AND ri.orderitem = bi.orderitem AND bri.returnorderitem = ri.id"
					+ " AND bri.status = ? AND ri.realquantity > 0"
					+ " AND b.id = ? AND bi.refoundquantity > 0"
					;
		jdbcTemplate.update(sqlReturn ,Code.BILL_CONFIRMED, Code.BILL_UNCONFIRMED, bill.getId());

		//修改 拒收订单状态,关联billitem -- 参数 已确认状态ID, 未确认状态ID, 流水号ID
		String sqlReject = "UPDATE bill_rejectorder_item bri, bill_item bi, bill b"
						+	" SET bri.status = ?, bri.billitem = bi.id, bri.updatetime = NOW()"
						+	" WHERE "
						+	" b.id = bi.bill AND  bi.orderitem = bri.orderitem"
						+	" AND bri.status = ? AND b.id = ? AND bi.refoundquantity > 0"
						;
		jdbcTemplate.update(sqlReject ,Code.BILL_CONFIRMED, Code.BILL_UNCONFIRMED, bill.getId());
		
		bill.setLastEmployee(employee);
		bill.setUpdatetime(new Date());
		bill.setStatus(new Code(Code.BILL_CONFIRMED));
		billDao.update(bill);
	}

	@Override
	public List<BillItem> query(Map<String, Object> params,
			Pagination pagination, short index) {
		return billDao.query(params, pagination, index);
	}
	
	@Override
	public List<Bill> queryBills(Map<String, Object> params,
			Pagination pagination, short index) {
		return billDao.queryBills(params, pagination, index);
	}
	
	@Override
	public List<Bill> queryByStatus(Map<String, Object> params, Pagination pagination,short index) {
		return billDao.queryByStatus(params, pagination, index);
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		billService = applicationContext.getBean(BillServiceImpl.class);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(billService);
		
	}
	/**
	 * 临时批量存储
	 * @author heyadong
	 *
	 */
	public static class OrderItemTemp{
		//orderItem ID
		private Long orderitem;
		//可结算数量
		private int availableQuantity;
		//退货总数(冲销+退款)
		private int returnQuantity;
		
		//据收退货数量(有销售订单,无退货订单,客户拒收)
		private int rejectQuantity;
		//当前历史结算数量
		private int historySettlement = 0;
		//当前历史退货数量
		private int historyRefound = 0;
		
		
		//实洋
		private BigDecimal saleprice;
		//订单发货时间
		private Date deliverytime;
		
		public int getRejectQuantity() {
			return rejectQuantity;
		}
		public void setRejectQuantity(int rejectQuantity) {
			this.rejectQuantity = rejectQuantity;
		}
		public Long getOrderitem() {
			return orderitem;
		}
		public void setOrderitem(Long orderitem) {
			this.orderitem = orderitem;
		}
		public int getAvailableQuantity() {
			return availableQuantity;
		}
		public void setAvailableQuantity(int availableQuantity) {
			this.availableQuantity = availableQuantity;
		}
		public int getReturnQuantity() {
			return returnQuantity;
		}
		public void setReturnQuantity(int returnQuantity) {
			this.returnQuantity = returnQuantity;
		}
		public BigDecimal getSaleprice() {
			return saleprice;
		}
		
		public void setSaleprice(BigDecimal saleprice) {
			this.saleprice = saleprice;
		}
		public Date getDeliverytime() {
			return deliverytime;
		}
		public void setDeliverytime(Date deliverytime) {
			this.deliverytime = deliverytime;
		}
		public int getHistorySettlement() {
			return historySettlement;
		}
		public void setHistorySettlement(int historySettlement) {
			this.historySettlement = historySettlement;
		}
		public int getHistoryRefound() {
			return historyRefound;
		}
		public void setHistoryRefound(int historyRefound) {
			this.historyRefound = historyRefound;
		}
		
	}
	/**
	 * 临时存储数据 
	 * @author heyadong
	 *
	 */
	public static class BillItemTemp {
		//bill item ID
		private long id;
		
		private long orderitem;
		//结算数量
		private int settlementQuantity = 0;
		//退货数量(退款)
		private int refoundQuantity = 0;
		//当前历史结算数量
		private int historySettlement = 0;
		//当前历史退货数量
		private int historyRefound = 0;
		
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getOrderitem() {
			return orderitem;
		}
		public void setOrderitem(long orderitem) {
			this.orderitem = orderitem;
		}
		public int getSettlementQuantity() {
			return settlementQuantity;
		}
		public void setSettlementQuantity(int settlementQuantity) {
			this.settlementQuantity = settlementQuantity;
		}
		public int getRefoundQuantity() {
			return refoundQuantity;
		}
		public void setRefoundQuantity(int refoundQuantity) {
			this.refoundQuantity = refoundQuantity;
		}
		
		public int getHistorySettlement() {
			return historySettlement;
		}
		public void setHistorySettlement(int historySettlement) {
			this.historySettlement = historySettlement;
		}
		public int getHistoryRefound() {
			return historyRefound;
		}
		public void setHistoryRefound(int historyRefound) {
			this.historyRefound = historyRefound;
		}
	}
	
	@Override
	public List<BillAccount> queryBillAccount(Map<String, Object> params,
			Pagination pagination, short index) {
		return billDao.queryBillAccount(params, pagination, index);
	}
	


	public void setHibernateLazyResolver(HibernateLazyResolver hibernateLazyResolver) {
		this.hibernateLazyResolver = hibernateLazyResolver;
	}



	@Override
	public void lock(Bill bill, Employee empolyee) throws BillException {
		if(!Code.BILL_UNCONFIRMED.equals(bill.getStatus().getId())){
			throw new BillException(String.format("帐单状态不确认,当前状态%s", bill.getStatus().getName()));
		} else {
			bill.setStatus(new Code(Code.BILL_LOCK));
			bill.setLastEmployee(empolyee);
			bill.setUpdatetime(new Date());
			billDao.save(bill);
		}
		
	}



	@Override
	public List<OrderItem> getOrderItems(Long billId, String orderId) {
		String sql = "SELECT oi.id FROM order_item oi "
					+ " LEFT JOIN bill_item bi ON (bi.orderitem = oi.id AND bi.bill = ? )"
					+ " WHERE oi._order = ? AND bi.orderitem IS NULL AND oi.deliveryquantity > 0"
					;
		List<Long> list = jdbcTemplate.queryForList(sql,new Object[]{billId, orderId}, Long.class);
		if (list.isEmpty()) {
			return new ArrayList<OrderItem>();
		} else {
			return orderDao.findOrderItem(list.toArray(new Long[list.size()]));
		}
	}
	
	@Override
	public void removeBillReceiptRecord(BillReceiptRecord billReceiptRecord) {
		billDao.remove(billReceiptRecord);
	}
	
	@Override
	public void updateBillStatusBySapInvoice(Bill bill) {
		String rejectSql = "UPDATE bill_rejectorder_item bri inner join bill_item bi on bri.billitem = bi.id"
				+ " inner join order_item oi on  bi.orderitem = oi.id" 
				+ " inner join _order o on oi._order = o.id"
				+ " left join order_no_invoice oni on oni._order = o.id "
				+ " SET bri.status = ? WHERE  bi.status = ? "
				+ " AND bi.bill = ? AND o.createtime < '2013-06-27' AND oni._order is null;";
								
		String returnSql = "UPDATE bill_returnorder_item bri inner join bill_item bi on bri.billitem = bi.id"
				+ " inner join order_item oi on  bi.orderitem = oi.id" 
				+ " inner join _order o on oi._order = o.id"
				+ " left join order_no_invoice oni on oni._order = o.id "
				+ " SET bri.status = ? WHERE  bi.status = ? "
				+ " AND bi.bill = ? AND o.createtime < '2013-06-27' AND oni._order is null"
				+ " OR o.id IN ('20130325715065','20130321604333','20130402750327','20130321604334','20130402750323','20130327737149','20130320659531','20130320659521');";

		String itemSql = "UPDATE bill_item bi inner join order_item oi on bi.orderitem = oi.id"
				+ " inner join _order o	on oi._order = o.id "
				+ " left join order_no_invoice oni on oni._order = o.id "
				+ " SET bi.status = ? WHERE bi.status = ?"
				+ " AND bi.bill = ? AND o.createtime < '2013-06-27' AND oni._order is null";
		int rejectNum = jdbcTemplate.update(rejectSql, Code.BILL_ORD_DATA, Code.BILL_CONFIRMED, bill.getId());
		int returnNum = jdbcTemplate.update(returnSql, Code.BILL_ORD_DATA, Code.BILL_CONFIRMED, bill.getId());
		int itemNum = jdbcTemplate.update(itemSql, Code.BILL_ORD_DATA, Code.BILL_CONFIRMED, bill.getId());
		LOG.info("Bill: " + bill.getId() + "期初数据处理。  拒收项更新 ：" + rejectNum + "; 退货项更新: " + returnNum + "; 订单项更新：" + itemNum);
	}
	
	@Override
	public List<BillItemSap> findSapBillItems(Bill bill) {
		List<BillItemSap> sapList = new ArrayList<BillItemSap>();
		
		String itemSql = "SELECT o.id outerorder, oi.id outeritem, bi.bill bill,"
				+ " o.customer customer, ps.outerid sapid, oi.purchasequantity quantity, o.channel channel, "
				+ " oi.deliveryquantity deliveryquantity, bi.settlementquantity settle, bi.historysettlement historysettle, oi.listprice listprice"
				+ " FROM bill_item bi "
				+ " INNER JOIN order_item oi ON (oi.id = bi.orderitem)"
				+ " INNER JOIN _order o ON (o.id = oi._order)"
				+ "	INNER JOIN product_sale ps ON (ps.id = oi.productsale)"
				+ " WHERE bi.bill = ? AND bi.status = 90002 AND bi.settlementquantity > 0 AND o.transferresult != 35003 AND o.shop=1 AND o.virtual = 0;";
		List<BillItemSap> itemSapList = jdbcTemplate.query(itemSql, new Object[]{bill.getId()}, 
				new BeanPropertyRowMapper<BillItemSap>(BillItemSap.class));
		sapList.addAll(itemSapList);
		
		String returnItemSql = "SELECT ri.returnorder outerorder, ri.id outeritem, bi.bill bill, 'X' as returnflag,"
				+ " o.customer customer, ps.outerid sapid, oi.purchasequantity quantity, o.id `order`, oi.id orderitem, o.channel channel, "
				+ " oi.deliveryquantity deliveryquantity, ri.realquantity settle, bi.historyrefound historysettle, oi.listprice listprice"
				+ " FROM bill_returnorder_item bri"
				+ " INNER JOIN returnorder_item ri ON (ri.id = bri.returnorderitem)"
				+ " INNER JOIN bill_item bi ON (bi.id = bri.billitem)"
				+ " INNER JOIN order_item oi ON (oi.id = bi.orderitem)"
				+ " INNER JOIN _order o ON (o.id = oi._order)"
				+ " INNER JOIN product_sale ps ON (ps.id = oi.productsale)"
				+ " WHERE bi.bill = ? AND bi.status = 90002 AND bi.refoundquantity > 0 AND o.transferresult != 35003 AND o.shop=1 AND o.virtual = 0;";
		List<BillItemSap> returnItemSapList = jdbcTemplate.query(returnItemSql, new Object[]{bill.getId()}, 
				new BeanPropertyRowMapper<BillItemSap>(BillItemSap.class));
		sapList.addAll(returnItemSapList);
		
		String rejectItemSql = "SELECT CONCAT(CONVERT(LEFT(oi._order,1),SIGNED) + 2 , RIGHT(o.id,LENGTH(oi._order) - 1)) outerorder, oi.id outeritem, bi.bill bill, 'X' as returnflag,"
				+ " o.customer customer, ps.outerid sapid, oi.purchasequantity quantity, o.id `order`, oi.id orderitem, o.channel channel, "
				+ " oi.deliveryquantity deliveryquantity, bi.refoundquantity settle, bi.historyrefound historysettle, oi.listprice listprice"
				+ "	FROM bill_rejectorder_item bri"
				+ " INNER JOIN bill_item bi ON (bi.id = bri.billitem)"
				+ " INNER JOIN order_item oi ON (oi.id = bi.orderitem)"
				+ " INNER JOIN _order o ON (o.id = oi._order)"
				+ "	INNER JOIN product_sale ps ON (ps.id = oi.productsale)"
				+ " WHERE bi.bill = ? AND bi.status = 90002 AND bi.refoundquantity > 0 AND o.transferresult != 35003  AND o.shop=1 AND o.virtual = 0;";
		List<BillItemSap> rejectItemSapList = jdbcTemplate.query(rejectItemSql, new Object[]{bill.getId()}, 
				new BeanPropertyRowMapper<BillItemSap>(BillItemSap.class));
		sapList.addAll(rejectItemSapList);
		
		LOG.info("内部结算Bill下传sap。发货项：" + itemSapList.size() + "；退货项：" + returnItemSapList.size() + "；拒收项：" + rejectItemSapList.size());
		return sapList;
	}
	
	@Override
	public void updateBillItemSapStatus(Bill bill) {
		String updateRejectSql = "UPDATE bill_rejectorder_item bri, bill_item bi SET bri.status = ? "
				+ "WHERE bri.billitem = bi.id AND bi.bill = ? AND bri.status = ?;";
		String updateReturnSql = "UPDATE bill_returnorder_item bri, bill_item bi SET bri.status = ? "
				+ "WHERE bri.billitem = bi.id AND bi.bill = ? AND bri.status = ?;";
		String updateItemSql = "UPDATE bill_item SET status = ? WHERE bill = ? AND status = ?;";	
			
		jdbcTemplate.update(updateRejectSql, Code.BILL_PROCESSED, bill.getId(), Code.BILL_CONFIRMED);
		jdbcTemplate.update(updateReturnSql, Code.BILL_PROCESSED, bill.getId(), Code.BILL_CONFIRMED);
		jdbcTemplate.update(updateItemSql, Code.BILL_PROCESSED, bill.getId(), Code.BILL_CONFIRMED);
	}



	@Override
	public void delete(Long[] billIds) {
		if(billIds.length>0) {
			for(long billId : billIds) {
				billDao.remove(billDao.get(billId));
			}
		}
	}
	
}
