 package com.winxuan.ec.front.controller.cps.query;

import java.util.Map;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-8
 */
public class QueryFactory {
	private Map<Long,Query> querys;

	public void setQuerys(Map<Long, Query> querys) {
		this.querys = querys;
	}
	
	public Query get(Long id){
		return querys.get(id);
	}
	
}
