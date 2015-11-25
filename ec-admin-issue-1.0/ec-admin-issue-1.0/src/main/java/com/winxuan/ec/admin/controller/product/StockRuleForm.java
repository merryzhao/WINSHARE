package com.winxuan.ec.admin.controller.product;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.StockRule;
import com.winxuan.ec.model.product.StockRuleDc;
import com.winxuan.ec.model.product.StockRuleLog;
import com.winxuan.ec.model.user.Employee;

/**
 * @author liou
 * @version 2013-9-5:下午5:43:39
 * 
 */
public class StockRuleForm {

	private List<StockRuleDc> stockRuleDc;
	
	private List<StockRuleDc> stRuleDcs;

	private Long id;

	private Long channel;

	private Long supply;

	private int stockType;
	
	private int percent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<StockRuleDc> getStockRuleDc() {
		return stockRuleDc;
	}

	public void setStockRuleDc(List<StockRuleDc> stockRuleDc) {
		this.stockRuleDc = stockRuleDc;
	}

	public List<StockRuleDc> getStRuleDcs() {
		return stRuleDcs;
	}

	public void setStRuleDcs(List<StockRuleDc> stRuleDcs) {
		this.stRuleDcs = stRuleDcs;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public Long getSupply() {
		return supply;
	}

	public void setSupply(Long supply) {
		this.supply = supply;
	}

	public int getStockType() {
		return stockType;
	}

	public void setStockType(int stockType) {
		this.stockType = stockType;
	}
	
	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public StockRule setUpdateStockRule(Employee employee) {
		StockRule stockRule = this.setStockRule(employee);
		Set<StockRuleDc> dcs = new HashSet<StockRuleDc>();
		for (StockRuleDc ruleDc : stockRuleDc) {
			if (ruleDc.getDc()!=null) {
				ruleDc.setStockRule(stockRule);
				dcs.add(ruleDc);
			}
		}
		stockRule.setId(id);
		stockRule.setStockRuleDc(dcs);
		return stockRule;
	}

	public StockRule setSaveStockRule(Employee employee) {
		StockRule stockRule = this.setStockRule(employee);
		if(stockRuleDc!=null&&!stockRuleDc.isEmpty()){
			Set<StockRuleDc> dcs = new HashSet<StockRuleDc>();
			for (StockRuleDc ruleDc : stockRuleDc) {
				ruleDc.setStockRule(stockRule);
				dcs.add(ruleDc);
			}
			stockRule.setStockRuleDc(dcs);
		}
		return stockRule;
	}

	public StockRule setStockRule(Employee employee) {
		StockRule stockRule = new StockRule();
		stockRule.setChannel(new Channel(channel));
		stockRule.setStockType(stockType);
		stockRule.setSupplyType(new Code(supply));
		stockRule.setPercent(percent);
		 Set<StockRuleLog> stockRuleLog = new HashSet<StockRuleLog>();
		 StockRuleLog ruleLog = new StockRuleLog();
		 ruleLog.setStockRule(stockRule);
		 ruleLog.setOpreator(employee);
		 ruleLog.setUpdateTime(new Date());
		 stockRuleLog.add(ruleLog);
		 stockRule.setStockRuleLog(stockRuleLog);
		return stockRule;
	}

}
