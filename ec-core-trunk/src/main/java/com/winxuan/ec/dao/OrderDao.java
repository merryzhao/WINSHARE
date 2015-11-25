/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderCloneLog;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderNote;
import com.winxuan.ec.model.order.OrderPackges;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Evict;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.FirstResult;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liyang
 * @version 1.0,2011-7-19
 */

public interface OrderDao {

	@Save
	void save(Order order);
	
	@Update
	void update(Order order);

	@Get
	Order get(String id);
	
	@SaveOrUpdate
	void save(OrderPackges orderPackges);
	
	@Query("from OrderPackges o where o.order=?")
	OrderPackges getOrderPackges(String orderid);
	
	@Query("select distinct o from Order o left join o.paymentList p left join o.itemList i left join i.productSale s left join o.channel c left join o.orderInit oi")
	@Conditions({
		  @Condition("o.creator = :creator"),
		  @Condition("o.invoiceList.size =:isnull  "),
		  @Condition("o.invoiceList.size >:isnotnull  "),
		  @Condition("o.saveMoney = :saveMoney"),
		  @Condition("o.deliveryFee = :deliveryFee"),
		  @Condition("o.advanceMoney = :advanceMoney"),
		  @Condition("o.isTransferred = :istransferred"),
		  @Condition("o.deliveryCompany.id = :deliveryCompany"),
		  @Condition("o.deliveryListPrice = :deliveryListPrice"),
		  @Condition("o.deliverySalePrice = :deliverySalePrice"),
		  @Condition("o.id in :orderId"),
		  @Condition("o.createTime>= :startCreateTime"), 
		  @Condition("o.createTime<= :endCreateTime"),
		  @Condition("o.lastProcessTime>= :startLastProcessTime"),
		  @Condition("o.lastProcessTime<= :endLastProcessTime"), 
		  @Condition("p.payTime>= :startPayTime"),
		  @Condition("p.payTime<= :endPayTime"),
		  @Condition("p.payment.type.id in :paymentType"),
		  @Condition("p.payment.id in :payment"),
		  @Condition("o.deliveryTime>= :startDeliveryTime"),
		  @Condition("o.deliveryTime<= :endDeliveryTime"), 
		  @Condition("o.shop.id in :shop"),
		  @Condition("o.deliveryCompany.id in :deliveryCompanys"),
		  @Condition("o.processStatus.id in :processStatus"), 
		  @Condition("o.paymentStatus.id in :paymentStatus"), 
		  @Condition("o.payType.id in :payType"), 
		  @Condition("o.deliveryType.id in :deliveryType"), 
		  @Condition("o.consignee.remark = :remark"), 
		  @Condition("o.customer.id  = :customerId"),
		  @Condition("o.customer.name in :name"),
		  @Condition("o.customer.realName in :realNames"),
		  @Condition("o.consignee.needInvoice =:needInvoice"),
		  @Condition("o.consignee.consignee in :consignee"),
		  @Condition("o.consignee.phone in :phone"), 
		  @Condition("o.consignee.mobile in :mobile"), 
		  @Condition("o.consignee.town.id = :town"), 
		  @Condition("o.listPrice >=:startListPrice"),
		  @Condition("o.listPrice <=:endListPrice"), 
		  @Condition("o.salePrice >=:startSalePrice"), 
		  @Condition("o.salePrice <=:endSalePrice"),
		  @Condition("i.productSale.id in :product"), 
		  @Condition("s.outerId in :owncode"),
		  @Condition("o.outerId in :outerId"), 
		  @Condition("o.consignee.needInvoice = :needInvoice"),
		  @Condition("o.distributionCenter.dcDest.id in :consigneeDc"),
		  @Condition("o.transferResult.id = :transferResult"),
		  @Condition("o.consignee.outOfStockOption.id in :outOfStockOption"), 
		  @Condition("o.channel.id in :channel"),
		  @Condition("o.supplyType.id in :saleType"),
		  @Condition("o.customer.grade in :grade"),
		  @Condition("o.customer.source.id = :source"),
		  @Condition("o.deliveryCode in :deliveryCode"), 
		  @Condition("i.productSale.product.sort.id in :sort"), 
		  @Condition("i.productSale.product.barcode in :barcode"), 
		  @Condition("o.storageType.id in :storageType"),
		  @Condition("o.supplyType.id in :supplyType"),
		  @Condition("o.virtual = :virtual"),
		  @Condition("o.unite = :unite"),
		  @Condition("c.parent.id in :parentChannel"),
		  @Condition("o.distributionCenter.dcOriginal.id in :dcoriginal"),
		  @Condition("o.distributionCenter.dcDest.id in :dcdest"),
		  @Condition("oi.status.id = :initStatus")
	})
	@OrderBys({
    	@OrderBy("o.createTime desc"),
    	@OrderBy("o.createTime asc"),
    	@OrderBy("o.deliveryTime desc"),
    	@OrderBy("o.deliveryTime asc"),
    	@OrderBy("o.id desc"),
    	@OrderBy("o.id asc"),
    	@OrderBy("o.deliveryListPrice desc"),
    	@OrderBy("o.deliveryListPrice asc"),
    	@OrderBy("o.listPrice desc"),
    	@OrderBy("o.listPrice asc")
	})
	List<Order> find(@ParameterMap Map<String, Object> parameters,@com.winxuan.framework.dynamicdao.annotation.query.Order Short sort,
			@Page Pagination pagination);

	@Query("from Order o where o.customer = ? order by o.id desc")
	List<Order> findByCustomer(Customer customer, @MaxResults int size);
	

	/**
	 * 查询发票
	 * 
	 * @param parameters
	 * <br/>
	 *            id:String[] 订单号 <br/>
	 *            outerId:String[] 外部订单号<br/>
	 *            startDate:Date 下单开始日期<br/>
	 *            endDate:Date 下单结束日期<br/>
	 *            customerName:String 用户名<br/>
	 * @param pagination
	 * @return objArray[0]订单，objArray[1]发票
	 */
	@Query("from Order o left join o.invoiceList i")
	@Conditions({ 
			@Condition("o.id in :orderIds"),
			@Condition("i.id in :invoiceIds"),
			@Condition("o.outerId in :outerIds"),
			@Condition("o.deliveryTime >= :startDate"),
			@Condition("o.deliveryTime <= :endDate"),
			@Condition("o.customer.name = :customerName") })
	List<Object[]> findOrderInvoice(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("from Order o where o.processStatus.id=8001 and o.payType.id=5002")
	long getCountOfUnaudited();
	
	@Evict
	void evict(Order order);
	
	@Merge
	void merge(Order order);
	
	
	@Query("from Order o where o.channel.id=? and o.outerId=?")
	List<Order> findByChannelIdAndOuterId(long channelId, String outerId);
	
	@Save
	void saveNote(OrderNote orderNote);
	
	@Query("from Order o where o.customer=? and o.id=?")
	boolean isExistByCustomerAndOrderId(Customer customer,String id);
	
	@Get
	OrderItem get(long id);
	
	@Query(sqlQuery=true,value="SELECT COUNT(1) FROM _order WHERE outerId = ?")
	boolean isExistOuterId(String outerId);

	@Query("from Order o left join o.consignee")
    @Conditions({
          @Condition("o.deliveryCompany.id = :deliveryCompany"),
          @Condition("o.deliveryTime>= :startDeliveryTime"),
          @Condition("o.deliveryTime<= :endDeliveryTime")
    })
    List<Object[]> findForDeliveryCompany(@ParameterMap Map<String, Object> param);
    
    
    /**
     * 不要在删这个方法了好吗?,配送公司获取订单用的
     * @param parameters
     * @param sort
     * @param pagination
     * @return
     */
    @Query("from Order o")
    @Conditions({
    	  @Condition("o.deliveryCode = :deliveryCode"),
          @Condition("o.deliveryCompany.id = :deliveryCompany"),
          @Condition("o.deliveryTime>= :startDeliveryTime"),
          @Condition("o.deliveryTime<= :endDeliveryTime"),
          @Condition("o.processStatus.id in :processStatus")
    })
     @OrderBys({
    	@OrderBy("o.deliveryTime desc"),
    	@OrderBy("o.deliveryTime asc"),
    	@OrderBy("o.deliveryCode desc"),
    	@OrderBy("o.deliveryCode asc")
	}) 
    List<Order> findForDeliveryCompany(@ParameterMap Map<String, Object> parameters,@com.winxuan.framework.dynamicdao.annotation.query.Order Short sort,
			@Page Pagination pagination);

    
	
	@Query("select distinct o from Order o where o.outerId is null")
	@Conditions({
		  @Condition("o.createTime>= :startCreateTime"), 
		  @Condition("o.createTime<= :endCreateTime"),
		  @Condition("o.deliveryTime>= :startDeliveryTime"),
		  @Condition("o.deliveryTime<= :endDeliveryTime"), 
		  @Condition("o.processStatus.id in :processStatus")
	})
	List<Order> findWithoutOuterId(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination);
	
	
	@Query(sqlQuery=true,value="SELECT ce.name as storageType,COUNT(*) as countAll,sum(od.deliveryquantity) as deliveryQuantityAll," +
						"sum(od.deliverylistprice) as listPriceAll,sum(od.deliverysaleprice) as salePriceAll," +
						"sum(od.deliverysaleprice)/sum(od.deliverylistprice) as discountAll,sum(od.deliveryfee) as deliveryFeeAll " +
						"FROM _order od LEFT JOIN code ce ON ce.id = od.storagetype " +
						"WHERE od.shop = :shop AND od.processstatus in (8004,8005,8011) AND od.deliverytime >= :startDeliveryTime " +
						"AND od.deliverytime <= :endDeliveryTime " +
						"GROUP BY od.storagetype")
	List<Map<String, Object>> findOrderCollectByShop(@ParameterMap Map<String, Object> parameters);

	//保存复制订单日志
	@Save
    void saveOrderCloneLog(OrderCloneLog clonelog);
	
	@Save
	void saveOrderExtend(OrderExtend orderExtend);
	
	@Query("SELECT distinct o FROM Order o LEFT JOIN o.orderExtends oe INNER JOIN o.consignee oc ")
	@Conditions({
	      @Condition("o.id IN :orders"),
	      @Condition("o.processStatus.id IN :status"),
		  @Condition("o.deliveryTime>= :startDeliveryTime"),
		  @Condition("o.deliveryTime<= :endDeliveryTime"), 
		  @Condition("o.channel.id IN :channels"),
		  @Condition("oe.meta = :meta AND oe.value != '' "),
		  @Condition("( oc.district.id IN :district  " +
		  		" OR oc.city.id IN :city " +
		  		" OR oc.province.id IN :province " +
		  		" OR oc.country.id IN :country" +
		  		" OR true = :ignore )")
	})
	@OrderBys({ 
    	@OrderBy("o.createTime desc"),
    	@OrderBy("o.createTime asc"),
    	@OrderBy("o.deliveryTime desc"),
    	@OrderBy("o.deliveryTime asc"),
    	@OrderBy("o.id desc"),
    	@OrderBy("o.id asc"),
    	@OrderBy("o.deliveryListPrice desc"),
    	@OrderBy("o.deliveryListPrice asc"),
    	@OrderBy("o.listPrice desc"),
    	@OrderBy("o.listPrice asc")
	})
	List<Order> findOrderWithMeta(@ParameterMap Map<String, Object> parameters,@com.winxuan.framework.dynamicdao.annotation.query.Order Short sort,@Page Pagination pagination);
	
	@Query("SELECT distinct o FROM Order o LEFT JOIN o.orderExtends oe with oe.meta=1 INNER JOIN o.consignee oc ")
	@Conditions({
	      @Condition("o.id IN :orders"),
	      @Condition("o.processStatus.id IN :status"),
		  @Condition("o.deliveryTime>= :startDeliveryTime"),
		  @Condition("o.deliveryTime<= :endDeliveryTime"), 
		  @Condition("o.channel.id IN :channels"),
//		  @Condition("IF(1=:meta, oe.value IS NOT NULL, oe.id IS NULL)"),
		  @Condition("1 = :meta AND oe.value != '' "),
		  @Condition("-1 = :nmeta AND oe.id IS NULL"),
		  @Condition("( oc.district.id IN :district  " +
		  		" OR oc.city.id IN :city " +
		  		" OR oc.province.id IN :province " +
		  		" OR oc.country.id IN :country" +
		  		" OR true = :ignore )")
	})
	@OrderBys({ 
    	@OrderBy("o.createTime desc"),
    	@OrderBy("o.createTime asc"),
    	@OrderBy("o.deliveryTime desc"),
    	@OrderBy("o.deliveryTime asc"),
    	@OrderBy("o.id desc"),
    	@OrderBy("o.id asc"),
    	@OrderBy("o.deliveryListPrice desc"),
    	@OrderBy("o.deliveryListPrice asc"),
    	@OrderBy("o.listPrice desc"),
    	@OrderBy("o.listPrice asc")
	})
	List<Order> findOrderWithMeta1(@ParameterMap Map<String, Object> parameters,@com.winxuan.framework.dynamicdao.annotation.query.Order Short sort,@Page Pagination pagination);
	
	@Query(sqlQuery = true, 
			value = "select ifnull(sum(c.quantity),0) from customer_bought c where c.buytime>date(now())")
	@Conditions({
	      @Condition("c.customer=:customerId"),
	      @Condition("c.productsale=:productSaleId")
	})
	long getPurchaseQuantityToday(@Parameter("customerId") Long customerId,
			@Parameter("productSaleId") long productSaleId);
	
	@Query("SELECT oi FROM OrderItem oi WHERE oi.id IN :orderItems")
	List<OrderItem> findOrderItem(@Parameter("orderItems") Long[] orderItemIds);
	
	@Query("SELECT oi.id FROM Order oi WHERE oi.processStatus.id = 8002 and oi.supplyType.id = 13102")
	@OrderBy("oi.deliveryTime asc")
	List<String> findBookingOrderId(@FirstResult int firstResult,@MaxResults int maxResults);
	
	@Query("select o.id from Order o where o.orderInit.status.id =:initStatus ")
	List<String> getInitOrders(@Page Pagination pagination,@Parameter ("initStatus") Long initStatus);
	
}
