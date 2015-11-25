package com.winxuan.ec.model.replenishment;

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

import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.code.Code;

/**
 * 智能补货周期管理
 * @author yangxinyi
 *
 */
@Entity
@Table(name = "mr_cycle")
public class MrCycle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;
	
	@ManyToOne
	@JoinColumn(name="mccategory")
	private McCategory mcCategory;
	
	//销售周期
	@Column(name = "salecycle")
	private Integer saleCycle;
	
	//补货周期
	@Column(name = "replenishmentcycle")
	private Integer replenishmentCycle;
	
	//在途周期
	@Column(name = "transitcycle")
	private Integer transitCycle;
	
	@Column(name = "createtime")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public McCategory getMcCategory() {
		return mcCategory;
	}

	public void setMcCategory(McCategory mcCategory) {
		this.mcCategory = mcCategory;
	}

	public Integer getSaleCycle() {
		return saleCycle;
	}

	public void setSaleCycle(Integer saleCycle) {
		this.saleCycle = saleCycle;
	}

	public Integer getReplenishmentCycle() {
		return replenishmentCycle;
	}

	public void setReplenishmentCycle(Integer replenishmentCycle) {
		this.replenishmentCycle = replenishmentCycle;
	}

	public Integer getTransitCycle() {
		return transitCycle;
	}

	public void setTransitCycle(Integer transitCycle) {
		this.transitCycle = transitCycle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}