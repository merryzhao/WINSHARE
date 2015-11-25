package com.winxuan.ec.admin.controller.order;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.Order;


/**
 * 类名：https/https报文发送处理类
 * 功能：https/https报文发送处理
 * 版本：1.0
 * 日期：2012-10-11
 * 作者：中国银联UPMP团队
 * 版权：中国银联
 * */
public class HttpUtil {
	public static String encoding;
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
	//正式地址
	public static final Long PAYMENT_ID = 127L;
	
	public static final String TEST_URL = "https://mgate.unionpay.com/gateway/merchant/trade";
	public static final String VERSION= "1.0.0";
	public static final String MER_ID= "898510148991003";
	public static final String MER_BACK_END_URL= "http://218.89.170.114/order/onlinepay/unionpaymobile/notify";
	public static final String SECURITY_KEY = "SGXOR43U9W8sGLvJIkl42vuDqiLdl4KM";
	public static final String	CHARSET_TYPE = "charset";
	public static final String	SIGN_METHOD="MD5";
	public static final String	CHARSET="UTF-8";
	public static final String SIGNATURE = "signature";
	public static final String SIGN_METHOD_TYPE = "signMethod";
	private static final String KEY_QUERY_URL = "https://mgate.unionpay.com/gateway/merchant/query";
	//public static final String MER_FRONT_END_URL= "http://10.100.152.37/your_path/yourFrontEndUrl";
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
	    
		public static String post(String url, String encoding, String content) {
			try {
				byte[] resp = post(url, content.getBytes(encoding));
				if (null == resp){
					return null;
				}
				return new String(resp, encoding);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	    
		public static String trede(Order order,String payId){
			String money = String.valueOf(order.getRequidPayMoney().multiply(new BigDecimal(100)).intValue());
			Map<String, String> req = new HashMap<String, String>();
			req.put("version", VERSION);// 版本号
			req.put("charset", CHARSET);// 字符编码
			req.put("transType", "01");// 交易类型
			req.put("merId", MER_ID);// 商户代码
			req.put("backEndUrl", MER_BACK_END_URL);// 通知URL
			//req.put("frontEndUrl", MER_FRONT_END_URL);// 前台通知URL(可选)
			req.put("orderDescription",order.getId());// 订单描述(可选)
			req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));// 交易开始日期时间yyyyMMddHHmmss
			req.put("orderNumber", payId);//订单号(商户根据自己需要生成订单号)：winxuan 使用支付号
			req.put("orderAmount", money);// 订单金额
	        req.put("orderCurrency", "156");// 交易币种(可选)
	        String result = HttpUtil.buildReq(req);
	        String tn = HttpUtil.post(TEST_URL, result);
			return tn;
		}
	    
		public static String post(String url, String content) {
	    	return post(url, encoding, content);
	    }


	    public static byte[] post(String url, byte[] content) {
			try {
				byte[] ret = post(url, new ByteArrayRequestEntity(content));
				return ret;
			} catch (Exception e) {
				return null;
			}
	    }

	    public static byte[] post(String url, RequestEntity requestEntity) throws Exception {

	        PostMethod method = new PostMethod(url);
	        method.addRequestHeader("Connection", "Keep-Alive");
	        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
	        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
	        method.setRequestEntity(requestEntity);
	        method.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        
	        try {
	            int statusCode = HTTP_CLIENT.executeMethod(method);
	            if (statusCode != HttpStatus.SC_OK) {
	                return null;
	            }
	            return method.getResponseBody();

	        } catch (SocketTimeoutException e) {
	        	return null;
	        } catch (Exception e) {
	        	return null;
	        } finally {
	            method.releaseConnection();
	        }
	    }
	    
	    /** 
	     * 除去请求要素中的空值和签名参数
	     * @param para 请求要素
	     * @return 去掉空值与签名参数后的请求要素
	     */
	    public static Map<String, String> paraFilter(Map<String, String> para) {
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
		 * 拼接请求字符串
		 * @param req 请求要素
		 * @return 请求字符串
		 */
		public static String buildReq(Map<String, String> req) {
		    // 除去数组中的空值和签名参数
	        Map<String, String> filteredReq = paraFilter(req);
			// 生成签名结果
			String signature = buildSignature(filteredReq);
			// 签名结果与签名方式加入请求提交参数组中
			filteredReq.put(SIGNATURE, signature);
			filteredReq.put(SIGN_METHOD_TYPE, SIGN_METHOD);
			return createLinkString(filteredReq, false, true);
		}
		
		
		 /**
	     * 生成签名
	     * @param req 需要签名的要素
	     * @return 签名结果字符串
	     */
	    public static String buildSignature(Map<String, String> req) {
			String prestr = createLinkString(req, true, false);
			prestr = prestr + QSTRING_SPLIT + upmpMd5Encrypt(SECURITY_KEY);
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
						value = URLEncoder.encode(value,CHARSET);
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
	    
	    public static String upmpMd5Encrypt(String str){
			
			if (str == null) {
				return null;
			}

			MessageDigest messageDigest = null;

			try {
				messageDigest = MessageDigest.getInstance(SIGN_METHOD);
				messageDigest.reset();
				messageDigest.update(str.getBytes(CHARSET));
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
	    public static boolean verifySignature(Map<String, String> para) {
	        String respSignature = para.get(SIGNATURE);
	        // 除去数组中的空值和签名参数
	        Map<String, String> filteredReq = paraFilter(para);
	        String signature = buildSignature(filteredReq);
	        return StringUtils.isNotEmpty(respSignature) && respSignature.equals(signature);
			
	    }
	    
	    /**
		 * 应答解析
		 * @param respString 应答报文
		 * @param resp 应答要素
		 * @return 应答是否成功
		 */
		public static boolean verifyResponse(String respString, Map<String, String> resp) {
			if (respString != null && !"".equals(respString)) {
				// 请求要素
				Map<String, String> para;
				try {
					para = parseQString(respString);
				} catch (Exception e) {
					return false;
				}
				boolean signIsValid = verifySignature(para);
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
		public static Map<String, String> parseQString(String str) throws UnsupportedEncodingException {

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
				map.put(key, URLDecoder.decode(temp.toString(), CHARSET));
			}
		}
		
		
		/**
	     * 拼接保留域
	     * @param req 请求要素
	     * @return 保留域
	     */
	    public static String buildReserved(Map<String, String> req) {
	        StringBuilder merReserved = new StringBuilder();
	        merReserved.append("{");
	        merReserved.append(createLinkString(req, false, true));
	        merReserved.append("}");
	        return merReserved.toString();
	    }
		
		/**
		 * 交易查询处理
		 * @param req 请求要素
		 * @param resp 应答要素
		 * @return 是否成功
		 */
		public static String query(BatchPay batchPay){
			Map<String, String> req = new HashMap<String, String>();
			req.put("version", VERSION);// 版本号
			req.put("charset", CHARSET);// 字符编码
			req.put("transType", "01");// 交易类型
			req.put("merId", MER_ID);// 商户代码
			req.put("orderTime",new SimpleDateFormat("yyyyMMddHHmmss").format(batchPay.getCreateTime()));// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd
			req.put("orderNumber", batchPay.getId());// 订单号
			// 保留域填充方法
	        Map<String, String> merReservedMap = new HashMap<String, String>();
	        merReservedMap.put("test", "test");
	        req.put("merReserved",buildReserved(merReservedMap));// 商户保留域(可选)		
			String nvp = buildReq(req);
			String respString = HttpUtil.post(KEY_QUERY_URL, nvp);
			return respString;
		}
	}
