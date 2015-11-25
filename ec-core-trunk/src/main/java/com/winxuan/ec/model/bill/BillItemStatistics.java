package com.winxuan.ec.model.bill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.channel.Channel;

/**
 * 订单项统计
 * @author heyadong
 *
 */
@Table(name="bill_item_statistics")
@Entity
public class BillItemStatistics {
	
	@Id
	Long orderItem;
	
	//发货数量
	@Column(name="deliveryquantity")
	Integer deliveryQuantity;
	//结算数量
	@Column(name="settlementquantity")
	//退货数量(退款)
	Integer settlementQuantity;
	@Column(name="refoundquantity")
	Integer refoundQuantity;
	
	@ManyToOne
	@JoinColumn(name="channel",updatable=false,insertable=false)
	Channel channel;
	

	public Long getOrderItem() {
		return orderItem;
	}
	
	public void setOrderItem(Long orderItem) {
		this.orderItem = orderItem;
	}
	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	public Integer getSettlementQuantity() {
		return settlementQuantity;
	}
	public void setSettlementQuantity(Integer settlementQuantity) {
		this.settlementQuantity = settlementQuantity;
	}
	public Integer getRefoundQuantity() {
		return refoundQuantity;
	}
	public void setRefoundQuantity(Integer refoundQuantity) {
		this.refoundQuantity = refoundQuantity;
	}
	
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	/**
	 * 可结算数量
	 */
	public int getAvailableQuantity() {
		return this.getDeliveryQuantity() - this.getSettlementQuantity();
	}
	
}
