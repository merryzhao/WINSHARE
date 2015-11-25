package com.winxuan.ec.model.returnorder;

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
 * @description:
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2015年1月16日 下午1:26:35
 */
@Entity
@Table(name = "returnorder_package_relate")
public class ReturnOrderPackageRelate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returnorder_package_id")
	private ReturnOrderPackage returnOrderPackage;
	
	@Column(name ="relateid")
	private String relateid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "relateship")
	private Code relateship;
	
	@Column(name="status")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReturnOrderPackage getReturnOrderPackage() {
		return returnOrderPackage;
	}

	public void setReturnOrderPackage(ReturnOrderPackage returnOrderPackage) {
		this.returnOrderPackage = returnOrderPackage;
	}

	public String getRelateid() {
		return relateid;
	}

	public void setRelateid(String relateid) {
		this.relateid = relateid;
	}

	public Code getRelateship() {
		return relateship;
	}

	public void setRelateship(Code relateship) {
		this.relateship = relateship;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
