package com.winxuan.ec.model.activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-10
 */
@Table(name="activity_show")
@Entity
public class ActivityShow {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	@Column
	private String title;
	@Column
	private String url;
	@Column(name="index_")
	private int index;
	@Column
	private boolean focus;
	@Column
	private boolean available;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}
