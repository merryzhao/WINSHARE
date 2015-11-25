/*
 * @(#)CollumnController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.report;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.winxuan.ec.model.report.Column;
import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.service.report.ReportService;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-8-23
 */
@Controller
@RequestMapping("/report/column")
public class ColumnController {
	
	private static final String URL = "/report/column_table";
	private static final String GRID = "grid";
	@Autowired
	ReportService reportService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView newColumn(@Valid ColumnForm columnForm,
			BindingResult result) {
		//获取grid
		Grid grid=reportService.get(columnForm.getGridId());
		Collection<Column> columns = (Collection)grid.getColumnList();
		int columnIndex =0;
		for(Column c : columns){
			int index =c.getIndex();
			columnIndex=index>columnIndex?index:columnIndex;
		}
		//设置值
		Column column = new Column();
		column.setGrid(grid);
		column.setAggregated(columnForm.isAggregated());
		column.setAscSql(columnForm.getAscSql());
		column.setDescSql(columnForm.getDescSql());
		column.setName(columnForm.getName());
		column.setOrder(columnForm.isOrder());
		column.setValue(columnForm.getValue());
		column.setWidth(columnForm.getWidth());
		column.setIndex(columnIndex+1);
		//创建
		reportService.createColumn(column);
		ModelAndView mav = new ModelAndView(URL);
		grid.getColumnList().add(column);
		mav.addObject(GRID, grid);
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid ColumnForm columnForm, BindingResult result) {	
		//获取column
		Column column = reportService.getColumn(columnForm.getId());
		//设置修改值
		column.setAggregated(columnForm.isAggregated());
		column.setAscSql(columnForm.getAscSql());
		column.setDescSql(columnForm.getDescSql());
		column.setName(columnForm.getName());
		column.setOrder(columnForm.isOrder());
		column.setValue(columnForm.getValue());
		column.setWidth(columnForm.getWidth());
		//修改
		reportService.updateColumn(column);
		ModelAndView mav = new ModelAndView(URL);
		mav.addObject(GRID, column.getGrid());
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id") Long id) {
		//获取column
		Column column = reportService.getColumn(id);
		//获取grid
		Grid grid = column.getGrid();
		//删除
		reportService.deleteColumn(column);
		//返回
		ModelAndView mav = new ModelAndView(URL);
		mav.addObject(GRID, grid);
		return mav;
	}
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView index(@RequestParam("id") Long id,
			 @RequestParam("upordown") String upordown) {
		//获取column
		Column column = reportService.getColumn(id);
		//获取grid
		Grid grid = column.getGrid();
		//确定目标顺序
		int target = 0;
		if("down".equals(upordown)){
			target = column.getIndex()+1;
		}else{
			target = column.getIndex()-1;
		}
	    //调换顺序
        for (com.winxuan.framework.report.Column col1 : grid.getColumnList()) {
        	Column col = (Column)col1;
        	if(col.getIndex()==target){
        		col.setIndex(column.getIndex());
        		column.setIndex(target);
        		break;
        	}
		}
		//修改
		reportService.update(grid);
		//获取修改后数据
		grid = reportService.get(grid.getId());
		//排序
		Column[] columns = new Column[grid.getColumnList().size()];
		int i = 0;
		for (com.winxuan.framework.report.Column columnset : grid.getColumnList()) {
			Column col = (Column)columnset;
			if(i==0){
				columns[i]=col;
			}else{
				if(columns[i-1].getIndex()>col.getIndex()){
					columns[i]=columns[i-1];
					columns[i-1] = col;
				}else{
					columns[i]=col;
				}
			}
			i++;
		}
		Set<Column> colset = new LinkedHashSet<Column>();
        for (int j=0;j<columns.length;j++) {
        	colset.add(columns[j]);
		}
        grid.setColumnList(colset);
		//返回
		ModelAndView mav = new ModelAndView(URL);
		mav.addObject(GRID, grid);
		return mav;
	}
}
