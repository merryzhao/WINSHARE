/*
 * @(#)CodeController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.code;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.service.code.CodeService;

/**
 * 代码维护后台控制
 * 
 * @author wumaojie
 * @version 1.0,2011-8-2
 */
@Controller
@RequestMapping("/code")
public class CodeController {

	private static final String PARENTCODE ="parentCode";
	
	private static final String CODELISTCHILD ="/code/codeListChild";
	
	@Autowired
	private CodeService codeService;  	
	
	/**
	 * 获取根节点
	 * 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view() {
		//获取根节点
		Code code = codeService.get(Code.ROOT);
		//返回
		ModelAndView mav = new ModelAndView("/code/codeList");
		mav.addObject("code",code);
		return mav;
	}
	/**
	 * 创建代码子节点
	 * 
	 * @param parentId
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView create(
			@RequestParam("parentId") Long parentId,
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam(value="parent",required=false) boolean isparent) {
		//获取父节点
		Code parent = codeService.get(parentId);
		//new出子节点
		Code child = new Code();
		child.setName(name);
		child.setDescription(description);
		child.setEditable(true);
		//在数据库中创建子节点
		codeService.create(parent, child);
		parent = codeService.get(parentId);
		//返回
		if(isparent){
			//parent =(Code) parent.getChildren().toArray()[(parent.getChildren().size())-1];
			ModelAndView mav = new ModelAndView("/code/codeListAddHead");
			mav.addObject("code",codeService.get(Code.ROOT));
			return mav;
		}else{
			ModelAndView mav = new ModelAndView(CODELISTCHILD);
			mav.addObject(PARENTCODE,parent);
			return mav;
		}
	}
	/**
	 * 修改节点
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ModelAndView update(
			@PathVariable("id") Long id,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="description",required=false) String description,
			@RequestParam(value="parent",required=false) boolean parent) {
		//获取节点
		Code code = codeService.get(id);
		code.setName(name);
		code.setDescription(description);
		//在数据库中修改节点
		codeService.update(code);
		code = codeService.get(id);
		//返回
		if(parent){
			ModelAndView mav = new ModelAndView("/code/codeListHead");
			mav.addObject(PARENTCODE,code);
			return mav;
		}
		ModelAndView mav = new ModelAndView(CODELISTCHILD);
		mav.addObject(PARENTCODE,code.getParent());
		return mav;
	}
	/**
	 * 代码是否启用变更
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/{id}/available", method = RequestMethod.GET)
	public ModelAndView updateAvailable(
			@PathVariable("id") Long id) {
		//获取节点
		Code code = codeService.get(id);
		if(code.isAvailable()){
			code.setAvailable(false);
		}else{
			code.setAvailable(true);
		}
		//在数据库中修改节点
		codeService.update(code);
		code = codeService.get(id);
		//返回
		ModelAndView mav = new ModelAndView(CODELISTCHILD);
		mav.addObject(PARENTCODE,code.getParent());
		return mav;
	}
	/**
	 * 顺序调整
	 * 
	 * @param id
	 * @param orientation
	 * @return
	 */
	@RequestMapping(value = "/{id}/{orientation}", method = RequestMethod.GET)
	public ModelAndView updateSort(
			@PathVariable("id") Long id,
			@PathVariable("orientation") String orientation) {
		//获取节点
		Code code = codeService.get(id);
		//顺序交换目标
		int iter = 0;
		if("up".equals(orientation)){
			iter = code.getIndex()-1;
		}else{
			iter = code.getIndex()+1;
		}
		//获得父节点
		Code parent = code.getParent();	
		//获取子节点链表
		Set<Code> codes = parent.getChildren();
		//查找目标对象交换顺序
		for (Code codeiter : codes) {
			if(codeiter.getIndex()==iter){
				codeiter.setIndex(code.getIndex());
				code.setIndex(iter);
				break;
			}
		}		
		//保存修改
		codeService.update(parent);
		parent = codeService.get(id).getParent();
		//排序
		Code[] codeArr = new Code[parent.getChildren().size()];
		int i = 0;
		for (Code codeset : parent.getChildren()) {
			if(i==0){
			codeArr[i]=codeset;
			}else{
				if(codeArr[i-1].getIndex()>codeset.getIndex()){
					codeArr[i]=codeArr[i-1];
					codeArr[i-1] = codeset;
				}else{
					codeArr[i]=codeset;
				}
			}
			i++;
		}
		Set<Code> codeset = new LinkedHashSet<Code>();
        for (int j=0;j<codeArr.length;j++) {
        	codeset.add(codeArr[j]);
		}
        parent.setChildren(codeset);
		//返回
		ModelAndView mav = new ModelAndView(CODELISTCHILD);
		mav.addObject(PARENTCODE,parent);
		return mav;
	}

}
