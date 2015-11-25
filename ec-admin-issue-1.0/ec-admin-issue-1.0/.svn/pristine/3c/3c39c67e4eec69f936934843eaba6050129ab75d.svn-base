package com.winxuan.ec.admin.controller.order;

import java.math.BigDecimal;
import java.util.Arrays;



/**
 * 结束查询参数
 * @author  chenlong
 * @version 1.0,2011-8-18
 */
public class OrderNewForm {
	//数组部分
	private Long[] productSale;	
	private Long[] seller;
	private BigDecimal[] listPrice;
	private BigDecimal[] salePrice;
	private int[] purchaseQuantity;
	//---收货人信息---
	//客户账号
	private String customerName;
	//收货人
	private String consignee;
	//电话
	private String phone;
	//手机
	private String mobile;
	//邮箱
	private String email;
	//邮编
	private String zipcode;
	//区域
	private Long town;
	//地址
	private String address;
	//备注 
	private String remark;
	//---配送信息---
	//配送方式
	private Long deliveryTypeId;
	//送货时间
	private Long deliverytime;
	//运费
	private BigDecimal deliveryfee;
	//---来源及支付信息

	//渠道
	private Long channel;
	//外部订单编号
	private String outerOrderId;
	//支付方式
	private Long paymentType;
	//---配送信息---
	//是否开发票
	private boolean invoice;
	//抬头类型
	private Long invoiceTitle;
	//公司
	private String companyName;
	//是否查检库存
	private boolean checkStock = true;
	//订单下传选择：下传中启、下传SAP、不下传
	private Long transferResultId;
	//物流中心
	private Long dc;
	//物流系统中的收货人（客户代码）
	private String dccustomer;
	//订单销售类型
	private Long orderSaleType;
	
	public boolean isCheckStock() {
		return checkStock;
	}
	public void setCheckStock(boolean checkStock) {
		this.checkStock = checkStock;
	}
	//数组部分
	public Long[] getProductSale() {
		return productSale;
	}
	public void setProductSale(Long[] productSale) {
		this.productSale = productSale;
	}
	public Long[] getSeller() {
		return seller;
	}
	public void setSeller(Long[] seller) {
		this.seller = seller;
	}
	public BigDecimal[] getListPrice() {
		return listPrice;
	}
	public void setListPrice(BigDecimal[] listPrice) {
		this.listPrice = listPrice;
	}
	public BigDecimal[] getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal[] salePrice) {
		this.salePrice = salePrice;
	}
	public int[] getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(int[] purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
    //--------
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public Long getTown() {
		return town;
	}
	public void setTown(Long town) {
		this.town = town;
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
	public Long getDeliveryTypeId() {
		return deliveryTypeId;
	}
	public void setDeliveryTypeId(Long deliveryTypeId) {
		this.deliveryTypeId = deliveryTypeId;
	}
	public Long getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(Long deliverytime) {
		this.deliverytime = deliverytime;
	}
	public BigDecimal getDeliveryfee() {
		return deliveryfee;
	}
	public void setDeliveryfee(BigDecimal deliveryfee) {
		this.deliveryfee = deliveryfee;
	}

	public Long getChannel() {
		return channel;
	}
	public void setChannel(Long channel) {
		this.channel = channel;
	}
	public String getOuterOrderId() {
		return outerOrderId;
	}
	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}
	public Long getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}
	
	public boolean isInvoice() {
		return invoice;
	}
	public void setInvoice(boolean invoice) {
		this.invoice = invoice;
	}
	public Long getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(Long invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	//总码洋
	public BigDecimal getAllListPrice(){
		BigDecimal allListPrice = new BigDecimal(0);
		int len = listPrice.length;
		for (int i = 0 ; i < len; i++){
			allListPrice = allListPrice.add(listPrice[i].multiply(new BigDecimal(purchaseQuantity[i] > 0 ? purchaseQuantity[i] : 0)));
		}
		return allListPrice;
	}
	//总实洋
	public BigDecimal getAllSalePrice(){
		BigDecimal allSalePrice = new BigDecimal(0);
		int len = salePrice.length;
		for (int i = 0 ; i < len; i++){
			allSalePrice = allSalePrice.add(salePrice[i].multiply(new BigDecimal(purchaseQuantity[i] > 0 ? purchaseQuantity[i] : 0)));
		}
	
		return allSalePrice;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    public Long getTransferResultId() {
		return transferResultId;
	}
	public void setTransferResultId(Long transferResultId) {
		this.transferResultId = transferResultId;
	}
	public Long getDc() {
		return dc;
	}
	public void setDc(Long dc) {
		this.dc = dc;
	}
	public String getDccustomer() {
		return dccustomer;
	}
	public void setDccustomer(String dccustomer) {
		this.dccustomer = dccustomer;
	}
	public Long getOrderSaleType() {
		return orderSaleType;
	}
	public void setOrderSaleType(Long orderSaleType) {
		this.orderSaleType = orderSaleType;
	}
	@Override
	public String toString() {
		return "OrderNewForm [productSale=" + Arrays.toString(productSale)
				+ ", seller=" + Arrays.toString(seller) + ", listPrice="
				+ Arrays.toString(listPrice) + ", salePrice="
				+ Arrays.toString(salePrice) + ", purchaseQuantity="
				+ Arrays.toString(purchaseQuantity) + ", customerName="
				+ customerName + ", consignee=" + consignee + ", phone="
				+ phone + ", mobile=" + mobile + ", email=" + email
				+ ", zipcode=" + zipcode + ", town=" + town
				+ ", address=" + address + ", remark=" + remark
				+ ", deliveryTypeId=" + deliveryTypeId + ", deliverytime="
				+ deliverytime + ", deliveryfee=" + deliveryfee
				+ ", orderSource=" + ", channel=" + channel
				+ ", outerOrderId=" + outerOrderId + ", paymentType="
				+ paymentType + ", isInvoice=" + invoice + ", invoiceTitle="
				+ invoiceTitle + ", companyName=" + companyName + "]";
	}
 }
