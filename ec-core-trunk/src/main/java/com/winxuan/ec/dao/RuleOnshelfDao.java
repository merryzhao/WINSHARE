/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.channel.RuleOnshelf;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author liyang
 * @version 1.0,2011-7-18
 */

public interface RuleOnshelfDao {

	@Get
	RuleOnshelf get(Long id);
	
	@Save
	void save(RuleOnshelf ruleOnshelf);
	
	@Query("from rule_onshelf r where channer=?")
	List<RuleOnshelf> find(Channel channel);
	
}
