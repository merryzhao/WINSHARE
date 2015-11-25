package com.winxuan.ec.support.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * cms维护URl的处理
 * @author probook
 *
 */


public class UrlUtils {
	private static final Log LOG = LogFactory.getLog(UrlUtils.class);
	//此方法可以用URL getHost方法代替，主要考虑截取前面整个url
	 public static String getUrlHostReg(String url)
	    {
	        Pattern p =Pattern.compile(".*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);  
	        Matcher matcher = p.matcher(url); 
	        if(matcher.find())
	        {
	        	return matcher.group();
	        }
	        	return "http://www.winxuan.com";
	          
	    }
	 public static String getUrlHost(String urlParam)
	    {
		
	       try {
			URL url = new URL(urlParam);
			return url.getHost();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			LOG.error("UrlUtils----getUrlHost:", e);
			return "http://www.winxuan.com";
		}
	       
	          
	    }
}
