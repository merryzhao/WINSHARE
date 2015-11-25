package com.winxuan.ec.model.report;

import java.util.Map;

import com.winxuan.framework.pagination.Pagination;

/**
 * @author yuhu
 * @version 1.0,2014-7-5上午11:44:32
 */
public class ReportAsyncParamDto {

	private Long gridId;
	
	private Map<String, String[]> parameters;
	
	private Column orderColumn;
	
	private boolean isAsc;
	
	private Pagination pagination;
	
	private Long employeeId;
	
	public ReportAsyncParamDto() {
		super();
	}


	public ReportAsyncParamDto( Long gridId,
			Map<String, String[]> parameters, Column orderColumn,
			boolean isAsc, Pagination pagination, Long employeeId) {
		super();
		this.gridId = gridId;
		this.parameters = parameters;
		this.orderColumn = orderColumn;
		this.isAsc = isAsc;
		this.pagination = pagination;
		this.employeeId = employeeId;
	}

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public Column getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(Column orderColumn) {
		this.orderColumn = orderColumn;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}


	public Long getGridId() {
		return gridId;
	}


	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}


	public Long getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

}
