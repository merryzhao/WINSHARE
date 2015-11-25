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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.user.Employee;

/**
 * 资源组
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "resource_group")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResourceGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8136219824306722518L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToMany(mappedBy = "resourceGroups", fetch = FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Resource> resources;
	
	@ManyToMany(mappedBy = "resourceGroups", fetch = FetchType.LAZY)
	private Set<Employee> employees;

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

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee employee){
		if(employees == null){
			employees = new HashSet<Employee>();
		}
		employees.add(employee);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	
	public void addResource(Resource resource){
		if(resources == null){
			resources = new HashSet<Resource>();
		}
		resources.add(resource);
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
		if (!(obj instanceof ResourceGroup)) {
			return false;
		}
		ResourceGroup other = (ResourceGroup) obj;
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
