/*
 * @(#)JotmFactoryBean.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.jta;

import javax.naming.NamingException;
import org.objectweb.jotm.Current;
import org.objectweb.jotm.Jotm;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * Jotm Factory
 * Spring 3.x 不包含此类
 * @author  HideHai
 * @version 1.0,2011-9-1
 */
public class JotmFactoryBean implements FactoryBean, DisposableBean
{
	private Current jotmCurrent;
	private Jotm jotm;

	public JotmFactoryBean() throws NamingException
	{
		this.jotmCurrent = Current.getCurrent();

		if (this.jotmCurrent == null)
		{
			this.jotm = new Jotm(true, false);
			this.jotmCurrent = Current.getCurrent();
		}
	}

	public void setDefaultTimeout(int defaultTimeout)
	{
		this.jotmCurrent.setDefaultTimeout(defaultTimeout);
	}

	public Jotm getJotm()
	{
		return this.jotm;
	}

	public Object getObject()
	{
		return this.jotmCurrent;
	}

	public Class getObjectType() {
		return this.jotmCurrent.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

	public void destroy()
	{
		if (this.jotm != null)
			this.jotm.stop();
	}
}

