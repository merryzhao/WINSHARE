/*
 * @(#)PromotionQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.support.util.DateUtils;

/**
 * 卖家查询form
 * 
 * @author zhongsen
 * @version 1.0,2011-9-28
 */
public class SellerQueryForm {
	private String nameValue;
	private int nameType;
	private List<String> businessScope;
	private Long shopState;
	private String dateBegin;
	private String dateEnd;
	private int dateType;

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	public int getNameType() {
		return nameType;
	}

	public void setNameType(int nameType) {
		this.nameType = nameType;
	}

	public List<String> getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(List<String> businessScope) {
		this.businessScope = businessScope;
	}

	public Long getShopState() {
		return shopState;
	}

	public void setShopState(Long shopState) {
		this.shopState = shopState;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	/**
	 * 构建参数map
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> getParamsMap() throws ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(nameValue)) {
			switch (nameType) {
			case 0:
				map.put("sellerName", nameValue);
				break;
			case 1:
				map.put("shopName", "%" + nameValue + "%");
				break;
			default:
				break;
			}
		}
		if (businessScope != null) {
			StringBuilder sb = new StringBuilder();
			for (String scope : businessScope) {
				sb.append(scope);
			}
			map.put("businessScope", sb.toString());
		}
		if (shopState != 0) {
			map.put("shopState", shopState);
		}
		boolean beginNotBlank = StringUtils.isNotBlank(dateBegin);
		boolean endNotBlank = StringUtils.isNotBlank(dateEnd);
		if (beginNotBlank || endNotBlank) {
			String[] paramArray = this.getDateParam(dateType);
			if (beginNotBlank && endNotBlank) {
				map.put(paramArray[0], DateUtils
						.getEarlyInTheDay(new SimpleDateFormat(
								DateUtils.SHORT_DATE_FORMAT_STR)
								.parse(dateBegin)));
				map.put(paramArray[1],
						DateUtils.getLateInTheDay(new SimpleDateFormat(
								DateUtils.SHORT_DATE_FORMAT_STR).parse(dateEnd)));
			}
			if (!beginNotBlank && endNotBlank) {
				map.put(paramArray[1],
						DateUtils.getLateInTheDay(new SimpleDateFormat(
								DateUtils.SHORT_DATE_FORMAT_STR).parse(dateEnd)));
			}
			if (beginNotBlank && !endNotBlank) {
				map.put(paramArray[0], DateUtils
						.getEarlyInTheDay(new SimpleDateFormat(
								DateUtils.SHORT_DATE_FORMAT_STR)
								.parse(dateBegin)));
			}
		}
		map.put("shopManager", true);
		return map;
	}

	private String[] getDateParam(int dateType) {
		String[] paramArray = {};
		switch (dateType) {
		// 开通
		case 0:
			paramArray = new String[] { "createDateBegin", "createDateEnd" };
			break;
		// 激活
		case 1:
			paramArray = new String[] { "activeDateBegin", "activeDateEnd" };
			break;
		// 截止
		case 2:
			paramArray = new String[] { "endDateBegin", "endDateEnd" };
			break;
		default:
			break;
		}
		return paramArray;
	}
}
