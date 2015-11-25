package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.util.Comparator;

import com.winxuan.ec.model.product.ProductSale;

/**
 * 
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2014-7-16
 */
public class OrderItemComparatorAsc implements Comparator<OrderItem>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8841940910686636727L;

	@Override
	public int compare(OrderItem o1, OrderItem o2) {
		ProductSale ps1 = o1.getProductSale();
		ProductSale ps2 = o2.getProductSale();
		Long l1 = (null == ps1) ? new Long(0) : ps1.getId();
		Long l2 = (null == ps2) ? new Long(0) : ps2.getId();
		return l1.compareTo(l2);
	}
	
}
