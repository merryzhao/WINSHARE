package com.winxuan.ec.front.controller.cps.query;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-8
 */
public interface QueryExtractor {
	
	 Map<String,Object> getQueryMap(HttpServletRequest request) throws CpsException;
	
	 
	 String  generateResult(List<UnionOrder> unionOrders,Map<String,Object> queryMap);
}
