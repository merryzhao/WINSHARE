package com.winxuan.ec.model.dc;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.code.Code;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 上午11:28:59 --!
 * @description:
 ******************************** 
 */
@Entity
@Table(name = "dcrule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DcRule implements Serializable {

	public static final Long CHENG_DU = 5L;

	public static final Long BEI_JING = 6L;

	private static final long serialVersionUID = -5142226640287180118L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "location")
	private Code location;

	@Column
	private Long priority;

	@Column
	private Boolean countrywide;

	@Column
	private String address;

	@Column
	private String description;

	@Column
	private Boolean available;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dc", targetEntity = DcRuleArea.class)
	private Set<DcRuleArea> dcAreaList;

	@Transient
	private Integer fillRate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Code getLocation() {
		return location;
	}

	public void setLocation(Code location) {
		this.location = location;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Boolean getCountrywide() {
		return countrywide;
	}

	public void setCountrywide(Boolean countrywide) {
		this.countrywide = countrywide;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isAvailable() {
		return available;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Set<DcRuleArea> getDcAreaList() {
		return dcAreaList;
	}

	public void setDcAreaList(Set<DcRuleArea> dcAreaList) {
		this.dcAreaList = dcAreaList;
	}

	public Integer getFillRate() {
		return fillRate;
	}

	public void setFillRate(Integer fillRate) {
		this.fillRate = fillRate;
	}

	public void addDcArea(DcRuleArea dcArea) {
		if (CollectionUtils.isEmpty(this.dcAreaList)) {
			this.dcAreaList = new HashSet<DcRuleArea>();
		}
		this.dcAreaList.add(dcArea);
	}

	public void remove(DcRuleArea dcArea) {
		if (CollectionUtils.isEmpty(this.dcAreaList)) {
			return;
		}
		this.dcAreaList.remove(dcArea);
	}

}
