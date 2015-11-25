/*
 * @(#)OrderProductComplexUtils.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.util.MagicNumber;


/**
 * 订单套装书拆分
 * @author HideHai
 * Date 2014-10-11
 */
public class OrderComplexUtils {

	private static String listKey = "ListPrice";
	private static String saleKey = "SalePrice";

	/**
	 * 如果订单包含套装书，则将套装书拆分成对应的订单项
	 * @param order
	 */
	public static void splitProductComplex(Order order){
		Map<String,BigDecimal> complexListPrice = new HashMap<String,BigDecimal>();	//套装商品的总码洋
		Map<String,BigDecimal> complexSalePrice = new HashMap<String,BigDecimal>();	//套装商品的总实洋
		Map<ProductSale,Integer> complexItem = new HashMap<ProductSale, Integer>();	//需生成订单项的子商品
		Set<ProductSale> allComplexItem = new LinkedHashSet<ProductSale>();	//所有套装的子商品
		Set<OrderItem> newItems = new LinkedHashSet<OrderItem>();	//新订单项

		Set<OrderItem> items = order.getItemList();					//已有订单项
		boolean hasComplex = false;
		initAllComplexItem(order, allComplexItem);

		for(OrderItem item : items){
			ProductSale productSale = item.getProductSale();
			if(isComplex(productSale)){
				addComplexItem(complexItem,complexListPrice,complexSalePrice,item);	//记录套装
				hasComplex = true;					//标记订单项有套装商品
			}else{
				if(existInAllComplex(allComplexItem,productSale)){	//订单项和已有套装子商品重合
					addItem(complexItem, productSale, item.getPurchaseQuantity());
					addListPrice(complexListPrice, item.getTotalListPrice());
					addSalePrice(complexSalePrice, item.getTotalSalePrice());
				}else{
					newItems.add(item); //非套装和订单套装子商品，直接添加到订单项
				}
			}
		}

		if(hasComplex){
			int size = complexItem.size();
			int count=0;
			BigDecimal checkSalePrice = BigDecimal.ZERO;
			BigDecimal actualSalePrice = BigDecimal.ZERO;

			for (Entry<ProductSale, Integer> entry : complexItem.entrySet()){
				count++;
				ProductSale itemProductSale = entry.getKey();		//商品
				Integer quantity = entry.getValue();				//数量
				BigDecimal totalListPrice = complexListPrice.get(listKey);	//须拆分商品的总码洋
				BigDecimal totalSalePrice = complexSalePrice.get(saleKey);	//须拆分商品的总实洋
				checkSalePrice = totalSalePrice;
				BigDecimal salePrice=null;
				salePrice = itemProductSale.getListPrice()
						.multiply(BigDecimal.valueOf(quantity))
						.divide(totalListPrice, MagicNumber.TEN,BigDecimal.ROUND_HALF_UP)
						.multiply(totalSalePrice)
						.divide(BigDecimal.valueOf(quantity), 1,BigDecimal.ROUND_UP);
				actualSalePrice = actualSalePrice.add(salePrice.multiply(BigDecimal.valueOf(quantity)));
				if(count == size){										//最后一项
					if(actualSalePrice.compareTo(checkSalePrice)!=0){	//和期望实洋不一致
						BigDecimal rate = (checkSalePrice.subtract(actualSalePrice))
								.divide(BigDecimal.valueOf(quantity),MagicNumber.TEN,BigDecimal.ROUND_HALF_UP)
								.setScale(2, BigDecimal.ROUND_UP);
						salePrice = salePrice.add(rate).setScale(2, BigDecimal.ROUND_UP);	//保留两位小数，进位处理
						salePrice = salePrice.compareTo(BigDecimal.ZERO) <=0 ? BigDecimal.ZERO : salePrice;
					}
				}
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setProductSale(itemProductSale);
				orderItem.setPurchaseQuantity(quantity);
				orderItem.setListPrice(itemProductSale.getListPrice());
				orderItem.setSalePrice(salePrice);
				orderItem.setShop(itemProductSale.getShop());
				newItems.add(orderItem);
			}
			order.setItemList(newItems);
			calcOrderInfo(order);
		}
	}

	/**
	 * 将订单中套装书所有的子商品放入集合
	 * @param order
	 * @param productSale
	 * @return
	 */
	private static void initAllComplexItem(Order order,Set<ProductSale> allComplexItem){
		Set<OrderItem> orderItems = order.getItemList();
		for (OrderItem orderItem : orderItems) {
			ProductSale productSaleItem = orderItem.getProductSale();
			if (isComplex(productSaleItem)) {
				Set<ProductSale> complexItems = productSaleItem.getComplexItemList();
				for(ProductSale productSale : complexItems){
					allComplexItem.add(productSale);
				}
			}
		}
	}

	/**
	 * 判断指定商品是否在需计算子商品集合中存在
	 * @param complexItem	套装子商品集合
	 * @param productSale	目标商品
	 * @return
	 */
	private static boolean existInComplexMap(Map<ProductSale,Integer> complexItem,ProductSale productSale){
		return complexItem.containsKey(productSale);
	}

	/**
	 * 判断指定商品是否在套装子商品集合中存在
	 * @param allComplexItem
	 * @param productSale
	 * @return
	 */
	private static boolean existInAllComplex(Set<ProductSale> allComplexItem,ProductSale productSale){
		return allComplexItem.contains(productSale);
	}

	/**
	 * 处理套装书的子商品和数量到子商品集合
	 * @param complexItem
	 * @param productSale
	 */
	private static void addComplexItem(Map<ProductSale,Integer> complexItem,
			Map<String,BigDecimal> complexListPrice,
			Map<String,BigDecimal> complexSalePrice,
			OrderItem orderItem){
		ProductSale productSale = orderItem.getProductSale();
		Set<ProductSale> complexItems = productSale.getComplexItemList();
		addSalePrice(complexSalePrice,orderItem.getTotalSalePrice());
		for(ProductSale sale : complexItems){
			addItem(complexItem, sale,orderItem.getPurchaseQuantity());
			addListPrice(complexListPrice,sale.getListPrice().multiply(BigDecimal.valueOf(orderItem.getPurchaseQuantity())));
		}
	}

	/**
	 * 添加须生成子商品的总码洋
	 * 如果商品已经存在，则累加码洋
	 * @param complexItem
	 * @param orderItem
	 */
	private static void addListPrice(Map<String,BigDecimal> complexListPrice,BigDecimal listprice){
		if(!complexListPrice.containsKey(listKey)){
			complexListPrice.put(listKey,listprice);
		}else{
			BigDecimal existedPrice = complexListPrice.get(listKey);
			BigDecimal priceResult = existedPrice == null ? BigDecimal.ZERO : existedPrice;
			complexListPrice.put(listKey, priceResult.add(listprice));
		}
	}

	private static void addSalePrice(Map<String,BigDecimal> complexSalePrice,BigDecimal saleprice){
		if(!complexSalePrice.containsKey(saleKey)){
			complexSalePrice.put(saleKey,saleprice);
		}else{
			BigDecimal existedPrice = complexSalePrice.get(saleKey);
			BigDecimal priceResult = existedPrice == null ? BigDecimal.ZERO : existedPrice;
			complexSalePrice.put(saleKey, priceResult.add(saleprice));
		}
	}


	/**
	 * @param complexItem
	 * @param sale
	 * @param quantity
	 */
	private static void addItem(Map<ProductSale,Integer> complexItem,ProductSale sale,int quantity){
		if(!existInComplexMap(complexItem, sale)){
			complexItem.put(sale,quantity);
		}else{
			Integer existedQuantity = complexItem.get(sale);
			int quantityResult = existedQuantity == null ? 0 : existedQuantity.intValue();
			complexItem.put(sale, quantityResult + quantity);
		}
	}

	/**
	 * 判断商品是否是自建套装
	 * @param productSale
	 * @return
	 */
	private static boolean isComplex(ProductSale productSale){
		Set<ProductSale> complexItems = productSale.getComplexItemList();
		return productSale.getProduct().isComplex() && complexItems != null
				&& !complexItems.isEmpty();
	}

	/**
	 * 重算订单信息
	 * @param order
	 */
	private static void calcOrderInfo(Order order){
		int quantity = 0;
		Set<OrderItem> itemList = order.getItemList();
		for (OrderItem item : itemList) {
			quantity += item.getPurchaseQuantity();
		}
		order.setPurchaseQuantity(quantity);
		order.setPurchaseKind(itemList.size());
	}

}
