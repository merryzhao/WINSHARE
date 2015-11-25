/*
 * @(#)DatePrefixSequenceIdentifierGenerator.java
 *
 * Copyright 2008 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

/**
 * description
 * 
 * @author an.liu
 * @version 1.0,2008-3-5
 */
public class DatePrefixSequenceIdentifierGenerator extends
		AbstrctSequenceIdentifierGenerator {

	protected static final String DATE_FORMAT = "date_format";

	protected static final String DEFAULT_DATE_FORMAT_VALUE = "yyyyMMdd";

	protected DateFormat dateFormat;

	public void configure(Type type, Properties properties, Dialect dialect)
			throws MappingException {
		super.configure(type, properties, dialect);
		dateFormat = new SimpleDateFormat(PropertiesHelper.getString(
				DATE_FORMAT, properties, DEFAULT_DATE_FORMAT_VALUE));
	}

	public String assemble(String primaryKey) {
		Date now = new Date();
		String sDate = dateFormat.format(now);
		return sDate+primaryKey;
	}

}
