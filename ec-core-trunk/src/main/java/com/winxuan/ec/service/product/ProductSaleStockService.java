package com.winxuan.ec.service.product;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winxuan.ec.enums.StockOriginEnum;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.CollectionItem;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ComplexStock;
import com.winxuan.ec.model.product.MessageStock;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;

/**
 * 商品相关业务逻辑
 * 
 * @author: HideHai
 * @date: 2013-7-25
 */
public interface ProductSaleStockService {

	/**
	 * 更新商品指定DC下的库存和占用。 为正数时表示加对应库存，为负数则为减去对应库存，
	 * 此方法只适用于Shop.WINXUAN_SHOP实物商品(下订单、取消订单、发货等)
	 * 
	 * @param dc
	 *            DC编码
	 * @param productSale
	 *            商品
	 * @param updateStockQuantity
	 *            库存量，为正数时表示加库存；为负数则为减库存。
	 * @param saleStockQuantity
	 *            占用量，为正数时表示加库存；为负数则为减库存。
	 */
	void updateQuantity(Code dc, ProductSale productSale, int updateStockQuantity, int saleStockQuantity)
			throws ProductSaleStockException;

	/**
	 * 判断商品在指定库位的可用量满足率
	 * 
	 * @param dc
	 *            DC编码
	 * @param productSale
	 *            商品
	 * @param quantity
	 *            下单数量
	 * @return 返回四舍五入两位小数的整数，比如：5 / 8 = 0.625，返回的就是6250
	 */
	int quantityFillRate(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException;

	/**
	 * 商品在指定DC的满足数量 如果复本量大于等于可用量，则可满足数量为可用量 如果复本量小于可用量，则可满足数量为复本量
	 * 如果商品存在不准确库存，则可用量为0
	 * 
	 * @param dc
	 *            库位
	 * @param productSale
	 *            商品
	 * @param quantity
	 *            复本量
	 * @return
	 */
	int usableQuantity(Code dc, ProductSale productSale, int quantity);

	int getAvalibleQuantity(ProductSale productSale, Code dc);

	/**
	 * 判断商品在各DC的库存满足率
	 * 
	 * @param productSale
	 *            商品
	 * @param quantity
	 *            下单数量
	 * @return 返回四舍五入两位小数的整数，比如：5 / 8 = 0.625，返回的就是6250
	 */
	Map<Code, Integer> quantityFillRate(ProductSale productSale, int quantity);

	/**
	 * 更新指定DC下实物库存，用于中启回抛实物库存更新
	 * 
	 * @param dc
	 *            DC编码
	 * @param productSale
	 *            商品
	 * @param quantity
	 *            库存量(零或者正整数)
	 */
	void updateActualQuantity(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException;

	/**
	 * 更新指定DC下虚拟库存，用于预售商品创建、采购设置等
	 * 
	 * @param dc
	 *            DC编码
	 * @param productSale
	 *            商品
	 * @param quantity
	 *            库存量(零或者正整数)
	 */
	void updateVirtualQuantity(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException;

	/**
	 * 计算套装书库存，正常销售商品取实物库存；预售商品取虚拟库存
	 * 
	 * @param dc
	 *            DC编码
	 * @param complexProductSales
	 *            子商品列表
	 * @return 返回套装书有效库存，子商品最小库存 - 5
	 */
	int computeComplexQuantity(Code dc, Set<ProductSale> complexProductSales);

	/**
	 * WMS回抛库存更新数据
	 * 
	 * @param dc
	 *            DC编码
	 * @param productSale
	 *            商品
	 * @param quantity
	 *            库存量(零或者正整数)
	 */
	void updateQuantityByWms(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException;

	/**
	 * 订单缺货、部分发货时调用，传入缺货的商品， 该接口根据规则判断商品库存数是否小于10，如果小于10，将商品立即作下架处理
	 * 
	 * @param dc
	 *            DC编码
	 * @param productSale
	 *            商品
	 */
	void saveIncorrectStock(Code dc, ProductSale productSale);

	/**
	 * 转换商品指定DC的库存信息为JSON字符串
	 * @param productSaleStockVos
	 * @return
	 */
	String parseStockInfo4Json(Set<ProductSaleStockVo> productSaleStockVos);

	void save(ProductSaleStock productSaleStock);

	/**
	 * 获取单个商品在仓库的库存之和
	 * 
	 * @param productSale
	 * @return
	 */
	int getSaleStockByDc(ProductSale productSale);

	/**
	 * 获取单个商品仓库最大库存
	 * 
	 * @param productSale
	 * @return
	 */
	int getMaxSaleStockByDc(ProductSale productSale);

	/**
	 * 百货，礼品卡等商品用户支付时 库存数量 - 订购数量
	 * 
	 * @param produstsale
	 * @param purchaseQuantity
	 */
	void subStock(ProductSale productSale, int purchaseQuantity);

	/**
	 * 增加库存占用
	 * 
	 * @param orderItem
	 */
	void addStockSales(OrderItem orderItem);

	/**
	 * 减少库存占用
	 * 
	 * @param orderItem
	 */
	void subStockSales(OrderItem orderItem);

	/**
	 * 减少库存占用
	 * 
	 * @param orderItem
	 */
	void subStockSales(CollectionItem collectionItem);

	int getStockFromZq(long productSaleId, Code dc);

	/**
	 * 获取可分配库存
	 * 
	 * @param origin
	 *            库存更新的来源，比如：收货库存更新、下单库存减少等
	 * @param channelId
	 *            渠道ID
	 * @param productSaleId
	 *            商品ID
	 */
	ComplexStock getStock(StockOriginEnum origin, long channelId, long productSaleId);

	/**
	 * 获取可分配库存,用于加盟店
	 * 
	 * @param origin
	 *            库存更新的来源，比如：收货库存更新、下单库存减少等
	 * @param channelId
	 *            渠道ID
	 * @param orderCustomerId
	 *            EC下单会员ID，用于加盟店
	 * @param productSaleId
	 *            商品ID
	 */
	ComplexStock getStock(StockOriginEnum origin, long channelId, long orderCustomerId, long productSaleId);

	/**
	 * 获取指定DC下可分配库存,用于供货
	 * 
	 * @param origin
	 *            库存更新的来源，比如：收货库存更新、下单库存减少等
	 * @param channelId
	 *            渠道ID
	 * @param productSaleIdList
	 * @param dcId
	 * @return
	 */
	Map<Long, Integer> getStock(StockOriginEnum origin, long channelId, List<Long> productSaleIds, long dcId);

	/**
	 * 计算实际锁定库存
	 * 
	 * @param stockLockRule
	 */
	int computeRealLock(StockLockRule stockLockRule);

	/**
	 * 中启回抛库存，重新计算根据系数锁定的锁定数量
	 * 
	 * @param productSale
	 */
	void afreshComputeRealLockByFactor(ProductSale productSale);

	/**
	 * 
	 * @param channel
	 * 
	 * @param productSale
	 * 
	 * @param sales
	 *            销量，正数增加销量，负数减少销量
	 */
	void updateLockSales(Channel channel, ProductSale productSale, int sales);

	/**
	 * 
	 * @param channel
	 * 
	 * @param orderCustomerId
	 *            EC下单会员ID，用于加盟店
	 * @param productSale
	 * 
	 * @param sales
	 *            销量，正数增加销量，负数减少销量
	 */
	void updateLockSales(Channel channel, long orderCustomerId, ProductSale productSale, int sales);

	/**
	 * 广播库存消息
	 * 
	 * @param messageStock
	 */
	void sendChannelStock(MessageStock messageStock);

}
