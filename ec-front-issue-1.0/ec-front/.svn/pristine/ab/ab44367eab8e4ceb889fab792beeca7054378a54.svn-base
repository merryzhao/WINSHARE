package com.winxuan.ec.front.controller.order.callback.mobileapp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winxuan.ec.model.bank.UnionPay;


/**
 * 类名：https/https报文发送处理类
 * 功能：https/https报文发送处理
 * 版本：1.0
 * 日期：2012-10-11
 * 作者：中国银联UPMP团队
 * 版权：中国银联
 * */
public class HttpUtil {
		/** = */
		public static final String QSTRING_EQUAL = "=";
	
		/** & */
		public static final String QSTRING_SPLIT = "&";
		
		//连接超时
		public static final Integer HTTP_CONNECTION_TIMEOUT = 15000;
	
		//应答超时
		public static final Integer HTTP_SO_TIMEOUT = 30000;
	
		//网络参数
		public static final boolean HTTP_STALE_CHECK_ENABLED = true;
		public static final boolean HTTP_TCP_NO_DELAY = true;
		public static final Integer HTTP_MAX_CONNECTIONS_PER_HOST = 100;
		public static final Integer HTTP_MAX_TOTAL_CONNECTIONS = 1000;
	
		//字符集
		public static final String HTTP_CONTENT_ENCODING = "utf-8";
		
		public static final String	CHARSET_TYPE = "charset";
		public static final String	SIGN_METHOD="MD5";
		public static final String SIGNATURE = "signature";
		public static final String SIGN_METHOD_TYPE = "signMethod";
	
		public static String encoding;
		private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);
		private static final HttpConnectionManager HTTP_CONNECTION_MANAGER;
		private static final HttpClient HTTP_CLIENT;
	    static {
	    	HttpConnectionManagerParams params = loadHttpConfFromFile();
	    	HTTP_CONNECTION_MANAGER = new MultiThreadedHttpConnectionManager();
	    	HTTP_CONNECTION_MANAGER.setParams(params);
	    	HTTP_CLIENT = new HttpClient(HTTP_CONNECTION_MANAGER);
	    }
	    
	    private static HttpConnectionManagerParams loadHttpConfFromFile(){
			encoding = HTTP_CONTENT_ENCODING;
			HttpConnectionManagerParams params = new HttpConnectionManagerParams();
	        params.setConnectionTimeout(HTTP_CONNECTION_TIMEOUT);
	        params.setSoTimeout(HTTP_SO_TIMEOUT);
	        params.setStaleCheckingEnabled(HTTP_STALE_CHECK_ENABLED);
	        params.setTcpNoDelay(HTTP_TCP_NO_DELAY);
	        params.setDefaultMaxConnectionsPerHost(HTTP_MAX_CONNECTIONS_PER_HOST);
	        params.setMaxTotalConnections(HTTP_MAX_TOTAL_CONNECTIONS);
	        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			return params;
	    }
	    
	    /** 
	     * 除去请求要素中的空值和签名参数
	     * @param para 请求要素
	     * @return 去掉空值与签名参数后的请求要素
	     */
		private static Map<String, String> paraFilter(Map<String, String> para) {
	        Map<String, String> result = new HashMap<String, String>();
	        if (para == null || para.size() <= 0) {
	            return result;
	        }
	        for (String key : para.keySet()) {
	            String value = para.get(key);
	            if (value == null || "".equals(value) || SIGNATURE.equalsIgnoreCase(key)
	                || SIGN_METHOD_TYPE.equalsIgnoreCase(key)) {
	                continue;
	            }
	            result.put(key, value);
	        }
	        return result;
	    }
	    
	    
		 /**
	     * 生成签名
	     * @param req 需要签名的要素
	     * @return 签名结果字符串
	     */
		private static String buildSignature(Map<String, String> req,UnionPay unionPay) {
			String prestr = createLinkString(req, true, false);
			prestr = prestr + QSTRING_SPLIT + upmpMd5Encrypt(unionPay.getSignKey());
			return upmpMd5Encrypt(prestr);
	    }
	    
	    /**
	     * 把请求要素按照“参数=参数值”的模式用“&”字符拼接成字符串
	     * @param para 请求要素
	     * @param sort 是否需要根据key值作升序排列
	     * @param encode 是否需要URL编码
	     * @return 拼接成的字符串
	     */
		public static String createLinkString(Map<String, String> para, boolean sort, boolean encode) {
	        List<String> keys = new ArrayList<String>(para.keySet());
	        if (sort){
	        	Collections.sort(keys);
	        }
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = para.get(key);
	            
	            if (encode) {
					try {
						value = URLEncoder.encode(value,UnionPay.CHARSET);
					} catch (UnsupportedEncodingException e) {
						LOG.info("请求ulr编译失败："+e);
					}
	            }
	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                sb.append(key).append(QSTRING_EQUAL).append(value);
	            } else {
	                sb.append(key).append(QSTRING_EQUAL).append(value).append(QSTRING_SPLIT);
	            }
	        }
	        return sb.toString();
	    }
	    
		private static String upmpMd5Encrypt(String str){
			
			if (str == null) {
				return null;
			}

			MessageDigest messageDigest = null;

			try {
				messageDigest = MessageDigest.getInstance(SIGN_METHOD);
				messageDigest.reset();
				messageDigest.update(str.getBytes(UnionPay.CHARSET));
			} catch (NoSuchAlgorithmException e) {

				return str;
			} catch (UnsupportedEncodingException e) {
				return str;
			}

			byte[] byteArray = messageDigest.digest();

			StringBuffer md5StrBuff = new StringBuffer();

			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				}
				else{
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}

			return md5StrBuff.toString();
			
		}
	    
	    /**
	     * 异步通知消息验证
	     * @param para 异步通知消息
	     * @return 验证结果
	     */
		public static boolean verifySignature(Map<String, String> para,UnionPay unionPay) {
	        String respSignature = para.get(SIGNATURE);
	        // 除去数组中的空值和签名参数
	        Map<String, String> filteredReq = paraFilter(para);
	        String signature = buildSignature(filteredReq,unionPay);
	        return StringUtils.isNotEmpty(respSignature) && respSignature.equals(signature);
			
	    }
	    
	    /**
		 * 应答解析
		 * @param respString 应答报文
		 * @param resp 应答要素
		 * @return 应答是否成功
		 */
		public static boolean verifyResponse(String respString, Map<String, String> resp,UnionPay unionPay) {
			if (respString != null && !"".equals(respString)) {
				// 请求要素
				Map<String, String> para;
				try {
					para = parseQString(respString);
				} catch (Exception e) {
					return false;
				}
				boolean signIsValid = verifySignature(para,unionPay);
				resp.putAll(para);
	            return signIsValid;
			}
			return false;
		}
		
		/**
		 * 解析应答字符串，生成应答要素
		 * 
		 * @param str 需要解析的字符串
		 * @return 解析的结果map
		 * @throws UnsupportedEncodingException
		 */
		private static Map<String, String> parseQString(String str) throws UnsupportedEncodingException {

			Map<String, String> map = new HashMap<String, String>();
			int len = str.length();
			StringBuilder temp = new StringBuilder();
			char curChar;
			String key = null;
			boolean isKey = true;

			for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
				curChar = str.charAt(i);// 取当前字符

				if (curChar == '&') {// 如果读取到&分割符
					putKeyValueToMap(temp, isKey, key, map);
					temp.setLength(0);
					isKey = true;
				} else {
					if (isKey) {// 如果当前生成的是key
						if (curChar == '=') {// 如果读取到=分隔符
							key = temp.toString();
							temp.setLength(0);
							isKey = false;
						} else {
							temp.append(curChar);
						}
					} else {// 如果当前生成的是value
						temp.append(curChar);
					}
				}
			}

			putKeyValueToMap(temp, isKey, key, map);

			return map;
		}
		
		private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
				String key, Map<String, String> map) throws UnsupportedEncodingException {
			if (isKey) {
				key = temp.toString();
				if (key.length() == 0) {
					throw new RuntimeException("QString format illegal");
				}
				map.put(key, "");
			} else {
				if (key.length() == 0) {
					throw new RuntimeException("QString format illegal");
				}
				map.put(key, URLDecoder.decode(temp.toString(), UnionPay.CHARSET));
			}
		}
		
		
	}
