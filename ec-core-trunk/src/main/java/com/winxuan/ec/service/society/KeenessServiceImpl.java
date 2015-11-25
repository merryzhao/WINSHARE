/*
 * @(#)KeenessServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.society;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.SocietyDao;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.society.Keenness;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.util.StringUtils;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-4
 */
@Service("keenessService")
public class KeenessServiceImpl implements KeenessService,Serializable{

	private static final long serialVersionUID = -3253260852747203219L;
	private static Map<String, Long> keyCache = null;
	private static Map<Long, Keenness> currentCache = null;

	@InjectDao
	private SocietyDao societyDao;

	@Override
	public String replaceSimple(String content){
		if(keyCache == null || currentCache == null){
			reloadKeeness();
		}
		if(!StringUtils.isNullOrEmpty(content)){
			for(String key : keyCache.keySet()){
				content = content.replaceAll(key, currentCache.get(keyCache.get(key)).getPlacid());
			}
		}
		return content;
	}

	@Override
	public String replaceRich(String content){
		if(keyCache == null || currentCache == null){
			reloadKeeness();
		}
		if(!StringUtils.isNullOrEmpty(content)){
			for(String key : keyCache.keySet()){
				Keenness keenness = currentCache.get(keyCache.get(key));
				content = content.replaceAll(key, parseRichUrl(keenness));
			}
		}
		return content;
	}

	@Override
	public void reloadKeeness() {
		//increasing
		if(keyCache == null || currentCache == null){
			keyCache = new HashMap<String, Long>();
			currentCache = new HashMap<Long, Keenness>();
		}
		List<Keenness> keennesses = societyDao.findKeeness(true);
		if(keennesses != null && !keennesses.isEmpty()){
			keyCache.clear();
			currentCache.clear();
			for(Keenness ss : keennesses){
				if(currentCache.get(ss.getId()) == null){
					keyCache.put(ss.getSensitive(),ss.getId());
					currentCache.put(ss.getId(), ss);
				}
			}
		}
	}


	private String parseRichUrl(Keenness keenness){
		if(keenness.getContent() != null){
			StringBuffer buffer = new StringBuffer();
			Content entity = keenness.getContent();
			buffer.append("<a");
			buffer.append(" href='");
			buffer.append(entity.getUrl());
			buffer.append("'");
			buffer.append(">");
			buffer.append(entity.getName());
			buffer.append("</a>");
			return buffer.toString();
		}
		return keenness.getPlacid();
	}



}

