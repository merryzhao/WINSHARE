/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.productmeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 商品
 * 
 * @author  
 * @version 1.0,2011-8-13
 */
@Controller
@RequestMapping("/productmeta")
public class ProductMetaController {
	private static final short INDEXZERO = 0;
	@Autowired
	private CodeService codeService;
	@Autowired
	private ProductService productService;
    private final Long typePrent=12000L;
    private final String formName="productMetaForm";
    private final String typeList="typeList";
    /**
	 * 获取商品列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="show",required=false) List<Boolean> show,
			@RequestParam(value="available",required=false) List<Boolean> available,@MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("/productmeta/list");
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(name!=null&&!"".equals(name)){
			parameters.put("name", name);
		}
		if(show!=null&&show.size()==1){
			parameters.put("show", show.get(0));
		}
		if(available!=null&&available.size()==1){
			parameters.put("available", available.get(0));
		}
		List<ProductMeta> list = productService.findProductMeta(parameters,pagination,INDEXZERO);
		modelAndView.addObject(pagination);
		modelAndView.addObject("list", list);
		return modelAndView;
	}
	
	/**
	 * 跳转到属性添加页面
	 * @return
	 */
	@RequestMapping(value = "/goadd", method = RequestMethod.GET)
	public ModelAndView newAddProductMeta() {
		ModelAndView mav = new ModelAndView("/productmeta/productmeta_attribute_add");
		//属性类型
 		mav.addObject(typeList, codeService.get(new Long(typePrent)).getChildren());
		ProductMetaForm productMetaForm=new ProductMetaForm();
		productMetaForm.setShow(1);
		productMetaForm.setAllowNull(1);
		productMetaForm.setAvailable(1);
 		productMetaForm.setDefaultValue("");
		productMetaForm.setDescription("");
        productMetaForm.setLength(0);
        productMetaForm.setName("");
        productMetaForm.setCategory(1);
         productMetaForm.setType(null);
		mav.addObject(formName, productMetaForm);
		return mav;
	}
	
	/**
	 * 添加属性
	 * @param productMetaForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView createProductMeta(@Valid ProductMetaForm productMetaForm,BindingResult result) {
		ModelAndView mav = new ModelAndView("redirect:/productmeta/list?available=1");
		if (result.hasErrors()) {
	 		mav.addObject(typeList, codeService.get(new Long(typePrent)).getChildren());
			mav.setViewName("/productmeta/productmeta_attribute_add");
			return mav;
		}
 		ProductMeta productMeta=new ProductMeta();
		productMeta.setLength(productMetaForm.getLength());
		productMeta.setAllowNull(productMetaForm.getAllowNull()==1);
		productMeta.setAvailable(productMetaForm.getAvailable()==1);
		productMeta.setEnumList(productMetaForm.getEnumList());
		productMeta.setName(productMetaForm.getName());
		productMeta.setShow(productMetaForm.getShow()==1);
		productMeta.setType(new Code(productMetaForm.getType()));
		productMeta.setDefaultValue(productMetaForm.getDefaultValue());
		productMeta.setCategory(productMetaForm.getCategory());
 		productService.createProductMeta(productMeta);
		return mav;
	}
	
	/**
	 * 跳转到属性编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/goedit", method = RequestMethod.GET)
	public ModelAndView editEditProductMeta(@PathVariable("id") Long id ) {
		ModelAndView mav = new ModelAndView("/productmeta/productmeta_attribute_edit");
		mav.addObject(typeList, codeService.get(new Long(typePrent)).getChildren());
		ProductMeta productMeta=productService.getProductMeta(id);
		ProductMetaForm productMetaForm=new ProductMetaForm();
		productMetaForm.setAllowNull(productMeta.isAllowNull()?1:0);
		productMetaForm.setAvailable(productMeta.isAvailable()?1:0);
		productMetaForm.setDescription(productMeta.getDescription());
		productMetaForm.setEnumList(productMeta.getEnumList());
		productMetaForm.setId(productMeta.getId());
		productMetaForm.setLength(productMeta.getLength());
		productMetaForm.setName(productMeta.getName());
		productMetaForm.setShow(productMeta.isShow()?1:0);
		productMetaForm.setType(productMeta.getType().getId());
		productMetaForm.setDefaultValue(productMeta.getDefaultValue());
		productMetaForm.setCategory(productMeta.getCategory());
		mav.addObject(formName, productMetaForm);
		return mav;
	}
	
	/**
	 * 更新属性
	 * @param productMetaForm
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ModelAndView updateProductMeta(@Valid ProductMetaForm productMetaForm,BindingResult result ) {
		ModelAndView mav = new ModelAndView("redirect:/productmeta/list?available=1");
		if (result.hasErrors()) {
	 		mav.addObject(typeList, codeService.get(new Long(typePrent)).getChildren());
			mav.setViewName("/productmeta/productmeta_attribute_add");
			return mav;
		}
		ProductMeta productMeta=productService.getProductMeta(productMetaForm.getId());
		productMeta.setLength(productMetaForm.getLength());
		productMeta.setDescription(productMetaForm.getDescription());
		productMeta.setAllowNull(productMetaForm.getAllowNull()==1);
		productMeta.setAvailable(productMetaForm.getAvailable()==1);
		productMeta.setEnumList(productMetaForm.getEnumList());
		productMeta.setName(productMetaForm.getName());
		productMeta.setShow(productMetaForm.getShow()==1);
		productMeta.setType(new Code(productMetaForm.getType()));
		productMeta.setDefaultValue(productMetaForm.getDefaultValue());
		productMeta.setCategory(productMetaForm.getCategory());
 		productService.updateProductMeta(productMeta);
		return mav;
	}
	


}
