package com.winxuan.ec.task.service.ec.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductSaleIncorrectStockDao;
import com.winxuan.ec.exception.OrderPresellException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.task.dao.ec.EcOrderDao;
import com.winxuan.ec.task.service.ec.EcOrderService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author caoyouwen
 *
 */
@Service("ecOrderService")
@Transactional(rollbackFor = Exception.class)
public class EcOrderServiceImpl implements EcOrderService {

	private static final Log LOG = LogFactory.getLog(EcOrderServiceImpl.class);

	private static final short CREATETIME_ASC = 1;
	private static final Integer PAGE_SIZE = 100;

	@Autowired
	private OrderService orderService;

	@Autowired
	private EcOrderDao ecOrderDao;
	
	@InjectDao
	private ProductSaleIncorrectStockDao productSaleIncorrectStockDao;

	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;

	@Override
	public void convertBookingOrder2UsualOrder() {
		Map<String,Integer> ableRate = new HashMap<String,Integer>();//本次迭代后的可转换量
		List<String> bookingOrdersId ;
		int start = 0;
		int size = 0;
		try {
			while((bookingOrdersId = orderService.findBookingOrderId(start,PAGE_SIZE)) != null && !bookingOrdersId.isEmpty()){
				LOG.info("第"+size+++"转单条数:"+bookingOrdersId.size());
				for (String orderId : bookingOrdersId) {
					Order order = orderService.get(orderId);
					Map<String,Integer> backRate = new HashMap<String, Integer>();//记录当前递减数量
					try {
						if (checkPartUsualOrder(order,ableRate,backRate)) {
							LOG.info(String.format("未转预售订单:---------------%s",order.getId()));
							if(backRate.size()>0){
								for(Map.Entry<String,Integer> entry:backRate.entrySet()){
									//还原转单失败的递减数量
									int backValue = ableRate.get(entry.getKey())+entry.getValue();
									ableRate.put(entry.getKey(), backValue);
								}
							}
							start++;
						}else{
							String msg = "";
							String dc = order.getDistributionCenter().getDcOriginal().getId().toString();
							for (OrderItem item : order.getItemList()) {
								ProductSale productsale = item.getProductSale();
								String key = productsale.getId()+dc;
								msg += String.format("productId:%s,saleStatus:%s,dc:%s,itemNum:%s,ableRate:%s,supplyStatus:%s\n", 
										productsale.getId(),productsale.getSaleStatus(),dc,
										item.getPurchaseQuantity(),ableRate.get(key),productsale.getSupplyType());
							}
							LOG.info(String.format("转单预售订单:<orderId:%s>\n%s",order.getId(),msg));
							ecOrderDao.convertBookingOrder2UsualOrder(order.getId());
						}
					} catch (Exception e) {
						LOG.info(String.format("预售订单:%s转正式订单异常:%s",order.getId(),e));
						start++;
					}
				}

			}
		} catch (OrderPresellException e) {
			LOG.info("查询订单状态预售状态的id"+e);
		}

	}

	/**
	 * 检查预售订单中所以商品均为正式商品转正式订单
	 * @param order
	 * @param ableRate 实际库存变量
	 * @return boolean
	 */
	private boolean checkPartUsualOrder(Order order,Map<String,Integer> ableRate,Map<String,Integer> backRate) {
		Set<OrderItem> items = order.getItemList();
		for (OrderItem itme : items) {
			if (itme.getProductSale().isPreSale()) {
				for (OrderItem itme2 : items) {
					if (checkProduct(itme2,order.getDistributionCenter().
							getDcOriginal(),ableRate,backRate)) {
						return true;
					}
				}
				return false;//订单中部分商品到货满足转单需求
			}
		}
		return false;//订单中商品均为销售商品
	}

	/**
	 * 检查部分商品到货是否可转正式订单
	 * @param itme2
	 * @param dc 订单仓库
	 * @param ableRate 实际库存变量
	 * @return boolean 返回ture该商品非满足转单条件，返回false该商品满足转单
	 */
	private boolean checkProduct(OrderItem itme,Code dc,Map<String,Integer> ableRate,Map<String,Integer> backRate){
		ProductSale productSale = itme.getProductSale();
		//排除正式商品
		if(productSale.isPreSale()){
			int convertibleRate  = 0;
			String key = productSale.getId()+dc.getId().toString();
			if(ableRate.get(key) == null){
				int able = ecOrderDao.getProductDcStock(productSale,dc);
				int sale = ecOrderDao.getProductSalePurchaseQuantityTotle(productSale,dc);
				convertibleRate  = able-sale;
				LOG.info(String.format("<(productid+dc)ableKey:%s,ableRate:%s,able:%s,sale:%s>",
						key,convertibleRate,able,sale));
			}else{
				convertibleRate = ableRate.get(key);
			}
			convertibleRate = convertibleRate - itme.getPurchaseQuantity();
			ableRate.put(key, convertibleRate);
			backRate.put(key, itme.getPurchaseQuantity());
			//不准确库存商品订单不转单
			ProductSaleStock pss = productSale.getStockByDc(dc);
			boolean flag = pss.getIncorrect() > 0;
			//可用量小于0
			boolean pass = convertibleRate < 0;
			if(pass||flag){
				return true;//商品不满足转单
			}
		}
		return false;//商品可转单
	}

	@Override
	public void convertRapidOrder2UsualOrder() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("supplyType", new Long[]{Code.ORDER_SALE_TYPE_RAPID});
		params.put("dcoriginal", new Long[]{Code.DC_8A19});
		params.put("processStatus", new Long[]{Code.ORDER_PROCESS_STATUS_WAITING_PICKING});
		params.put("storageType", new Long[]{Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM});
		Pagination pagination = new Pagination(PAGE_SIZE);
		Map<Long, Integer> currentSales = new HashMap<Long, Integer>(); // 本次迭代新增的占用
		int current = 0;
		do {
			current = pagination.getCurrentPage();
			List<Order> rapidOrders = orderService.find(params,
					CREATETIME_ASC, pagination);
			current++;
			if(CollectionUtils.isNotEmpty(rapidOrders)){
				for (Order order : rapidOrders) {
					if (canConvertRapidOrder2UsualOrder(order, currentSales)) {
						try{
							ecOrderDao.convertRapidOrder2UsualOrder(order.getId());
						}catch (Exception e) {
							LOG.error("快速分拨订单:" + order.getId() + "\t转单过程中异常");
						}
					}else{
						LOG.error("快速分拨订单:" + order.getId() + "\t不满足转为正常订单的条件");
					}
				}
				pagination.setCurrentPage(current);
			}else{
				break;
			}
		} while (current <= pagination.getPageCount());
	}

	/**
	 * 转单的库存满足率：8A19的实物库存 - 8A19正在配货的订单中对该商品的占用 > 当前订单单品项的购买量：true
	 * @param order
	 * @return
	 */
	private boolean canConvertRapidOrder2UsualOrder(Order order, Map<Long, Integer> currentSales){
		Set<OrderItem> orderItems = order.getItemList();
		if(CollectionUtils.isEmpty(orderItems)){
			return false;
		}
		for(OrderItem orderItem : orderItems){
			ProductSale productSale = orderItem.getProductSale();
			ProductSaleStock productSaleStock = productSale.getStockByDc(new Code(Code.DC_8A19));
			int stock = productSaleStock.getStock();
			int purchaseQuantity = orderItem.getPurchaseQuantity();
			if(stock < purchaseQuantity){
				return false;
			}
			int sales = getRapidOrderProductSaleSalesWhenConvert(productSale);
			int currentSale = currentSales.get(productSale.getId()) == null ? 0 : currentSales.get(productSale.getId());
			if(stock - sales - currentSale < purchaseQuantity){
				return false;
			}
			if(currentSale == 0){
				currentSales.put(productSale.getId(), purchaseQuantity);
			}else{
				currentSales.put(productSale.getId(), currentSale + purchaseQuantity);
			}
		}
		return true;
	}

	private int getRapidOrderProductSaleSalesWhenConvert(ProductSale productSale){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processStatus", new Long[]{Code.ORDER_PROCESS_STATUS_PICKING});
		params.put("dcoriginal", new Long[]{Code.DC_8A19});
		params.put("supplyType", new Long[]{Code.ORDER_SALE_TYPE_NORMAL});
		params.put("storageType", new Long[]{Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM});
		params.put("product", productSale.getId());
		List<Order> salesOrder = orderService.find(params, CREATETIME_ASC, new Pagination(1));
		if(CollectionUtils.isEmpty(salesOrder)){
			return 0;
		}
		for(Order order : salesOrder){
			for(OrderItem item : order.getItemList()){
				if(item.getProductSale().getId().equals(productSale.getId())){
					return item.getSaleQuantity();
				}
			}
		}
		return 0;
	}

	@Override
	public void parseOrderStock(Long orderId, Long orderItemId, Long productSaleId, String stockInfo) {
		String insertSql = "INSERT INTO etl_order_item_stock(_order,order_item,productsale,dc,stock,sale) " +
				"VALUES(?,?,?,?,?,?)";
		String updateSql = "UPDATE etl_order_item_stock SET stock = ?, sale = ? where id = ?";

		JSONArray jsonArray = JSONArray.fromObject(stockInfo);
		if(jsonArray != null){
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				Long etlOrderItemStockId = ecOrderDao.findEtlOrderItemStock(orderItemId, jsonObject.getLong("dc")); 
				if(jsonObject != null){
					LOG.info(String.format("ps:%s dc:%s stock:%s sale:%s",productSaleId,jsonObject.getLong("dc"),
							jsonObject.getInt("stock"),jsonObject.getInt("sale")));
					if(etlOrderItemStockId != null) { 
						jdbcTemplateEcCore.update(updateSql, new Object[]{ 
							jsonObject.getInt("stock"),
							jsonObject.getInt("sale"),
							etlOrderItemStockId});
					} else {
						jdbcTemplateEcCore.update(insertSql, new Object[]{
								orderId,
								orderItemId,
								productSaleId,
								jsonObject.getLong("dc"),
								jsonObject.getInt("stock"),
								jsonObject.getInt("sale")
						});
					}
				}
			}
		}
	}

}

