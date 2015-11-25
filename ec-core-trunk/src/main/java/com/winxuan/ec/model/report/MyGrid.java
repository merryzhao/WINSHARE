package com.winxuan.ec.model.report;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 我的报表
 * @author Heshuai
 *
 */
@Entity
@Table(name="my_report_grid")
public class MyGrid implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8369049586100814767L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "employee")
	private Long employee;
	

	
	
    
	
    @Column(name = "grid")
	private Long gridList;

	@Transient
    private List<Grid> list;
	
	public List<Grid> getList() {
		return list;
	}

	public void setList(List<Grid> list) {
		this.list = list;
	}

	public Long getGridList() {
		return gridList;
	}

	public void setGridList(Long gridList) {
		this.gridList = gridList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	

	
}
