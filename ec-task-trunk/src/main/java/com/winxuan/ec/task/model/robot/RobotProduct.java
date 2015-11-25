package com.winxuan.ec.task.model.robot;

import java.util.List;

/**
 * robot Product数据
 * 主要用于导数据 载体
 * @author Heyadong
 * @version 1.0, 2012-3-27
 */
public class RobotProduct {
	/**
	 * barcode
	 */
	private String barcode;
	/**
	 * robot分类Id 
	 */
	private List<Long> robots;
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public List<Long> getRobots() {
		return robots;
	}
	public void setRobots(List<Long> robots) {
		this.robots = robots;
	}
}
