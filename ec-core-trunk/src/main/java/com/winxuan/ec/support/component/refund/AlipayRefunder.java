/*
 * @(#)AlipayRefunder.java
 *
 */

package com.winxuan.ec.support.component.refund;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.multipart.FilePartSource;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.exception.AlipayRefunderException;
import com.winxuan.ec.model.bank.Alipay;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.model.refund.RefundMessage;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.support.util.ApplicationContextUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.util.http.HttpProtocolHandler;
import com.winxuan.ec.support.util.http.HttpRequest;
import com.winxuan.ec.support.util.http.HttpResponse;
import com.winxuan.ec.support.util.http.HttpResultType;


/**
 * description
 * @author  huangyixiang
 * @version 2013-5-15
 */
public class AlipayRefunder extends Refunder<Alipay>{
	
	private static final Log LOG = LogFactory.getLog(AlipayRefunder.class);
	
	private static final String SPLIT = "^";
	private static final String SIGN_NAME = "sign";
	
	private ExceptionLogService exceptionLogService = ApplicationContextUtils.getBean(ExceptionLogService.class);
	private CodeService codeService = ApplicationContextUtils.getBean(CodeService.class);
	private RefundService refundService = ApplicationContextUtils.getBean(RefundService.class);

	public String refund(RefundCredential refundCredential) throws Exception{
		Alipay alipay = getBank();
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", alipay.getRefundService());
        sParaTemp.put("partner", alipay.getPartner());
        sParaTemp.put("_input_charset", alipay.getCharset());
		sParaTemp.put("notify_url", alipay.getRefundNotifyUrl());
		sParaTemp.put("batch_no", refundCredential.getId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sParaTemp.put("refund_date", format.format(new Date()));
		sParaTemp.put("batch_num", String.valueOf(MagicNumber.ONE));
		String detailData = refundCredential.getOuterId()
				+ SPLIT + refundCredential.getMoney()
				+ SPLIT + "协商退款";
		sParaTemp.put("detail_data", detailData);
		String sHtmlText = buildRequest(sParaTemp,refundCredential);
		return sHtmlText;
	}
	
	public Long getRefundProcessStatus(String res){
		String isSuccess = null;
		
		try {
			Pattern p = Pattern.compile("<is_success>(.*)</is_success>");
			Matcher m = p.matcher(res);
			boolean b = m.find();
			if(b){
				isSuccess  = m.group(1);
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
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
	
	public boolean checkCallBackUrl(HttpServletRequest httpServletRequest){
		Alipay alipay = getBank();
		return AlipayNotify.verify(getReturnParams(httpServletRequest), alipay);
	}
	
	@Override
	public RefundCallBackForm getCallBackForm(
			HttpServletRequest httpServletRequest) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RefundCallBackForm refundCallBackForm = new RefundCallBackForm();
		refundCallBackForm.setRefundCredentialId(httpServletRequest.getParameter("batch_no"));
		String[] resultDetail = httpServletRequest.getParameter("result_details").split("\\^");
		if(resultDetail != null && resultDetail.length >= MagicNumber.THREE){
			String status = resultDetail[2];
			if("SUCCESS".equals(status)){
				refundCallBackForm.setStatus(new Code(RefundCredential.STATUS_SUCCESS));
			}
			else{
				refundCallBackForm.setStatus(new Code(RefundCredential.STATUS_FAILED));
			}
		}
		try {
			refundCallBackForm.setRefundtime(dateFormat.parse(httpServletRequest.getParameter("notify_time")));
		} catch (ParseException e) {
			LOG.error(e.getMessage(),e);
		}
		return refundCallBackForm;
	}
	
    private String buildRequest(Map<String, String> sParaTemp,RefundCredential refundCredential) throws AlipayRefunderException, HttpException, IOException {
    	Alipay bank = getBank();
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setCharset(bank.getCharset());
        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(bank.getRefundAction() + "?_input_charset=" + bank.getCharset());
        this.showPostParameters(request,refundCredential);
        HttpResponse response = httpProtocolHandler.execute(request,"","");
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }
    
    private StringBuffer showPostParameters(HttpRequest request,RefundCredential refundCredential) {
		NameValuePair[] parametrs = request.getParameters();
		StringBuffer buildr = new StringBuffer();
		for (NameValuePair nameValuePair : parametrs) {
			buildr.append(nameValuePair.getName() + "="
					+ nameValuePair.getValue() + ",");
		}
		exceptionLogService.info(refundCredential.getId(), String.format("请求报文:%s,\n请求地址:%s",
				buildr, request.getUrl()), refundCredential.getPayment().getName());
		LOG.info(buildr.toString());
		return buildr;
	}
    
    private Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
    	Alipay bank = getBank();
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put(SIGN_NAME, mysign);
        sPara.put("sign_type", bank.getSignType());
        return sPara;
    }
    
    private String buildRequestMysign(Map<String, String> sPara) {
    	Alipay bank = getBank();
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = MD5.sign(prestr, bank.getKey(), bank.getCharset());
        return mysign;
    }
	
    private NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }
    
    
    
    /**
     * 
     * @author Administrator
     *
     */
    static class AlipayCore {

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
         * 生成文件摘要
         * @param strFilePath 文件路径
         * @param fileDegest 摘要算法
         * @return 文件摘要结果
         */
        public static String getAbstract(String strFilePath, String fileDegestType) throws IOException {
            PartSource file = new FilePartSource(new File(strFilePath));
        	if("MD5".equals(fileDegestType)){
        		return DigestUtils.md5Hex(file.createInputStream());
        	}
        	else if("SHA".equals(fileDegestType)) {
        		return DigestUtils.sha256Hex(file.createInputStream());
        	}
        	else {
        		return "";
        	}
        }
    }

    /**
     * 
     * @author Administrator
     *
     */
    static class MD5 {

        /**
         * 签名字符串
         * @param text 需要签名的字符串
         * @param key 密钥
         * @param input_charset 编码格式
         * @return 签名结果
         */
        public static String sign(String text, String key, String inputCharset) {
        	text = text + key;
            return DigestUtils.md5Hex(getContentBytes(text, inputCharset));
        }
        
        /**
         * 签名字符串
         * @param text 需要签名的字符串
         * @param sign 签名结果
         * @param key 密钥
         * @param input_charset 编码格式
         * @return 签名结果
         */
        public static boolean verify(String text, String sign, String key, String inputCharset) {
        	text = text + key;
        	String mysign = DigestUtils.md5Hex(getContentBytes(text, inputCharset));
        	return mysign.equals(sign);
        }

        /**
         * @param content
         * @param charset
         * @return
         * @throws SignatureException
         * @throws UnsupportedEncodingException 
         */
        private static byte[] getContentBytes(String content, String charset) {
            if (charset == null || "".equals(charset)) {
                return content.getBytes();
            }
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
        }

    }
    /**
     * 
     * @author Administrator
     *
     */
    static class AlipayNotify {
    	private static final Log LOG = LogFactory.getLog(AlipayNotify.class);
    	private static final String SIGN_NAME = "sign";

        /**
         * 验证消息是否是支付宝发出的合法消息
         * @param params 通知返回来的参数数组
         * @return 验证结果
         */
        public static boolean verify(Map<String, String> params, Alipay bank) {

            //判断responsetTxt是否为true，isSign是否为true
            //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
            //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        	String responseTxt = "true";
    		if(params.get("notify_id") != null) {
    			String notifyId = params.get("notify_id");
    			responseTxt = verifyResponse(notifyId, bank);
    		}
    	    String sign = "";
    	    if(params.get(SIGN_NAME) != null) {sign = params.get(SIGN_NAME);}
    	    boolean isSign = getSignVeryfy(params, sign, bank);

            //写日志记录（若要调试，请取消下面两行注释）
            //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
    	    //AlipayCore.logResult(sWord);

    	    return isSign && "true".equals(responseTxt);
        }

        /**
         * 根据反馈回来的信息，生成签名结果
         * @param Params 通知返回来的参数数组
         * @param sign 比对的签名结果
         * @return 生成的签名结果
         */
    	private static boolean getSignVeryfy(Map<String, String> params, String sign, Alipay bank) {
        	//过滤空值、sign与sign_type参数
        	Map<String, String> sParaNew = AlipayCore.paraFilter(params);
            //获取待签名字符串
            String preSignStr = AlipayCore.createLinkString(sParaNew);
            //获得签名验证结果
            boolean isSign = false;
        	isSign = MD5.verify(preSignStr, sign, bank.getKey(), bank.getCharset());
            return isSign;
        }

        /**
        * 获取远程服务器ATN结果,验证返回URL
        * @param notify_id 通知校验ID
        * @return 服务器ATN结果
        * 验证结果集：
        * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
        * true 返回正确信息
        * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
        */
        private static String verifyResponse(String notifyId, Alipay bank) {
            //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

            String partner = bank.getPartner();
            String veryfyUrl = bank.getRefundAction() + "?service=notify_verify&" + "partner=" + partner + "&notify_id=" + notifyId;

            return checkUrl(veryfyUrl);
        }

        /**
        * 获取远程服务器ATN结果
        * @param urlvalue 指定URL路径地址
        * @return 服务器ATN结果
        * 验证结果集：
        * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
        * true 返回正确信息
        * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
        */
        private static String checkUrl(String urlvalue) {
            String inputLine = "";

            try {
                URL url = new URL(urlvalue);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream()));
                inputLine = in.readLine().toString();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                inputLine = "";
            }

            return inputLine;
        }
    }
    
	@Override
	public RefundCredential refundFailed(RefundCredential refundCredential){
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
				LOG.info(e.getMessage());
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
	
	
}

