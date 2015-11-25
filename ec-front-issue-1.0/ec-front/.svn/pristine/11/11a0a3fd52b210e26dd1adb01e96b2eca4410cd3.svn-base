package com.winxuan.ec.front.controller.order.callback;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-5-15 下午3:41:41  --!
 * 
 ********************************
 */
@Controller
@RequestMapping(value="/order/onlinepay/chinapay/refund")
public class ChinaPayRefundCallBackController {
	private static final Log LOG = LogFactory.getLog(ChinaPayRefundCallBackController.class);
	
	@RequestMapping(value = "/return")
	public ModelAndView returnCallBack(HttpServletRequest request){
		LOG.info(request.getParameterMap());
		return new ModelAndView();
	}
	
}
