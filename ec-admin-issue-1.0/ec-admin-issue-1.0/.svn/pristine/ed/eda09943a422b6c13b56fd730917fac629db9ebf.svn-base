package com.winxuan.ec.admin.controller.dc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author  周斯礼
 * @version 2013-8-20
 */

public class DcRuleForm {

	@NotBlank
	private Long id;
	
	@NotBlank
	@Pattern(regexp="^[1-5]$")
	private Long priority;
	
	@NotNull
	private boolean countrywide;
	
	@NotNull
	private boolean available;
	
	private String address;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public boolean isCountrywide() {
		return countrywide;
	}

	public void setCountrywide(boolean countrywide) {
		this.countrywide = countrywide;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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
	
	
}


