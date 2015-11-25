package com.winxuan.ec.admin.controller.returnorder;

import org.springframework.beans.factory.annotation.Autowired;
import com.winxuan.ec.service.order.OrderService;

/**
 * @description:退货包件信息录入
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2015年1月5日 上午10:21:06
 */
public class ReturnOrderPackageForm {
	@Autowired
	OrderService orderService;
	
	//类型
	private String type;
	
	//运单号
	private String expressid;
	//签收时间
	private String signtime;
	//发件人姓名
	private String customer;
	//承运商
	private String carrier;
	//签收人
	private String signname;
	//发件人联系方式
	private String phone;
	//揽件/邮戳时间
	private String expresstime;
	//退货类型
	private String returnType;
	//发件人地址
	private String address;
	//备注
	private String remark;
	//商品信息
	private String[] productSale;
	//实收数量
	private int[] quantity;
	//商品暂存库位
	private String[] locations;
	//关联订单号
	private String orderid;
	//渠道退货单号
	private String returnid;
	//包件暂存库位
	private String storagelocation;
	//应收包件数量
	private String shouldquantity;
	//实收包件数量
	private String realquantity;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getExpresstime() {
		return expresstime;
	}
	public void setExpresstime(String expresstime) {
		this.expresstime = expresstime;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getExpressid() {
		return expressid;
	}
	public void setExpressid(String expressid) {
		this.expressid = expressid;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String[] getProductSale() {
		return productSale;
	}
	public void setProductSale(String[] productSale) {
		this.productSale = productSale;
	}
	public int[] getQuantity() {
		return quantity;
	}
	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getSigntime() {
		return signtime;
	}
	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}
	public String getSignname() {
		return signname;
	}
	public void setSignname(String signname) {
		this.signname = signname;
	}
	public String getReturnid() {
		return returnid;
	}
	public void setReturnid(String returnid) {
		this.returnid = returnid;
	}
	public String getStoragelocation() {
		return storagelocation;
	}
	public void setStoragelocation(String storagelocation) {
		this.storagelocation = storagelocation;
	}
	public String getShouldquantity() {
		return shouldquantity;
	}
	public void setShouldquantity(String shouldquantity) {
		this.shouldquantity = shouldquantity;
	}
	public String getRealquantity() {
		return realquantity;
	}
	public void setRealquantity(String realquantity) {
		this.realquantity = realquantity;
	}
	public String[] getLocations() {
		return locations;
	}
	public void setLocations(String[] locations) {
		this.locations = locations;
	}
	
}
