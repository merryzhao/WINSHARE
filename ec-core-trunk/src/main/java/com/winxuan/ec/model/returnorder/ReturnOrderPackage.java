package com.winxuan.ec.model.returnorder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;

/**
 * @description:
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2015年1月16日 下午1:26:10
 */
@Entity
@Table(name = "returnorder_package")
public class ReturnOrderPackage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "type")
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returntype")
	private Code returntype;
	
	@Column(name = "carrier")
	private String carrier;
	
	@Column(name = "expressid")
	private String expressid;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "expressfee")
	private BigDecimal expressFee;
	
	@Column(name = "expresstime")
	private Date expressTime;
	
	@Column(name = "s_signtime")
	private Date sSignTime;
	
	@Column(name = "s_signname")
	private String sSignName;
	
	@Column(name = "s_quantity")
	private int sQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "s_vendor")
	private Channel sVendor;
	
	@Column(name = "s_returnreason")
	private String sReturnReason;
	
	@Column(name = "remark")
	private String remark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "returnOrderPackage", targetEntity = ReturnOrderPackageRelate.class)
    @OrderBy("id")
    private Set<ReturnOrderPackageRelate> returnOrderPackageRelateList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "returnOrderPackage", targetEntity = ReturnOrderPackageItem.class)
    @OrderBy("id")
    private Set<ReturnOrderPackageItem> returnOrderPackageItemList;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getExpressid() {
		return expressid;
	}

	public void setExpressid(String expressid) {
		this.expressid = expressid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}

	public Date getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}

	public Date getsSignTime() {
		return sSignTime;
	}

	public void setsSignTime(Date sSignTime) {
		this.sSignTime = sSignTime;
	}

	public String getsSignName() {
		return sSignName;
	}

	public void setsSignName(String sSignName) {
		this.sSignName = sSignName;
	}

	public int getsQuantity() {
		return sQuantity;
	}

	public void setsQuantity(int sQuantity) {
		this.sQuantity = sQuantity;
	}

	public Channel getsVendor() {
		return sVendor;
	}

	public void setsVendor(Channel sVendor) {
		this.sVendor = sVendor;
	}

	public String getsReturnReason() {
		return sReturnReason;
	}

	public void setsReturnReason(String sReturnReason) {
		this.sReturnReason = sReturnReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Code getReturntype() {
		return returntype;
	}

	public void setReturntype(Code returntype) {
		this.returntype = returntype;
	}

	public Set<ReturnOrderPackageRelate> getReturnOrderPackageRelateList() {
		return returnOrderPackageRelateList;
	}

	public void setReturnOrderPackageRelateList(
			Set<ReturnOrderPackageRelate> returnOrderPackageRelateList) {
		this.returnOrderPackageRelateList = returnOrderPackageRelateList;
	}

	public Set<ReturnOrderPackageItem> getReturnOrderPackageItemList() {
		return returnOrderPackageItemList;
	}

	public void setReturnOrderPackageItemList(
			Set<ReturnOrderPackageItem> returnOrderPackageItemList) {
		this.returnOrderPackageItemList = returnOrderPackageItemList;
	}
	
}
