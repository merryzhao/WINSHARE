/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.service.report;

import java.util.List;

import com.winxuan.ec.model.report.Column;
import com.winxuan.ec.model.report.Condition;
import com.winxuan.ec.model.report.DataSource;
import com.winxuan.ec.model.report.EmployeeGridLog;
import com.winxuan.ec.model.report.Enumeration;
import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.model.report.MyGrid;
import com.winxuan.ec.model.report.ReportAsyncParamDto;
import com.winxuan.ec.model.report.ReportLog;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liyang
 * @version 1.0,2011-8-22
 */

public interface ReportService {

	/**
	 * 保存报表
	 * 
	 * @param Grid
	 * @return
	 * @return
	 */
	void save(Grid grid);
	
	/**
	 * 保存报表操作日志
	 * @param employeeGridLog
	 */
	void save(EmployeeGridLog employeeGridLog);

	/**
	 * 获取报表
	 * 
	 * @param Grid
	 * @return
	 * @return
	 */
	Grid get(Long id);
	/**
	 * 获取全部报表
	 * 
	 * 
	 *
	 */
	List<Grid> findGrid();
	/**
	 * 更新报表
	 * 
	 * @param Grid
	 * @return
	 * @return
	 */
	void update(Grid grid);

	/**
	 * 获得搜索条件
	 * 
	 * @param id
	 *            搜索条件id
	 * @return codition
	 */
	Condition getCondition(Long id);

	/**
	 * 添加搜索条件
	 * 
	 * @param Condition
	 * @return
	 */
	void createCondition(Condition condition);

	/**
	 * 更新搜索条件
	 * 
	 * @param Condition
	 * @return
	 */
	void updateCondition(Condition condition);

	/**
	 * 删除搜索条件
	 * 
	 * @param Condition
	 * @return
	 */
	void deleteCondition(Condition condition);

	/**
	 * 获得显示条件
	 * 
	 * @param id
	 *            显示条件id
	 * @return Column
	 */
	Column getColumn(Long id);

	/**
	 * 添加显示条件
	 * 
	 * @param Condition
	 * @return
	 */
	void createColumn(Column column);

	/**
	 * 更新显示条件
	 * 
	 * @param Condition
	 * @return
	 */
	void updateColumn(Column column);

	/**
	 * 删除显示条件
	 * 
	 * @param Condition
	 * @return
	 */
	void deleteColumn(Column column);

	/**
	 * 创建数据源
	 * 
	 * @param datasource
	 * @return
	 */
	void createDataSource(DataSource dataSource);

	/**
	 * 更新数据源
	 * 
	 * @param datasource
	 * @return
	 */
	void updateDataSource(DataSource dataSource);

	/**
	 * 删除数据源
	 * 
	 * @param datasource
	 * @return
	 */
	void deleteDataSource(Long id);

	/**
	 * 删除枚举
	 * 
	 * @param datasource
	 * @return
	 */
	void deleteEnumeration(Enumeration enumeration);

	/**
	 * 更新枚举
	 * 
	 * @param datasource
	 * @return
	 */
	void updateEnumeration(Enumeration enumeration);

	/**
	 * 创建枚举
	 * 
	 * @param datasource
	 * @return
	 */
	void createEnumeration(Enumeration enumeration);

	/**
	 * 查找数据源列表
	 * 
	 * @return
	 */
	List<DataSource> findDataSourceOnlyAvailable();

	/**
	 * 根据id得到数据源
	 * @param dataSourceId
	 * @return
	 */
	DataSource getDataSourceById(Long dataSourceId);
	
	/**
	 * 查找枚举列表
	 * 
	 * @return
	 */
	List<Enumeration> findEnumerationOnlyAvailable();

	/**
	 * 根据id得到枚举
	 * @param dataSourceId
	 * @return
	 */
	Enumeration getEnumerationById(Long enumerationId);
	
	/**
	 * 根据报表检索查询日志
	 * @return
	 */
	List<ReportLog> findReportLog(Grid grid,Employee employee,Pagination pagination);
	
	/**
	 * 异步导出报表
	 * @param param
	 */
	void createGridAsync(ReportAsyncParamDto param);
	
	/**
	 * 查询我的报表
	 * @param employee
	 * @return
	 */
	List<MyGrid> findMyGridList(Employee employee);
	
	/**
	 * 添加我的报表
	 * @param myGrid
	 */
	void addMyGrid(MyGrid myGrid);
	
	/**
	 * 移出我的报表
	 * @param id
	 */
	void rmoveMyGrid(Long id);
	
}
