package com.winxuan.ec.model.config;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 
 * @author cast911
 *
 */
@Entity
@Table(name = "system_group")
public class SystemGroup {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "systemGroup", targetEntity = SystemConfig.class)
	private Set<SystemConfig> systemConfigs;

	public Set<SystemConfig> getSystemConfigs() {
		return systemConfigs;
	}

	public void setSystemConfigs(Set<SystemConfig> systemConfigs) {
		this.systemConfigs = systemConfigs;
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

}
