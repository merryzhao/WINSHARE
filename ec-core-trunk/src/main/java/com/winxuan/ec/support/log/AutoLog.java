/*
 * @(#)AutoLog.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.log;

import java.util.Date;

import com.winxuan.framework.validator.Principal;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-4
 */
public interface AutoLog {
	
	void setTargetId(String targetId);
	
	void setFieldName(String fieldName);

	void setOriginalValue(String originalValue);

	void setChangedValue(String changedValue);

	void setUpdateTime(Date updateTime);

	void setOperator(Principal operator);
}
