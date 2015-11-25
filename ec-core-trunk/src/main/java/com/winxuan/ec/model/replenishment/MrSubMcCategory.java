/**
 * 
 */
package com.winxuan.ec.model.replenishment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author monica
 * MC二次分类
 */
@Entity
@Table(name = "mr_submccategory")
public class MrSubMcCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	//MC分类
	@Column(name = "mccategory")
	private String mcCategory;
	
	//MC二次分类
	@Column(name="submccategory")
	private String subMcCategory;
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getMcCategory(){
		return mcCategory;
	}
	public void setMcCategory(String mcCategory){
		this.mcCategory = mcCategory;
	}
	
	public String getSubMcCategory(){
		return subMcCategory;
	}
	public void setSubMcCategory(String subMcCategory){
		this.subMcCategory = subMcCategory;
	}
}
