package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.promotion.Promotion;

/**
 * 订单的促销活动
 * @author yuhu
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "order_promotion")
public class OrderPromotion implements Serializable{

	private static final long serialVersionUID = -3497647095285401519L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_promotion")
	private Promotion promotion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;
	
	@Column(name = "savemoney")
	private BigDecimal saveMoney;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_presentbatch")
	private PresentBatch presentBatch;
	
	@Column(name = "presentnum")
	private Integer presentNum;
	
	public OrderPromotion() {
		super();
	}

	public OrderPromotion(Promotion promotion) {
		super();
		this.promotion = promotion;
		this.type = promotion.getType();
	}

	public OrderPromotion(Promotion promotion,
			BigDecimal saveMoney, PresentBatch presentBatch, Integer presentNum) {
		super();
		this.promotion = promotion;
		this.type = promotion.getType();
		this.saveMoney = saveMoney;
		this.presentBatch = presentBatch;
		this.presentNum = presentNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public BigDecimal getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(BigDecimal saveMoney) {
		this.saveMoney = saveMoney;
	}

	public PresentBatch getPresentBatch() {
		return presentBatch;
	}

	public void setPresentBatch(PresentBatch presentBatch) {
		this.presentBatch = presentBatch;
	}

	public Integer getPresentNum() {
		return presentNum;
	}

	public void setPresentNum(Integer presentNum) {
		this.presentNum = presentNum;
	}

}
