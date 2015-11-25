package com.winxuan.ec.service.bill;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.BillException;
import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.bill.BillAccount;
import com.winxuan.ec.model.bill.BillItem;
import com.winxuan.ec.model.bill.BillItemSap;
import com.winxuan.ec.model.bill.BillReceiptRecord;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * 帐单结算服务
 * 
 * @author heyadong
 * 
 */
public interface BillService {

	/**
	 * 结算渠道BillAccount
	 * 
	 * @param BillAccount
	 */
	void saveBillAccount(BillAccount billAccount);

	/**
	 * 追加渠道
	 * 
	 * @param billAccount
	 * @param channel
	 */
	void appendToBillAccount(BillAccount billAccount, List<Channel> channels)
			throws BillException;

	/**
	 * 移除渠道
	 * 
	 * @param billAccount
	 * @param channel
	 * @throws BillException
	 */
	void removeFromBillAccount(Long billAccountId, Long channelId)
			throws BillException;

	/**
	 * 修改容差
	 * 
	 * @param billAccount
	 * @param money
	 * @param employee
	 */
	void updateTolerance(BillAccount billAccount, BigDecimal money,
			Employee employee);

	/**
	 * 渠道新订单项,退货单数据同步
	 */
	void sync(List<Channel> channels);

	/**
	 * 获取Bill
	 * 
	 * @param id
	 * @return
	 */
	Bill get(Long id);

	void update(Bill bill);
	
	BillAccount getBillAccount(Long billAccountId);

	/**
	 * 获取最近一次帐单,如果没有则为NULL
	 * 
	 * @param billAccountId
	 */
	Bill lastBill(Long billAccountId);

	/**
	 * 分配订单
	 * 
	 * @param invoice
	 *            发票编号
	 * @param list
	 *            清单编号
	 * @param billAccount
	 * @param channels
	 * @param money
	 * @param employee
	 * @return
	 * @throws BillException
	 */
	Bill allotment(String invoice, String list, BillAccount billAccount,
			List<Channel> channels, BigDecimal money, Employee employee)
			throws BillException;

	/**
	 * 向分配记录追加订单项
	 * 
	 * @param orderitems
	 * @param billId
	 * @throws BillException
	 */
	void appendAllocatment(List<OrderItem> orderitems, Long billId,
			Employee employee) throws BillException;

	/**
	 * 移出已分配的订单项
	 * 
	 * @param item
	 * @param billId
	 */
	void removeAllocatment(OrderItem item, Long billId, Employee employee)
			throws BillException;

	/**
	 * 确认
	 * 
	 * @param bill
	 * @param employee
	 * @throws BillException
	 */
	void confirm(Bill bill, Employee employee) throws BillException;

	/**
	 * 锁定
	 * 
	 * @param bill
	 * @param empolyee
	 */
	void lock(Bill bill, Employee empolyee) throws BillException;

	/**
	 * 查询BillItem
	 * 
	 * @param params
	 * @param pagination
	 * @param index
	 * @return
	 */
	List<BillItem> query(@ParameterMap Map<String, Object> params,
			@Page Pagination pagination, @Order short index);

	/**
	 * 模糊查询Bill
	 * 
	 * @param params
	 * @param pagination
	 * @param index
	 * @return
	 */
	List<Bill> queryBills(Map<String, Object> params, Pagination pagination,
			short index);

	/**
	 * 查询BillAccount
	 * 
	 * @param params
	 * @param pagination
	 * @param index
	 * @return
	 */
	List<BillAccount> queryBillAccount(
			@ParameterMap Map<String, Object> params,
			@Page Pagination pagination, @Order short index);

	/**
	 * 获取可以分配的订单项
	 * 
	 * @param billId
	 * @param orderId
	 * @return
	 */
	List<OrderItem> getOrderItems(Long billId, String orderId);

	/**
	 * 获取到款记录BillReceiptRecord
	 */
	BillReceiptRecord getBillReceiptRecord(Long billReceiptRecordId);
	
	/**
	 * 创建到款记录
	 */
	void saveBillReceiptRecord(BillReceiptRecord billReceiptRecord);
	
	/**
	 * 删除
	 */
	void removeBillReceiptRecord(BillReceiptRecord billReceiptRecord);
	
	/**
	 * 搜索某账号相应状态的订单
	 * @param params
	 * @param pagination
	 * @param index
	 * @return
	 */
	List<Bill> queryByStatus(Map<String, Object> params, Pagination pagination,short index);
	
	/**
	 * 将SAP已开票的单项状态改为90009
	 * @param billId
	 */
	void updateBillStatusBySapInvoice(Bill bill);
	
	/**
	 * 查找下传SAP的订单项
	 * @param bill
	 * @return
	 */
	List<BillItemSap> findSapBillItems(Bill bill);
	
	/**
	 * 更新结算单状态为已下传SAP
	 */
	void updateBillItemSapStatus(Bill bill);

	/**
	 * 删除bill
	 * @param billIds
	 */
	void delete(Long[] billIds);
	
}
