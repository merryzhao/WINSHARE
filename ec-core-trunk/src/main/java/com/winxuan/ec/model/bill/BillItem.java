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

/**
 * 帐单项
 * @author heyadong
 *
 */
@Table(name="bill_item")
@Entity
public class BillItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name="bill")
	private Bill bill;
	@ManyToOne
	@JoinColumn(name="orderitem",insertable=false,updatable=false)
	private OrderItem orderItem;
	
	@ManyToOne
	@JoinColumn(name="orderitem",insertable=false,updatable=false)
	private BillItemStatistics billItemStatistics;
	
	//结算数量
	@Column(name="settlementquantity")
	private Integer settlementQuantity;
	//退货数量(退款)
	@Column(name="refoundquantity")
	private Integer refoundQuantity;
	//当前历史结算数量
	@Column(name="historysettlement")
	private Integer historySettlement;
	//当前历史退货数量
	@Column(name="historyrefound")
	private Integer historyRefound; 
	

	@ManyToOne
	@JoinColumn(name="status")
	private Code status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
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
	
	public Code getStatus() {
		return status;
	}
	public void setStatus(Code status) {
		this.status = status;
	}
	public BillItemStatistics getBillItemStatistics() {
		return billItemStatistics;
	}
	public void setBillItemStatistics(BillItemStatistics billItemStatistics) {
		this.billItemStatistics = billItemStatistics;
	}
	
	public Integer getHistorySettlement() {
		return historySettlement;
	}
	public void setHistorySettlement(Integer historySettlement) {
		this.historySettlement = historySettlement;
	}
	public Integer getHistoryRefound() {
		return historyRefound;
	}
	public void setHistoryRefound(Integer historyRefound) {
		this.historyRefound = historyRefound;
	}
	
}
