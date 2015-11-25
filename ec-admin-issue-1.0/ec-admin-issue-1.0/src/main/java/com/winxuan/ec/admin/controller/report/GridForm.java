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
public class GridForm {
	private Long id;
	private String name;
	private String letter;
	private String title;
	private Long dataSource;
	private boolean paged;
	private int pageSize;
	private boolean exported;
	private int exportSize;
	private String mainSql;
	private String orderSql;
	private String aggregateSql;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getDataSource() {
		return dataSource;
	}
	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}
	public boolean isPaged() {
		return paged;
	}
	public void setPaged(boolean paged) {
		this.paged = paged;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isExported() {
		return exported;
	}
	public void setExported(boolean exported) {
		this.exported = exported;
	}
	public int getExportSize() {
		return exportSize;
	}
	public void setExportSize(int exportSize) {
		this.exportSize = exportSize;
	}
	public String getMainSql() {
		if(mainSql==null){
		return "";
		}else{
		return mainSql;
		}
	}
	public void setMainSql(String mainSql) {
		this.mainSql = mainSql;
	}
	public String getOrderSql() {
		if(orderSql==null){
		return "";
		}else{
		return orderSql;
		}
	}
	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}
	public String getAggregateSql() {
		if(aggregateSql==null){
		return "";
		}else{
		return aggregateSql;
		}
	}
	public void setAggregateSql(String aggregateSql) {
		this.aggregateSql = aggregateSql;
	}

}
