package com.winxuan.ec.admin.controller.returnorder;

import com.winxuan.ec.model.product.ProductSale;

/**
 * @description:
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2015年3月12日 上午11:06:03
 */
public class PackageItemTempForm {
	
	private ProductSale productsale;
	private int quantity;
	private String location;
	
	public ProductSale getProductsale() {
		return productsale;
	}
	public void setProductsale(ProductSale productsale) {
		this.productsale = productsale;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
