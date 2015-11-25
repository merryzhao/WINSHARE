package com.winxuan.ec.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author cast911
 *
 */
@Entity
@Table(name = "system_config")
public class SystemConfig {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String key;

	@Column
	private String value;

	@Column
	private String description;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group")
	private SystemGroup systemGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public SystemGroup getSystemGroup() {
		return systemGroup;
	}

	public void setSystemGroup(SystemGroup systemGroup) {
		this.systemGroup = systemGroup;
	}

	

}
