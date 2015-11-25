package com.winxuan.ec.front.controller.cps.extractor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class DawenExtractor extends AbstractExtractor{

	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		final int six  = 6;
		Order order = unionOrder.getOrder();
		postMethod.addParameter("svcd", "");
		postMethod.addParameter("cvcd","");
		postMethod.addParameter("trk_cid","124");
		postMethod.addParameter("trk_od",String.valueOf(order.getId()));
		postMethod.addParameter("trk_am",String.valueOf(order.getActualMoney()));
		postMethod.addParameter("trk_sp","2");
		postMethod.addParameter("trk_cm","102");
		postMethod.addParameter("trk_sa","");
		postMethod.addParameter("_to_page","");
		postMethod.addParameter("do","");
		postMethod.addRequestHeader("rnd",
				RandomCodeUtils.create(RandomCodeUtils.MODE_NUMBER, six));
		postMethod.addParameter("trk_er","");
		postMethod.addParameter("trk_rr","");	
	}

	@Override
	protected String extractCookieValue(HttpServletRequest request)
			throws CpsException {		
		return "dawen";
	}

	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}

	

}
