/*
 * @(#)Refunder.java
 *
 */

package com.winxuan.ec.support.component.refund;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;

/**
 * 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class Refunder<T extends Bank> {
	
	private T bank ;
	
	public void setBank(T bank) {
		this.bank = bank;
	}

	public T getBank() {
		return bank;
	}

	/**
	 * 实际申请退款，向第三方发起请求
	 * @param refundCredential
	 * @return
	 * @throws Exception
	 */
	public abstract String refund(RefundCredential refundCredential) throws Exception;
	
	/**
	 * 解析请求响应
	 * @param res
	 * @return 退款状态
	 */
	public abstract Long getRefundProcessStatus(String res);
	
	/**
	 * 检查回调的url是否是第三方发送
	 * @param httpServletRequest
	 * @return
	 */
	public abstract boolean checkCallBackUrl(HttpServletRequest httpServletRequest);
	
	/**
	 * 解决退款失败
	 * @param refundCredential
	 * @throws Exception
	 */
	public abstract RefundCredential refundFailed(RefundCredential refundCredential);
	
	public abstract RefundCallBackForm getCallBackForm(HttpServletRequest httpServletRequest);
	
	public RefundCredential refundCallBackSetCredential(HttpServletRequest request, RefundCredential refundCredential){
		RefundCallBackForm callBackForm = getCallBackForm(request);
		refundCredential.setStatus(callBackForm.getStatus());
		refundCredential.setRefundTime(callBackForm.getRefundtime());
//		refundCredential.setOuterId(callBackForm.getOuterId());
		String method = request.getMethod();
		StringBuilder message = new StringBuilder(method)
							.append(" ");
		if("POST".equalsIgnoreCase(method)){
			message.append(getReturnParamsString(request));
		}
		else{
			message.append(request.getRequestURI())
				.append("?").append(request.getQueryString());
		}
		refundCredential.setMessage(message.toString());
		return refundCredential;
	}
	
	public String getSuccessResponse(){
		return "success";
	}
	public String getFailResponse(){
		return "fail";
	}
	
	public static Map<String, String> getReturnParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		for (Object param : requestParams.keySet()) {
			String name = (String) param;
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			/*if (encode) {
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化（现在已经使用）
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			}*/
			params.put(name, valueStr);
		}
		return params;
	}
	
	public static String getReturnParamsString(HttpServletRequest request){
		StringBuilder message = new StringBuilder()
			.append(request.getRequestURI())
			.append("?");
		Map<String,String> map = getReturnParams(request);
		int i = 0;
		for(Entry<String,String> entry : map.entrySet()){
			if(i != 0){
				message.append("&");
			}
			message.append(entry.getKey()).append("=").append(entry.getValue());
			i++;
		}
		return message.toString();
	}
}
