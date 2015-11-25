/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.seller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.Seller;
import com.winxuan.framework.util.security.MD5Custom;

/**
 * 
 * 
 * @author rsy
 * @version 1.0,2011-9-27
 */
public class ShopForm {

	private static final long AREA = 1510L;
	private Long id;

	// 用户名
	@NotBlank(message = "用户名不能为空")
	private String name;

	// 密码
	@NotBlank(message = "密码名不能为空")
	private String passWrod;

	// 店铺名称
	@NotBlank(message = "店铺名不能为空")
	private String shopName;

	@NotBlank(message = "客服电话不能为空")
	private String serviceTel;

	@NotNull(message = "运费不能为空")
	private BigDecimal deliveryFee;

	// 经营范围
	@NotNull(message = "经营范围不能为空")
	private List<String> businessScope;

	// 公司名称
	@NotBlank(message = "公司名不能为空")
	private String companyName;

	// 负责人
	@NotBlank(message = "负责人不能为空")
	private String chargeMan;

	// 电话
	@NotBlank(message = "电话不能为空")
	private String phone;

	// 手机
	@NotBlank(message = "手机不能为空")
	private String mobilePhone;

	// 邮箱
	@NotBlank(message = "邮箱不能为空")
	private String email;

	// 地址
	@NotBlank(message = "地址不能为空")
	private String address;

	// 银行名称
	@NotBlank(message = "银行名不能为空")
	private String bank;

	// 银行账号
	@NotBlank(message = "银行账号不能为空")
	private String bankAcount;

	// 邮编
	@NotBlank(message = "邮编不能为空")
	private String zipCode;

	// log地址
	private String logo;

	// 截止日期
	@NotBlank(message = "截止日期不能为空")
	private String endDate;

	// 存储方式
	private Long storageType;
	
	private String operate;

	public Long getStorageType() {
		return storageType;
	}

	public void setStorageType(Long storageType) {
		this.storageType = storageType;
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

	public String getPassWrod() {
		return passWrod;
	}

	public void setPassWrod(String passWrod) {
		this.passWrod = passWrod;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAcount() {
		return bankAcount;
	}

	public void setBankAcount(String bankAcount) {
		this.bankAcount = bankAcount;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setBusinessScope(List<String> businessScope) {
		this.businessScope = businessScope;
	}

	public List<String> getBusinessScope() {
		return businessScope;
	}

	public Seller getSeller(Seller seller) {
		if (seller == null) {
			seller = new Seller();
		}
		if("add".equals(operate)){
			seller.setRegisterTime(new Date());
		}
		if (seller.getPassword() == null
				|| !seller.getPassword().equals(passWrod)) {
			seller.setPassword(MD5Custom.encrypt(passWrod));
		}
		seller.setEmail(email);
		seller.setMobile(mobilePhone);
		seller.setPhone(phone);
		seller.setRealName(chargeMan);
		seller.setShopManager(true);
		seller.setAvailable(true);
		seller.setName(name);// 卖家账号
		Area district = new Area();
		district.setId(AREA);
		seller.setSource(new Code(Code.USER_SOURCE_EC_SELLER));
		seller.setLocation(district);// 储存地点
		seller.setStorageType(new Code(storageType));
		return seller;
	}

	public Shop getShop(Shop shop, Employee createUser) throws ParseException {
		if (shop == null) {
			shop = new Shop();
		}
		if("add".equals(operate)){
			shop.setCreateDate(new Date());
		}
		shop.setBank(bank);
		shop.setBankAcount(bankAcount);
		String bs = "";
		if (businessScope != null) {
			for (String s : businessScope) {
				bs = bs + s;
			}
		}
		shop.setBusinessScope(bs);
		shop.setCompanyName(companyName);
		shop.setDeliveryFee(deliveryFee);
		shop.setServiceTel(serviceTel);
		if (endDate != null && !"".equals(endDate.trim())) {
			shop.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
		}
		if (createUser != null) {
			shop.setCreateUser(createUser);
		}
		shop.setZipCode(zipCode);
		shop.setShopName(shopName);
		shop.setAddress(address);
		return shop;
	}

	public void setFormValue(Shop shop) {
		id = shop.getId();
		if (shop.getSellers() != null) {
			for (Seller s : shop.getSellers()) {
				if (s.isShopManager()) {
					name = s.getName();
					passWrod = s.getPassword();
					phone = s.getPhone();
					mobilePhone = s.getMobile();
					email = s.getEmail();
					chargeMan = s.getRealName();
					storageType = s.getStorageType().getId();
					break;
				}

			}
		}
		List<String> scops = new ArrayList<String>();
		String scop = shop.getBusinessScope();
		if (scop != null && !"".equals(scop.trim())) {
			for (int i = 0; i < scop.length(); i++) {
				scops.add(scop.substring(i, i + 1));
			}
		}
		businessScope = scops;
		bank = shop.getBank();
		bankAcount = shop.getBankAcount();
		companyName = shop.getCompanyName();
		if (shop.getEndDate() != null) {
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(shop
					.getEndDate());
		}
		shopName = shop.getShopName();
		address = shop.getAddress();
		zipCode = shop.getZipCode();
		logo = shop.getLogo();
		deliveryFee = shop.getDeliveryFee();
		serviceTel = shop.getServiceTel();

	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	
}
