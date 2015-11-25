package com.winxuan.ec.front.controller.groupbuy;

/**
 * 
 * @author cast911 玄玖东
 *
 */
public class GroupInfoForm {

	
	private String name;
	private String companyName;
	private String phone;
	private String content;
	private String sRand;
	public String getsRand() {
		return sRand;
	}
	public void setsRand(String sRand) {
		this.sRand = sRand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
