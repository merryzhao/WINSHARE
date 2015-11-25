/*
 * @(#)CodeServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.code;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CodeDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author liyang
 * @version 1.0,2011-7-18
 */

@Service("codeService")
@Transactional(rollbackFor = Exception.class)
public class CodeServiceImpl implements CodeService,Serializable{

	private static final long serialVersionUID = -3153917376056902727L;
	@InjectDao
	private CodeDao codeDao;
	
	@Read
	@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
	public Code get(Long id) {
		return codeDao.get(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
	public Code[] find(Long[] id) {
		Code[] returnCode = new Code[id.length];
		for (int i = 0; i < returnCode.length; i++) {
			returnCode[i] = get(id[i]);
		}
		return returnCode;
	}

	public void update(Code code) {
	    codeDao.update(code);
	}

	public void create(Code parent, Code child) {
		parent.addChild(child);
		codeDao.save(child);
	}

	@Override
	public List<Code> findByParent(Long parent) {
		
		return codeDao.findByParent(parent);
	}
	
	@Override
	public List<Code> getAllDc(){
		return codeDao.findByParent(Code.DELIVERY_CENTER); 
	}

	@Override
	public List<Code> findById(Long[] id) {
		return codeDao.find(id);
	}
	
	@Override
	public Code getDcCodeByDcName(String dcName){
		List<Code> allDcList = getAllDc();
		for(Code currentRow : allDcList){
			if(currentRow.getName().equals(dcName)){
				return currentRow;
			}
		}
		return new Code(Code.DELIVERY_CENTER);
	}
}
