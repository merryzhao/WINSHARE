package com.winxuan.ec.model.bill;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.user.Employee;


/**
 * 订单项日志
 * @author heyadong
 *
 */
@Entity
@Table(name="bill_item_log")
public class BillItemLog {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="bill")
	private Bill bill;
	@Column(name="settlementquantity")
	private int settlementQuantity;
	@Column(name="refoundquantity")
	private int refoundQuantity;
	
	

	@ManyToOne
	@JoinColumn(name="orderitem")
	private OrderItem orderItem;
	@Column
	private String log;
	
	@Column
	private Date updatetime;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	
	
	public BillItemLog(){}
	public BillItemLog(Bill bill, OrderItem item, Employee employee,Date date){
		this.bill = bill;
		this.orderItem = item;
		this.employee = employee;
		this.updatetime = date;
	}
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

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getSettlementQuantity() {
		return settlementQuantity;
	}
	public void setSettlementQuantity(int settlementQuantity) {
		this.settlementQuantity = settlementQuantity;
	}
	public int getRefoundQuantity() {
		return refoundQuantity;
	}
	public void setRefoundQuantity(int refoundQuantity) {
		this.refoundQuantity = refoundQuantity;
	}
}
