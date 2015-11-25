package com.winxuan.ec.model.product;

import java.io.Serializable;
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

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;

/**
 * 库存管理
 * 
 * @author liou
 * @version 2013-9-4:下午2:05:20
 * 
 */
@Entity
@Table(name = "stock_rule")
public class StockRule implements Serializable {
	/**
	 * 实物库存
	 */
	public static final int STOCK_TYPE_ACTUAL = 1;

	/**
	 * 虚拟库存
	 */
	public static final int STOCK_TYPE_VIRTUAL = 2;

	/**
	 * 
	 */
	private static final long serialVersionUID = -950355891619150175L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplytype")
	private Code supplyType;

	@Column(name = "stocktype")
	private int stockType;

	/**
	 * 系数
	 */
	@Column
	private int percent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stockRule", targetEntity = StockRuleDc.class)
	@OrderBy("dc")
	private Set<StockRuleDc> stockRuleDc;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stockRule", targetEntity = StockRuleLog.class)
	@OrderBy("updateTime desc")
	private Set<StockRuleLog> stockRuleLog;

	public StockRule() {
		super();
	}

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

	public Code getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(Code supplyType) {
		this.supplyType = supplyType;
	}

	public int getStockType() {
		return stockType;
	}

	public void setStockType(int stockType) {
		this.stockType = stockType;
	}

	public Set<StockRuleDc> getStockRuleDc() {
		return stockRuleDc;
	}

	public void setStockRuleDc(Set<StockRuleDc> stockRuleDc) {
		this.stockRuleDc = stockRuleDc;
	}

	public Set<StockRuleLog> getStockRuleLog() {
		return stockRuleLog;
	}

	public void setStockRuleLog(Set<StockRuleLog> stockRuleLog) {
		this.stockRuleLog = stockRuleLog;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	public StockRuleDc getStockRuleDc(long dc){
		if(CollectionUtils.isNotEmpty(this.stockRuleDc)){
			for(StockRuleDc srd : this.stockRuleDc){
				if(dc == srd.getDc().getId().longValue()){
					return srd;
				}
			}
		}
		return null;
	}

}
