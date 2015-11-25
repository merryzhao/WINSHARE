/*
 * @(#)GridForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.report;



/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-8-24
 */
public class ConditionForm {
	private Long id;
	private Long gridId;
	private String name;
	private String parameterName;
	private short type;
	private short control;
	private Long enumerationId;
	private boolean allowNull;
	private String defaultValue;
	private int index;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGridId() {
		return gridId;
	}
	public void setGridId(Long gridId) {
		this.gridId = gridId;
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
	public short getControl() {
		return control;
	}
	public void setControl(short control) {
		this.control = control;
	}
	public Long getEnumerationId() {
		return enumerationId;
	}
	public void setEnumerationId(Long enumerationId) {
		this.enumerationId = enumerationId;
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
	
}
