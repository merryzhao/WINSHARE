package com.winxuan.ec.model.union;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * 联盟佣金
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
@Table(name = "union_commission")
@Entity
public class UnionCommission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518370929776575643L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_union")
	private Union union;
	
	@Column
	private String time;
	
	@Column(name = "effivemoney")
	private BigDecimal effiveMoney;
	
	@Column
	private BigDecimal commission;
	
	@Column(name = "ispay")
	private boolean isPay;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "unionCommission", targetEntity = UnionCommissionLog.class)
	@OrderBy("id")
    private Set<UnionCommissionLog> unionCommissionLogList;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getEffiveMoney() {
		return effiveMoney;
	}

	public void setEffiveMoney(BigDecimal effiveMoney) {
		this.effiveMoney = effiveMoney;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public boolean isPay() {
		return isPay;
	}

	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	public Set<UnionCommissionLog> getUnionCommissionLogList() {
		return unionCommissionLogList;
	}

	public void setUnionCommissionLogList(
			Set<UnionCommissionLog> unionCommissionLogList) {
		this.unionCommissionLogList = unionCommissionLogList;
	}

	public void addUnionCommissionLog(UnionCommissionLog unionCommissionLog){
		if (unionCommissionLogList == null) {
			unionCommissionLogList = new HashSet<UnionCommissionLog>();
		}
		unionCommissionLog.setUnionCommission(this);
		unionCommissionLogList.add(unionCommissionLog);
	}
	
}
