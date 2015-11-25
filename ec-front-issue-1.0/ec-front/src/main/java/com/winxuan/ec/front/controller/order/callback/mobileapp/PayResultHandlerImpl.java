package com.winxuan.ec.front.controller.order.callback.mobileapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risetek.cupsdk.IPayResultHandler;
import com.winxuan.ec.exception.PaymentCredentialException;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.CallBackForm;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.service.order.BatchPayService;

/**
 * 银行手机支付实现接口
 * @author luosh
 *
 */
@Service("payResultHandler")
public class PayResultHandlerImpl implements IPayResultHandler{
	private static final Log LOG = LogFactory.getLog(PayResultHandlerImpl.class);
	
	private static final long PAYMENT_ID = 127L;
	
	@Autowired
	private BatchPayService batchPayService;
	
	/**
	 * 支付结果处理
	 * 如果通知请求成功送达商户但商户的应答信息没有成功返回银联手机支付服务器，
	 * 银联手机支付服务器会视为通知失败从而会重新通知，这时就会出现商户收到同
	 * 一笔订单的多条支付通知请求，因此商户服务器必须能够识别并处理这种重复通知。
	 * @param paySuccess	支付是否成功
	 * @param payId		交易号
	 * @param amount		金额(单位:分)
	 * @param description	描述
	 * @return 				支付结果处理是否完成
	 */
	public boolean handle(boolean paySuccess, String payId, long amount, String description) {
		if(!paySuccess){
			return false;
		}
		CallBackForm callBackForm = new CallBackForm();
		BatchPay batchPay = batchPayService.get(payId);
		List<Order> orderList = new ArrayList<Order>(batchPay.getOrderList());
		if(CollectionUtils.isEmpty(orderList)){
			return false;
		}
		Order order = orderList.get(0);
		
		if(order == null){
			return false;
		}
		BigDecimal money = new BigDecimal(amount).divide(new BigDecimal(100));
		callBackForm.setMoney(money);
		callBackForm.setBatchId(payId);
		callBackForm.setOuterTradeNo(payId);
		callBackForm.setPaymentId(PAYMENT_ID);
		try{
			batchPayService.onlinePayCallback(callBackForm,new HashMap<String,String[]>());
			return true;
		}catch(Exception e){
			if(e.getClass().equals(PaymentCredentialException.class)){
				if(e.getMessage().contains(payId + "已支付过")){
					LOG.info("batchPayId:" + callBackForm.getBatchId() +" has payed");
					return true;
				}
			}
			LOG.info("batchPayId:" + callBackForm.getBatchId() +"; orderId:" + order.getId() +"; handle order is error!");
			return false;
		}
	}
}
