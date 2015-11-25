/*
 * @(#)SearchRecommendation
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * description
 * 
 * @author huangyixiang
 * @version 1.0,2011-11-28
 */
@Entity
@Table(name = "search_recommendation")
public class SearchRecommendation implements Serializable{

	
	public static final short MODE_SEARCH = 3;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8174521178030923172L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String recommendid;

	@Column
	private String recommendation;

	@Column
	private float preference;

	@Column
	private short mode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecommendid() {
		return recommendid;
	}

	public void setRecommendid(String recommendid) {
		this.recommendid = recommendid;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public float getPreference() {
		return preference;
	}

	public void setPreference(float preference) {
		this.preference = preference;
	}

	public short getMode() {
		return mode;
	}

	public void setMode(short mode) {
		this.mode = mode;
	}
}
