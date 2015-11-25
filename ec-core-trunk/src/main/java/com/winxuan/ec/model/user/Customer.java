/*
 * @(#)Customer.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.annotations.BatchSize;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.ec.model.customer.CustomerExtension;
import com.winxuan.ec.model.customer.CustomerPayee;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user")
@BatchSize(size = 10)
public class Customer extends User {

	/**
	 * 普通会员
	 */
	public static final short GRADE_TYPE_BRONZE = 0;

	/**
	 * 银卡会员
	 */
	public static final short GRADE_TYPE_SILVER = 1;

	/**
	 * 金卡会员
	 */
	public static final short GRADE_TYPE_GOLD = 2;

	public static final Customer DIRECT_CUSTOMER = new Customer(
			GRADE_TYPE_BRONZE);

	
	/**
	 * 一般登录
	 */
	public static final short NORMAL_LOGIN_TYPE = 0;
	
	/**
	 * 联合登录
	 */
	public static final short UNION_LOGIN_TYPE  = 1; 
	
	/**
	 * 返利登录
	 */
	public static final short FANLI_LOGIN_TYPE = 2;
	
	public static final Map<Short, String> ALL_GRADE_TYPE = new TreeMap<Short, String>();
	/**
	 * 会员等级英语简称
	 */
	public static final Map<Short, String> GRADE_TYPE_ES = new TreeMap<Short, String>();

	private static final long serialVersionUID = 1L;

	static {
		ALL_GRADE_TYPE.put(GRADE_TYPE_BRONZE, "普通会员");
		ALL_GRADE_TYPE.put(GRADE_TYPE_SILVER, "银卡会员");
		ALL_GRADE_TYPE.put(GRADE_TYPE_GOLD, "金卡会员");
	}
	static {
		GRADE_TYPE_ES.put(GRADE_TYPE_BRONZE, "N");
		GRADE_TYPE_ES.put(GRADE_TYPE_SILVER, "S");
		GRADE_TYPE_ES.put(GRADE_TYPE_GOLD, "G");
	}
	/**
	 * 用户等级
	 */
	@Column
	private short grade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private CustomerAccount account;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", targetEntity = CustomerAddress.class)
	private List<CustomerAddress> addressList;
	


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country")
	private Area country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province")
	private Area province;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city")
	private Area city;

	@Column
	private String avatar;

	@Column(name = "firstorder")
	private Short firstOrder;
	
	@Column(name = "lasttradetime")
	private Date lastTradeTime;

	@Column
	private BigDecimal discount = BigDecimal.ONE;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@Access(value = AccessType.FIELD)
	private CustomerExtension extension;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@Access(value = AccessType.FIELD)
	private CustomerExtend customerExtend;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@Access(value = AccessType.FIELD)
	private CustomerPayee payee;

	@Column(name = "nickname")
	private String nickName;
	
	@Column(name="lastlogintype")
	private short lastLoginType;
	
	public Customer(){
		
	}

	public Customer(Long id) {
		setId(id);
	}

	public Customer(short grade) {
		setGrade(grade);
	}

	public Area getCountry() {
		return country;
	}

	public void setCountry(Area country) {
		this.country = country;
	}

	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	
	public short getGrade() {
		return grade;
	}

	public void setGrade(short grade) {
		this.grade = grade;
	}

	public CustomerAccount getAccount() {
		return account;
	}

	public void setAccount(CustomerAccount account) {
		this.account = account;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public List<CustomerAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<CustomerAddress> addressList) {
		this.addressList = addressList;
	}

	public Date getLastTradeTime() {
		return lastTradeTime;
	}

	public void setLastTradeTime(Date lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}
	
	public short getLastLoginType() {
		return lastLoginType;
	}

	public void setLastLoginType(short lastLoginType) {
		this.lastLoginType = lastLoginType;
	}

	/**
	 * 获取用户设为常用的地址，没有常用就返回最后一条
	 * 
	 * @return
	 */
	public CustomerAddress getUsualAddress() {
		if (addressList != null && !addressList.isEmpty()) {
			CustomerAddress returnAddress = null;
			for (CustomerAddress address : addressList) {
				if (address.isUsual()) {
					return address;
				}
				returnAddress = address;
			}
			return returnAddress;
		}
		return null;
	}

	public CustomerExtension getExtension() {
		try {
			extension.getId();
			return extension;
		} catch (Exception e) {
			return null;
		}
	}

	public void setExtension(CustomerExtension extension) {
		this.extension = extension;
	}

	public CustomerPayee getPayee() {
		try {
			payee.getId();
			return payee;
		} catch (Exception e) {
			return null;
		}
	}

	public void setPayee(CustomerPayee payee) {
		this.payee = payee;
	}

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * 获取代理价格
	 * 
	 * @param productSale
	 * @return
	 */
	private BigDecimal getAgentPrice(ProductSale productSale) {
		BigDecimal salePrice = productSale.getEffectivePrice();
		if (channel.getId().equals(Channel.CHANNEL_AGENT)) {
			return salePrice.multiply(discount)
					.setScale(1, BigDecimal.ROUND_HALF_UP).setScale(2);
		}
		return salePrice;
	}

	/**
	 * 根据用户等级、代理折扣等取得商品销售价格<br/>
	 * 图书音像根据代理折扣进行折上折，或根据会员等级计算会员价（原来商品是7折以上时才有会员价）
	 * 
	 * @param productSale
	 * @return
	 */
	public BigDecimal getSalePrice(ProductSale productSale) {
		BigDecimal salePrice = productSale.getEffectivePrice();
		Long sort = productSale.getProduct().getSort().getId();
		if (sort.equals(Code.PRODUCT_SORT_BOOK)
				|| sort.equals(Code.PRODUCT_SORT_VIDEO)) {
			if (channel.getId().equals(Channel.CHANNEL_AGENT)) {
				return getAgentPrice(productSale);
			} else if (grade == GRADE_TYPE_SILVER) {
				return productSale.getSilverPrice();
			} else if (grade == GRADE_TYPE_GOLD) {
				return productSale.getGoldPrice();
			}
		}
		return salePrice;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String getDisplayName() {
		if (!StringUtils.isBlank(nickName)) {
			return this.nickName;
		}

		return super.getName();
	}

	/**
	 * 替换邮箱字符
	 * 
	 * @param email
	 * @return
	 */
	public String getProtectionName() {
		String email = this.getDisplayName();
		if (super.checkEmail(email)) {
			int start = email.lastIndexOf("@");
			int end = email.lastIndexOf(".");
			if (start > 0) {
				String emailhost = email.substring(start, end);
				StringBuilder buildre = new StringBuilder(emailhost.length());
				for (int j = 0; j < emailhost.length(); j++) {
					buildre.append("*");
				}
				email = email.replace(emailhost, buildre.toString());
			}
		}
		return email;

	}

	public void setFirstOrder(boolean firstOrder) {
		if(firstOrder){
			this.firstOrder=MagicNumber.ONE; // 首次下单
		}else{
			this.firstOrder=MagicNumber.ZERO; //　非首次下单
		}
	}

	public boolean isFirstOrder() {
		if(firstOrder==null){
			return false;
		}else{
			return MagicNumber.ONE==firstOrder;
		}
	}
	
	public CustomerExtend getCustomerExtend() {
		try {
			customerExtend.getId();
			return customerExtend;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getPayEmail(){
		CustomerExtend customerExtend = getCustomerExtend();
		if(customerExtend == null){
			return null;
		}
		return customerExtend.getPayEmail();
	}

	public void setCustomerExtend(CustomerExtend customerExtend) {
		this.customerExtend = customerExtend;
	}
	
	/**
	 * 新建第三方账号
	 * @param customer
	 * @return Customer
	 */
	public Customer createThirdCustomer(){
		Customer thirdCustomer = new Customer();
		thirdCustomer.setName(this.getName());
		thirdCustomer.setSource(this.getSource());//source:第三方
		thirdCustomer.setEmail(this.getName());
		Date now = new Date();
		thirdCustomer.setRegisterTime(this.getRegisterTime()!=null?this.getRegisterTime(): now);
		thirdCustomer.setLastLoginTime(this.getLastLoginTime()!=null?this.getLastLoginTime(): now);
		thirdCustomer.setChannel(this.getChannel());
		thirdCustomer.setDiscount(this.getDiscount());
		thirdCustomer.setAvatar(this.getAvatar());
		thirdCustomer.setGender(this.getGender());
		thirdCustomer.setFirstOrder(this.isFirstOrder());
		thirdCustomer.setLastLoginType(this.getLastLoginType());
		thirdCustomer.setLastTradeTime(this.getLastTradeTime()!=null?this.getLastTradeTime(): now);
		thirdCustomer.setAccount(this.getAccount());
		thirdCustomer.setNickName(StringUtils.isEmpty(this.getNickName())?this.getName():this.getNickName());
		return thirdCustomer;
	}
}
