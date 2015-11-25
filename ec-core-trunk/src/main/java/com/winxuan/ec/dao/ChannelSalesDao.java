package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.channel.ChannelSalesProduct;
import com.winxuan.ec.model.channel.ChannelSalesSapRecord;
import com.winxuan.ec.model.channel.ChannelSalesUploadRecord;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
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
 * 渠道销售DAO
 * @author heyadong
 *
 */
public interface ChannelSalesDao {

	@Get
	ChannelSalesUploadRecord get(Long id);
	
	
	@Save
	void save(ChannelSalesUploadRecord uploadrecord);
	
	@Query("SELECT csur FROM ChannelSalesUploadRecord csur")
	@Conditions({
		@Condition(" csur.status.id NOT IN :nstatus")
	})
	List<ChannelSalesUploadRecord> find(@ParameterMap Map<String,Object> params,@Page Pagination pagination);
	
	@Query("SELECT csp FROM ChannelSalesProduct csp")
	@Conditions({
		@Condition(" csp.channel IN :channels"),
		@Condition(" csp.channel.id IN :channelIds"),
		@Condition(" csp.channelProduct IN :channelProducts"),
		@Condition(" csp.productSale.id IN :productSales")
	})
	@OrderBys({
		@OrderBy("csp.id")
	})
	List<ChannelSalesProduct> findChannelSalesProduct(@ParameterMap Map<String,Object> params,@Page Pagination pagination, @Order Short order);
	
	@Get
	ChannelSalesProduct getChannelSalesProduct(Long id);
	
	@Save
	void saveChannelSalesProduct(ChannelSalesProduct channelSalesProduct);
	
	@Delete
	void deleteChannelSalesProduct(ChannelSalesProduct channelSalesProduct);
	
	@Save
	void saveSapRecord(ChannelSalesSapRecord sapRecord);
	
}
