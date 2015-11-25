package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * 父订单
 * 
 * @author zhoujun
 * @version 1.0,2014-10-10
 */
@Entity
@Table(name = "parent_order")
public class ParentOrder implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 259172353572349014L;


	@Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator",
            strategy = "com.winxuan.ec.support.util.DatePrefixSequenceIdentifierGenerator",
            parameters = { @Parameter(name = "table", value = "serializable"),
                    @Parameter(name = "column", value = "maxid"),
                    @Parameter(name = "" + "target_name", value = "tablename"),
                    @Parameter(name = "target_value", value = "parent_order"), @Parameter(name = "length", value = "6"),
                    @Parameter(name = "step", value = "500") })
    private String id;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;

	@Column(name = "outerid")
	private String outerId;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processstatus")
	private Code processStatus;

	@Column(name = "listprice")
	private BigDecimal listPrice;

	@Column(name = "saleprice")
	private BigDecimal salePrice;

	@Column(name = "savemoney")
	private BigDecimal saveMoney = BigDecimalUtils.ZERO;

	@Column(name = "deliverylistprice")
	private BigDecimal deliveryListPrice;

	@Column(name = "deliverysaleprice")
	private BigDecimal deliverySalePrice;

	@Column(name="deliveryfee")
	private BigDecimal deliveryfee;
	
	@Column(name = "purchasequantity")
	private int purchaseQuantity;

	@Column(name = "purchasekind")
	private int purchaseKind;

	@Column(name = "deliveryquantity")
	private int deliveryQuantity;

	@Column(name = "deliverykind")
	private int deliveryKind;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentOrder", targetEntity = Order.class)
    private Set<Order> orderList;

    @Column(name = "lastprocesstime")
    private Date lastProcessTime;
    
    @Column(name = "split")
    private boolean split;
    
	public String getId() {
		return id;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Code getProcessStatus() {
        this.processStatus =  new Code(ParentOrderProcessStatusEnum.getProcessStatusCode(this.orderList));
		return processStatus;
	}

	public void setProcessStatus(Code processStatus) {
		this.processStatus = processStatus;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(BigDecimal saveMoney) {
		this.saveMoney = saveMoney;
	}

	public BigDecimal getDeliveryListPrice() {
		return deliveryListPrice;
	}

	public void setDeliveryListPrice(BigDecimal deliveryListPrice) {
		this.deliveryListPrice = deliveryListPrice;
	}

	public BigDecimal getDeliverySalePrice() {
		return deliverySalePrice;
	}
	
	public BigDecimal getDeliveryfee() {
		return deliveryfee;
	}

	public void setDeliveryfee(BigDecimal deliveryfee) {
		this.deliveryfee = deliveryfee;
	}

	public void setDeliverySalePrice(BigDecimal deliverySalePrice) {
		this.deliverySalePrice = deliverySalePrice;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public int getPurchaseKind() {
		return purchaseKind;
	}

	public void setPurchaseKind(int purchaseKind) {
		this.purchaseKind = purchaseKind;
	}

	public int getDeliveryQuantity() {
		for(Order order:this.orderList){
			this.deliveryQuantity += order.getDeliveryQuantity();
		}
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public int getDeliveryKind() {
		return deliveryKind;
	}

	public void setDeliveryKind(int deliveryKind) {
		this.deliveryKind = deliveryKind;
	}

	public Set<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(Set<Order> orderList) {
		this.orderList = orderList;
	}
	
	public Date getLastProcessTime() {
		return lastProcessTime;
	}

	public void setLastProcessTime(Date lastProcessTime) {
		this.lastProcessTime = lastProcessTime;
	}


	public boolean isSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}

	public BigDecimal getRequidPayMoney(){
		BigDecimal requidPayMoney = BigDecimal.ZERO;
		for(Order order:this.orderList){
			requidPayMoney = requidPayMoney.add(order.getRequidPayMoney());
		}
		return requidPayMoney;
	}
	
	public int getPaymentListSize(){
		int paymentListSize = 0;
		for(Order order:this.orderList){
			paymentListSize += order.getPaymentList().size();
		}
		return paymentListSize;
	}
	
	public Order getMostRequidPayMoneyOrder(){
	    BigDecimal mostRequidPayMoney = BigDecimal.ZERO;
	    Order returnOrder = null;
		for(Order order:this.orderList){
	    	if(order.getRequidPayMoney().compareTo(mostRequidPayMoney)>= 0){
	    		mostRequidPayMoney = order.getRequidPayMoney();
	    		returnOrder = order;
	    	}
	    }
		return returnOrder;
	}
	
	public List<String> getOrderListString(){
		List<String> orders = new ArrayList<String>();
		for(Order order:this.orderList){
			orders.add(order.getId());
		}
		return orders;
	}
	
	public boolean isArchiveOrIntercept(){
		boolean isArchiveOrIntercept = true;
		for(Order order:this.orderList){
			if(!(Code.ORDER_PROCESS_STATUS_ARCHIVE.equals(order.getProcessStatus().getId())
				|| Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT.equals(order.getProcessStatus().getId()))){
				isArchiveOrIntercept = false;
				break;
			}
		}
		return isArchiveOrIntercept;
	}
}
