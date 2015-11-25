/*
 * @(#)SapReplenishmentService.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.sap;

import java.util.List;

import com.winxuan.ec.model.replenishment.MrProductFreeze;



/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-2
 */
public interface SapReplenishmentService {
	void sendReplenishmentItems(Object[] params);
	void updateReplenishmentItems();
	void transferReplenishmentItems(final List<MrProductFreeze> mrProductFreezes);
}
