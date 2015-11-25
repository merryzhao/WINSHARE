package com.winxuan.ec.model.report;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.user.Employee;

/**
 * @author yuhu
 * @version 1.0,2014-7-4下午01:05:54
 */
@Entity
@Table(name = "report_log")
public class ReportLog implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grid")
	private Grid grid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private Employee employee;
	
	@Column
	private Date createTime;
	
	@Column
	private String path;
	
	@Column
	private String errorMessage;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reportLog", targetEntity = ReportLogcolumn.class)
	private Set<ReportLogcolumn> logcolumnList;
	
	public ReportLog() {
		super();
	}

	public ReportLog(Grid grid, Employee employee, Date createTime) {
		super();
		this.grid = grid;
		this.employee = employee;
		this.createTime = createTime;
	}
	
	public void addColumn(ReportLogcolumn column){
		if(CollectionUtils.isEmpty(logcolumnList)){
			logcolumnList = new HashSet<ReportLogcolumn>();
		}
		column.setReportLog(this);
		logcolumnList.add(column);
	}

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Set<ReportLogcolumn> getLogcolumnList() {
		return logcolumnList;
	}

	public void setLogcolumnList(Set<ReportLogcolumn> logcolumnList) {
		this.logcolumnList = logcolumnList;
	}
	
	/**
	 * 获取本次查询的查询条件
	 * @return
	 */
	public String getColumnString(){
		StringBuffer columnBuffer = new StringBuffer();
		columnBuffer.append("查询人：").append(employee.getName());
		if(!CollectionUtils.isEmpty(logcolumnList)){
			columnBuffer.append("&#10;").append("查询条件：");
			for(ReportLogcolumn column : logcolumnList){
				columnBuffer.append("&#10;").append(column.getColumnString());
			}
		}
		return columnBuffer.toString();
	}
	
}
