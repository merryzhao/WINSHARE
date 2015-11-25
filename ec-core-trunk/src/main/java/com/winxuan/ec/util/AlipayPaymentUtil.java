package com.winxuan.ec.util;

import com.alipay.util.Md5Encrypt;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author bianlin
 *
 */
public class AlipayPaymentUtil{
	private static final Log LOG = LogFactory.getLog(AlipayPaymentUtil.class);
	public static String createUrl(String paygateway, String service, 
			String signType, String outTradeNo, String inputCharset, 
			String partner, String key, String showUrl, 
			String body, String totalFee, String paymentType, 
			String sellerEmail, String subject, String notifyUrl, 
			String returnUrl, String paymethod, String defaultbank, String qrPayMode)
	  {
	    Map params = new HashMap();
	    params.put("service", service);
	    params.put("partner", partner);
	    params.put("subject", subject);
	    params.put("body", body);
	    params.put("out_trade_no", outTradeNo);
	    params.put("total_fee", totalFee);
	    params.put("show_url", showUrl);
	    params.put("payment_type", paymentType);
	    params.put("seller_email", sellerEmail);
	    params.put("return_url", returnUrl);
	    params.put("notify_url", notifyUrl);
	    params.put("_input_charset", inputCharset);
	    params.put("paymethod", paymethod);
	    params.put("defaultbank", defaultbank);
	    params.put("qr_pay_mode", qrPayMode);
	    
	    String prestr = "";

	    prestr = prestr + key;

	    String sign = Md5Encrypt.md5(getContent(params, key));

	    String parameter = "";
	    parameter = parameter + paygateway;

	    List keys = new ArrayList(params.keySet());
	    for (int i = 0; i < keys.size(); i++) {
	      String value = (String)params.get(keys.get(i));
	      if ((value == null) || (value.trim().length() == 0)){
	        continue;
	      }
	      try
	      {
	        parameter = parameter + keys.get(i) + "=" + 
	          URLEncoder.encode(value, inputCharset) + "&";
	      }
	      catch (UnsupportedEncodingException e) {
	    	  LOG.info(e.getMessage(), e);
	      }
	    }

	    parameter = parameter + "sign=" + sign + "&sign_type=" + signType;

	    return sign;
	  }
	
	  private static String getContent(Map params, String privateKey)
	  {
	    List keys = new ArrayList(params.keySet());
	    Collections.sort(keys);

	    String prestr = "";

	    boolean first = true;
	    for (int i = 0; i < keys.size(); i++) {
	      String key = (String)keys.get(i);
	      String value = (String)params.get(key);
	      if ((value == null) || (value.trim().length() == 0)) {
	        continue;
	      }
	      if (first) {
	        prestr = prestr + key + "=" + value;
	        first = false;
	      } else {
	        prestr = prestr + "&" + key + "=" + value;
	      }
	    }

	    return prestr + privateKey;
	  }
}
