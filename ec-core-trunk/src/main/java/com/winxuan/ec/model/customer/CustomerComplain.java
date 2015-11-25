package com.winxuan.ec.model.customer;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;

/**
 * 用户投诉
 * @author cast911 玄玖东
 *
 */
@Entity
@Table(name = "customer_complain")
public class CustomerComplain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;
	@Column
	private String email;
	@Column
	private String phone;
	@Column
	private String title;
	@Column
	private String content;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state")
	private Code state;
	
	@Column(name = "createtime")
	private Date createTime;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@Column(name = "updatetime")
	private Date updateTime;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updateuser")
	private Employee user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "complain", targetEntity = CustomerComplainReply.class)
	private Set<CustomerComplainReply> replyList;

	public Set<CustomerComplainReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(Set<CustomerComplainReply> replyList) {
		this.replyList = replyList;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}


}
