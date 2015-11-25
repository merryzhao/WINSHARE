package com.winxuan.ec.service.channel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.ChannelSalesException;
import com.winxuan.ec.model.channel.ChannelSalesProduct;
import com.winxuan.ec.model.channel.ChannelSalesRecord;
import com.winxuan.ec.model.channel.ChannelSalesUploadRecord;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * 渠道销售接口
 * 该销售特指渠道真实销售
 * ,并非EC渠道下传销售
 * @author heyadong
 *
 */
public interface ChannelSalesService {
	
	/**
	 * 上传销售记录
	 * @param uploadrecord
	 * @param records
	 */
	void upload(ChannelSalesUploadRecord uploadrecord, List<ChannelSalesRecord> records);
	
	/**
	 * 销售记录保存,独立事务
	 * @param uploadrecord
	 * @param records
	 */
	Long transcationUpload(ChannelSalesUploadRecord uploadrecord, List<ChannelSalesRecord> records);
	
	List<ChannelSalesUploadRecord> find(Map<String,Object> params, Pagination pagination);
	
	/**
	 * 删除上传记录
	 * @param uploadrecordId
	 * @param operator
	 */
	void delete(Long uploadrecordId, Employee operator);
	
	ChannelSalesUploadRecord get(Long id);
	
	/**
	 * 冲销
	 * @param uploadrecordId 上传销售ID
	 * @param employee
	 * @throws ChannelSalesException
	 */
	void rollback(Long uploadrecordId, Employee employee) throws ChannelSalesException;
	
	List<ChannelSalesProduct> findChannelSalesProduct(@ParameterMap Map<String,Object> params,@Page Pagination pagination, @Order Short order);
	
	/**
	 * 删除渠道商品关联关系
	 * @param channelProductID
	 * @param employee
	 */
	void deleteChannelSalesProduct(Long[] channelProductID, Employee employee);
	
	/**
	 * 批量添加渠道商品对应关系
	 * @param channels 指定渠道Id, 多个,则同时添加多个渠道如：苏宁先采后销,苏宁先销后采
	 * @param channelproduct 商品对应关系,K:渠道商品编码,  V:EC销售商品ID
	 * @param employee 操作人
	 */
	void appendChannelSalesProduct(List<Long> channels, Map<String,Long> channelsalesProducts,  Employee employee) throws ChannelSalesException;
	
	
	void sendToSap(BigDecimal money, Employee employee) throws ChannelSalesException;
	
	/**
	 * 更新校验失败的记录，筛选出超卖超退的销售商品
	 * @param uploadRecordId
	 */
	void audit(Long uploadRecordId);
	/**
	 * 最小下传金额
	 * @return
	 */
	BigDecimal getMinMoney();
}
