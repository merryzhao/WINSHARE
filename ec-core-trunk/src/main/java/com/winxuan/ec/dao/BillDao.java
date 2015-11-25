package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.bill.BillAccount;
import com.winxuan.ec.model.bill.BillItem;
import com.winxuan.ec.model.bill.BillItemLog;
import com.winxuan.ec.model.bill.BillReceiptRecord;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * BillDao
 * @author heyadong
 *
 */
public interface BillDao {
	@Save
	void save(Bill bill);
	@Update
	void update(Bill bill);
	@Get
	Bill get(Long id);
	@Get
	BillItem getBillItem(Long billItem);
	@Save
	void saveLog(BillItemLog log);
	@Save
	void saveBillAccount(BillAccount billAccount);
	@Save
	void saveBillReceiptRecord(BillReceiptRecord billReceiptRecord);
	
	@Delete
	void remove(BillItem billitem);
	@Delete
	void remove(BillReceiptRecord billReceiptRecord);
	@Delete
	void remove(Bill bill);
	
	@Get
	BillAccount getBillAccount(Long billAccountId);
	@Get
	BillReceiptRecord getBillReceiptRecord(Long billReceiptRecordId);
	@Update
	void updateBillAccount(BillAccount billAccount);
	
	@Query("SELECT bi FROM BillItem bi")
	@Conditions({
		@Condition(" bi.bill.id = :bill"),
		@Condition(" bi.bill.list = :list"),
		@Condition(" bi.bill.invoice = :invoice"),
		@Condition(" bi.orderItem.id IN :orderItems")
	})
	@OrderBys({
		@OrderBy(" bi.id ASC")
	})
	List<BillItem> query(@ParameterMap Map<String, Object> params, @Page Pagination pagination,@Order short index);
	
	@Query("SELECT b FROM Bill b")
	@Conditions({
		@Condition(" b.id = :bill"),
		@Condition(" b.list LIKE :list"),
		@Condition(" b.invoice LIKE :invoice"),
		@Condition(" b.billAccount.name LIKE :accountName"),
		@Condition(" b.createtime >= :startDate"),
		@Condition(" b.createtime <= :endDate")
	})
	@OrderBys({
		@OrderBy(" b.id ASC")
	})
	List<Bill> queryBills(@ParameterMap Map<String, Object> params, @Page Pagination pagination,@Order short index);
	
	@Query("SELECT b FROM Bill b")
	@Conditions({
		@Condition(" b.billAccount.id = :billAccountId"),
		@Condition(" b.status.id IN :statusCodes")
	})
	List<Bill> queryByStatus(@ParameterMap Map<String, Object> params, @Page Pagination pagination,@Order short index);
	
	@Query("SELECT bi FROM BillItem bi WHERE bi.bill.id = ? AND bi.orderItem.id = ?")
	BillItem find(Long billId, Long orderItemId);
	
	
	
	@Query("SELECT ba FROM BillAccount ba")
	@Conditions({
		@Condition(" ba.name = :name")
	})
	@OrderBys({
		@OrderBy(" ba.id ASC")
	})
	List<BillAccount> queryBillAccount(@ParameterMap Map<String, Object> params, @Page Pagination pagination,@Order short index);
}
