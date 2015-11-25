package com.winxuan.ec.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-9-9 下午8:58:46  --!
 * @description:
 ********************************
 */
public class ProductComplexUtils {

	public Date findMaxData(Set<ProductSale> productSaleList) {
		return this.findMaxData(new ArrayList<ProductSale>(productSaleList));
	}

	/**
	 * 找寻最晚的时间
	 * 
	 * @return
	 */
	public Date findMaxData(List<ProductSale> productSaleList) {
		DateTime time = null;
		for (ProductSale productSale : productSaleList) {
			if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale
					.getSupplyType().getId())) {
				Date bookTime = productSale.getBooking().getEndDate();
				if (new DateTime(bookTime).isAfter(time)) {
					time = new DateTime(bookTime);
				}
				if(time==null){
					time = new DateTime(bookTime);
				}
			}
		}
		return time.toDate();
	}

	
	public Date findMinData(Set<ProductSale> productSaleList){
		return this.findMinData(new ArrayList<ProductSale>(productSaleList));
	}
	
	/**
	 * 找寻最早的时间
	 * 
	 * @return
	 */
	public Date findMinData(List<ProductSale> productSaleList) {
		DateTime time = null;
		for (ProductSale productSale : productSaleList) {
			if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale
					.getSupplyType().getId())) {
				Date bookTime = productSale.getBooking().getStartDate();
				if (new DateTime(bookTime).isBefore(time)) {
					time = new DateTime(bookTime);
				}
				if(time==null){
					time = new DateTime(bookTime);
				}
			}
		}
		return time.toDate();
	}

}
