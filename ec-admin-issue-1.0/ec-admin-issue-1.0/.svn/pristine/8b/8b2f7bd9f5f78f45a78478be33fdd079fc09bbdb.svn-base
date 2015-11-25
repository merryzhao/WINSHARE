package com.winxuan.ec.admin.controller.union;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.winxuan.ec.support.util.DateUtils;
import com.winxuan.framework.util.AcceptHashMap;
import com.winxuan.framework.util.StringUtils;

/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public class UnionOrderForm {
	private Long unionId;

	private String name;

	private Date startCreateTime;

	private Date endCreateTime;


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

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	/**
	 * 生成查询条件
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> generateQueryMap() throws ParseException {
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()
		 	.acceptIf("unionId", unionId, unionId!=null)
		 	.acceptIf("name", "%"+name+"%", !StringUtils.isNullOrEmpty(name))
			.acceptIf("startCreateDate", startCreateTime == null ? null:DateUtils.getEarlyInTheDay(startCreateTime),startCreateTime != null)
			.acceptIf("endCreateDate", endCreateTime == null ? null:DateUtils.getLateInTheDay(endCreateTime), endCreateTime != null);
			return parameters;
	}

}
