package com.winxuan.ec.model.bill;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.user.Employee;

/**
 * 渠道帐单帐户
 * @author heyadong
 *
 */
@Table(name="bill_account")
@Entity
public class BillAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column
	String name;
	
	
	//余额
	@Column
	BigDecimal balance;
	//容差
	@Column
	BigDecimal tolerance;
	
	//上次修改人
	@ManyToOne
	@JoinColumn(name="lastemployee")
	Employee lastEmployee;

	@OneToMany(targetEntity=Channel.class,fetch=FetchType.EAGER)
	@JoinTable(name = "billaccount_channel", joinColumns = {@JoinColumn(name = "billaccount")}, inverseJoinColumns = {@JoinColumn(name = "channel")})
	List<Channel> channels;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getTolerance() {
		return tolerance;
	}
	public void setTolerance(BigDecimal tolerance) {
		this.tolerance = tolerance;
	}
	public Employee getLastEmployee() {
		return lastEmployee;
	}
	public void setLastEmployee(Employee lastEmployee) {
		this.lastEmployee = lastEmployee;
	}
	
	public List<Channel> getChannels() {
		return channels;
	}
	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
