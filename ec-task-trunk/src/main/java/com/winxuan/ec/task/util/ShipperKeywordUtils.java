/*
 * @(#)ShipperKeywordUtils.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.task.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * shipper.properties文件内容：<br />
 * address=信箱,部队,监狱,看守所,乡,村,邮箱 <br />
 * remark=申通,中通,全峰,EMS,邮政,平邮,快递,ems <br />
 * 
 * @author wangbiao
 * @version 1.0 date 2015-1-20
 */
public class ShipperKeywordUtils {

	private static final Log LOG = LogFactory.getLog(ShipperKeywordUtils.class);

	/**
	 * 详细地址关键字
	 */
	private static final String ADDRESS_KEYWORD = "address";

	/**
	 * 备注关键字
	 */
	private static final String REMARK_KEYWORD = "remark";

	private static Properties properties = new Properties();

	static {
		InputStream input = null;
		try {
			input = ShipperKeywordUtils.class.getClassLoader().getResourceAsStream("shipper.properties");
			properties.load(input);
		} catch (IOException e) {
			LOG.error("承运商匹配规则关键字资源配置文件没有找到！");
			throw new RuntimeException("承运商匹配规则关键字资源配置文件没有找到！");
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					LOG.error("读取承运商匹配规则关键字资源配置文件流关闭失败！");
					throw new RuntimeException("读取承运商匹配规则关键字资源配置文件流关闭失败！");
				}
			}
		}

	}

	private ShipperKeywordUtils() {

	}

	/**
	 * 获取到详细地址关键词
	 * @return
	 */
	public static List<String> getAddressKeyword() {
		return getKeyword(ADDRESS_KEYWORD);
	}
	
	/**
	 * 获取备注的关键词
	 * @return
	 */
	public static List<String> getRemarkKeyword() {
		return getKeyword(REMARK_KEYWORD);
	}
	
	/**
	 * 该方法不能向外暴露
	 * @param keyword
	 * @return
	 */
	private static List<String> getKeyword(String keyword) {
		Preconditions.checkArgument(null != keyword,"keyword argument is null!");
		String keywordPro = properties.getProperty(keyword);
		if (StringUtils.isBlank(keywordPro)) {
			return Lists.newArrayList();
		}
		return Splitter.on(",").omitEmptyStrings().trimResults().splitToList(keywordPro);
	}

}
