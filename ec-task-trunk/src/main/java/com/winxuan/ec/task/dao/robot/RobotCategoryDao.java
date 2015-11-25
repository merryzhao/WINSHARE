package com.winxuan.ec.task.dao.robot;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.task.model.robot.RobotCategory;

/**
 * Robot采集系统分类
 * @author Heyadong
 * @version 1.0, 2012-3-23
 */
public interface RobotCategoryDao extends Serializable{
	/**
	 * 抓到的全部最新的图书分类
	 * @return
	 */
	public List<RobotCategory> getNewCategories() throws Exception;
	
	/**
	 * 同步更新将状态为new的分类.修改为当前日期
	 * @param categories
	 */
	public void syncUpdate(List<RobotCategory> categories) throws Exception;
}
