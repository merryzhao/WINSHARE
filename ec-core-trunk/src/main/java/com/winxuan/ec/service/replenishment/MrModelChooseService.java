/*
 * @(#)MrProductFreezeService.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.util.List;

import com.winxuan.ec.model.replenishment.MrModelChoose;

/**
 * description
 * @author  lijunhong
 * @version 1.0,2013-8-28
 */
public interface MrModelChooseService {

	void save(MrModelChoose mrModelChoose);
	
	MrModelChoose get(Long id);

	void delete(MrModelChoose mrModelChoose);

	void update(MrModelChoose mrModelChoose);

	List<MrModelChoose> getAll();
	
	long isExistedByGrade(MrModelChoose mrModelChoose);
}
