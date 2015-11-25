/*
 * @(#)ProductSaleLabelController.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.product;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.product.Label;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleLabel;
import com.winxuan.ec.service.product.LabelService;
import com.winxuan.ec.service.product.ProductSaleLabelService;
import com.winxuan.ec.service.product.ProductSaleService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 商品标签对应关系
 * 
 * @author wangbiao
 * @version 1.0 2015-3-23
 */
@Controller
@RequestMapping("/product/label")
public class ProductSaleLabelController {

	private static final String SUCCESS_VIEW = "/order/success_result";

	@Autowired
	private LabelService labelService;

	@Autowired
	private ProductSaleLabelService productSaleLabelService;

	@Autowired
	private ProductSaleService productSaleService;

	@RequestMapping(value = "/addlabel", method = RequestMethod.POST)
	public ModelAndView createProductSaleLabel(
			@RequestParam(value = "productSaleLabelStr", required = true) String productSaleLabelStr) {
		ModelAndView mav = new ModelAndView("redirect:/product/label/showproductsalelabels");
		productSaleLabelStr = StringUtils.trimToEmpty(productSaleLabelStr);
		List<String> productSaleLabelStrs = Splitter.on("\r\n").omitEmptyStrings().splitToList(productSaleLabelStr);
		if (CollectionUtils.isNotEmpty(productSaleLabelStrs)) {
			for (String labelStr : productSaleLabelStrs) {
				List<String> splitToList = Splitter.on("\t").omitEmptyStrings().splitToList(StringUtils.trimToEmpty(labelStr));
				if (splitToList.size() >= 2) {
					long productSaleId = Long.parseLong(StringUtils.trimToNull(splitToList.get(0)));
					long labelId = Long.parseLong(StringUtils.trimToNull(splitToList.get(1)));
					if (!this.isExistProductSaleLabel(productSaleId, labelId)) {
						ProductSale productSale = this.productSaleService.findProductSale(productSaleId);
						Label label = this.labelService.get(labelId);
						// 商品不存在或者商品不存在的都不新增
						if (null != productSale && null != label) {
							ProductSaleLabel productSaleLabel = new ProductSaleLabel();
							productSaleLabel.setLabel(label);
							productSaleLabel.setProductSale(productSale);
							productSaleLabel.setAvailable(Boolean.TRUE);
							this.productSaleLabelService.save(productSaleLabel);
						}
					}
				}
			}
		}
		mav.addObject("errMsg", "添加成功！");
		return mav;
	}

	/***
	 * 查询某商品是否已经存在了这个标签
	 * 
	 * @param productSaleId
	 * @param labelId
	 * @return
	 */
	private boolean isExistProductSaleLabel(long productSaleId, long labelId) {
		Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("productSaleId", productSaleId);
		parameters.put("labelId", labelId);
		List<ProductSaleLabel> productSaleLabels = this.productSaleLabelService
				.queryProductSaleLabels(parameters, null);
		return null != productSaleLabels && !productSaleLabels.isEmpty();
	}

	@RequestMapping(value = "/showproductsalelabels")
	public ModelAndView showProductSaleLabelList(@Valid ProductSaleLabelForm queryForm,
			@MyInject Pagination pagination,
			@RequestParam(value = "errMsg", required = false, defaultValue = "") String errMsg) {
		ModelAndView mav = new ModelAndView("/product/productLabelList");
		Map<String, Object> parameters = Maps.newHashMap();
		if (null != queryForm.getProductSaleId()) {
			parameters.put("productSaleId", queryForm.getProductSaleId());
		}
		if (StringUtils.isNotEmpty(queryForm.getSellName())) {
			parameters.put("sellName", "%" + queryForm.getSellName() + "%");
		}
		if (StringUtils.isNotEmpty(queryForm.getBarcode())) {
			parameters.put("barcode", "%" + queryForm.getBarcode() + "%");
		}
		if (null != queryForm.getLabelId()) {
			parameters.put("labelId", queryForm.getLabelId());
		}
		List<ProductSaleLabel> productSaleLabels = this.productSaleLabelService.queryProductSaleLabels(parameters,
				pagination);
		List<Label> labels = this.labelService.queryAllLabels();
		mav.addObject("productSaleLabels", productSaleLabels);
		List<Node> nodes = Lists.newArrayList();
		this.addNodes(nodes, labels);
		mav.addObject("labels", CollectionUtils.isNotEmpty(nodes) ? this.toJson(nodes) : "");
		mav.addObject("pagination", pagination);
		mav.addObject("queryForm", queryForm);
		mav.addObject("errMsg", errMsg);
		return mav;
	}

	/**
	 * 转成json
	 * 
	 * @param nodes
	 * @return
	 */
	private String toJson(List<Node> nodes) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(nodes);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 处理树
	 * 
	 * @param nodes
	 * @param labels
	 */
	private void addNodes(List<Node> nodes, Collection<Label> labels) {
		if (CollectionUtils.isNotEmpty(labels)) {
			for (Label label : labels) {
				if (label.getAvailable()) {
					Node node = new Node();
					node.setId(label.getId());
					node.setName(label.getName());
					node.setpId(null == label.getParent() ? NumberUtils.LONG_ZERO : label.getParent().getId());
					node.setOpen(Boolean.FALSE);
					nodes.add(node);
					Set<Label> children = label.getChildren();
					if (CollectionUtils.isNotEmpty(children)) {
						this.addNodes(nodes, children);
					}

				}
			}
		}

	}

	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public ModelAndView editLabel(@PathVariable("id") Long id, @RequestParam(required = true) String labelName) {
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		Label label = this.labelService.get(id);
		label.setName(labelName);
		this.labelService.update(label);
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, 1);
		map.put(ControllerConstant.MESSAGE_KEY, "标签修改成功！");
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createLabel(@RequestParam(required = true) Long parentId,
			@RequestParam(required = true) String labelName) {
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		Label label = new Label();
		Label parent = this.labelService.get(parentId);
		label.setParent(parent);
		label.setName(labelName);
		label.setAvailable(Boolean.TRUE);
		this.labelService.save(label);
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, 1);
		map.put(ControllerConstant.MESSAGE_KEY, "新增标签成功！");
		mav.addAllObjects(map);
		return mav;
	}

	/**
	 * 设置无效
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteLabel", method = RequestMethod.GET)
	public ModelAndView deleteLabel(@RequestParam(required = true) Long id) {
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		Label label = this.labelService.get(id);
		label.setAvailable(Boolean.FALSE);
		this.labelService.update(label);
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, 1);
		map.put(ControllerConstant.MESSAGE_KEY, "删除标签成功！");
		mav.addAllObjects(map);
		return mav;
	}

}
