/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.service.channel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.RuleOnshelfDao;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.channel.RuleOnshelf;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author liyang
 * @version 1.0,2011-7-18
 */

@Service("ruleOnshelfService")
@Transactional(rollbackFor = Exception.class)
public class RuleOnshelfServiceImpl implements RuleOnshelfService{

	@InjectDao
	private RuleOnshelfDao ruleOnshelfDao;
	
	@Read
	@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
	public RuleOnshelf get(Long id) {
		return ruleOnshelfDao.get(id);
	}

	public void create(RuleOnshelf ruleOnshelf) {
		ruleOnshelfDao.save(ruleOnshelf);
	}

	
	public void update(RuleOnshelf ruleOnshelf) {
		// TODO Auto-generated method stub
	}
    
	@Read
	@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
	public List<RuleOnshelf> find(Channel channel) {
		return ruleOnshelfDao.find(channel);
	}

}
