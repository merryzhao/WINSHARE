/*
 * @(#)Grid.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import com.winxuan.framework.report.DataSourceAware;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-8-14
 */
@Entity
@Table(name = "report_grid")
public class Grid implements com.winxuan.framework.report.Grid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String letter;

	@Column
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "datasource")
	private DataSource dataSource;

	@Column
	private boolean paged;

	@Column(name = "pagesize")
	private int pageSize;

	@Column
	private boolean exported;

	@Column(name = "exportsize")
	private int exportSize;

	@Column(name = "mainsql")
	private String mainSql;

	@Column(name = "ordersql")
	private String orderSql;

	@Column(name = "aggregatesql")
	private String aggregateSql;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grid", targetEntity = com.winxuan.ec.model.report.Column.class)
	@OrderBy("index")
	private Set<com.winxuan.ec.model.report.Column> columnList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grid", targetEntity = Condition.class)
	@OrderBy("index")
	private Set<Condition> conditionList;

	@Transient
	private Long myGridId;
	
	public Long getMyGridId() {
		return myGridId;
	}

	public void setMyGridId(Long myGridId) {
		this.myGridId = myGridId;
	}

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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
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
		return mainSql;
	}

	public void setMainSql(String mainSql) {
		this.mainSql = mainSql;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getAggregateSql() {
		return aggregateSql;
	}

	public void setAggregateSql(String aggregateSql) {
		this.aggregateSql = aggregateSql;
	}

	public void setColumnList(Set<com.winxuan.ec.model.report.Column> columnList) {
		this.columnList = columnList;
	}

	public Set<Condition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(Set<Condition> conditionList) {
		this.conditionList = conditionList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = getPinYinHeadChar(letter);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Grid)) {
			return false;
		}
		Grid other = (Grid) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<com.winxuan.framework.report.Column> getColumnList() {
		return (Collection) columnList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<com.winxuan.framework.report.Condition> getConditonList() {
		return (Collection) conditionList;
	}

	public DataSourceAware getDataSourceAware() {
		return dataSource;
	}

	public String getPinYinHeadChar(String str) {
		String convert = "";
		char word = str.charAt(0);
		HanyuPinyinOutputFormat s = new HanyuPinyinOutputFormat();
		String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word, s);
		if (pinyinArray != null) {
			convert += pinyinArray[0].charAt(0);
		} else {
			convert += word;
		}
		return convert;
	}

	public com.winxuan.ec.model.report.Column getColumn(int index) {
		if (columnList == null || columnList.isEmpty() || index < 0
				|| index > columnList.size() - 1) {
			return null;
		}
		int iterateIndex = 0;
		for (com.winxuan.ec.model.report.Column column : columnList) {
			if (iterateIndex++ == index) {
				return column;
			}
		}
		return null;
	}

	public List<com.winxuan.ec.model.report.Column> getColumns() {
		if (columnList == null || columnList.isEmpty()) {
			return null;
		}
		List<com.winxuan.ec.model.report.Column> columns = new ArrayList<com.winxuan.ec.model.report.Column>();
		for (com.winxuan.ec.model.report.Column column : columnList) {
			columns.add(column);
		}
		return columns;
	}
}
