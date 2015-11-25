/*
 * @(#)ChannelProductService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.product;

import com.winxuan.ec.model.product.MessageStock;

/**
 * 渠道商品接口
 * 
 * @author qiaoyao
 * @version 1.0,2011-11-9
 */
public interface ChannelProductService {

	/**
	 * 实时更新指定商品在所有API对接渠道的库存，若 stock <= 0 则对商品下架
	 * @param productSaleId
	 * @param stock
	 */
	void updateStock(long productSaleId, int stock);
	
	void updateStock(MessageStock messageStock);
	
	void update(long productSaleId);
}
