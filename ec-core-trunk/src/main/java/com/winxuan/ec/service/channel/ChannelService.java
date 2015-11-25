/*
 * @(#)ChannelService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.channel;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.channel.Channel;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface ChannelService {

	/**
	 * 根据渠道ID获得渠道
	 * 
	 * @param id
	 * @return 存在返回Channel，不存在返回null
	 */
	Channel get(Long id);
	
	/**
	 * 添加渠道
	 * @param parent 被添加渠道的父级渠道
	 * @param child 被添加的子渠道，传入的子渠道仅需设置name,type,available属性即可
	 */
	void create(Channel parent,Channel child);
	
	/**
	 * 修改渠道<br/>
	 * 
	 * @param channel
	 */
	void update(Channel channel);
	
	/**
	 * 查询渠道的子渠道
	 */
   List<Channel> findChildren(Long id);
   
   /**
    * 查询渠道
    * @return
    */
   List<Channel> find(Map<String, Object> parameters);
   
   /**
    * 查询供货渠道
    */
   List<Channel> findSupplyChannel();
}
