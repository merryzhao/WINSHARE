package com.winxuan.ec.model.authority;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 权限资源类
 * @author sunflower
 *
 */
@Entity
@Table(name = "resource")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7198011113698420664L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "resource_group_relation", joinColumns = { @JoinColumn(name = "resource") }, inverseJoinColumns = { @JoinColumn(name = "resourcegroup") })
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ResourceGroup> resourceGroups;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "createtime")
	private Date createTime;

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<ResourceGroup> getResourceGroups() {
		return resourceGroups;
	}

	public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
		this.resourceGroups = resourceGroups;
	}
	
	public void addResourceGroup(ResourceGroup resourceGroup){
		if(resourceGroups == null){
			resourceGroups = new HashSet<ResourceGroup>();
		}
		resourceGroups.add(resourceGroup);
	}

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Resource)) {
			return false;
		}
		Resource other = (Resource) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
