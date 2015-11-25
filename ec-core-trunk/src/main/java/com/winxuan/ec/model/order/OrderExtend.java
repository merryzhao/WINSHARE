package com.winxuan.ec.model.order;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 订单扩展 信息
 * @author heyadong
 * @version 1.0, 2012-8-10 下午04:48:10
 */
@Entity
@Table(name="order_extend")
public class OrderExtend {
	
	/**
	 * 订单包件数
	 */
	public static final Integer ORDER_PACKAGES = 1;
	
	/**
	 * apiId,记录api用户下单
	 */
	public static final Integer APP_ID = 2;
	
	/**
	 * 订单发货下传SAP
	 */
	public static final Integer SYN_SAP_DELIVERY = 21;
	
	/**
	 * 订单发货拒收下传SAP
	 */
	public static final Integer SYN_SAP_REJECT = 22;
	
	/**
	 * 退货订单下传SAP,由于有多个退订单..,  value为退货单号.
	 */
	public static final Integer SYN_SAP_RETURN =23;
	
	/**
	 * 订单发货,记入历史统计
	 */
	public static final Integer STATISTICS_DELIVERY = 31;
	
	/**
	 * 订单发货拒收记计历史统计
	 */
	public static final Integer STATISTICS_REJECT = 32;
	/**
	 * 退货订单记录历史统计,由于有多个退订单..,  value为退货单号.
	 */
	public static final Integer STATISTICS_RETURN = 33;
	
	/**
	 * 标记渠道订单是否可以重新下传
	 */
	public static final Integer CHANNEL_FLAG = 8103;
	
	/**
	 * 渠道订单包裹号
	 */
	public static final Integer CHANNEL_PACKAGE_NUMBER = 8104;
	
	/**
	 * 当当COD收货仓库
	 */
	public static final Integer DANGDANG_COD_WAREHOUSE_KEY = 8201;
	
	public static final String DANGDANG_COD_WAREHOUSE_BEIJING = "当当北京仓库";
	public static final String DANGDANG_COD_WAREHOUSE_CHENGDU = "当当成都仓库";
	
	/**
	 * 当当COD电子面单
	 */
	public static final Integer DANGDANG_COD_ELEC_BILL = 8202;
	/**
	 * COD成都目标仓库
	 */
	public static final Integer COD_TARGET_WAREHOUSE=201;
	
	/**
	 * 京东COD收货仓库
	 */
	public static final Integer JINGDONG_COD_WAREHOUSE_KEY = 8203;
	/**
	 * 京东COD电子面单
	 */
	public static final Integer JINGDONG_COD_ELEC_BILL=8204;
	
	public static final String JINGDONG_COD_WAREHOUSE_CHENGDU = "360BUY成都仓库";
	
	public static final Map<Integer, OrderExtend> ORDER_EXTEND_MAP = new HashMap<Integer, OrderExtend>(); 
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_order",updatable=false)
	private Order order;
	
	@Column
	private Integer meta;
	
	@Column(name="_value")
	private String value;
	
	@Transient
	private Integer warehouse;

	static{
		ORDER_EXTEND_MAP.put(JINGDONG_COD_ELEC_BILL, new OrderExtend(JINGDONG_COD_ELEC_BILL,JINGDONG_COD_WAREHOUSE_KEY));
		ORDER_EXTEND_MAP.put(DANGDANG_COD_ELEC_BILL, new OrderExtend(DANGDANG_COD_ELEC_BILL,DANGDANG_COD_WAREHOUSE_KEY));
	}
	
	public OrderExtend() {
		super();
	}

	public OrderExtend(Integer meta,Integer warehouse) {
		super();
		this.meta = meta;
		this.warehouse=warehouse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Integer getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Integer warehouse) {
		this.warehouse = warehouse;
	}

	/**
	 * 获取电子面单值
	 * @param order
	 * @return
	 */
	public static String getExtendValueByKey(Order order) {
		for (OrderExtend orderExtend : order.getOrderExtends()) {
			OrderExtend oExtend = ORDER_EXTEND_MAP.get(orderExtend.getMeta());
			if(oExtend!=null&&oExtend.getMeta().equals(orderExtend.getMeta())){
				return orderExtend.getValue();
			}
		}
		return "";
	}
	/**
	 * 获取发货仓的值
	 * @param order
	 * @return
	 */
	public static String getCodWarehouseData(Order order) {
		Integer warehouseMeta = getCODWarehouse(order);
		if(warehouseMeta!=null){
			for (OrderExtend orderExtend : order.getOrderExtends()) {
				if(warehouseMeta.equals(orderExtend.getMeta())){
					return orderExtend.getValue();
				}
			}
		}
		return "";
	}
	
	/**
	 * 获取COD订单发货仓
	 * @param order
	 * @return
	 */
	public static Integer getCODWarehouse(Order order) {
		for (OrderExtend orderExtend : order.getOrderExtends()) {
			OrderExtend oExtend = ORDER_EXTEND_MAP.get(orderExtend.getMeta());
			if(oExtend!=null&&oExtend.getMeta().equals(orderExtend.getMeta())){
				return oExtend.getWarehouse();
			}
		}
		return null;
	}
}
