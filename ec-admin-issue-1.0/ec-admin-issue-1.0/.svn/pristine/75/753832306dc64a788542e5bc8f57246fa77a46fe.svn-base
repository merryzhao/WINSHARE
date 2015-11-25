/*
 * @(#)GridController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
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
import com.winxuan.ec.service.report.ReportService;
import com.winxuan.ec.service.report.ReportServiceImpl;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.report.DatabaseUtils;
import com.winxuan.framework.report.GridCreator;
import com.winxuan.framework.report.Table;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-8-23 
 */
@Controller
@RequestMapping("/report/grid")
public class GridController {

	private static final short EIGHT = 8;

	@Autowired
	private ReportService reportService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toNew() throws SQLException {
		List<DataSource> dataSources = reportService
				.findDataSourceOnlyAvailable();
		ModelAndView mav = new ModelAndView("/report/edit_grid");
		mav.addObject("dataSources", dataSources);
		List<String> tables = null;
		try{
    		tables = DatabaseUtils.getTables(dataSources.get(0));
    		if (tables != null) {
    			mav.addObject("tables", tables);
    		}
		} catch(Exception e){
		    mav.addObject("flag", ControllerConstant.RESULT_SUCCESS);
		    return mav;
		}
		mav.addObject("flag", ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView toUpdate(@PathVariable("id") Long id)
			throws SQLException {
		Grid grid = reportService.get(id);
		List<DataSource> dataSources = reportService
				.findDataSourceOnlyAvailable();
		List<Enumeration> enumerations = reportService
				.findEnumerationOnlyAvailable();
		ModelAndView mav = new ModelAndView("/report/edit_grid");
		mav.addObject("grid", grid);
		mav.addObject("dataSources", dataSources);
		mav.addObject("enumerations", enumerations);

		DataSource dataSource = reportService.getDataSourceById(grid
				.getDataSource().getId());
		List<String> tables = null;
		tables = DatabaseUtils.getTables(dataSource);
		if (tables != null) {
			mav.addObject("tables", tables);

		}
		mav.addObject("dataSourceName", dataSource.getName());
		mav.addObject("flag", ControllerConstant.RESULT_PARAMETER_ERROR);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		List<Grid> list = reportService.findGrid();
		ModelAndView mav = new ModelAndView("/report/list_grid");
		mav.addObject("list", list);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid GridForm gridForm, BindingResult result) {
		// new
		Grid grid = new Grid();
		// 设置值
		grid.setName(gridForm.getName());
		grid.setLetter(gridForm.getName());
		grid.setTitle(gridForm.getName());
		grid.setPaged(gridForm.isPaged());
		grid.setExported(gridForm.isExported());
		grid.setPageSize(gridForm.getPageSize());
		grid.setExportSize(gridForm.getExportSize());
		grid.setMainSql(gridForm.getMainSql());
		grid.setOrderSql(gridForm.getOrderSql());
		grid.setAggregateSql(gridForm.getAggregateSql());
		// 数据源
		grid.setDataSource(reportService.getDataSourceById(gridForm
				.getDataSource()));
		// 保存
		reportService.save(grid);
		return new ModelAndView("redirect:/report/grid/list");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid GridForm gridForm, BindingResult result) {
		// 获取grid
		Grid grid = reportService.get(gridForm.getId());
		// 设置修改值
		grid.setName(gridForm.getName());
		grid.setLetter(gridForm.getName());
		// grid.setTitle(gridForm.getTitle());
		grid.setPaged(gridForm.isPaged());
		grid.setExported(gridForm.isExported());
		grid.setPageSize(gridForm.getPageSize());
		grid.setExportSize(gridForm.getExportSize());
		grid.setMainSql(gridForm.getMainSql());
		grid.setOrderSql(gridForm.getOrderSql());
		grid.setAggregateSql(gridForm.getAggregateSql());
		// 数据源
		grid.setDataSource(reportService.getDataSourceById(gridForm
				.getDataSource()));
		// 修改
		reportService.update(grid);
		return new ModelAndView("redirect:/report/grid/" + gridForm.getId());
	}

	@RequestMapping(value = "/listTable/{dataSourceId}", method = RequestMethod.GET)
	public ModelAndView listTables(
			@PathVariable("dataSourceId") Long dataSourceId)
			throws SQLException {
		ModelAndView mav = new ModelAndView("/report/tableTree");
		DataSource dataSource = reportService.getDataSourceById(dataSourceId);
		List<String> tables = null;
		tables = DatabaseUtils.getTables(dataSource);
		if (tables != null) {
			mav.addObject("dataSource", dataSource);
			mav.addObject("tables", tables);
		} else {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_INTERNAL_ERROR);
		}
		return mav;
	}

	@RequestMapping(value = "/showTable", method=RequestMethod.POST)
	public ModelAndView showTable(
			@RequestParam("gridId") Long id,
			@RequestParam(value = "orderColumnIndex", required = false, defaultValue = "-1") int orderColumnIndex,
			@RequestParam(value = "isAsc", required = false, defaultValue = "false") boolean isAsc,
			@MyInject Pagination pagination,
			@RequestParam(value = "format", required = false) String format,
			@MyInject Employee employee,
			HttpServletRequest request) throws SQLException {
	    Map<String, String> defaultParam = new HashMap<String, String>();
	    defaultParam.put("_user_id", employee.getId().toString());
	    
	    
		Grid grid = reportService.get(id);
		String[] isAsyncValues = request.getParameterValues("isAsync");
		boolean isAsync = isAsyncValues != null && isAsyncValues.length>0;
		
		ModelAndView mav = new ModelAndView("/report/showTable");
		if(ReportServiceImpl.reportCache.get(employee.getId()+":"+grid.getId()) != null){
			mav.addObject("isAsync", 1);
			return mav;
		}
		
		if(isAsync){
			pagination.setPageSize(Integer.parseInt(request.getParameter("size_end").trim()));
		} else if (grid.isExported() && ("xls".equals(format) || "csv".equals(format))) {
			pagination.setPageSize(grid.getExportSize());
		} else if (grid.isPaged()) {
			pagination.setPageSize(grid.getPageSize());
		} else {
			pagination = null;
		}
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		for (Condition condition : grid.getConditionList()) {
			String name = condition.getParameterName();
			String[] values = null;
			if (condition.getControl() == Condition.CONTROL_CHECK_BOX) {
				String[] requestValues = request.getParameterValues(name);
				if (requestValues != null && requestValues.length > 0) {
					values = requestValues;
				}
			} else {
				String requestValue = request.getParameter(name);
				if(StringUtils.isBlank(requestValue)){
				    requestValue = defaultParam.get(name);
				}
				if (!StringUtils.isBlank(requestValue)) {
					if(condition.getType() == Condition.TYPE_MULTIPLE_LINE_NUMBER || condition.getType() == Condition.TYPE_MULTIPLE_LINE_STRING){
						values = requestValue.split("\r\n");
					} else {
						values = new String[] { requestValue.trim() };
					}
				}
			}
			if (values != null) {
				parameters.put(name, values);
			}
		}
		Column orderColumn = grid.getColumn(orderColumnIndex);
		
		mav.addObject("gridId", grid.getId());
		mav.addObject("isAsc", isAsc);
		mav.addObject("orderColumnIndex", orderColumnIndex);
		mav.addObject("pagination", pagination);
		mav.addObject("grid", grid);
		mav.addObject("parameters", parameters);
		
		if(isAsync){
			ReportServiceImpl.reportCache.put(employee.getId()+":"+grid.getId(), 1);
			ReportAsyncParamDto param = new ReportAsyncParamDto(grid.getId(), parameters, orderColumn,
					isAsc, pagination,employee.getId());
			reportService.createGridAsync(param);
			mav.addObject("isAsync", 1);
			return mav;
		}
		Table table = GridCreator.create(grid, parameters, orderColumn,
				isAsc, pagination);
		//记录日志
		EmployeeGridLog employeeGridLog = new EmployeeGridLog(grid, employee, new Date());
		reportService.save(employeeGridLog);
		mav.addObject("table", table);
		if (grid.isExported() && table != null && table.getBody() != null
				&& table.getBody().length > 0) {
			int count = grid.isPaged() && pagination != null ? pagination
					.getCount() : table.getBody().length;
			int exportSize = grid.getExportSize();
			int exportPageCount = count % exportSize == 0 ? count / exportSize
					: count / exportSize + 1;
			int[][] exportPagination = new int[exportPageCount][2];
			for (int i = 0; i < exportPagination.length; i++) {
				exportPagination[i][0] = exportSize * i + 1;
				exportPagination[i][1] = exportPagination[i][0] + exportSize
						- 1;
				if (exportPagination[i][1] > count) {
					exportPagination[i][1] = count;
				}
			}
			mav.addObject("exportPagination", exportPagination);
		}
		return mav;
	}

	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	public ModelAndView serachPrepare(@PathVariable("id") Long id,@MyInject Employee employee) throws SQLException {
		
		ModelAndView mav = new ModelAndView("/report/search_grid");
		// 得到grid
		Grid grid = reportService.get(id);
		if(grid != null){
			Pagination pagination = new Pagination();
			pagination.setPageSize(8);
			List<ReportLog> logs = reportService.findReportLog(grid,employee, pagination);

			// 得到conditions
			Collection<com.winxuan.framework.report.Condition> conditions = grid
					.getConditonList();
			// 数据容器list
			List<Object[]> enumerations = new ArrayList<Object[]>();
			// 循环condition获取枚举
			for (com.winxuan.framework.report.Condition con : conditions) {
				Condition condition = (Condition) con;
				// 如果是枚举类型
				if (condition.getType() == EIGHT) {
					// 枚举key value对象
					List<String[]> enumKeyV = new ArrayList<String[]>();
					// 枚举类型
					LinkedHashMap<String, String> linkedHashMap = DatabaseUtils
							.getEnumeration(condition.getEnumeration()
									.getDataSource(), condition
									.getEnumeration());
					if (linkedHashMap != null) {
						// key
						Set<String> set = linkedHashMap.keySet();
						// 循环key 取出枚举值
						for (String key : set) {
							String[] keyV = { key, linkedHashMap.get(key) };
							enumKeyV.add(keyV);
						}
						enumerations.add(new Object[] { condition, enumKeyV });
					}

				}
			}
			mav.addObject("enumerations", enumerations);
			mav.addObject("logs", logs);
		}
		mav.addObject("grid", grid);
		if(ReportServiceImpl.reportCache.get(employee.getId()+":"+grid.getId()) != null){
			mav.addObject("asyncReporting",1);
		}else{
			mav.addObject("asyncReporting",0);
		}
		return mav;
	}
	
	@RequestMapping(value = "/mygrid", method = RequestMethod.GET)
	public ModelAndView myGrid(@MyInject Employee employee) throws SQLException {
		ModelAndView mav  = new ModelAndView("/report/mygrid");
		List<MyGrid> mygridlist = reportService.findMyGridList(employee);
		MyGrid mg = new MyGrid();
		List<Grid> list = new ArrayList<Grid>();
		for(int i=0;i<mygridlist.size();i++){
			Grid grid =reportService.get(mygridlist.get(i).getGridList());
			if(null==grid){
				continue;
			}
			grid.setMyGridId(mygridlist.get(i).getId());
			mg.setEmployee(mygridlist.get(i).getEmployee());
			list.add(grid);
		}
		mg.setList(list);
		mav.addObject("mygridlist",mg);
		return mav;
	}
	
	@RequestMapping(value="/addmygrid/{id}", method=RequestMethod.GET)
	public ModelAndView addMyGrid(@MyInject Employee employee,@PathVariable("id")Long id) throws SQLException {		
		MyGrid mygrid = new MyGrid();
		mygrid.setEmployee(employee.getId());
		mygrid.setGridList(id);
		boolean isadd = true;
		List<MyGrid> mygridlist = reportService.findMyGridList(employee);
		for(int i=0;i<mygridlist.size();i++){
			if(mygridlist.get(i).getEmployee().equals(employee.getId())
					&&mygridlist.get(i).getGridList().equals(id)){
				isadd = false;
			}
		}
		if(isadd){
		reportService.addMyGrid(mygrid);
		}
		return new ModelAndView("redirect:/report/grid/mygrid");
	}
	
	@RequestMapping(value="/romvemygrid/{id}", method=RequestMethod.GET)
	public ModelAndView romveMyGrid(@MyInject Employee employee,@PathVariable("id")Long id) throws SQLException {		
		reportService.rmoveMyGrid(id);
		return new ModelAndView("redirect:/report/grid/mygrid");
	}
	
}
