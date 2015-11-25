package com.winxuan.ec.task.model.ebook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 中图法分类
 * 
 * @author luosh
 * 
 */
@Entity
@Table(name = "T_IN_FRANCE_CATEGORY")
public class InFranceCategory  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	
	/** 代码 */
	private String code;
	/** 中文名 */
	private String name;
	
	/**
	 * 0:未删除；1：删除
	 */
	private Integer deleteFlag;
	
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	/** 代码 */
	public String getCode() {
		return code;
	}
	/** 代码 */
	public void setCode(String code) {
		this.code = code;
	}
	/** 中文名 */
	public String getName() {
		return name;
	}
	/** 中文名 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 0:未删除；1：删除
	 * 
	 */
	@Column(name = "DELETE_FLAG")
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * 0:未删除；1：删除
	 * 
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
