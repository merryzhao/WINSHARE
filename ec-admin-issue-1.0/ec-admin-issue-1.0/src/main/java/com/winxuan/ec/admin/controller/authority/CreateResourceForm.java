package com.winxuan.ec.admin.controller.authority;

import java.io.Serializable;

/**
 * 资源添加表单数据
 * @author sunflower
 *
 */
public class CreateResourceForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1790478831344968192L;
	
	private String code;
	private String value;
	private String description;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
