package com.winxuan.ec.model.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.Employee;

/**
 * 锁定库存日志
 * @author liou
 * 2014-10-14上午10:22:09
 */
@Entity
@Table(name = "stock_lock_rule_log")
public class StockLockRuleLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stocklockrule")
	private StockLockRule stockLockRule;
	/**
	 * 锁定库存
	 */
	@Column(name="lockstock")
	private Integer lockStock;
	
	/**
	 * 锁系数
	 */
	@Column(name="lockfactor")
	private Integer lockFactor;
	/**
	 * 真实锁定
	 */
	@Column(name="reallock")
	private Integer realLock;
	/**
	 * 销量
	 */
	@Column(name="sales")
	private Integer sales;
	
	@Column(name="begintime")
	private Date beginTime;
	
	@Column(name="endtime")
	private Date endTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private Employee operator;
	
	@Column(name="createtime")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StockLockRule getStockLockRule() {
		return stockLockRule;
	}

	public void setStockLockRule(StockLockRule stockLockRule) {
		this.stockLockRule = stockLockRule;
	}

	public Integer getLockStock() {
		return lockStock;
	}

	public void setLockStock(Integer lockStock) {
		this.lockStock = lockStock;
	}

	public Integer getLockFactor() {
		return lockFactor;
	}

	public void setLockFactor(Integer lockFactor) {
		this.lockFactor = lockFactor;
	}

	public Integer getRealLock() {
		return realLock;
	}

	public void setRealLock(Integer realLock) {
		this.realLock = realLock;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Employee getOperator() {
		return operator;
	}

	public void setOperator(Employee operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
