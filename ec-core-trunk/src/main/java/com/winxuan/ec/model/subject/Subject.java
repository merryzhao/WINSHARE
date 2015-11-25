package com.winxuan.ec.model.subject;

import java.io.Serializable;
import java.util.Date;

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
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "_subject")
public class Subject implements Serializable {

	public static final boolean DEPLOY_YES = true;
	public static final boolean DEPLOY_NO = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5366436547159084668L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String title;

	@Column
	private boolean deploy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sort")
	private Code sort;
    
    @Column
    private String tagurl;

	@Column(name = "update_time")
	private Date updateTime;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isDeploy() {
		return deploy;
	}

	public void setDeploy(boolean deploy) {
		this.deploy = deploy;
	}

	public Code getSort() {
		return sort;
	}

	public void setSort(Code sort) {
		this.sort = sort;
	}

	public String getTagurl() {
		return tagurl;
	}

	public void setTagurl(String tagurl) {
		this.tagurl = tagurl;
	}

}
