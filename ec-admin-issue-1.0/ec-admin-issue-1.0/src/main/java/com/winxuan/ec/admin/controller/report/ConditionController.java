/*
 * @(#)ConditionController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.report;

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
import com.winxuan.ec.model.report.Condition;
import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.service.report.ReportService;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-8-24
 */
@Controller
@RequestMapping("/report/condition")
public class ConditionController {
	private static final String URL = "/report/condition_table";
	private static final String GRID = "grid";
	@Autowired
	ReportService reportService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView newCondition(@Valid ConditionForm conditionForm,
			BindingResult result) {
		//获取grid
		Grid grid = reportService.get(conditionForm.getGridId());
		Set<Condition> conditions = grid.getConditionList();
		int conditionIndex =0;
		for(Condition c : conditions){
			int index =c.getIndex();
			conditionIndex=index>conditionIndex?index:conditionIndex;
		}
		Condition condition = new Condition();
		//设置值
		condition.setGrid(grid);
		condition.setName(conditionForm.getName());
		condition.setParameterName(conditionForm.getParameterName());
		condition.setType(conditionForm.getType());
		condition.setAllowNull(conditionForm.isAllowNull());
		condition.setControl(conditionForm.getControl());
		condition.setDefaultValue(conditionForm.getDefaultValue());
		condition.setIndex(conditionIndex+1);
		//枚举
		if (null != conditionForm.getEnumerationId()) {
			condition.setEnumeration(reportService
					.getEnumerationById(conditionForm.getEnumerationId()));
		}
		//创建
		reportService.createCondition(condition);
		ModelAndView mav = new ModelAndView(URL);
		grid.getConditionList().add(condition);
		mav.addObject(GRID, grid);
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid ConditionForm conditionForm, BindingResult result) {
		//获取condition
		Condition condition = reportService.getCondition(conditionForm.getId());
		//获取grid
		Grid grid = condition.getGrid();
		//设置值
		condition.setName(conditionForm.getName());
		condition.setParameterName(conditionForm.getParameterName());
		condition.setType(conditionForm.getType());
		condition.setAllowNull(conditionForm.isAllowNull());
		condition.setControl(conditionForm.getControl());
		condition.setDefaultValue(conditionForm.getDefaultValue());
		//枚举
		if (null != conditionForm.getEnumerationId()) {
			condition.setEnumeration(reportService
					.getEnumerationById(conditionForm.getEnumerationId()));
		}
        //修改
		reportService.updateCondition(condition);
		ModelAndView mav = new ModelAndView(URL);
		mav.addObject(GRID, grid);
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id") Long id) {
		//获取condition
		Condition condition = reportService.getCondition(id);
		//获取grid
		Grid grid = condition.getGrid();
		//删除
		reportService.deleteCondition(condition);
		ModelAndView mav = new ModelAndView(URL);
		mav.addObject(GRID, grid);
		return mav;
	}
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView index(@RequestParam("id") Long id,
			 @RequestParam("upordown") String upordown) {
		//获取conditionsetumn
		Condition condition = reportService.getCondition(id);
		//获取grid
		Grid grid = condition.getGrid();
		//确定目标顺序
		int target = 0;
		if("down".equals(upordown)){
			target = condition.getIndex()+1;
		}else{
			target = condition.getIndex()-1;
		}
	    //调换顺序
        for (Condition con : grid.getConditionList()) {
        	if(con.getIndex()==target){
        		con.setIndex(condition.getIndex());
        		condition.setIndex(target);
        		break;
        	}
		}
		//修改
		reportService.update(grid);
		//获取修改后数据
		grid = reportService.get(grid.getId());
		//排序
		Condition[] conditions = new Condition[grid.getConditionList().size()];
		int i = 0;
		for (Condition conditionset :grid.getConditionList()) {
			if(i==0){
				conditions[i]=conditionset;
			}else{
				if(conditions[i-1].getIndex()>conditionset.getIndex()){
					conditions[i]=conditions[i-1];
					conditions[i-1] = conditionset;
				}else{
					conditions[i]=conditionset;
				}
			}
			i++;
		}
		Set<Condition> conditionsetset = new LinkedHashSet<Condition>();
        for (int j=0;j<conditions.length;j++) {
        	conditionsetset.add(conditions[j]);
		}
        grid.setConditionList(conditionsetset);
		//返回
		ModelAndView mav = new ModelAndView(URL);
		mav.addObject(GRID, grid);
		return mav;
	}
}
