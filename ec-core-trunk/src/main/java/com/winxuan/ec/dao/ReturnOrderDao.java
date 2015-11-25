/*
 * @(#)ReturnOrderDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderPackage;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageItem;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageRelate;
import com.winxuan.ec.model.returnorder.ReturnOrderTag;
import com.winxuan.ec.model.returnorder.ReturnOrderTrack;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-14
 */
public interface ReturnOrderDao {

	@Save
	void save(ReturnOrder returnOrder);

	@Get
	ReturnOrder get(Long id);

	@Update
	void update(ReturnOrder returnOrder);

	@Query("from ReturnOrder ro order by ro.id desc")
	@Conditions({ @Condition("ro.originalOrder.id in :orderId"),
		@Condition("ro.id in :returnOrderId"),
		@Condition("ro.originalOrder.outerId in :outerId"),
		@Condition("ro.type.id in :type"),
		@Condition("ro.status.id in :status"),
		@Condition("ro.originalOrder.consignee.consignee = :consignee"),
		@Condition("ro.originalOrder.storageType.id in :storageType"),
		@Condition("ro.originalOrder.virtual = :virtual"),
		@Condition("ro.creator.realName = :creator"),
		@Condition("ro.createTime >= :startCreateTime"),
		@Condition("ro.createTime <= :endCreateTime"),
		@Condition("ro.originalOrder.channel.id = :channelId"),
		@Condition("ro.originalOrder.deliveryTime >= :startDeliveryTime"),
		@Condition("ro.originalOrder.deliveryTime <= :endDeliveryTime"),
		@Condition("ro.transferred = :transferred"),
		@Condition("ro.needtransfers = :needtransfers"),
		@Condition("ro.originalOrder.shop.id = :shopId"),
		@Condition("ro.refundTime >= :startLastProcessTime"),
		@Condition("ro.refundTime <= :endLastProcessTime"),
		@Condition("ro.customer = :customer"),
		@Condition("ro.returnOrderDc.targetDc.id in :returnDc"),
		@Condition("ro.returnOrderDc.targetRealDc.id in :receiveDc"),
		@Condition("ro.shouldrefund.id = :isrefund")})

		List<ReturnOrder> find(@ParameterMap Map<String, Object> parameters,
				@Page Pagination pagination);

	@Save
	void save(ReturnOrderTrack track);

	@Save
	void save(ReturnOrderTag returnOrderTag);

	@Query(sqlQuery=true,value="SELECT c1.name as storagetype,count(*) as countAll,sum(ri.realquantity) as realQuantityAll," +
			"sum(ro.refundgoodsvalue) as refundValueAll,sum(oi.listprice) as listPriceAll,sum(oi.saleprice) as salePriceAll," +
			"sum(oi.saleprice*oi.deliveryquantity) as salePriceSaleAll,sum(oi.saleprice)/sum(oi.listprice) as discountAll " +
			"FROM returnorder ro INNER JOIN _order od ON ro.originalorder = od.id " +
			"INNER JOIN returnorder_item ri ON ro.id = ri.returnorder " +
			"INNER JOIN order_item oi ON oi.id = ri.orderitem " +
			"INNER JOIN product_sale ps ON (ps.id = oi.productsale) " +
			"INNER JOIN code c1 ON c1.id = od.storagetype " +
			"WHERE od.shop = :shop AND ro.`status` = 25005 AND ro.type in (24001,24002) " +
			"AND ro.refundtime >= :startLastProcessTime AND ro.refundtime <= :endLastProcessTime " +
			"GROUP BY od.storagetype")
	List<Map<String, Object>> findReturnOrderCollectByShop(@ParameterMap Map<String, Object> parameters);
	
	@Query("from ReturnOrderTag rot where rot.returnOrder.id = ?")
	List<ReturnOrderTag> findReturnOrderTagByReturnOrderId(Long id);
	
	@Query("from ReturnOrderTag rot where rot.returnOrder.id = ? and rot.tag.id = ?")
	ReturnOrderTag findReturnOrderTagByReturnOrderIdAndTagId(Long id, Long tagId);
	
	@Save
	void save(ReturnOrderPackage returnPackage);
	
	@Save
	void save(ReturnOrderPackageItem item);
	
	@Save
	void save(ReturnOrderPackageRelate relate);
	
	@Query("select rop from ReturnOrderPackage rop left join rop.returnOrderPackageRelateList ropr ")
	@Conditions({
		//@Condition("rop.expressid like '%:expressid%'"),
		@Condition("rop.expressid = :expressid "),
		@Condition("ropr.relateid = :orderid and ropr.relateship = '600141' "),
		@Condition("rop.status.id = :status"),
		@Condition("rop.customer = :customer"),
		@Condition("rop.phone = :phone"),
		@Condition("ropr.relateid = :returnid and ropr.relateship = '600142' "),
		@Condition("rop.sSignTime >= :starttime"),
		@Condition("rop.sSignTime <= :endtime")
	})
	List<ReturnOrderPackage> findReturnOrderPackage(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("from ReturnOrderPackageItem item where item.returnOrderPackage.id = ?")
	List<ReturnOrderPackageItem> getPackageItem(Long id);
	
	@Query("from ReturnOrderPackage returnpackage where returnpackage.id = ?")
	ReturnOrderPackage getPackage(Long packageid);
	
	@Update
	void update(ReturnOrderPackage returnOrderPackage);
	
	@Update
	void update(ReturnOrderPackageRelate returnOrderPackageRelate);

}
