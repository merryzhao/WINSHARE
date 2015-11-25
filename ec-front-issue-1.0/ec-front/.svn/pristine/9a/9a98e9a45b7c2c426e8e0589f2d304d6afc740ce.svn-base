package com.winxuan.ec.front.controller.order.callback.mobileapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.UnionPay;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.payment.PaymentService;

/**
 * 银行手机支付回调
 * @author luosh
 *
 */
@Controller
@RequestMapping("/order/onlinepay/unionpaymobile")
public class UnionPayController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UnionPayController.class);
	private static final String SUCCESS = "00";
	@Autowired
	private OrderService orderService;
	@Autowired
	private BatchPayService batchPayService;
	@Autowired
	private PaymentService paymentService;
	
	
	/**
	 * 手機支付成功后得到通知
	 * @param request
	 * @param response
	 * @return 银行支付结果信息
	 */
	@RequestMapping(value="/notify", method={RequestMethod.POST})
	@ResponseBody
	public void payNotify(HttpServletRequest request,HttpServletResponse response) {
		UnionPay unionPay = (UnionPay) BankConfig.getBank(UnionPay.PAYMENT_ID);
		Map<String,String> params =	getRequestParams(request);
		String transStatus = request.getParameter("transStatus");// 交易状态
		String order = request.getParameter("orderNumber");//batchNo
		String qn = request.getParameter("qn");//qn
		//验证支付结果
		if(verifyParams(params,unionPay,transStatus)){
			//处理订单状态
			orderPay(unionPay,order, qn);
		}
	}
	
	
	public Map<String,String> getRequestParams(HttpServletRequest request){
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}
	
	private boolean verifyParams(Map<String,String> params,UnionPay unionPay,String status){
		if(HttpUtil.verifySignature(params,unionPay)){ 
			if (null != status && SUCCESS.equals(status)) {
				return true;
			} else {
				String request = HttpUtil.createLinkString(params, true, false);
				LOG.error("unionpay status error:"+request);
			}
		}else{
			String request = HttpUtil.createLinkString(params, true, false);
			LOG.error("unionpay notify verifyRequest failed:"+request);
		}
		return false;
	}
	
	private void orderPay(UnionPay unionPay,String order,String qn){
		try {
			BatchPay batchPay = batchPayService.get(order);
			batchPay.setSuccess(true);
			batchPayService.update(batchPay);
			Set<Order> orders = batchPay.getOrderList();
			List<Order> orderList = new ArrayList<Order>();
			orderList.addAll(orders);
			PaymentCredential paymentCredential = new PaymentCredential();
			paymentCredential.setOuterId(qn);
			paymentCredential.setCustomer(orderList.get(0).getCustomer());
			paymentCredential.setMoney(batchPay.getTotalMoney());
			paymentCredential.setOperator(orderList.get(0).getCustomer());
			paymentCredential.setPayment(paymentService.get(unionPay.getId()));
			paymentCredential.setPayTime(new Date());
			orderService.pay(orderList, paymentCredential, orderList.get(0).getCustomer());
		} catch (Exception e) {
			LOG.error("unionpay notify error:"+e.getMessage(),e);
		}
	}
}
