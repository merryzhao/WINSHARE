/*
 * @(#)MySQL5InnoDBDialect.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.hibernate;


import org.hibernate.Hibernate;
import org.hibernate.dialect.function.StandardSQLFunction;
/**
 * description
 * @author  huangyixiang
 * @version 2011-12-7
 */
public class MySQLDialect extends org.hibernate.dialect.MySQLDialect
{
    @SuppressWarnings("deprecation")
	public MySQLDialect()
    {
        super();
        registerFunction("useindex", new StandardSQLFunction("useindex", Hibernate.BOOLEAN));
    }
}