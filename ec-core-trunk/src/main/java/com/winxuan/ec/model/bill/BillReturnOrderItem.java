package com.winxuan.ec.model.bill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;

/**
 * 退货单项
 * @author heyadong
 *
 */
@Entity
@Table(name="bill_returnorder_item")
public class BillReturnOrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="returnorderitem")
	private ReturnOrderItem returnOrderItem;
	
	@ManyToOne
	@JoinColumn(name="returnorder")
	private ReturnOrder returnOrder;
	
	@ManyToOne
	@JoinColumn(name="orderitem")
	private OrderItem orderItem;
	
	@ManyToOne
	@JoinColumn(name="status")
	private Code status;
	
	@Column
	private int realquantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public ReturnOrderItem getReturnOrderItem() {
		return returnOrderItem;
	}

	public void setReturnOrderItem(ReturnOrderItem returnOrderItem) {
		this.returnOrderItem = returnOrderItem;
	}

	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public int getRealquantity() {
		return realquantity;
	}

	public void setRealquantity(int realquantity) {
		this.realquantity = realquantity;
	}
	
}
