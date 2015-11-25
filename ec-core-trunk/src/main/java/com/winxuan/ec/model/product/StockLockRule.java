package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;

/**
 * 库存锁定
 * 
 * @author liou 2014-10-14上午10:20:30
 */
@Entity
@Table(name = "stock_lock_rule")
public class StockLockRule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2638776965411144678L;

	// 固定数量锁定
	public static final short LOCK_TYPE_QUANTITY = 1;

	// 系数锁定
	public static final short LOCK_TYPE_FACTOR = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;

	@Column(name = "productsale")
	private Long productSale;

	/**
	 * 下单用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	/**
	 * 锁定库存
	 */
	@Column(name = "lockstock")
	private Integer lockStock;

	/**
	 * 锁定系数
	 */
	@Column(name = "lockfactor")
	private Integer lockFactor;

	/**
	 * 真实锁定
	 */
	@Column(name = "reallock")
	private int realLock;

	/**
	 * 销量
	 */
	@Column
	private int sales;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "begintime")
	private Date beginTime;

	@Column(name = "endtime")
	private Date endTime;

	@Column(name = "updatetime")
	private Date updateTime;

	@Column(name = "status")
	private Boolean status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stockLockRule", targetEntity = StockLockRuleLog.class)
	@OrderBy("id desc")
	private Set<StockLockRuleLog> lockRuleLogs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Long getProductSale() {
		return productSale;
	}

	public void setProductSale(Long productSale) {
		this.productSale = productSale;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public int getRealLock() {
		return realLock;
	}

	public void setRealLock(int realLock) {
		this.realLock = realLock;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<StockLockRuleLog> getLockRuleLogs() {
		return lockRuleLogs;
	}

	public void setLockRuleLogs(Set<StockLockRuleLog> lockRuleLogs) {
		this.lockRuleLogs = lockRuleLogs;
	}

	public short getLockType() {
		return null != this.lockStock ? LOCK_TYPE_QUANTITY : LOCK_TYPE_FACTOR;
	}

	/**
	 * 日志
	 * 
	 * @param employee
	 * @param stockLockRule
	 */
	public void getStockLockRuleLog(Employee employee) {
		Set<StockLockRuleLog> lockRuleLogs = new HashSet<StockLockRuleLog>();
		StockLockRuleLog lockRuleLog = new StockLockRuleLog();
		lockRuleLog.setBeginTime(beginTime);
		lockRuleLog.setEndTime(endTime);
		lockRuleLog.setLockFactor(lockFactor);
		lockRuleLog.setLockStock(lockStock);
		lockRuleLog.setRealLock(realLock);
		lockRuleLog.setSales(sales);
		lockRuleLog.setCreateTime(new Date());
		lockRuleLog.setStockLockRule(this);
		lockRuleLog.setOperator(employee);
		lockRuleLogs.add(lockRuleLog);
		setLockRuleLogs(lockRuleLogs);
	}
	
	public void getStockLockRule(StockLockRule stockLockRule){
		setBeginTime(stockLockRule.getBeginTime());
		setEndTime(stockLockRule.getEndTime());
		setLockStock(stockLockRule.getLockStock());
		setLockFactor(stockLockRule.getLockFactor());
		setUpdateTime(new Date());
		setStatus(true);
		if(stockLockRule.getEndTime().compareTo(new Date())<0){
			setStatus(false);
		}
		//setRealLock(stockLockRule.getRealLock());
	}
}
