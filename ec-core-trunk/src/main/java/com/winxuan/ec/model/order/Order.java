/*
 * @(#)Order.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.xwork.ArrayUtils;
import org.apache.commons.lang.xwork.time.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * 订单
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "_order")
public class Order implements Serializable {

    /**
     * 订单已发货的处理状态
     */
    public static final Long[] DELIVERY_PROCESSSTATUS = new Long[] { Code.ORDER_PROCESS_STATUS_DELIVERIED,
            Code.ORDER_PROCESS_STATUS_COMPLETED, Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL,
            Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG };
    /**
     * 购买数量
     */
    public static final Integer PURCHASE_QUANTITY = MagicNumber.ONE;
    
    /**
     * 发货数量
     */
    public static final Integer DELIVERY_QUANTITY = MagicNumber.TWO;
    
    /**
     * 查询物流信息的时间范围
     */
    private static final Integer MAX_START_MOUTH = 2;
    private static final Integer MAX_END_DAY = 1;
    private static final Integer MAX_HOUR = 23;
    private static final Integer MAX_MINUTE = 59;
    private static final Integer MAX_SECOND = 59;

    private static final long serialVersionUID = -6298309747155311546L;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator",
            strategy = "com.winxuan.ec.support.util.DatePrefixSequenceIdentifierGenerator",
            parameters = { @Parameter(name = "table", value = "serializable"),
                    @Parameter(name = "column", value = "maxid"),
                    @Parameter(name = "" + "target_name", value = "tablename"),
                    @Parameter(name = "target_value", value = "_order"), @Parameter(name = "length", value = "6"),
                    @Parameter(name = "step", value = "500") })
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paytype")
    private Code payType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliverytype")
    private DeliveryType deliveryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplytype")
    private Code supplyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storagetype")
    private Code storageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliverycompany")
    private DeliveryCompany deliveryCompany;

    @Column(name = "deliverycode")
    private String deliveryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel")
    private Channel channel;

    @Column(name = "outerid")
    private String outerId;

    @Column(name = "listprice")
    private BigDecimal listPrice;

    @Column(name = "saleprice")
    private BigDecimal salePrice;

    @Column(name = "savemoney")
    private BigDecimal saveMoney = BigDecimalUtils.ZERO;

    @Column(name = "deliveryfee")
    private BigDecimal deliveryFee;

    @Column(name = "advancemoney")
    private BigDecimal advanceMoney;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processstatus")
    private Code processStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "erpstatus")
    private Code erpStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentstatus")
    private Code paymentStatus;

    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "deliverytime")
    private Date deliveryTime;

    @Column(name = "lastprocesstime")
    private Date lastProcessTime;

    @Column(name = "maxpaytime")
    private Date maxPayTime = DateUtils.addYears(new Date(), MagicNumber.FIVE);

    @Column(name = "deliverylistPrice")
    private BigDecimal deliveryListPrice;

    @Column(name = "deliverysalePrice")
    private BigDecimal deliverySalePrice;

    @Column
    private boolean virtual;

    @Column
    private boolean unite;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @Fetch(FetchMode.JOIN)
    private OrderConsignee consignee;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)   
    private OrderDistributionCenter distributionCenter;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private OrderNote note;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private OrderReceive receive;
    
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private OrderCollection orderCollection;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private OrderInit orderInit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderItem.class)
    @OrderBy("id")
    private Set<OrderItem> itemList;
    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderPayment.class)
    @OrderBy("id")
    private Set<OrderPayment> paymentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderTrack.class)
    @OrderBy("id")
    private Set<OrderTrack> trackList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderStatusLog.class)
    @OrderBy("id")
    private Set<OrderStatusLog> statusLogList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderUpdateLog.class)
    @OrderBy("id")
    private Set<OrderUpdateLog> updateLogList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "originalOrder", targetEntity = ReturnOrder.class)
    @OrderBy("id")
    private Set<ReturnOrder> returnOrderList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderPromotion.class)
    @OrderBy("id")
    private Set<OrderPromotion> promotionList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderItemStock.class)
    @OrderBy("id")
    private Set<OrderItemStock> orderItemStocks;

    @Transient
    private List<OrderLogistics> logisticsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderInvoice.class)
    private Set<OrderInvoice> invoiceList;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "order",
            targetEntity = OrderDeliverySplit.class)
    private Set<OrderDeliverySplit> deliverySplits;

    @Column(name = "purchasequantity")
    private int purchaseQuantity;

    @Column(name = "purchasekind")
    private int purchaseKind;

    @Column(name = "deliveryquantity")
    private int deliveryQuantity;

    @Column(name = "deliverykind")
    private int deliveryKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transferresult")
    private Code transferResult;
    
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_order")
    @JoinTable(name = "split_order", joinColumns = @JoinColumn(name = "_order"), inverseJoinColumns = @JoinColumn(name = "parent_order"))  
    private ParentOrder parentOrder;

    @Version
    private int version;

    // 订单库存查检选项,默认需要查检
    @Transient
    private boolean checkStock = true;

    // 强制短信发送,不论消息是否禁用
    @Transient
    private boolean forceSendMessage = false;
    
    @Transient
    private boolean isSplit = false;

    // 订单广播项,by cast911
    @Transient
    private List<Long> broadCastList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", targetEntity = OrderExtend.class)
    private List<OrderExtend> orderExtends;

    public String getId() {
        return id;
    }

    public Set<OrderDeliverySplit> getDeliverySplits() {
        return deliverySplits;
    }

    public void setDeliverySplits(Set<OrderDeliverySplit> deliverySplits) {
        this.deliverySplits = deliverySplits;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public DeliveryCompany getDeliveryCompany() {
        return deliveryCompany;
    }

    public List<Long> getBroadCastList() {
        return broadCastList;
    }

    public void setBroadCastList(List<Long> broadCastList) {
        this.broadCastList = broadCastList;
    }

    public void setDeliveryCompany(DeliveryCompany deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    public BigDecimal getDeliveryListPrice() {
        return deliveryListPrice;
    }

    public void setDeliveryListPrice(BigDecimal deliveryListPrice) {
        this.deliveryListPrice = deliveryListPrice;
    }

    public BigDecimal getDeliverySalePrice() {
        return deliverySalePrice;
    }

    public void setDeliverySalePrice(BigDecimal deliverySalePrice) {
        this.deliverySalePrice = deliverySalePrice;
    }

    public BigDecimal getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(BigDecimal saveMoney) {
        this.saveMoney = saveMoney;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(BigDecimal advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getLastProcessTime() {
        return lastProcessTime;
    }

    public void setLastProcessTime(Date lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }

    public Date getMaxPayTime() {
        return maxPayTime;
    }

    public void setMaxPayTime(Date maxPayTime) {
        this.maxPayTime = maxPayTime;
    }

    public void setConsignee(OrderConsignee consignee) {
        this.consignee = consignee;
    }

    public OrderDistributionCenter getDistributionCenter() {
		return distributionCenter;
	}

	public void setDistributionCenter(OrderDistributionCenter distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public boolean isUnite() {
        return unite;
    }

    public void setUnite(boolean unite) {
        this.unite = unite;
    }

    public Code getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(Code supplyType) {
        this.supplyType = supplyType;
    }

    public Set<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(Set<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public Set<OrderPayment> getPaymentList() {
        return paymentList;
    }

    public OrderPayment getPayment(Long paymentId) {
        for (OrderPayment orderPayment : paymentList) {
            if (orderPayment.getPayment().getId().equals(paymentId)) {
                return orderPayment;
            }
        }
        return null;
    }

    public OrderPayment getOnlinePayPayment() {
        OrderPayment onlinePayOrderPayment = null;
        for (OrderPayment orderPayment : this.getPaymentList()) {
            Long paymentTypeId = orderPayment.getPayment().getType().getId();
            if (Code.PAYMENT_TYPE_ONLINE.equals(paymentTypeId)) {
                onlinePayOrderPayment = orderPayment;
            }
        }
        return onlinePayOrderPayment;
    }
    
    /**
     * 检查订单线上支付并且可退款金额大于0
     * 检查支付凭证是否可退款
     * @return
     */
    public List<OrderPayment> getCanRefundOnlinePayPayment() {
        List<OrderPayment> onlinePayOrderPayment = new ArrayList<OrderPayment>();
        for (OrderPayment orderPayment : this.getPaymentList()) {
            Long paymentTypeId = orderPayment.getPayment().getType().getId();
            if (Code.PAYMENT_TYPE_ONLINE.equals(paymentTypeId)
            		&& orderPayment.getCanRefundMoney().compareTo(BigDecimal.ZERO)>0) {
               onlinePayOrderPayment.add(orderPayment);
            }
        }
        return onlinePayOrderPayment;
    }

    public void setPaymentList(Set<OrderPayment> paymentList) {
        this.paymentList = paymentList;
    }

    public Set<OrderTrack> getTrackList() {
        return trackList;
    }

    public void setTrackList(Set<OrderTrack> trackList) {
        this.trackList = trackList;
    }

    public Set<OrderStatusLog> getStatusLogList() {
        return statusLogList;
    }

    public void setStatusLogList(Set<OrderStatusLog> statusLogList) {
        this.statusLogList = statusLogList;
    }

    public Set<OrderUpdateLog> getUpdateLogList() {
        return updateLogList;
    }

    public void setUpdateLogList(Set<OrderUpdateLog> updateLogList) {
        this.updateLogList = updateLogList;
    }

    public OrderConsignee getConsignee() {
        return consignee;
    }

    public List<OrderLogistics> getLogisticsList() {
        return logisticsList;
    }

    public void setLogisticsList(List<OrderLogistics> logisticsList) {
        this.logisticsList = logisticsList;
    }

    public Set<OrderInvoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(Set<OrderInvoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public Set<OrderPromotion> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(Set<OrderPromotion> promotionList) {
        this.promotionList = promotionList;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    public Set<OrderItemStock> getOrderItemStocks() {
		return orderItemStocks;
	}

	public void setOrderItemStocks(Set<OrderItemStock> orderItemStocks) {
		this.orderItemStocks = orderItemStocks;
	}

	/**
     * 是否有暂存款支付且暂存款未支付
     * 
     * @return
     */
    public boolean useAccountAndUnpaid() {
        if (this.paymentList == null) {
            return false;
        }
        for (OrderPayment op : this.paymentList) {
            if (op.getPayment() != null && op.getPayment().getId().equals(Payment.ACCOUNT) && !op.isPay()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有暂存款支付
     * 
     * @return
     */
    public boolean isUseAccount() {
        if (this.paymentList == null) {
            return false;
        }
        for (OrderPayment op : this.paymentList) {
            if (op.getPayment() != null && op.getPayment().getId().equals(Payment.ACCOUNT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 订单一共需要支付的金额(不考虑订单状态，已经支付了多少，不考虑是否货到付款)，等于实洋+运费-优惠
     * 
     * @return
     */
    public BigDecimal getTotalPayMoney() {
        return salePrice.add(deliveryFee).subtract(saveMoney);
    }

    /**
     * 订单目前还需支付金额，针对订单状态和用户已支付金额计算后获取
     * 
     * @return
     */
    public BigDecimal getRequidPayMoney() {
        Long status = getProcessStatus().getId();
        if (status.equals(Code.ORDER_PROCESS_STATUS_NEW) || status.equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)
                || status.equals(Code.ORDER_PROCESS_STATUS_PICKING)
                || status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED)) {
            return getTotalPayMoney().subtract(advanceMoney);
        } else if (status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG) 
        		|| (status.equals(Code.ORDER_PROCESS_STATUS_COMPLETED) 
        				&& this.paymentStatus.getId().equals(Code.ORDER_PAY_STATUS_NONE)
        				&& this.deliveryType.getId().equals(DeliveryType.DIY))) {
            BigDecimal requidPayMoney = getDeliveryBalancePrice().add(getDeliveryFee()).subtract(advanceMoney)
                    .subtract(getTotalBalancePrice().subtract(getSalePrice().subtract(getSaveMoney())));
            return requidPayMoney.compareTo(BigDecimal.ZERO) > 0 ? requidPayMoney : BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 快递公司查询物流信息接口中的代收金额逻辑，以前是直接取的订单中的应收金额，
     * 当货到付款的订单变成交易完成后，订单中的应收金额就变成0了，该逻辑影响物流公司的正常使用
     * 
     * 现将物流接口中的代收金额逻辑改为：订单发货前，代收金额为0； 订单发货后(包括交易完成状态)，代收金额为用户还需支付的金额
     * 
     * @return
     */
    public BigDecimal getLogisticsAgentMoney() {
        Long status = getProcessStatus().getId();
        // 订单发货前，代收金额为0
        if (status.equals(Code.ORDER_PROCESS_STATUS_NEW) || status.equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)
                || status.equals(Code.ORDER_PROCESS_STATUS_PICKING)) {
            return BigDecimal.ZERO;
        } else {

            BigDecimal requidPayMoney = getDeliveryBalancePrice().add(getDeliveryFee()).subtract(advanceMoney)
                    .subtract(getTotalBalancePrice().subtract(getSalePrice().subtract(getSaveMoney())));
            return requidPayMoney.compareTo(BigDecimal.ZERO) > 0 ? requidPayMoney : BigDecimal.ZERO;
        }
    }

    public OrderReceive getReceive() {
        if (processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_COMPLETED)) {
            try {
                if (receive != null) {
                    return receive;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void setReceive(OrderReceive receive) {
        this.receive = receive;
    }

    /**
     * 添加订单商品项
     * 
     * @param item
     * 
     * 注：item根据ProductSale的id进行排序，请在调用此方法前保证已经设置ProductSale
     */
    public void addItem(OrderItem item) {
        if (itemList == null) {
        	itemList = new TreeSet<OrderItem>(new OrderItemComparatorAsc());
        }
        item.setOrder(this);
        itemList.add(item);
    }

    /**
     * 根据商品得到订单项
     * 
     * @param productSale
     * @return
     */
    public OrderItem getItem(ProductSale productSale) {
        if (itemList == null || productSale == null) {
            return null;
        }
        for (OrderItem oi : itemList) {
            if (oi.getProductSale().getId().equals(productSale.getId())) {
                return oi;
            }
        }
        return null;
    }

    public OrderItem getItemBySAP(String sapCode) {
        if (itemList == null || StringUtils.isBlank(sapCode)) {
            return null;
        } else {
            for (OrderItem oi : itemList) {
                if (oi.getProductSale().getOuterId().equals(sapCode)) {
                    return oi;
                }
            }
            return null;
        }
    }

    /**
     * 获取订单中的所有商品
     * 
     * @return
     */
    public List<ProductSale> getProductSales() {
        if (itemList == null) {
            return null;
        }
        List<ProductSale> psList = new ArrayList<ProductSale>();
        for (OrderItem oi : itemList) {
            psList.add(oi.getProductSale());
        }
        return psList;
    }

    /**
     * 添加订单支付信息
     * 
     * @param payment
     */
    public void addPayment(OrderPayment payment) {
        if (paymentList == null) {
            paymentList = new HashSet<OrderPayment>();
        }
        payment.setOrder(this);
        paymentList.add(payment);
    }

    /**
     * 添加订单促销信息
     * 
     * @param promotion
     */
    public void addPromotion(OrderPromotion promotion) {
        if (promotion.getPromotion() != null) {
            if (promotionList == null) {
                promotionList = new HashSet<OrderPromotion>();
            }
            promotion.setOrder(this);
            promotionList.add(promotion);
            updateMaxPayTime(promotion.getPromotion().getMaxPayTime());
        }
    }

    /**
     * 检查该订单中是否已经设置了promotion这个促销活动
     * 
     * @param promotion
     * @return
     */
    public boolean checkPromotion(Promotion promotion) {
        if (promotionList == null || promotion == null) {
            return false;
        }
        for (OrderPromotion op : promotionList) {
            if (op.getPromotion().getId().equals(promotion.getId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 添加订单处理状态日志，<br>
     * 同时更新订单状态，<br>
     * 设置最后处理时间, <br>
     * 添加到广播项,订单状态变更的时候,广播出去 //cast911 <br>
     * 
     * @param status
     * @param operator
     */
    public void addStatusLog(Code status, User operator) {
        Date now = new Date();
        setProcessStatus(status);
        setLastProcessTime(now);
        OrderStatusLog statusLog = new OrderStatusLog();
        statusLog.setOrder(this);
        statusLog.setOperateTime(now);
        statusLog.setOperator(operator);
        statusLog.setStatus(status);
        if (statusLogList == null) {
            statusLogList = new HashSet<OrderStatusLog>();
        }
        statusLogList.add(statusLog);
        this.addBroadCastCode(status);
    }

    /**
     * 添加订单广播项
     * 
     * @param code
     */
    public void addBroadCastCode(Code code) {
        if (CollectionUtils.isEmpty(this.broadCastList)) {
            this.broadCastList = new ArrayList<Long>();
        }
        this.broadCastList.add(code.getId());
    }

    /**
     * 添加订单跟踪信息
     * 
     * @param track
     */
    public void addTrack(OrderTrack track) {
        if (trackList == null) {
            trackList = new HashSet<OrderTrack>();
        }
        track.setOrder(this);
        trackList.add(track);
    }

    /**
     * 添加订单修改信息
     * 
     * @param updateLog
     */
    public void addUpdateLog(OrderUpdateLog updateLog) {
        if (updateLogList == null) {
            updateLogList = new HashSet<OrderUpdateLog>();
        }
        updateLog.setOrder(this);
        updateLogList.add(updateLog);
    }

    /**
     * 是否已发货
     * 
     * @return
     */
    public boolean isDeliveried() {
        if (processStatus == null || processStatus.getId() == null) {
            return false;
        }
        return processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_DELIVERIED)
                || processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)
                || processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_COMPLETED);
    }

    /**
     * 订单是否能发货
     * 
     * @return
     */
    public boolean canbeDelivery() {
        return processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_PICKING)
                || processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT);
    }

    /**
     * 创建发票
     * 
     * @param invoice
     */
    public void addInvoice(OrderInvoice invoice) {
        if (invoiceList == null) {
            invoiceList = new HashSet<OrderInvoice>();
        }
        invoice.setCreateTime(new Date());
        invoice.setOrder(this);
        invoiceList.add(invoice);
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

    public Code getErpStatus() {
        return erpStatus;
    }

    public void setErpStatus(Code erpStatus) {
        this.erpStatus = erpStatus;
    }

    public Code getPayType() {
        return payType;
    }

    public void setPayType(Code payType) {
        this.payType = payType;
    }

    public Code getStorageType() {
        return storageType;
    }

    public void setStorageType(Code storageType) {
        this.storageType = storageType;
    }

    public Code getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Code processStatus) {
        this.processStatus = processStatus;
    }

    public Code getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Code paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Code getTransferResult() {
        return transferResult;
    }

    public void setTransferResult(Code transferResult) {
        this.transferResult = transferResult;
    }

    public ParentOrder getParentOrder() {
		return parentOrder;
	}

	public void setParentOrder(ParentOrder parentOrder) {
		this.parentOrder = parentOrder;
	}

	public Set<ReturnOrder> getReturnOrderList() {
        return returnOrderList;
    }

    public void setReturnOrderList(Set<ReturnOrder> returnOrderList) {
        this.returnOrderList = returnOrderList;
    }

    public OrderNote getNote() {
        return note;
    }

    public void setNote(OrderNote note) {
        this.note = note;
    }

    public boolean isCheckStock() {
        return checkStock;
    }

    public void setCheckStock(boolean checkStock) {
        this.checkStock = checkStock;
    }

	public OrderCollection getOrderCollection() {
		return orderCollection;
	}

	public void setOrderCollection(OrderCollection orderCollection) {
		this.orderCollection = orderCollection;
	}

	public OrderInit getOrderInit() {
		return orderInit;
	}

	public void setOrderInit(OrderInit orderInit) {
		this.orderInit = orderInit;
	}

	public boolean isAllowedQueryLogistics() {
        if (!isDeliveried()) {
            return false;
        }
        String deliveryCompany = getDeliveryCompany() == null ? null : getDeliveryCompany().getCode();
        String deliveryCode = getDeliveryCode();
        DateTime time = new DateTime();
        return !StringUtils.isBlank(deliveryCompany)
                && !StringUtils.isBlank(deliveryCode)
                && createTime.after(time.minusMonths(MAX_START_MOUTH).toDate())
                && createTime.before(time.minusDays(MAX_END_DAY).withHourOfDay(MAX_HOUR).withMinuteOfHour(MAX_MINUTE)
                        .withSecondOfMinute(MAX_SECOND).toDate());
    }

    /**
     * 是否需要网上支付
     * 
     * @return
     */
    public boolean isRequidOnlinePay() {
        if (processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_NEW)
                && payType.getId().equals(Code.ORDER_PAY_TYPE_FIRST_PAY)) {
            if (paymentList != null) {
                for (OrderPayment orderPayment : paymentList) {
                    if (orderPayment.getPayment().getType().getId().equals(Code.PAYMENT_TYPE_ONLINE)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 是否需要邮局汇款支付
     * 
     * @return
     */
    public boolean isRequidPostPay() {
        if (processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_NEW)
                && payType.getId().equals(Code.ORDER_PAY_TYPE_FIRST_PAY)) {
            if (paymentList != null) {
                for (OrderPayment orderPayment : paymentList) {
                    if (orderPayment.getPayment().getId().equals(Payment.POST)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 是否需要银行转账支付
     * 
     * @return
     */
    public boolean isRequidBankPay() {
        if (processStatus.getId().equals(Code.ORDER_PROCESS_STATUS_NEW)
                && payType.getId().equals(Code.ORDER_PAY_TYPE_FIRST_PAY)) {
            if (paymentList != null) {
                for (OrderPayment orderPayment : paymentList) {
                    if (orderPayment.getPayment().getId().equals(Payment.BANK)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 是否需要货到付款支付
     * 
     * @return
     */
    public boolean isRequidCod() {
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(Payment.COD)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否需要现金支付
     * 
     * @return
     */
    public boolean isRequidCash() {
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(Payment.CASH)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否账期支付
     * 
     * @return
     */
    public boolean isOffPeriod() {
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(Payment.OFF_PERIOD)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否有退换货
     */
    public boolean isReturnOrder() {
        if (!CollectionUtils.isEmpty(returnOrderList)) {
			return true;
        }
        return false;
    }

    /**
     * 订单商品实洋扣除礼券、优惠后的金额（不考虑订单状态、已经支付了多少）
     */
    public BigDecimal getActualMoney() {
        return salePrice.subtract(saveMoney).subtract(getCouponMoney());
    }

    /**
     * 得到订单礼券金额
     * 
     * @return
     */
    public BigDecimal getCouponMoney() {
        if (paymentList != null && !paymentList.isEmpty()) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(Payment.COUPON)) {
                    return orderPayment.getPayMoney();
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 得到订单礼品卡金额
     * 
     * @return
     */
    public BigDecimal getGiftCardMoney() {
        if (paymentList != null && !paymentList.isEmpty()) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(Payment.GIFT_CARD)) {
                    return orderPayment.getPayMoney();
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 返利有效金额 =发货总实洋-礼券-退货金额
     * 
     * @return
     */
    public BigDecimal getEffiveMoney() {
        if (isCanceled()) {
            return BigDecimal.ZERO;
        }
        BigDecimal coupon = BigDecimal.ZERO;
        BigDecimal returnMoney = BigDecimal.ZERO;
        BigDecimal deliveryBalancePrice = BigDecimal.ZERO;
        for (OrderItem orderItem : itemList) {
            BigDecimal returnQuantity = new BigDecimal(orderItem.getReturnQuantity());
            returnMoney = returnMoney.add(orderItem.getBalancePrice().multiply(returnQuantity));
        }
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(Payment.COUPON)) {
                    coupon = orderPayment.getPayMoney();
                }
            }
        }
        deliveryBalancePrice = (Code.ORDER_PROCESS_STATUS_DELIVERIED.equals(processStatus.getId())) ? getActualMoney()
                : getDeliveryBalancePrice();
        BigDecimal effiveMoney = deliveryBalancePrice.subtract(returnMoney).subtract(coupon);
        return effiveMoney.compareTo(BigDecimal.ZERO) > 0 ? effiveMoney : BigDecimal.ZERO;
    }

    /**
     * 礼品卡总金额
     * 
     * @return
     */
    public BigDecimal getTotalGiftCard() {
        return getPayMoneyForPayment(Payment.GIFT_CARD, null);
    }

    /**
     * 得到暂存款支付金额
     * 
     * @return
     */
    public BigDecimal getAccoutMoney() {
        return getPayMoneyForPayment(Payment.ACCOUNT, null);
    }

    /**
     * 得到暂存款已支付的金额
     * 
     * @return
     */
    public BigDecimal getAccountPaidMoney() {
        return getPayMoneyForPayment(Payment.ACCOUNT, true);
    }

    /**
     * 得到暂存款未支付的金额
     * 
     * @return
     */
    public BigDecimal getAccountUnpaidMoney() {
        return getPayMoneyForPayment(Payment.ACCOUNT, false);
    }

    /**
     * 得到暂存款是否可以支付这张订单
     * 
     * @return
     */
    public boolean isAccountCanPay() {
        return this.customer.getAccount().getBalance().compareTo(getAccoutMoney()) >= 0;
    }

    private BigDecimal getPayMoneyForPayment(Long payment, Boolean isPaid) {
        BigDecimal paidMoney = BigDecimal.ZERO;
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getId().equals(payment)) {
                    if (isPaid == null) {
                        paidMoney = paidMoney.add(orderPayment.getPayMoney());
                    } else if (isPaid.equals(Boolean.TRUE) && orderPayment.isPay()) {
                        paidMoney = paidMoney.add(orderPayment.getPayMoney());
                    } else if (isPaid.equals(Boolean.FALSE) && !orderPayment.isPay()) {
                        paidMoney = paidMoney.add(orderPayment.getPayMoney());
                    }

                }
            }
        }
        return paidMoney;
    }

    /**
     * 取得线上支付的金额
     * 
     * @return
     */
    public BigDecimal getOnlinePayMoney() {
        BigDecimal money = BigDecimal.ZERO;
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getType().getId().equals(Code.PAYMENT_TYPE_ONLINE)) {
                    money = money.add(orderPayment.getPayMoney());
                }
            }
        }
        return money;
    }

    /**
     * 取得还需线上支付的金额
     * 
     * @return
     */
    public BigDecimal getRequireOnlinePayMoney() {
        BigDecimal money = BigDecimal.ZERO;
        if (paymentList != null) {
            for (OrderPayment orderPayment : paymentList) {
                if (orderPayment.getPayment().getType().getId().equals(Code.PAYMENT_TYPE_ONLINE)
                        && !orderPayment.isPay()) {
                    money = money.add(orderPayment.getPayMoney());
                }
            }
        }
        return money;
    }

    /**
     * 得到订单发票金额 发货前：订单实洋-优惠-礼券礼品卡支付 发货后：已发货的非礼券礼品卡金额-运费
     * 
     * @param order
     * @return
     */
    public BigDecimal getInvoiceMoney() {
        BigDecimal result = BigDecimal.ZERO;
        if (this.isDeliveried()) {
            Set<OrderPayment> orderPayments = getPaymentList();
            if (orderPayments != null && !orderPayments.isEmpty()) {
                for (OrderPayment orderPayment : orderPayments) {
                    if (!orderPayment.getPayment().getId().equals(Payment.COUPON)
                            && !orderPayment.getPayment().getId().equals(Payment.GIFT_CARD)) {
                        result = result.add(orderPayment.getDeliveryMoney());
                    }
                }
            }
            result = result.subtract(getDeliveryFee());
            return (result.compareTo(BigDecimalUtils.ZERO) >= 0) ? result : BigDecimalUtils.ZERO;
        }

        result = getSalePrice();
        Set<OrderPayment> orderPayments = getPaymentList();
        if (orderPayments != null && !orderPayments.isEmpty()) {
            for (OrderPayment orderPayment : orderPayments) {
                if (orderPayment.getPayment().getId().equals(Payment.COUPON)
                        || orderPayment.getPayment().getId().equals(Payment.GIFT_CARD)) {
                    result = result.subtract(orderPayment.getPayMoney());
                }
            }
        }
        result = result.subtract(getSaveMoney());
        return (result.compareTo(BigDecimalUtils.ZERO) >= 0) ? result : BigDecimalUtils.ZERO;
    }

    /**
     * 得到订单发票对象
     * 
     * @return
     */
    public OrderInvoice getInvoice(String content, Code mode, BigDecimal money) {
        OrderInvoice orderInvoice = new OrderInvoice();
        orderInvoice.setAddress(this.consignee.getAddress());
        orderInvoice.setCity(this.consignee.getCity());
        orderInvoice.setConsignee(this.consignee.getConsignee());
        orderInvoice.setContent(content);
        orderInvoice.setCountry(this.consignee.getCountry());
        orderInvoice.setCreateTime(this.createTime);
        orderInvoice.setMode(mode);
        orderInvoice.setDeliveryOption(this.consignee.getDeliveryOption());
        orderInvoice.setDistrict(this.consignee.getDistrict());
        orderInvoice.setTown(this.consignee.getTown());
        orderInvoice.setEmail(this.consignee.getEmail());
        orderInvoice.setMobile(this.consignee.getMobile());
        orderInvoice.setMoney(money);
        orderInvoice.setPhone(this.consignee.getPhone());
        orderInvoice.setProvince(this.consignee.getProvince());
        orderInvoice.setQuantity(this.getPurchaseQuantity());
        orderInvoice.setTitle(StringUtils.isBlank(this.consignee.getInvoiceTitle()) ? this.consignee.getConsignee()
                : this.consignee.getInvoiceTitle());
        orderInvoice.setTitleType(this.consignee.getInvoiceTitleType() == null ? new Code(Code.INVOICE_TYPE_GENERAL)
                : this.consignee.getInvoiceTitleType());
        orderInvoice.setType(new Code(Code.INVOICE_TYPE_GENERAL));
        orderInvoice.setStatus(new Code(Code.INVOICE_STATUS_NORMAL));
        orderInvoice.setZipCode(this.consignee.getZipCode());
        orderInvoice.setOrder(this);
        return orderInvoice;
    }

    /**
     * 得到根据退款排序的OrderPayment
     * 
     * @return
     */
    public List<OrderPayment> getRefundSortedPaymentList() {
        if (getPaymentList() == null || getPaymentList().size() == 0) {
            return null;
        }
        List<OrderPayment> paymentList = new ArrayList<OrderPayment>(getPaymentList());
        Collections.sort(paymentList, new RefundOrderPaymentComparator());
        return paymentList;
    }

    /**
     * 根据订单项计算订单商品的总实洋
     * 
     * @return
     */
    public BigDecimal getSalePriceByItems() {
        if (itemList == null || itemList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal salePrice = BigDecimal.ZERO;
        for (OrderItem item : itemList) {
            salePrice = salePrice.add(item.getSalePrice());
        }
        return salePrice;
    }

    /**
     * 订单是否已取消
     * 
     * @return
     */
    public boolean isCanceled() {
        Long status = getProcessStatus().getId();
        return status.equals(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)
                || status.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL)
                || status.equals(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL)
                || status.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL)
                || status.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)
                || status.equals(Code.ORDER_PROCESS_STATUS_ARCHIVE);
    }

    /**
     * 取得还未支付的订单支付方式
     * 
     * @return
     */
    public OrderPayment getUnpaidPayment() {
        Set<OrderPayment> paymentList = getPaymentList();
        for (OrderPayment orderPayment : paymentList) {
            if (!orderPayment.isPay()) {
                return orderPayment;
            }
        }
        return null;
    }

    /**
     * 获得订单使用的礼券编号
     * 
     * @return
     */
    public List<Long> getUsedPresentId() {
        if (CollectionUtils.isEmpty(paymentList)) {
            return null;
        }
        List<Long> presentIds = new ArrayList<Long>();
        for (OrderPayment orderPayment : paymentList) {
            if (orderPayment.getPayment().getId().equals(Payment.COUPON)) {
                presentIds.add(Long.valueOf(orderPayment.getCredential().getOuterId()));
            }
        }
        return presentIds;
    }

    /**
     * 获取订单的总积分
     * 
     * @return
     */
    public int getPurchaseTotalPoints() {
        int totalPoints = 0;
        if (itemList == null || itemList.isEmpty()) {
            return totalPoints;
        }
        for (OrderItem orderItem : itemList) {
            totalPoints = totalPoints + orderItem.getPurchaseQuantity() * orderItem.getPoints();
        }
        return totalPoints;
    }

    /**
     * 获取发货的总积分
     * 
     * @return
     */
    public int getDeliveryTotalPoints() {
        int totalPoints = 0;
        if (itemList == null || itemList.isEmpty()) {
            return totalPoints;
        }
        for (OrderItem orderItem : itemList) {
            totalPoints = totalPoints + orderItem.getDeliveryQuantity() * orderItem.getPoints();
        }
        return totalPoints;
    }

    /**
     * 获取退款的总积分
     * 
     * @return
     */
    public int getReturnTotalPoints() {
        int totalPoints = 0;
        if (itemList == null || itemList.isEmpty()) {
            return totalPoints;
        }
        for (OrderItem orderItem : itemList) {
            totalPoints = totalPoints + orderItem.getReturnQuantity() * orderItem.getPoints();
        }
        return totalPoints;
    }

    /**
     * 获得订单可退款金额
     * 
     * @return
     */
    public BigDecimal getCanRefundMoney() {
        if (paymentList == null || paymentList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal canRefundMoney = BigDecimal.ZERO;
        for (OrderPayment payment : paymentList) {
            canRefundMoney = canRefundMoney.add(payment.getCanRefundMoney());
        }
        return canRefundMoney;
    }

    @Override
    public String toString() {
        return "Order [getId()=" + getId() + ", getProcessStatus()=" + getProcessStatus() + "]";
    }

    /**
     * 遍历OrderItem.totalBalancePrice
     * 
     * @return
     */
    public BigDecimal getTotalBalancePrice() {
        BigDecimal totalComputePrice = BigDecimal.ZERO;
        for (OrderItem item : getItemList()) {
            totalComputePrice = totalComputePrice.add(item.getTotalBalancePrice());
        }
        return totalComputePrice;
    }

    /**
     * 遍历OrderItem.deliveryBalancePrice
     * 
     * @return
     */
    public BigDecimal getDeliveryBalancePrice() {
        BigDecimal deliveryComputePrice = BigDecimal.ZERO;
        for (OrderItem item : getItemList()) {
            deliveryComputePrice = deliveryComputePrice.add(item.getDeliveryBalancePrice());
        }
        return deliveryComputePrice;
    }

    /**
     * 获取订单中所有订单级促销活动节省的金额
     * 
     * @return
     */
    public BigDecimal getPromotionSaveMoney() {
        BigDecimal pSaveMoney = saveMoney == null ? BigDecimal.ZERO : saveMoney;
        if (!CollectionUtils.isEmpty(promotionList)) {
            for (OrderPromotion op : promotionList) {
                if (Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE.equals(op.getPromotion().getType().getId())
                        || Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE.equals(op.getPromotion().getType().getId())) {
                    pSaveMoney = pSaveMoney.add(op.getSaveMoney());
                }
            }
        }
        return pSaveMoney;
    }

    /**
     * 更新订单的最大支付时间 如果传入的时间比当前的支付期限要短，则设置传入的时间为订单的支付期限
     * 
     * @param payTerm
     */
    public void updateMaxPayTime(Date payTerm) {
        if (payTerm != null) {
            if (maxPayTime.after(payTerm)) {
                setMaxPayTime(payTerm);
            }
        }
    }

    /**
     * 获取订单中所有的满送券活动列表
     * 
     * @return
     */
    public List<OrderPromotion> getSendPresentPromotion() {
        List<OrderPromotion> list = new ArrayList<OrderPromotion>();
        if (!CollectionUtils.isEmpty(promotionList)) {
            for (OrderPromotion op : promotionList) {
                if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(op.getPromotion().getType().getId())) {
                    list.add(op);
                }
            }
        }
        return list;
    }

    /**
     * 是否可以归档
     * 
     * @return
     */
    public boolean getCanBeArchived() {
        if (Code.EC2ERP_STATUS_CANCEL.equals(this.getTransferResult().getId())) {
            Long statusId = getProcessStatus().getId();
            if (statusId.equals(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)
                    || statusId.equals(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL)) {
                return true;
            }
        } else if (getChannel() != null && getChannel().isOuterChannel()) {
            if (getProcessStatus() != null && getProcessStatus().getId() != null) {
                Long statusId = getProcessStatus().getId();
                if (statusId.equals(Code.ORDER_PROCESS_STATUS_NEW)
                        || statusId.equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)
                        || (!Code.EC2ERP_STATUS_NEW.equals(this.getTransferResult().getId()) && statusId
                                .equals(Code.ORDER_PROCESS_STATUS_PICKING))
                        || statusId.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)
                        || statusId.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL)
                        || statusId.equals(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)
                        || statusId.equals(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 取消下传到中启的订单-取消
     * 
     * @return
     */
    public boolean canbeDonotUploadErpCancel() {
        return ArrayUtils.contains(new Long[] { Code.ORDER_PROCESS_STATUS_NEW,
                Code.ORDER_PROCESS_STATUS_WAITING_PICKING, Code.ORDER_PROCESS_STATUS_PICKING }, this.getProcessStatus()
                .getId());
    }

    /**
     * 是否可以取消订单 狭义上的取消 仅在.等待配货状态以前。 或设置不下传到中启的订单.并且在正在匹配货以前.才返回true
     * 
     * @return
     */
    public boolean getCanbeCancel() {
        if (Code.EC2ERP_STATUS_CANCEL.equals(getTransferResult().getId())) {
            return canbeDonotUploadErpCancel();
        } else {
            return ArrayUtils.contains(new Long[] { Code.ORDER_PROCESS_STATUS_NEW,
                    Code.ORDER_PROCESS_STATUS_WAITING_PICKING }, this.getProcessStatus().getId());
        }
    }

    /**
     * 客户取消
     * 
     * @return
     */
    public boolean canbeCustomerCancel(User operator) {
        Long[] expectedStatusId;
        if (!operator.getId().equals(Employee.SYSTEM)) {
            expectedStatusId = new Long[] { Code.ORDER_PROCESS_STATUS_NEW, Code.ORDER_PROCESS_STATUS_WAITING_PICKING };
        } else {
            expectedStatusId = new Long[] { Code.ORDER_PROCESS_STATUS_NEW, Code.ORDER_PROCESS_STATUS_WAITING_PICKING,
                    Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT, Code.ORDER_PROCESS_STATUS_PICKING };
        }
        return ArrayUtils.contains(expectedStatusId, this.getProcessStatus().getId());
    }

    /**
     * 仓库取消
     * 
     * @return
     */
    public boolean canbeErpCancel() {
        return ArrayUtils.contains(new Long[] { Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT }, this.getProcessStatus()
                .getId());

    }

    /**
     * 缺货取消
     * 
     * @return
     */
    public boolean canbeOutOfStockCancel() {
        return ArrayUtils.contains(new Long[] { Code.ORDER_PROCESS_STATUS_PICKING,
                Code.ORDER_PROCESS_STATUS_WAITING_PICKING, Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT }, this
                .getProcessStatus().getId());
    }

    /**
     * 退货取消
     * 
     * @return
     */
    public boolean canbeRejectionCancel() {
        return ArrayUtils.contains(new Long[] { Code.ORDER_PROCESS_STATUS_DELIVERIED,
                Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG, Code.ORDER_PROCESS_STATUS_COMPLETED }, this
                .getProcessStatus().getId());
    }

    /**
     * 系统取消
     * 
     * @return
     */
    public boolean canbeSystemCancel() {
        return Code.ORDER_PROCESS_STATUS_NEW.equals(this.getProcessStatus().getId());
    }

    /**
     * 订单渠道所是否在指定渠道
     * 
     * @param channels
     * @return
     */
    public boolean existsChannel(Long... channel) {
        if (this.getChannel() == null || channel == null) {
            return false;
        } else {
            Long id = this.getChannel().getId();
            for (Long l : channel) {
                if (id.equals(l)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 订单处理状态是否在指定状态内
     * 
     * @param status
     *            指定状态
     * @return
     */
    public boolean existsOrderProcessStatus(Long... status) {
        if (this.getProcessStatus() == null || status == null) {
            return false;
        } else {
            Long s = this.getProcessStatus().getId();
            for (Long l : status) {
                if (s.equals(l)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否有合法手机号
     * 
     * @return
     */
    public boolean hasConsigneeMobile() {
        if (this.getConsignee() != null && this.getConsignee().getMobile() != null
                && this.getConsignee().getMobile().matches("\\d{11}")) {
            return true;
        }
        return false;
    }

    public String getPromotionTitleString() {
        Set<OrderPromotion> promotions = getPromotionList();
        if (promotions != null) {
            StringBuffer result = new StringBuffer();
            for (OrderPromotion promotion : promotions) {
                result.append(promotion.getPromotion().getTitle());
                result.append("\r\n");
            }
            return result.toString();
        }
        return String.valueOf("无促销");
    }

    /**
     * 获取订单的发货重量或者购买重量
     * @param purchaseOrDelivery 是发货的重量还是购买的重量(Order.PURCHASE_QUANTITY/Order.DELIVERY_QUANTITY)
     * @return
     */
    public int getOrderWeight(Integer purchaseOrDelivery) {
        if (!CollectionUtils.isEmpty(getItemList())) {
            int weight = 0;
            int tempWeight = 0;
            ProductSale ps = null;
            for (OrderItem item : getItemList()) {
                ps = item.getProductSale();
                if (ps != null) {
                    tempWeight = ps.getWeight();
                    if (tempWeight > 0) {
                    	if (Order.PURCHASE_QUANTITY.equals(purchaseOrDelivery)) {
                    		weight = weight + tempWeight * item.getPurchaseQuantity();
						} else {
							weight = weight + tempWeight * item.getDeliveryQuantity();
						}
                    } 
                }
            }
            return weight;
        }
        return 0;
    }

    /**
     * 是否能被复制
     * 
     * @return
     */
    public boolean isCanBeCopy() {
        if (this.isCanceled() && !this.getChannel().isOuterChannel()) {
            return true;
        }
        return false;
    }

    public List<OrderExtend> getOrderExtends() {
        return orderExtends;
    }

    public void setOrderExtends(List<OrderExtend> orderExtends) {
        this.orderExtends = orderExtends;
    }

    public String getPackages() {
        for (OrderExtend o : this.getOrderExtends()) {
            if (o.getMeta() == OrderExtend.ORDER_PACKAGES) {
                return o.getValue();
            }
        }
        return "";
    }
    
    public String getChannelPackageNum() {
    	for(OrderExtend oe : this.getOrderExtends()) {
    		if(OrderExtend.CHANNEL_PACKAGE_NUMBER.equals(oe.getMeta())) {
    			return oe.getValue();
    		}
    	}
    	return "";
    }

    /**
     * 得到指定状态的订单状态
     * 
     * @param statusId
     * @return
     */
    public OrderStatusLog getLogByStatus(Long statusId) {
        for (OrderStatusLog log : this.getStatusLogList()) {
            if (log.getStatus().getId().equals(statusId)) {
                return log;
            }
        }
        return null;
    }

    /**
     * 订单缺货后的选项
     * 
     * @return true: 缺货发货， false:缺货取消
     */
    public boolean getOutOfStockOption() {
        return Code.OUT_OF_STOCK_OPTION_SEND.equals(this.getConsignee().getOutOfStockOption().getId());
    }

    /**
     * 订单是否包含百货商品
     * 
     * @return
     */
    public boolean isExistMerchandiseItem() {
        if (itemList == null) {
            return false;
        }
        for (OrderItem item : itemList) {
            Product product = item.getProductSale().getProduct();
            if (product.getSort().getId().equals(Code.PRODUCT_SORT_MERCHANDISE)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 是否是渠道订单
     * @return
     */
    public boolean isChannelOrder(){
    	return StringUtils.isNotBlank(outerId);
    }

    public boolean isForceSendMessage() {
        return forceSendMessage;
    }
    public void setForceSendMessage(boolean forceSendMessage) {
        this.forceSendMessage = forceSendMessage;
    }

	public boolean isSplit() {
		return isSplit;
	}

	public void setSplit(boolean isSplit) {
		this.isSplit = isSplit;
	}

	public String getDetail() {
        StringBuffer detail = new StringBuffer();
        detail.append("orderid=" + this.getId()).append("&ordertotal=" + this.getSalePrice())
                .append("&orderfull=" + this.getListPrice());
        Set<OrderItem> itemList = this.getItemList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(itemList)) {
            detail.append("&skulist=");
            for (OrderItem item : itemList) {
                detail.append(item.getProductSale().getId()).append(",").append(item.getSalePrice()).append(",")
                        .append(item.getPurchaseQuantity()).append(", ,")
                        .append(item.getProductSale().getProduct().getManufacturer()).append(",")
                        .append(item.getProductSale().getProduct().getAuthor()).append(",").append(item.getListPrice())
                        .append(";");
            }
        }
        return detail.toString().substring(0, detail.length() - 1);
    }
    /**
     * 验证是否COD订单
     * @return
     */
    public boolean isCODOrder(){
    	String channelId = Channel.CHANNEL_MAP.get(getChannel().getId());
    	if(StringUtils.isNotBlank(channelId)){
    		if(channelId.equals(Channel.CHANNEL_COD)){
    			return isOrderExtendCod();
    		}
    	}
    	return false;
    }
    
    /**
     * 验证是否有电子面单
     * @return
     */
    public boolean isOrderExtendCod(){
    	if(orderExtends!=null&&!orderExtends.isEmpty()){
    		for(OrderExtend oe:orderExtends){
        		Integer meta = oe.getMeta();
        		if(meta.compareTo(OrderExtend.DANGDANG_COD_ELEC_BILL)==0
        				||meta.compareTo(OrderExtend.JINGDONG_COD_ELEC_BILL)==0){
        			return true;
        		}
        	}
    	}
    	return false;
    }
    /**
     * 判断是否需要集货
     * @return
     */
    public boolean isCollection(){
    	return orderCollection != null && orderCollection.isCollect(); 
    }
    
    /**
     * 订单商品项、订购数量
     * @param order
     * @return
     */
	public Map<ProductSale,Integer> getProductsaleNumMap(){
		Map<ProductSale,Integer> map  =  new HashMap<ProductSale,Integer> ();
		for(OrderItem item : this.getItemList()){
			map.put(item.getProductSale(), item.getPurchaseQuantity());
		}
		return map;
	}
	
	/**
	 * 距订单创建时间36小时还有多少时分秒
	 */
	public String getDistance36HourValue() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.createTime);
		calendar.add(Calendar.HOUR_OF_DAY, 36);
		long time = calendar.getTimeInMillis() - new Date().getTime();
		if (time < 0L) {
			return "00:00:00";
		}
		long hour = time / (1000 * 3600);
		long min = time / (1000 * 60) - hour * 60;
		long s = (time / 1000) - (hour * 3600) - (min * 60);
		return new StringBuilder().append(hour).append(":").append(min).append(":").append(s).toString();
	}
	
}
