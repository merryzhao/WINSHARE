package com.winxuan.ec.support.component.refund;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.winxuan.ec.exception.AlipayRefunderException;
import com.winxuan.ec.model.bank.AliWap;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.model.refund.RefundMessage;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.support.component.refund.AlipayRefunder.MD5;
import com.winxuan.ec.support.util.ApplicationContextUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.util.http.HttpProtocolHandler;
import com.winxuan.ec.support.util.http.HttpRequest;
import com.winxuan.ec.support.util.http.HttpResponse;
import com.winxuan.ec.support.util.http.HttpResultType;
/**
 * 支付宝平台退款
 * @author youwen
 *
 */
public class AliWapRefunder extends Refunder<AliWap>{
	private static final String SPLIT = "^";
	private static final String SIGN_NAME = "sign";
	private static Logger log = Logger.getLogger(AliWapRefunder.class);
	private CodeService codeService = ApplicationContextUtils.getBean(CodeService.class);
	private ExceptionLogService exceptionLogService = ApplicationContextUtils.getBean(ExceptionLogService.class);
	private RefundService refundService = ApplicationContextUtils.getBean(RefundService.class);
	
	@Override
	public String refund(RefundCredential refundCredential) throws Exception {
		AliWap  aliWap = getBank();
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", aliWap.getService());
		params.put("partner", aliWap.getPartner());
		params.put("_input_charset", AliWap.INPUT_CHARSET);
		params.put("notify_url", aliWap.getNotifyUrl());
		params.put("seller_email", aliWap.getSellerEmail());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.put("refund_date", format.format(new Date()));
		params.put("batch_no", refundCredential.getId());
		params.put("batch_num", String.valueOf(MagicNumber.ONE));
		params.put("seller_user_id", aliWap.getPartner());
		String detailData = refundCredential.getOuterId()
				+ SPLIT + refundCredential.getMoney()
				+ SPLIT + "协商退款";
		params.put("detail_data", detailData);
		String sHtmlText = buildRequest(params,aliWap);
		return sHtmlText;
	}

	@Override
	public Long getRefundProcessStatus(String res) {
String isSuccess = null;
		
		try {
			Pattern p = Pattern.compile("<is_success>(.*)</is_success>");
			Matcher m = p.matcher(res);
			boolean b = m.find();
			if(b){
				isSuccess  = m.group(1);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		
		Long status;
		if("T".equals(isSuccess)){
			status = RefundCredential.STATUS_OTHER_WAIT;
		}
		else if("F".equals(isSuccess)){
			status = RefundCredential.STATUS_FAILED;
		}
		else if("P".equals(isSuccess)){
			status = RefundCredential.STATUS_OTHER_WAIT;
		}
		else {
			status = RefundCredential.STATUS_FAILED;
		}
		return status;
	}

	@Override
	public boolean checkCallBackUrl(HttpServletRequest httpServletRequest) {
		//notify in ec-pay
		return false;
	}

	@Override
	public RefundCredential refundFailed(RefundCredential refundCredential) {
		if(screeningMessage(refundCredential.getPayment().getId(),refundCredential.getMessage())){
			RefundCredential rc = new RefundCredential();
			try {
				if(refundCredential.getRefundTime()==null){
					refundCredential.setRefundTime(new Date());
				}
				BeanUtils.copyProperties(rc, refundCredential);
				Set<RefundCredential> rcs = new HashSet<RefundCredential>();
				rc.setParent(refundCredential);
				rc.setStatus(codeService.get(refundCredential.STATUS_SYS_WAIT));
				rcs.add(rc);
				refundCredential.setChildren(rcs);
				refundCredential.setStatus(codeService.get(refundCredential.STATUS_DISUSE));
			} catch (Exception e) {
				log.info(e.getMessage());
				exceptionLogService.info(refundCredential.getId(),"新建退款:"+e.getMessage(),refundCredential.getPayment().getName());
			}
			return refundCredential;
		}
		return refundCredential;
	}
	
	/**
	 * 查看报文信息中是否包含允许错误信息
	 * @param paymentId
	 * @param message
	 * @return
	 */
	public boolean screeningMessage(Long paymentId,String message){
		List<RefundMessage> rerefundMessages = refundService.find(paymentId,true);
		for (RefundMessage refundMessage : rerefundMessages) {
			Pattern p = Pattern.compile(refundMessage.getMessage());
			Matcher m = p.matcher(message);
			if(m.find()){
				return true;
			}
		}
		return false;
	}

	@Override
	public RefundCallBackForm getCallBackForm(
			HttpServletRequest httpServletRequest) {
		//notify in ec-pay
		return null;
	}
	
	
	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
	 * 如果接口execute中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
	 * @param params
	 * @param refundCredential
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String buildRequest(Map<String, String> params,AliWap aliWap) throws AlipayRefunderException, HttpException, IOException{
		//生成签名结果
        Map<String, String> sPara = paraFilter(params);
        sPara.put(SIGN_NAME,signParams(sPara, aliWap.getKey()));
        sPara.put("sign_type", AliWap.SIGN_TYPE);
        
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(AliWap.INPUT_CHARSET);
        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(aliWap.getRefundAction()+"?_input_charset="+AliWap.INPUT_CHARSET);
        HttpResponse response = httpProtocolHandler.execute(request,"","");
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
	}
	
	 /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
	
	/** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || "".equals(value) || "sign".equalsIgnoreCase(key)
                || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    /**
     * 生成签名
     * @return
     */
    public String signParams(Map<String, String> params,String key){
    	String prestr = createLinkString(params);
    	String md5 = MD5.sign(prestr,key,AliWap.INPUT_CHARSET);
        return md5;
    }
    
     
    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

}
