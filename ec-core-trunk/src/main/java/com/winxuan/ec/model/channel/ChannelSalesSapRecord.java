package com.winxuan.ec.model.channel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.Employee;

/**
 * 渠道销售,SAP下传操作记录
 * @author heyadong
 *
 */
@Entity
@Table(name="sales_sap_record")
public class ChannelSalesSapRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="operator")
	private Employee operator;
	
	@Column
	private Date updatetime;
	
	//输入的下传金额
	@Column
	private BigDecimal listprice;
	//实际 下传销售金额
	@Column 
	private BigDecimal sales;
	//实际下传退货金额
	@Column
	private BigDecimal refund;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getOperator() {
		return operator;
	}

	public void setOperator(Employee operator) {
		this.operator = operator;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public BigDecimal getListprice() {
		return listprice;
	}

	public void setListprice(BigDecimal listprice) {
		this.listprice = listprice;
	}
	
	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}
}
