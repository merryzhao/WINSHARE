/*
 * @(#)InoviceItemIdGenerator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.component.ec;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import com.winxuan.ec.support.util.AbstrctSequenceIdentifierGenerator;

/**
 * description
 * 
 * @author HideHai x
 * @version 1.0,2011-12-12
 */
@Component("invoiceItemIdGenerator")
public class InvoiceItemIdGenerator implements Serializable {

	private static final long serialVersionUID = -6408625238630505985L;
	
	private static AbstrctSequenceIdentifierGenerator generator = null;

	private static SessionImplementor implementor = null;
	
	private static Properties properties = new Properties();
	static {
		properties.setProperty("table", "serializable");
		properties.setProperty("column", "maxid");
		properties.setProperty("target_name", "tablename");
		properties.setProperty("target_value", "invoice_item");
		properties.setProperty("length", "5");
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
	}

	@Autowired
	private SessionFactory sessionFactory;


	public String generator() {
		generator = new AbstrctSequenceIdentifierGenerator() {
			private static final long serialVersionUID = 1L;
			private static final String DEFAULT_DATE_FORMAT_VALUE = "yyyyMM";
			private static final String GENERATOR_BEFORE = "50";
			private DateFormat dateFormat = new SimpleDateFormat(
					DEFAULT_DATE_FORMAT_VALUE);;

			@Override
			public String assemble(String sPrimaryKey) {
				Date now = new Date();
				String sDate = dateFormat.format(now);
				return GENERATOR_BEFORE + sDate + sPrimaryKey;
			}
		};
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.AUTO);
		implementor = (SessionImplementor) session;
		generator.configure(null, properties, Dialect.getDialect(properties));
		return generator.generate(implementor, null).toString();
	}

}
