/*
 * @(#)RuleOnshelfService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.channel;

import java.util.List;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.channel.RuleOnshelf;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2011-7-15
 */
public interface RuleOnshelfService {

	/**
	 * 根据渠道上下架规则ID获得渠道上下架规则
	 * 
	 * @param id
	 * @return 存在返回RuleOnshelf，不存在返回null
	 */
	RuleOnshelf get(Long id);

	/**
	 * 新建渠道上下架规则 必须设置如下属性：<br/>
	 * 
	 * @param channel
	 */
	void create(RuleOnshelf ruleOnshelf);

	/**
	 * 修改渠道上下架规则<br/>
	 * 
	 * @param ruleOnshelf
	 */
	void update(RuleOnshelf ruleOnshelf);

	/**
	 * 查询渠道上下架规则
	 * 
	 * @param channel
	 */
	List<RuleOnshelf> find(Channel channel);
}
