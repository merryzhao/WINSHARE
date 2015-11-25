package com.winxuan.ec.model.cm;

import java.util.HashMap;

/**
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-10-15下午02:49:22 -_-!
 */
public class CmsConstant {
	
	public  static final  HashMap<String, String> VALUETYPE = new HashMap<String, String>();
	
	static {
		VALUETYPE.put("L", "Long");
		VALUETYPE.put("D", "Date");
		VALUETYPE.put("I", "Integer");
		VALUETYPE.put("S", "String");
		VALUETYPE.put("B", "Boolean");
	}

}
