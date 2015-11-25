/*
 * @(#)Column.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.report;

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
@Table(name = "report_grid_column")
public class Column implements com.winxuan.framework.report.Column{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grid")
	private Grid grid;

	@javax.persistence.Column
	private String name;

	@javax.persistence.Column(name="_value")
	private String value;

	@javax.persistence.Column
	private String width;

	@javax.persistence.Column(name = "_order")
	private boolean order;

	@javax.persistence.Column(name = "ascsql")
	private String ascSql;

	@javax.persistence.Column(name = "descsql")
	private String descSql;

	@javax.persistence.Column(name = "_index")
	private int index;
	
	@javax.persistence.Column
	private boolean aggregated;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCleanValue(){
		return value.replaceAll("[\\${}]*", "");
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
	
}
