/*
 * @(#)InvoiceSequenceIdentifierGenerator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.Type;


/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-29
 */
public class InvoiceSequenceIdentifierGenerator extends AbstrctSequenceIdentifierGenerator {

	protected static final String DEFAULT_DATE_FORMAT_VALUE = "yyyyMM";

	protected DateFormat dateFormat;

	public void configure(Type type, Properties properties, Dialect dialect)
	throws MappingException {
		super.configure(type, properties, dialect);
		dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT_VALUE);
	}

	@Override
	public String assemble(String sPrimaryKey) {
		Date now = new Date();
		String sDate = dateFormat.format(now);
		return sDate+sPrimaryKey;
	}
}

