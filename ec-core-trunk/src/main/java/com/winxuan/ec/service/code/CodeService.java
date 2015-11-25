/*
 * @(#)CodeService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.code;

import java.util.List;

import com.winxuan.ec.model.code.Code;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-15
 */
public interface CodeService {

	/**
	 * 获得代码
	 * 
	 * @param id
	 *            代码id
	 * @return 代码
	 */
	Code get(Long id);

	/**
	 * 获得一批代码
	 * 
	 * @param id
	 * @return
	 */
	Code[] find(Long[] id);

	/**
	 * 更新代码
	 * 
	 * @param code
	 *            代码
	 */
	void update(Code code);

	/**
	 * 创建代码
	 * 
	 * @param parent
	 *            被创建的节点的父节点
	 * @param child
	 *            创建的节点
	 */
	void create(Code parent, Code child);

	List<Code> findByParent(Long parent);
	
	List<Code> getAllDc();
	
	/**
	 * 得到指定节点
	 * @param id
	 * @return
	 */
	List<Code> findById(Long[] id);
	
	Code getDcCodeByDcName(String dcName);
}
