package com.winxuan.ec.task.service.ec;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.task.model.robot.RobotCategory;



/**
 * EC分类dao
 * 主要用于与robot系统新抓的分类同步.
 * @author Heyadong
 * @version 1.0, 2012-3-23
 */
public interface EcCategoryService  extends Serializable{
	/**
	 * 同步Robot分类,添加到Ec分类下.
	 * 同步的个数
	 * @param categories
	 */
	void syncCategory(List<RobotCategory> categories) throws Exception;
}
