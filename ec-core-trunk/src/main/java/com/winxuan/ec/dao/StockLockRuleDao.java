package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author liou 2014-10-15下午1:19:41
 */
public interface StockLockRuleDao {

	@Get
	StockLockRule get(Long id);

	@Save
	void save(StockLockRule lockRule);

	@Update
	void update(StockLockRule lockRule);
	
	@Query(value="update stock_lock_rule set sales=if(convert(sales + (?), signed)<=0, 0, convert(sales + (?), signed)) where status=1 and begintime<NOW() and endtime>NOW() and channel=? and productsale=? and customer is null", sqlQuery=true, executeUpdate=true)
	int updateSales(int sales1, int sales2, long channelId, long productSaleId);
	
	@Query(value="update stock_lock_rule set sales=if(convert(sales + (?), signed)<=0, 0, convert(sales + (?), signed)) where status=1 and begintime<NOW() and endtime>NOW() and channel=? and productsale=? and customer=?", sqlQuery=true, executeUpdate=true)
	int updateSales(int sales1, int sales2, long channelId, long productSaleId, long orderCustomerId);

	@Query("from StockLockRule slr")
	@Conditions({ 
		@Condition("slr.channel.id in :channels"), 
		@Condition("slr.productSale in :productSales"),
		@Condition("slr.customer.id in :customerId"), 
		@Condition("slr.sales = :sales"), 
		@Condition("slr.status = :status"),
		@Condition("slr.createTime = :createTimeBegin"), 
		@Condition("slr.createTime = :createTimeEnd"),
		@Condition("slr.beginTime >= :beginTimeBegin"), 
		@Condition("slr.beginTime <= :beginTimeEnd") })
	@OrderBys({ 
		@OrderBy("slr.id"), 
		@OrderBy("slr.id desc"),
		@OrderBy("slr.createTime"),
		@OrderBy("slr.createTime desc") })
	List<StockLockRule> find(@Page Pagination pagination, @ParameterMap Map<String, Object> parame, @Order short sort);
	
	@Query("from StockLockRule slr where slr.status=1 and slr.beginTime<NOW() and slr.endTime>NOW()")
	@Conditions({
		@Condition("slr.productSale = :productSaleId") })
	List<StockLockRule> findEffective(@ParameterMap Map<String, Object> parameters);
	
	@Query("from StockLockRule slr where slr.status=1 and slr.beginTime<NOW() and slr.endTime>NOW() and slr.channel.id=? and slr.productSale=? and slr.customer.id is null")
	StockLockRule findEffective(long channelId, long productSaleId);

	@Query("from StockLockRule slr where slr.status=1 and slr.beginTime<NOW() and slr.endTime>NOW() and slr.channel.id=? and slr.productSale=? and slr.customer.id=?")
	StockLockRule findEffective(long channelId, long productSaleId, long orderCustomerId);

	@Query(value = "select ifnull(sum(if(slr.reallock>slr.sales, slr.reallock-slr.sales, 0)),0) num from stock_lock_rule slr where slr.status=1 and slr.begintime<NOW() and slr.endtime>NOW() and slr.productsale=?", sqlQuery = true)
	long getLockedSumAvailableStock(long productSaleId);
	
	@Query("from StockLockRule slr where slr.customer is null and slr.channel.id=? and slr.productSale=?")
	StockLockRule getStockLockBycustomerNull(Long channelId,Long productSale);
	
	@Query(value="select slr.productsale,sum(if(slr.reallock>slr.sales, slr.reallock-slr.sales, 0)) num from stock_lock_rule slr where slr.status=1 and slr.begintime<NOW() and slr.endtime>NOW() and slr.productsale in :productSaleIds group by slr.productsale", sqlQuery=true)
	List<Map<String, Object>> getLockedSumAvailableStocks(@Parameter("productSaleIds")List<Long> productSaleIds);
}
