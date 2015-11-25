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
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-9
 */
public class OrderProductComplexUtils {

	public static void splitProductComplex(Order order) {
		Set<OrderItem> newItems = new LinkedHashSet<OrderItem>();
		BigDecimal complexSalePrice = BigDecimal.ZERO;
		BigDecimal complexListPrice = BigDecimal.ZERO;
		boolean hasComplex = false;
		Set<OrderItem> items = order.getItemList();
		Map<ProductSale, Integer> complexItemMap = new HashMap<ProductSale, Integer>();
		for (OrderItem item : items) {
			ProductSale productSale = item.getProductSale();
			if (isComplex(productSale)) {
				complexSalePrice = complexSalePrice.add(item
						.getTotalSalePrice());
				for (ProductSale complexItem : productSale.getComplexItemList()) {
					complexListPrice = complexListPrice.add(complexItem
							.getListPrice().multiply(
									BigDecimal.valueOf(item
											.getPurchaseQuantity())));
					addItem(complexItemMap, complexItem,
							item.getPurchaseQuantity());
				}
				hasComplex = true;
			} else {
				if (isComplexRelation(order, productSale)) {
					complexListPrice = complexListPrice.add(item
							.getTotalListPrice());
					complexSalePrice = complexSalePrice.add(item
							.getTotalSalePrice());
					addItem(complexItemMap, productSale,
							item.getPurchaseQuantity());
				} else {
					newItems.add(item);
				}
			}
		}
		if (hasComplex) {
			for (Entry<ProductSale, Integer> entry : complexItemMap.entrySet()) {
				ProductSale complexItem = entry.getKey();
				int quantity = entry.getValue();
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setProductSale(complexItem);
				orderItem.setPurchaseQuantity(quantity);
				orderItem.setListPrice(complexItem.getListPrice());
				BigDecimal salePrice = complexItem
						.getListPrice()
						.multiply(BigDecimal.valueOf(quantity))
						.divide(complexListPrice, MagicNumber.TEN,
								BigDecimal.ROUND_HALF_UP)
						.multiply(complexSalePrice)
						.divide(BigDecimal.valueOf(quantity), 1,
								BigDecimal.ROUND_UP);
				orderItem.setSalePrice(salePrice);
				orderItem.setShop(complexItem.getShop());
				newItems.add(orderItem);
			}
			order.setItemList(newItems);
			calculateSize(order);
		}
	}

	private static void calculateSize(Order order) {
		int quantity = 0;
		Set<OrderItem> itemList = order.getItemList();
		for (OrderItem item : itemList) {
			quantity += item.getPurchaseQuantity();
		}
		order.setPurchaseQuantity(quantity);
		order.setPurchaseKind(itemList.size());
	}
	
	private static boolean isComplexRelation(Order order,
			ProductSale productSale) {
		for (OrderItem orderItem : order.getItemList()) {
			ProductSale productSaleItem = orderItem.getProductSale();
			if (isComplex(productSaleItem)) {
				Set<ProductSale> complexItems = productSaleItem
						.getComplexItemList();
				if (complexItems.contains(productSale)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isComplex(ProductSale productSale) {
		Set<ProductSale> complexItems = productSale.getComplexItemList();
		return productSale.getProduct().isComplex() && complexItems != null
				&& !complexItems.isEmpty();
	}
	
	private static void addItem(Map<ProductSale, Integer> itemMap,
			ProductSale productSale, int quantity) {
		Integer existedQuantityInteger = itemMap.get(productSale);
		int existedQuantity = existedQuantityInteger == null ? 0
				: existedQuantityInteger.intValue();
		itemMap.put(productSale, existedQuantity + quantity);
	}

}
