package com.winxuan.ec.admin.controller.roadmap;

import java.util.Date;

 

import com.winxuan.ec.model.user.Employee;
/**
 * description
 * @author renshiyong
 * @version 1.0,2011-8-23
 */

public class RoadmapBean {
	private Long id;
	private Date createTime;
	private String content;
	private Employee employee;
    private String noStylecontent;
    public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setNoStylecontent(String noStylecontent) {
		this.noStylecontent = noStylecontent;
	}
	public String getNoStylecontent() {
		return noStylecontent;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
