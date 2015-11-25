package com.winxuan.ec.model.product;

import java.io.Serializable;

import com.winxuan.ec.enums.StockOriginEnum;

/**
 * 
 * @author YangJun
 * @version 1.0, 2014
 *
 */
public class MessageStock implements Serializable {
	private static final long serialVersionUID = 1294950550218105514L;

	private StockOriginEnum origin = StockOriginEnum.DEFAULT;

	private long productSaleId;

	// 渠道ID
	private Long channelId;

	// 下单用户
	private Long orderCustomerId;

	// 增量库存
	private Integer incrementStock;

	public MessageStock() {
		super();
	}

	/**
	 * 
	 * @param origin
	 *            库存更新的来源
	 * @param productSaleId
	 *            EC编码ID
	 */
	public MessageStock(StockOriginEnum origin, long productSaleId) {
		super();
		this.origin = origin;
		this.productSaleId = productSaleId;
	}

	/**
	 * 
	 * @param origin
	 *            库存更新的来源
	 * @param productSaleId
	 *            EC编码ID
	 * @param channelId
	 *            渠道ID
	 */
	public MessageStock(StockOriginEnum origin, long productSaleId, Long channelId) {
		super();
		this.origin = origin;
		this.productSaleId = productSaleId;
		this.channelId = channelId;
	}

	/**
	 * 
	 * @param origin
	 *            库存更新的来源
	 * @param productSaleId
	 *            EC编码ID
	 * @param channelId
	 *            渠道ID
	 * @param incrementStock
	 *            增量库存
	 */
	public MessageStock(StockOriginEnum origin, long productSaleId, Long channelId, Integer incrementStock) {
		this(origin, productSaleId, channelId);
		this.incrementStock = incrementStock;
	}

	/**
	 * 
	 * @param origin
	 *            库存更新的来源
	 * @param productSaleId
	 *            EC编码ID
	 * @param channelId
	 *            渠道ID
	 * @param orderCustomerId
	 *            下单用户,用于加盟店
	 * @param incrementStock
	 *            增量库存
	 */
	public MessageStock(StockOriginEnum origin, long productSaleId, Long channelId, Long orderCustomerId,
			Integer incrementStock) {
		this(origin, productSaleId, channelId, incrementStock);
		this.orderCustomerId = orderCustomerId;
	}

	public StockOriginEnum getOrigin() {
		return origin;
	}

	public long getProductSaleId() {
		return productSaleId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public Long getOrderCustomerId() {
		return orderCustomerId;
	}

	public Integer getIncrementStock() {
		return incrementStock;
	}

	/**
	 * 判断是否增量更新库存
	 * 
	 * @param channelId
	 *            渠道ID
	 * @return 返回true时,直接使用渠道增量接口更新库存,否则使用EC提供的库存接口获取当前可分配库存
	 */
	public boolean isIncrement(long channelId) {
		if (null == this.channelId || this.channelId.longValue() != channelId) {
			return false;
		}

		if (null != origin && null != incrementStock && StockOriginEnum.INCREMENT.getOrigin() == origin.getOrigin()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否增量更新库存,用于加盟店
	 * 
	 * @param channelId
	 *            渠道ID
	 * @param orderCustomerId
	 *            下单用户ID
	 * @return 返回true时,直接使用渠道增量接口更新库存,否则使用EC提供的库存接口获取当前可分配库存
	 */
	public boolean isIncrement(long channelId, long orderCustomerId) {
		if (null == this.channelId || null == this.orderCustomerId) {
			return false;
		}
		if (this.channelId.longValue() != channelId) {
			return false;
		}
		if(this.orderCustomerId.longValue() != orderCustomerId){
			return false;
		}

		if (null != origin && null != incrementStock && StockOriginEnum.INCREMENT.getOrigin() == origin.getOrigin()) {
			return true;
		}

		return false;
	}
}
