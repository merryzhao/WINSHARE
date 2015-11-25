/*
 * @(#)ChannelServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.service.channel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ChannelDao;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author chenlong
 * @version 1.0,2011-7-18
 */
@Service("channelService")
@Transactional(rollbackFor = Exception.class)
public class ChannelServiceImpl implements ChannelService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5703829153880264460L;
	@InjectDao
	private ChannelDao channelDao;

	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Channel get(Long id) {
		return channelDao.get(id);
	}

	public void create(Channel parent, Channel child) {
		parent.addChild(child);
		channelDao.save(child);
	}

	public void update(Channel channel) {
		channelDao.update(channel);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Channel> findChildren(Long id) {
		return channelDao.findChildren(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Channel> find(Map<String, Object> parameters) {
		return channelDao.find(parameters);
	}

	@Override
	public List<Channel> findSupplyChannel() {
		return channelDao.findSupplyChannel();
	}

}
