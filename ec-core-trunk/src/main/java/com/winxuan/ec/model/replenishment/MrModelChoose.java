/*
 * @(#)MrModelChoose.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.replenishment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;

/**
 * description 补货系统：模型选择
 * 
 * @author wangbiao
 * @version 1.0 date 2013-8-19 上午10:24:13
 */

@Entity
@Table(name = "mr_model_choose")
public class MrModelChoose implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8952926065915169798L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 销售分级
	 */
	@Column(name = "grade")
	private String grade;

	/**
	 * 模型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model")
	private Code model;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Code getModel() {
		return model;
	}

	public void setModel(Code model) {
		this.model = model;
	}

}
