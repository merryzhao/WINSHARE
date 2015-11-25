package com.winxuan.ec.task.service.ec.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.task.dao.ec.EcCategoryDao;
import com.winxuan.ec.task.dao.robot.RobotCategoryDao;
import com.winxuan.ec.task.model.robot.RobotCategory;
import com.winxuan.ec.task.service.ec.EcCategoryService;
/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-22
 */
@Service(value="ecCategoryService")
@Transactional(rollbackFor=Exception.class)
public class EcCategoryServiceImpl implements EcCategoryService {

	private static final long serialVersionUID = 7563950883673478110L;

	@Autowired
	private EcCategoryDao ecCategoryDao;
	
	@Autowired
	private RobotCategoryDao robotCategoryDao;
	@Override
	public void syncCategory(List<RobotCategory> categories) throws Exception {
		ecCategoryDao.syncCategory(categories);
		//更新code为同步日期
		robotCategoryDao.syncUpdate(categories);
	}

}
