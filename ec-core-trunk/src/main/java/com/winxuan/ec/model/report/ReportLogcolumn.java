package com.winxuan.ec.model.report;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
 * @author yuhu
 * @version 1.0,2014-7-4下午01:52:28
 */
@Entity
@Table(name = "report_logcolumn")
public class ReportLogcolumn implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "reportlog")
	private ReportLog reportLog;
	
	@Column(name="columnname")
	private String columnName;
	
	@Column(name="columnvalue")
	private String columnValue;

	public ReportLogcolumn() {
		super();
	}

	public ReportLogcolumn(String columnName, String columnValue) {
		super();
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReportLog getReportLog() {
		return reportLog;
	}

	public void setReportLog(ReportLog reportLog) {
		this.reportLog = reportLog;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	
	public String getColumnString(){
		return columnName+":"+columnValue;
	}
	
}
