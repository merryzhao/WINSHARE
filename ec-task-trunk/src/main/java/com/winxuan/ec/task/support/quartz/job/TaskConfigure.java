/*
 * @(#)TaskConfigure.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.quartz.job;

import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-12-14
 */
public interface TaskConfigure {
	
	public static final int LEVEL_MAIL = MagicNumber.ONE;
	public static final int LEVEL_MESSAGE = MagicNumber.TWO;
	public static final int LEVEL_ALL = MagicNumber.THREE;

	int getNotifyLevel();
	
	void doNotify(String... msg);
}

