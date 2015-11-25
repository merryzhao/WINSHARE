/*
 * @(#)Area.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.area;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.tree.Tree;


/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-7
 */
@Entity
@Table(name = "area")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Area extends Tree<Area>{

	/**
	 * 所有区域
	 */
	public static final Long ROOT = new Long(22);

	/**
	 * 中国
	 */
	public static final Long CHINA = new Long(23);
	
	/**
	 * 成都
	 */
	public static final Long CHENDU = new Long(1507);
	
	/**
	 * 成都-金牛区
	 */
	public static final Long CHENDU_JINNIU = 1510L;
	
	/**
	 * 香港
	 */
	public static final Area AREA_HONGKONG = new Area(179L);
	
	/**
	 * 澳门
	 */
	public static final Area AREA_MACAO = new Area(151L);
	
	/**
	 * 台湾
	 */
	public static final Area AREA_TAIWAN = new Area(176L);
	
	public static final Area AREA_CHINA = new Area(CHINA);

	private static final long serialVersionUID = -3279470380280488328L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private boolean available;

	@Column(name = "supportcod")
	private boolean supportCod;

	@Column(name = "index_")
	private int index;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Area parent;
	
	@Column
	private int level;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = Area.class)
	@OrderBy("index")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Area> children;
	
	public Area(){
	}
	
	public Area(Long id){
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}
	
	public boolean getHasChildren(){
		return this.hasChildren();
	}
	
	public Set<Area> getChildList(){
		return this.children;
	}

	public Set<Area> getChildren() {
		return children;
	}

	public void setChildren(Set<Area> children) {
		this.children = children;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isSupportCod() {
		return supportCod;
	}

	public void setSupportCod(boolean supportCod) {
		this.supportCod = supportCod;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * 是否是中国大陆
	 * @return
	 */
	public boolean isMainlandChina() {
		return isGrandChild(AREA_CHINA) && !isGrandChild(AREA_HONGKONG)
				&& !isGrandChild(AREA_MACAO) && !isGrandChild(AREA_TAIWAN);
	}
	
	/**
	 * 是否西南三省+重庆
	 */
	public boolean isSouthWest() {
		return 157L == getId() || 175L == getId() || 181L == getId() || 183L == getId();
	}
	
	/**
	 * 是否是特别行政区，即是否是港澳台地区
	 */
	public boolean isPecialAdministrativeRegion() {
		return new Long(179L).equals(getId()) || new Long(151L).equals(getId()) || new Long(176L).equals(getId());
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
