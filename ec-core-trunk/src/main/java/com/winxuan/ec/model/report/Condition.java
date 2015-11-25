/*
 * @(#)Condition.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-8-15
 */
@Entity
@Table(name = "report_grid_condition")
public class Condition implements com.winxuan.framework.report.Condition{

	/**
	 * 文本框
	 */
	public static final short CONTROL_TEXT=1;
	
	/**
	 * 文本区域
	 */
	public static final short CONTROL_TEXT_AREA=2;
	
	/**
	 * 下拉列表
	 */
	public static final short CONTROL_SELECT=3;
	
	/**
	 * 单选按钮
	 */
	public static final short CONTROL_RADIO=6;
	
	/**
	 * 复选框
	 */
	public static final short CONTROL_CHECK_BOX=7;
	
	/**
	 * 日期选择器
	 */
	public static final short CONTROL_DATE_PICKER=9;
	/**
	 * 日期时间选择器
	 */
	public static final short CONTROL_DATE_TIME_PICKER=8;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grid")
	private Grid grid;
	
	@Column
	private String name;

	@Column(name = "parametername")
	private String parameterName;

	@Column
	private short type;
	
	@Column
	private short control;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enumeration")
	private Enumeration enumeration;

	@Column(name = "allownull")
	private boolean allowNull;

	@Column(name = "defaultvalue")
	private String defaultValue;

	@Column(name = "_index")
	private int index;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public short getControl() {
		return control;
	}

	public void setControl(short control) {
		this.control = control;
	}

	public Enumeration getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(Enumeration enumeration) {
		this.enumeration = enumeration;
	}
	
}
