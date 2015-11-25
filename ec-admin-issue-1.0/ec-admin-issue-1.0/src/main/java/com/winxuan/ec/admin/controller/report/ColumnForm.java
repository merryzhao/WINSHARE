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
public class ColumnForm {
	private Long id;
	private Long gridId;
	private String name;
	private String value;
	private String width;
	private boolean order;
	private String ascSql;
	private String descSql;
	private int index;
	private boolean aggregated;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public String getAscSql() {
		return ascSql;
	}

	public void setAscSql(String ascSql) {
		this.ascSql = ascSql;
	}

	public String getDescSql() {
		return descSql;
	}

	public void setDescSql(String descSql) {
		this.descSql = descSql;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isAggregated() {
		return aggregated;
	}

	public void setAggregated(boolean aggregated) {
		this.aggregated = aggregated;
	}

	@Override
	public String toString() {
		return "ColumnForm [id=" + id + ", gridId=" + gridId + ", name=" + name
				+ ", value=" + value + ", width=" + width + ", order=" + order
				+ ", ascSql=" + ascSql + ", descSql=" + descSql + ", index="
				+ index + ", aggregated=" + aggregated + "]";
	}

}
