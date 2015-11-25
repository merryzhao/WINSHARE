/**
 * 
 */
package com.winxuan.ec.model.promotion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.present.PresentBatch;

/**
 * @author zhousl
 *
 * 2013-10-21
 */

@Entity
@Table(name="promotion_register_rule")
public class PromotionRegisterRule implements Serializable{

	private static final long serialVersionUID = 661253974386728450L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="_promotion")
	private Promotion promotion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="_presentbatch")
	private PresentBatch presentBatch;
	
	@Column(name="presentnum")
	private Integer presentNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
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
