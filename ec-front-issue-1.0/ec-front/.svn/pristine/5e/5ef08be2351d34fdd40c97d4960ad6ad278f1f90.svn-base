package com.winxuan.ec.front.controller.cps.query;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.service.order.UnionOrderService;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-8
 */
public class Query {
	
	@Autowired
	UnionOrderService unionOrdeService;
	
	private QueryExtractor queryExtractor;
	
	public String query(HttpServletRequest request) throws CpsException{
		Map<String,Object> queryMap  = queryExtractor.getQueryMap(request);
		List<UnionOrder> unionOrders = findUnionOrder(queryMap);
		String result = queryExtractor.generateResult(unionOrders,queryMap);
		return result;
	}
	
	public List<UnionOrder> findUnionOrder(Map<String,Object> queryMap){
		return unionOrdeService.find(queryMap, Short.valueOf("0"), null);
	}

	public void setQueryExtractor(QueryExtractor queryExtractor) {
		this.queryExtractor = queryExtractor;
	}
	
	
}
