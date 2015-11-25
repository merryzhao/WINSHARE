package com.winxuan.ec.admin.controller.order;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.framework.util.AcceptHashMap;

/**
 * 结束查询参数
 * 
 * @author zhoujun
 * @version 1.0,2011-8-2
 */
public class OrderForm {
	
	public static final String SHORT_DATE_FORMAT_STR="yyyy-MM-dd";
	public static final String LONG_DATE_FORMAT_STR="yyyy-MM-dd HH:mm:ss";
	private static final DateFormat SHORT_DATE_FORMAT=new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
	private static final DateFormat LONG_DATE_FORMAT=new SimpleDateFormat(LONG_DATE_FORMAT_STR);
	private static final String EARLY_TIME="00:00:00";
	private static final String LATE_TIME="23:59:59";
	/**
	 * 订单
	 */
	private String orderId;
	/**
	 * 下单开始时间
	 */
	private Date startCreateTime;

	/**
	 * 下单结束时间
	 */
	private Date endCreateTime;

	/**
	 * 处理日期开始时间
	 */
	private Date startLastProcessTime;

	/**
	 * 处理日期结束时间
	 */
	private Date endLastProcessTime;

	/**
	 * 到款日期开始时间
	 */
	private Date startPayTime;

	/**
	 * 到款日期结束时间
	 */
	private Date endPayTime;

	/**
	 * 配送日期开始时间
	 */
	private Date startDeliveryTime;

	/**
	 * 配送日期结束时间
	 */
	private Date endDeliveryTime;

	/**
	 * 店铺(可复选)
	 */
	private Long[] shop ;

	/**
	 * 订单状态
	 */
	private Long[] processStatus;

	/**
	 * 到款状态
	 */
	private Long[] paymentStatus;

	/**
	 * 支付方式
	 */
	private Long[] paymentType;

	/**
	 * 配送方式
	 */
	private Long[] deliveryType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 注册名(可批量)
	 */
	private String registerName;

	/**
	 * 客户名(可批量)
	 */
	private String consignee;

	/**
	 * 电话(可批量)
	 */
	private String phone;
	
	/**
	 * 手机(可批量)
	 */
	private String mobile;

	/**
	 * 区域
	 */
	private Long town;

	/**
	 * 总码洋开始金额
	 */
	private BigDecimal startListPrice;

	/**
	 * 总码洋结束金额
	 */
	private BigDecimal endListPrice;

	/**
	 * 总实洋起始金额
	 */
	private BigDecimal startSalePrice;
	/**
	 * 总实洋结束金额
	 */
	private BigDecimal endSalePrice;

	/**
	 * 包含商品(可批量)
	 */
	private String product;

	/**
	 * sap编码(可批量)
	 */
	private String owncode;
	//商品编码
	private String barcode;
	

    /**
	 * 外部交易号
	 * 
	 */
	private String outerId;

	/**
	 * 是否需要发票
	 */
	private Boolean needInvoice;

	/**
	 * 
	 * 缺货处理方式
	 */
	private Long[] outOfStockOption;

	/**
	 * 渠道
	 * 
	 */
	private Long channel;

	/**
	 * 渠道
	 */
	private Long channelChildren;
	/**
	 * 销售类型
	 */
	private Long[] saleType;

	/**
	 * 会员等级
	 */
	private Short[] gradeType;

	/**
	 * 交寄单号
	 */
	private String deliveryCode;
	/**
	 * 商品类别
	 */
	private Long[] sort;

	/**
	 * 存储状态
	 */
	private Long[] storageType;
	
	private String customerName;
	
	private Boolean virtual;
	
	/**
	 * 显示条件
	 */
	private String[] selectRow;
	
	/**
	 * 承运商
	 */
	private Long[] deliveryCompany;
	

	/**
	 * 出货DC
	 */
	private Long[] dcoriginal;
	
	/**
	 * 发货DC
	 */
	private Long[] dcdest;

	
	public Long[] getDcoriginal(){
		return this.dcoriginal;
	}
	
	public void setDcoriginal(Long[] dcoriginal){
		this.dcoriginal = dcoriginal;
	}
	
	public Long[] getdcdest(){
		return this.dcdest;
	}
	
	public void setDcdest(Long[] dcdest){
		this.dcdest = dcdest;
	}
	
	public Long[] getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(Long[] deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getOwncode() {
		return owncode;
	}

	public void setOwncode(String owncode) {
		this.owncode = owncode;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Date getStartLastProcessTime() {
		return startLastProcessTime;
	}

	public void setStartLastProcessTime(Date startLastProcessTime) {
		this.startLastProcessTime = startLastProcessTime;
	}

	public Date getEndLastProcessTime() {
		return endLastProcessTime;
	}

	public void setEndLastProcessTime(Date endLastProcessTime) {
		this.endLastProcessTime = endLastProcessTime;
	}

	public Date getStartPayTime() {
		return startPayTime;
	}

	public void setStartPayTime(Date startPayTime) {
		this.startPayTime = startPayTime;
	}

	public Date getEndPayTime() {
		return endPayTime;
	}

	public void setEndPayTime(Date endPayTime) {
		this.endPayTime = endPayTime;
	}

	public Date getStartDeliveryTime() {
		return startDeliveryTime;
	}

	public void setStartDeliveryTime(Date startDeliveryTime) {
		this.startDeliveryTime = startDeliveryTime;
	}

	public Date getEndDeliveryTime() {
		return endDeliveryTime;
	}

	public void setEndDeliveryTime(Date endDeliveryTime) {
		this.endDeliveryTime = endDeliveryTime;
	}

	public Long[] getShop() {
		return shop;
	}

	public void setShop(Long[] shop) {
		this.shop = shop;
	}

	public Long[] getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Long[] processStatus) {
		this.processStatus = processStatus;
	}

	public Long[] getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long[] paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long[] getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long[] paymentType) {
		this.paymentType = paymentType;
	}

	public Long[] getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Long[] deliveryType) {
		this.deliveryType = deliveryType;
	}

	public void setSort(Long[] sort) {
		this.sort = sort;
	}

	public void setStorageType(Long[] storageType) {
		this.storageType = storageType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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

	public Long getTown() {
		return town;
	}

	public void setTown(Long town) {
		this.town = town;
	}

	public BigDecimal getStartListPrice() {
		return startListPrice;
	}

	public void setStartListPrice(BigDecimal startListPrice) {
		this.startListPrice = startListPrice;
	}

	public BigDecimal getEndListPrice() {
		return endListPrice;
	}

	public void setEndListPrice(BigDecimal endListPrice) {
		this.endListPrice = endListPrice;
	}

	public BigDecimal getStartSalePrice() {
		return startSalePrice;
	}

	public void setStartSalePrice(BigDecimal startSalePrice) {
		this.startSalePrice = startSalePrice;
	}

	public BigDecimal getEndSalePrice() {
		return endSalePrice;
	}

	public void setEndSalePrice(BigDecimal endSalePrice) {
		this.endSalePrice = endSalePrice;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Boolean getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	public Long[] getOutOfStockOption() {
		return outOfStockOption;
	}

	public void setOutOfStockOption(Long[] outOfStockOption) {
		this.outOfStockOption = outOfStockOption;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public Long getChannelChildren() {
		return channelChildren;
	}

	public void setChannelChildren(Long channelChildren) {
		this.channelChildren = channelChildren;
	}	



	public Short[] getGradeType() {
		return gradeType;
	}

	public void setGradeType(Short[] gradeType) {
		this.gradeType = gradeType;
	}

	public Long[] getSaleType() {
		return saleType;
	}

	public void setSaleType(Long[] saleType) {
		this.saleType = saleType;
	}

	

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public Long[] getSort() {
		return sort;
	}

	public Long[] getStorageType() {
		return storageType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
	
	public Boolean getVirtual() {
		return virtual;
	}

	public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}

	public String[] getSelectRow() {
		return selectRow;
	}

	public void setSelectRow(String[] selectRow) {
		this.selectRow = selectRow;
	}

	/**
	 * 生成查询条件
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> generateQueryMap(ChannelService channelService) throws ParseException,IllegalArgumentException {
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()
				.acceptIf("orderId", convertToArray(orderId),!StringUtils.isBlank(orderId))
				.acceptIf("startCreateTime", startCreateTime == null ? null:getEarlyInTheDay(startCreateTime),startCreateTime != null)
				.acceptIf("endCreateTime", endCreateTime == null ? null:getLateInTheDay(endCreateTime), endCreateTime != null)
				.acceptIf("startLastProcessTime", startLastProcessTime == null ? null :getEarlyInTheDay(startLastProcessTime),startLastProcessTime != null)
				.acceptIf("endLastProcessTime", endLastProcessTime == null ? null : getLateInTheDay(endLastProcessTime),endLastProcessTime != null)
				.acceptIf("startPayTime", startPayTime ==null ?null : getEarlyInTheDay(startPayTime), startPayTime != null)
				.acceptIf("endPayTime", endPayTime == null ? null : getLateInTheDay(endPayTime), endPayTime != null)
				.acceptIf("startDeliveryTime", startDeliveryTime == null ? null : getEarlyInTheDay(startDeliveryTime),startDeliveryTime != null)
				.acceptIf("endDeliveryTime", endDeliveryTime==null ? null  :getLateInTheDay(endDeliveryTime),endDeliveryTime != null)
				.acceptIf("shop", shop, shop != null)
				.acceptIf("deliveryCompanys", deliveryCompany, deliveryCompany != null)
				.acceptIf("processStatus", processStatus, processStatus != null)
				.acceptIf("paymentStatus", paymentStatus, paymentStatus != null)
				.acceptIf("paymentType", paymentType, paymentType != null)
				.acceptIf("deliveryType", deliveryType, deliveryType != null)
				.acceptIf("remark", remark, !StringUtils.isBlank(remark))
				.acceptIf("name", convertToArray(registerName),!StringUtils.isBlank(registerName))
				.acceptIf("consignee", convertToArray(consignee),!StringUtils.isBlank(consignee))
				.acceptIf("phone", convertToArray(phone),!StringUtils.isBlank(phone))
				.acceptIf("mobile", convertToArray(mobile),!StringUtils.isBlank(mobile))
				.acceptIf("town", town,town != null && town != -1)
				.acceptIf("startListPrice", startListPrice,startListPrice != null)
				.acceptIf("endListPrice", endListPrice, endListPrice != null)
				.acceptIf("startSalePrice", startSalePrice,endSalePrice != null)
				.acceptIf("endSalePrice", endSalePrice, endSalePrice != null)		
				.acceptIf("owncode", convertToArray(owncode),!StringUtils.isBlank(owncode))
				.acceptIf("outerId", convertToArray(outerId),!StringUtils.isBlank(outerId))
				.acceptIf("barcode", convertToArray(barcode),!StringUtils.isBlank(barcode))
				.acceptIf("needInvoice", needInvoice, needInvoice != null)
				.acceptIf("outOfStockOption", outOfStockOption,outOfStockOption != null)
				.acceptIf("saleType", saleType, saleType != null)
				.acceptIf("grade", gradeType, gradeType != null)
				.acceptIf("deliveryCode", convertToArray(deliveryCode),!StringUtils.isBlank(deliveryCode))
				.acceptIf("sort", sort, sort != null)
				.acceptIf("consignee", customerName, !StringUtils.isBlank(customerName))
				.acceptIf("virtual", virtual, virtual != null)
				.acceptIf("storageType", storageType, storageType != null)
				.acceptIf("dcdest", dcdest, dcdest != null)
				.acceptIf("dcoriginal", dcoriginal, dcoriginal != null);
		List<Long> channelIds = new ArrayList<Long>();
		if (channelChildren != null && channelChildren != -1) {
			parameters.put("channel", channelChildren);
		} else if (channel != null && channel != -1) {
			Channel newChannel = channelService.get(channel);
			Set<Channel> channelSet = newChannel.getChildren();
			for (Iterator<Channel> it = channelSet.iterator(); it.hasNext();) {
				Channel channelChild = it.next();
				channelIds.add(channelChild.getId());
			}
			channelIds.add(channel);
			parameters.put("channel", channelIds);
		}
	    if(!StringUtils.isBlank(product)){
	    	parameters.put("product", convertToLongArray(convertToArray(product)));
	    }
	    if(StringUtils.isBlank(orderId) 
	    		&& startCreateTime == null 
	    		&& startLastProcessTime == null 
	    		&& startPayTime == null 
	    		&& startDeliveryTime == null
	    		&& processStatus == null
	    		&& StringUtils.isBlank(outerId)
	    		&& StringUtils.isBlank(deliveryCode)){
	    	throw new IllegalArgumentException("带*号的条件必须至少填写一项！");
	    }
		return parameters;
	}

	public String[] convertToArray(String parameter) {
		return StringUtils.isBlank(parameter) ? null : parameter.split("\r\n");
	}
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Long[] convertToLongArray(String []parameter){
		if(parameter == null){
			return null;
		}
		Long[] product = new Long[parameter.length];
		for(int i=0;i<parameter.length;i++){
			   product[i]=Long.parseLong(parameter[i]);
			}
		return product;
	}
	
	/**
	 * 得到某个日期在这一天中时间最早的日期对象
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getEarlyInTheDay(Date date) throws ParseException{
		String dateString=SHORT_DATE_FORMAT.format(date)+" "+EARLY_TIME;
		return LONG_DATE_FORMAT.parse(dateString);
	}
	
	/**
	 * 得到某个日期在这一天中时间最晚的日期对象
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLateInTheDay(Date date) throws ParseException{
		String dateString=SHORT_DATE_FORMAT.format(date)+" "+LATE_TIME;
		return LONG_DATE_FORMAT.parse(dateString);
	}
	public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
