package com.winxuan.ec.model.returnorder;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;

/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-12 上午9:22:37  --!
 * @description:
 ********************************
 */
@Entity
@Table(name="returnorder_dc")
public class ReturnOrderDc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1564190352035677027L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name="returnorder")
	private ReturnOrder returnOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="targetdc")
	private Code targetDc;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="realdc")
	private Code targetRealDc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReturnOrder getRetrunOrder() {
		return returnOrder;
	}

	public void setRetrunOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

	public Code getTargetDc() {
		return targetDc;
	}

	public void setTargetDc(Code targetDc) {
		this.targetDc = targetDc;
	}

	public Code getTargetRealDc() {
		return targetRealDc;
	}

	public void setTargetRealDc(Code targetRealDc) {
		this.targetRealDc = targetRealDc;
	}

}
