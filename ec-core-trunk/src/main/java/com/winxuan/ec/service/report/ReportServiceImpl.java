/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.service.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ColumnDao;
import com.winxuan.ec.dao.ConditionDao;
import com.winxuan.ec.dao.DataSourceDao;
import com.winxuan.ec.dao.EmployeeDao;
import com.winxuan.ec.dao.EnumerationDao;
import com.winxuan.ec.dao.GridDao;
import com.winxuan.ec.model.employee.EmployeeAttach;
import com.winxuan.ec.model.report.Column;
import com.winxuan.ec.model.report.Condition;
import com.winxuan.ec.model.report.DataSource;
import com.winxuan.ec.model.report.EmployeeGridLog;
import com.winxuan.ec.model.report.Enumeration;
import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.model.report.MyGrid;
import com.winxuan.ec.model.report.ReportAsyncParamDto;
import com.winxuan.ec.model.report.ReportLog;
import com.winxuan.ec.model.report.ReportLogcolumn;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.support.util.FileType;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.report.GridCreator;
import com.winxuan.framework.report.Table;

/**
 * description
 * 
 * @author liyang
 * @version 1.0,2011-8-22
 */

@Service("reportService")
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {
	
	public static Map<String,Integer> reportCache = new HashMap<String,Integer>();
	private static final Log LOG = LogFactory.getLog(ReportServiceImpl.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	
	private static final int REPORT_PAGE_SIZE = 1000;

	@InjectDao
	private DataSourceDao dataSourceDao;

	@InjectDao
	private GridDao gridDao;

	@InjectDao
	private EnumerationDao enumerationDao;

	@InjectDao
	private ConditionDao conditionDao;

	@InjectDao
	private ColumnDao columnDao;
	
	@InjectDao
	private EmployeeDao employeeDao;

	@Autowired
	private AttachService attachService;
	

	public void save(Grid grid) {
		gridDao.save(grid);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Grid get(Long id) {
		return gridDao.get(id);
	}
    
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condition getCondition(Long id) {
		return conditionDao.get(id);
	}

	public void createCondition(Condition condition) {
		conditionDao.save(condition);
	}

	public void updateCondition(Condition condition) {
		conditionDao.update(condition);
	}

	public void deleteCondition(Condition condition) {
		conditionDao.delete(condition);
	}
   
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Column getColumn(Long id) {
		return columnDao.getColumn(id);
	}

	public void createColumn(Column column) {
		columnDao.save(column);
	}

	public void updateColumn(Column column) {
		columnDao.update(column);
	}

	public void deleteColumn(Column column) {
		columnDao.delete(column);
	}

	public void createDataSource(DataSource dataSource) {
		dataSourceDao.save(dataSource);
	}

	public void updateDataSource(DataSource dataSource) {
		dataSourceDao.update(dataSource);
	}

	public void deleteDataSource(Long id) {
	}

	public void deleteEnumeration(Enumeration enumeration) {
		enumerationDao.delete(enumeration);
	}

	public void updateEnumeration(Enumeration enumeration) {
		enumerationDao.update(enumeration);
	}

	public void createEnumeration(Enumeration enumeration) {
		enumerationDao.save(enumeration);
	}

	public void update(Grid grid) {
		gridDao.update(grid);
	}
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DataSource> findDataSourceOnlyAvailable() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("available", true);
		return dataSourceDao.find(parameters);
	}
    
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataSource getDataSourceById(Long dataSourceId) {
		return dataSourceDao.get(dataSourceId);
	}
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Enumeration> findEnumerationOnlyAvailable() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("available", true);
		return enumerationDao.find(parameters);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Enumeration getEnumerationById(Long enumerationId) {
		return enumerationDao.get(enumerationId);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Grid> findGrid() {
		return gridDao.findGrid();
	}

	@Override
	public void save(EmployeeGridLog employeeGridLog) {
		gridDao.save(employeeGridLog);
	}

	@Override
	public List<ReportLog> findReportLog(Grid grid,Employee employee,Pagination pagination) {
		return gridDao.findReportLog(grid,employee, pagination);
	}

	@Async
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public void createGridAsync(ReportAsyncParamDto param) {
		Pagination pagination = param.getPagination();
		Employee employee = employeeDao.get(param.getEmployeeId());
		Grid grid = gridDao.get(param.getGridId());
		
		ReportLog log = new ReportLog(grid, employee, new Date());
		for(Entry<String, String[]> entry : param.getParameters().entrySet()){
			StringBuffer values = new StringBuffer();
			for(String value:entry.getValue()){
				values.append(value).append(" ");
			}
			log.addColumn(new ReportLogcolumn(entry.getKey(),values.toString()));
		}
		try {
			List<List<String>> data = new ArrayList<List<String>>();
			List<String> head = null;
			int page = (int)Math.floor(pagination.getPageSize()/REPORT_PAGE_SIZE);
			if(page>1){
				pagination.setPageSize(REPORT_PAGE_SIZE);
			}else{
				page = 1;
			}
			for(int i=0;page>i;i++){
				Table table = GridCreator.create(grid, param.getParameters(), param.getOrderColumn(),
						param.isAsc(), pagination);
				if(!CollectionUtils.isEmpty(table.getBodyList())){
					data.addAll(table.getBodyList());
				}else{
					log.setErrorMessage("导出数据为空");
				}
				if(head == null){
					head = table.getHeaderList();
				}
				pagination.setCurrentPage(pagination.getCurrentPage()+1);
			}
			if(!CollectionUtils.isEmpty(data)){
				EmployeeAttach employeeAttach = new EmployeeAttach();
				employeeAttach.setEmployee(employee);
				employeeAttach.setName(employee.getName()+"report"+grid.getId()+DATE_FORMAT.format(new Date()));
				employeeAttach.setType(FileType.XLS.toString());
				String path = attachService.addAttach(employeeAttach, head, data);
				log.setPath(path);
			}
			EmployeeGridLog employeeGridLog = new EmployeeGridLog(grid, employee, new Date());
			gridDao.save(employeeGridLog);
		} catch (Exception e) {
			if(e.getMessage().startsWith("Already closed")){
				createGridAsync(param);
			}else{
				LOG.error("后台导出报表错误",e);
				log.setErrorMessage("系统异常");
			}
		}finally{
			reportCache.remove(employee.getId()+":"+grid.getId());
		}
		gridDao.save(log);
	}
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MyGrid> findMyGridList(Employee employee) {		
		return gridDao.findMyGrid(employee.getId());
	}

	@Override
	public void addMyGrid(MyGrid myGrid) {
		gridDao.addMyGrid(myGrid);
	}

	@Override
	public void rmoveMyGrid(Long id) {
		gridDao.rmoveMyGrid(id);
	}
	
}
