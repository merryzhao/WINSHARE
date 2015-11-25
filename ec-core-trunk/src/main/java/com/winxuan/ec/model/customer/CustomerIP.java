package com.winxuan.ec.model.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.IpUtils;

/**
 * 访问ip信息
 * 
 * @author youwen
 * 
 */
@Entity
@Table(name = "customer_ip")
public class CustomerIP {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "customercookie")
	private String customerCookie;
	
	@Column
	private String ip;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="country")
	private Area country;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area")
	private Area area;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="province")
	private Area province;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="city")
	private Area city;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="county")
	private Area county;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="town")
	private Area town;
	
	@Column
	private String isp;
	
	@Column
	private String region;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer")
	private Customer customer;
	
	@Column(name = "createtime")
	private Date createTime;
	
	@Column
	private String countryname;
	
	@Column
	private String provincename;
	
	@Column
	private String cityname;
	
	public CustomerIP(String ipString) {
		String result = IpUtils.getAddressByIp(ipString);
		JSONObject json = JSONObject.fromObject(result, IpUtils.config());
		JSONObject data = json.getJSONObject("data");
		this.ip = data.getString("ip");
		this.countryname = data.getString("country");
		this.provincename = data.getString("region");
		this.region = data.getString("area");
		this.cityname = data.getString("city");
		this.createTime = new Date();
		this.isp = data.getString("isp");
	}
	
	public CustomerIP(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
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

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

	public Area getCounty() {
		return county;
	}

	public void setCounty(Area county) {
		this.county = county;
	}

	public Area getTown() {
		return town;
	}

	public void setTown(Area town) {
		this.town = town;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCustomerCookie() {
		return customerCookie;
	}

	public void setCustomerCookie(String customerCookie) {
		this.customerCookie = customerCookie;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String toString() {
		return String.format(",ip:%s(%s-%s-%s-%s-%s)isp:%s", this.ip,
				this.countryname, this.provincename, this.region, this.cityname, this.county,
				this.isp);
	}
	
	public Area getLastArea(){
		if(this.town!=null){
			return this.town;
		}else if(this.county!=null){
			return this.county;
		}else if(this.city!=null){
			return this.city;
		}else if(this.province!=null){
			return this.province;
		}else if(this.area!=null){
			return this.area;
		}else if(this.country!=null){
			return this.country;
		}
		return null;
	}
}
