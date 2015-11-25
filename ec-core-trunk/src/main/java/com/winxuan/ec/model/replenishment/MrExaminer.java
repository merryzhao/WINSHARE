/**
 * 
 */
package com.winxuan.ec.model.replenishment;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.User;

/**
 * @author monica
 * 补货审核权限管理
 */
@Entity
@Table(name = "mr_examiner")
public class MrExaminer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

	//最低数量
	@Column(name = "bottomquantity")
	private Integer bottomQuantity;
	
	//最高数量
	@Column(name = "topquantity")
	private Integer topQuantity;
	
	//最低总码洋
	@Column(name = "bottomamount")
	private BigDecimal bottomAmount;
	
	//最高总码洋
	@Column(name = "topamount")
	private BigDecimal topAmount;
	
	public MrExaminer(){
		
	}
	
	public MrExaminer(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	
	public Integer getBottomQuantity(){
		return bottomQuantity;
	}
	
	public void setBottomQuantity(Integer bottomQuantity){
		this.bottomQuantity = bottomQuantity;
	}
	
	public Integer getTopQuantity(){
		return topQuantity;
	}
	
	public void setTopQuantity(Integer topQuantity){
		this.topQuantity = topQuantity;
	}
	
	public BigDecimal getBottomAmount(){
		return bottomAmount;
	}
	
	public void setBottomAmount(BigDecimal bottomAmount){
		this.bottomAmount = bottomAmount;
	}
	
	public BigDecimal getTopAmount(){
		return topAmount;
	}
	
	public void setTopAmount(BigDecimal topAmount){
		this.topAmount = topAmount;
	}
}
