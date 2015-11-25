package com.winxuan.ec.task.model.ebook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 中图法分类与图书分类的对应关系
 * @author luosh
 *
 */
@Entity
@Table(name = "T_IN_FRANCE_BOOK_CATEGORY")
public class InFranceBookCategory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	
	/**
	 * 0:有效；1：无效
	 */
	private Integer isActive;

	/** 图书分类 */
	private Long categoryId;
	/**
	 * 中图法分类
	 */
	private Long inFranceCategoryId;
	
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	/**
	 * 0:有效；1：无效
	 */
	@Column(name = "IS_ACTIVE")
	public Integer getIsActive() {
		return isActive;
	}

	/**
	 * 0:有效；1：无效
	 */
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	/** 图书分类 */
	@Column(name = "CATEGORY_ID")
	public Long getCategoryId() {
		return categoryId;
	}

	/** 图书分类 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 中图法分类
	 */
	@Column(name = "IN_FRANCE_CATEGORY_ID")
	public Long getInFranceCategoryId() {
		return inFranceCategoryId;
	}

	/**
	 * 中图法分类
	 */
	public void setInFranceCategoryId(Long inFranceCategoryId) {
		this.inFranceCategoryId = inFranceCategoryId;
	}
}
