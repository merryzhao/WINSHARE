package com.winxuan.ec.support.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;

import com.winxuan.ec.support.util.http.HttpProtocolHandler;
import com.winxuan.ec.support.util.http.HttpRequest;
import com.winxuan.ec.support.util.http.HttpResponse;
import com.winxuan.ec.support.util.http.HttpResultType;
import common.Logger;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-25
 */
public class IpUtils {
	private static final String UNKNOWN = "unknown";
	private static final Logger LOG = Logger.getLogger(IpUtils.class);
	/**
	 * 淘宝ip地址共享库
	 */
	private static final String TAOBAO_IP_URL = "http://ip.taobao.com/service/getIpInfo2.php";
	private static JsonConfig jsonConfig = new JsonConfig();
	private static  boolean init = false;

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getAddressByIp(String ipString){  
		NameValuePair[] params = {new NameValuePair("ip",ipString)};
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
	    request.setCharset("UTF-8");
	    request.setParameters(params);
	    request.setUrl(TAOBAO_IP_URL);
	    HttpResponse response = null;
	    String strResult = "";
		try {
			response = httpProtocolHandler.execute(request, "", "");
			 if (response == null) {
		         return null;
		     }
		    strResult = response.getStringResult();
		} catch (HttpException e) {
			LOG.error("网络异常"+e);
		} catch (IOException e) {
			LOG.error(e);
		}
        return strResult;  
    }
	
	public static void jsonFilter(){
		PropertyFilter propertyFilter = new PropertyFilter() {
			List<String> filter = Arrays.asList(new String[]{"country","area","region","city","county","data","code","ip","isp"});
			public boolean apply(Object srouce, String name, Object value) {
				return !filter.contains(name);
			}
		};
		jsonConfig.setJsonPropertyFilter(propertyFilter);
		init = true;
	}
	
	public static JsonConfig config(){
		if(!init){
			jsonFilter();
		}
		return jsonConfig;
	}
}
