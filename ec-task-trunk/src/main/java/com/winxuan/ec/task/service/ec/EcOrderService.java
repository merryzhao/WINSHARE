package com.winxuan.ec.task.service.ec;

/**
 * 
 * @author caoyouwen
 *
 */
public interface EcOrderService {
	
	/**
	 *  预售订单 （预售订单，订单状态为等待配货，订单付款状态为已支付或部分支付）
	 *  1.如果订单中所有商品为正式销售商品则转为正式订单；
	 *  2.如果订单发货仓库部分到货，单预售商品未转为销售商品，则获取（可转换量=实际库存-正在配送占用量）
	 *  用订单中每件商品的可转换量递减当前商品的购买数量，若可转换量小于0时则不能再转换预售订单
	 *  转换订单：将预售订单（13102）状态变更为正常销售（13101）
	 */
	void convertBookingOrder2UsualOrder();
	
	/**
	 *  快拨订单转正常订单
	 *  转换条件：
	 *  1、订单处理状态：等待配货
	 *  2、订单存储方式：代储代发
	 *  3、订单类型：快速分拨
	 */
	void convertRapidOrder2UsualOrder();
	
	void parseOrderStock(Long orderId, Long orderItemId, Long productSaleId, String stockInfo);
}
