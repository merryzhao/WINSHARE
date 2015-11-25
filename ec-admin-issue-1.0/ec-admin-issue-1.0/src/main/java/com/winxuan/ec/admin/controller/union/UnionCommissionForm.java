package com.winxuan.ec.admin.controller.union;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winxuan.framework.util.AcceptHashMap;
import com.winxuan.framework.util.StringUtils;
/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public class UnionCommissionForm {
	private Long unionId;

	private String name;
	
	private int startYear;
	
	private int startMonth;
	
	private int endYear;
	
	private int endMonth;
	
	private Integer isPay;

	public Long getUnionId() {
		return unionId;
	}

	public void setUnionId(Long unionId) {
		this.unionId = unionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}	

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Map<String, Object> generateQueryMap() {
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()
			.acceptIf("unionId", unionId, unionId!=null)
			.acceptIf("name", name, !StringUtils.isNullOrEmpty(name));
		if(isPay == 0){
			parameters.put("isPay", false);
		}else if(isPay == 1){
			parameters.put("isPay", true);
		}
		return parameters;
	}
	public Map<String,Object> generateTimeMap(){
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()
			.accept("startYear", startYear)
			.accept("startMonth", startMonth)
		    .accept("endYear",endYear)
		    .accept("endMonth", endMonth);
		return parameters;
		    
	}
	
	public List<String> generateDate(){	
		final int monthCount = 12;
		List<String> dateList = new ArrayList<String>();
		if(startYear == endYear){
			for(;startMonth <= endMonth;startMonth++){	
				String temp = String.valueOf(startYear)+"-"+String.valueOf(startMonth);
				dateList.add(temp);
			}
		}
		else if(startYear < endYear){
			for(;startYear < endYear;startYear++){
				for(;startMonth <= monthCount;startMonth++){
					String temp = String.valueOf(startYear)+"-"+String.valueOf(startMonth);
					dateList.add(temp);
				}
				startYear++;
			}
		}
		return dateList;
	}
	
}
