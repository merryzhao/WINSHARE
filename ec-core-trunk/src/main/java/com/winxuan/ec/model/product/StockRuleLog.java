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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.user.Employee;

/**
 * 库存日志管理
 * 
 * @author liou
 * @version 2013-9-4:下午2:06:35
 * 
 */
@Entity
@Table(name = "stock_rule_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockRuleLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stockrule")
	private StockRule stockRule;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="opreator")
	private Employee opreator;

	@Column(name = "updatetime")
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StockRule getStockRule() {
		return stockRule;
	}

	public void setStockRule(StockRule stockRule) {
		this.stockRule = stockRule;
	}

	public Employee getOpreator() {
		return opreator;
	}

	public void setOpreator(Employee opreator) {
		this.opreator = opreator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
