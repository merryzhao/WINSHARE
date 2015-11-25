package com.winxuan.ec.task.service.robot;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.task.model.robot.RobotCategory;

/**
 * Robot系统 categoryService
 * @author Heyadong
 * @version 1.0, 2012-3-23
 */
public interface RobotCategoryService extends Serializable{
	/**
	 * 抓到的全部最新的图书分类
	 * @return
	 */
	public List<RobotCategory> getNewCategories() throws Exception;
	
}
