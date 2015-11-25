package com.winxuan.ec.task.service.robot.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.task.dao.robot.RobotCategoryDao;
import com.winxuan.ec.task.model.robot.RobotCategory;
import com.winxuan.ec.task.service.robot.RobotCategoryService;

/**
 * 实现类
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Service("robotCategoryService")
@Transactional(rollbackFor=Exception.class)
public class RobotCategoryServiceImpl implements RobotCategoryService{

	private static final long serialVersionUID = -1600841776865456900L;
	@Autowired
	private RobotCategoryDao robotCategoryDao;
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	@Override
	public List<RobotCategory> getNewCategories() throws Exception {
		return robotCategoryDao.getNewCategories();
	}

}
