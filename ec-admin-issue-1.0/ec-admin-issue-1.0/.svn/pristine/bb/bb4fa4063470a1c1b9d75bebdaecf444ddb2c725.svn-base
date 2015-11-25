package com.winxuan.ec.admin.controller.cm;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.framework.util.AcceptHashMap;


/**
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-10-16下午07:16:57
 * -_-!
 */
public class FragmentForm implements Serializable {

	private static final long serialVersionUID = 3990580171913464436L;

	private Long id;

	/**
	 * 片段标题
	 */
	private String fragmentName;

	/**
	 * 片段类型
	 */
	private Short type;

	private Short rule;

	private Long category;

	/**
	 * 片段描述
	 */
	private String description;

	private String jspFile;
	/**
	 * 专业店分类page标识
	 */
	private String fragmentPage;

	/**
	 * 页面片断索引值
	 */
	private Long fragmentIndex;

	private Integer quantity;

	private Integer imageType;

	public Long getFragmentIndex() {
		return fragmentIndex;
	}

	public void setFragmentIndex(Long fragmentIndex) {
		this.fragmentIndex = fragmentIndex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getRule() {
		return rule;
	}

	public void setRule(Short rule) {
		this.rule = rule;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJspFile() {
		return jspFile;
	}

	public void setJspFile(String jspFile) {
		this.jspFile = jspFile;
	}

	public String getFragmentPage() {
		return fragmentPage;
	}

	public void setFragmentPage(String fragmentPage) {
		this.fragmentPage = fragmentPage;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

	public String getFragmentName() {
		return fragmentName;
	}

	public void setFragmentName(String fragmentName) {
		this.fragmentName = fragmentName;
	}

	public Map<String, Object> generateQueryMap() {
		Map<String, Object> result = new AcceptHashMap<String, Object>()
				.acceptIf("name", this.fragmentName,
						!StringUtils.isBlank(this.fragmentName))
				.acceptIf("rule", rule, rule != null)
				.acceptIf("imageType", imageType, imageType != null)
				.acceptIf("type", type, type != null)
				.acceptIf("id", id, id != null)
				.acceptIf("page", fragmentPage, !StringUtils.isBlank(this.fragmentPage))
				.acceptIf("index", fragmentIndex, fragmentIndex != null);
		return result;
	}
}
