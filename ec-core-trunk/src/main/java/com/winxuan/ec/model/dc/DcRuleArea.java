package com.winxuan.ec.model.dc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 上午11:29:06  --!
 * @description:
 ********************************
 */
@Entity
@Table(name = "dcrule_area")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DcRuleArea {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dc")
	private DcRule dc;

	@ManyToOne
	@JoinColumn(name = "area")
	private Area area;

	@ManyToOne
	@JoinColumn(name = "type")
	private Code type;
	
	@Column
	private Long priority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DcRule getDc() {
		return dc;
	}

	public void setDc(DcRule dc) {
		this.dc = dc;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	
}
