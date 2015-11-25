package com.winxuan.ec.task.model.robot;

/**
 * Robot采集系统的分类
 * @author Heyadong
 * @version 1.0, 2012-3-23
 */
public class RobotCategory {
	public RobotCategory(){}
	public RobotCategory(Integer id, String label, String title, Integer parent, String code){
		this.id = id;
		this.label = label;
		this.title = title;
		this.parent = parent;
		this.code = code;
	}
	/**
	 * 最新分类code状态标识.
	 */
	public final static  String CODE_NEW = "new";
	/**
	 * robotID,自增..与EC系统的 newshop.robot_category.robot_id是对应的
	 */
	private Integer id;
	/**
	 * 卓越ID路径
	 */
	private String label;
	/**
	 * 名字
	 */
	private String title;
	/**
	 * 父ID
	 */
	private Integer parent;
	/**
	 * code
	 */
	private String code;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
