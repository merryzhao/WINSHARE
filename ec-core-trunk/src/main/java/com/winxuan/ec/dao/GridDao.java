/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.report.EmployeeGridLog;
import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.model.report.MyGrid;
import com.winxuan.ec.model.report.ReportLog;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;
/**
 * description
 * @author liyang
 * @version 1.0,2011-8-22
 */

public interface GridDao {
	@Get
	Grid get(Long id);
	
	@Update
	void update(Grid grid);
	
	@Save
	void save(Grid grid);
	
	@Save
	void addMyGrid(MyGrid myGrid);
		
	@Save
	void save(EmployeeGridLog employeeGridLog);
	
	@Save
	void save(ReportLog log);
	
	@Query("from Grid g ")
	List<Grid> findGrid();
	
	@Query("from ReportLog r where r.grid=:grid and r.employee=:employee order by r.id desc")
	List<ReportLog> findReportLog(@Parameter("grid") Grid grid,@Parameter("employee") Employee employee,@Page Pagination pagination);
	
	@Query("from MyGrid r where  r.employee=? order by r.id desc")
	List<MyGrid> findMyGrid(Long userId);
	
	@Query(sqlQuery=true,value="DELETE FROM my_report_grid  WHERE id=?",executeUpdate=true)
	int rmoveMyGrid(Long id);
}
