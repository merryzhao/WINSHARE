
package com.winxuan.ec.admin.controller.productmeta;
import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.winxuan.ec.model.product.ProductMetaEnum;
/**
 * 商品
 * 
 * @author 
 * @version 1.0,2011-7-13
 */
public class ProductMetaForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
    @NotNull
	private String name;
    @NotNull
	private Long type;
	private int allowNull;
	private final int maxLength=999;
    
	@NotNull
	@Min(1)
	@Max(maxLength)
	private int length;

	private String defaultValue;

	private int available;

	private int show;

	private Set<ProductMetaEnum> enumList;
   
	private String description;
	
	private int category;
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public int getAllowNull() {
		return allowNull;
	}

	public void setAllowNull(int allowNull) {
		this.allowNull = allowNull;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	public Set<ProductMetaEnum> getEnumList() {
		return enumList;
	}

	public void setEnumList(Set<ProductMetaEnum> enumList) {
		this.enumList = enumList;
	}

 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
