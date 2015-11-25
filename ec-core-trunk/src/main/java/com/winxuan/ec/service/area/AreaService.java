/*
 * @(#)AreaService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.area;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.area.Area;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface AreaService {

	/**
	 * 获得区域
	 * 
	 * @param id
	 *            区域编号
	 * @return 存在返回对应区域，不存在返回null
	 */
	Area get(Long id);

	/**
	 * 更新区域
	 * 
	 * @param area
	 *            区域
	 */
	void update(Area area);

	/**
	 * 传入区域名字获取区域信息 e: 四川省->成都市->成华区 xxx省->xx市->成华区
	 * 
	 * @param name
	 * @return
	 */
	List<Area> get(String name);

	/**
	 * 根据区县获取默认乡镇
	 * 
	 * @param area
	 *            区县
	 * @return
	 */
	Area getDefTownByDistrict(Area district);

	/**
	 * 获取没有默认乡镇的区县数量
	 * 
	 * @return
	 */
	long getNoDefTownDistrictCount();

	/**
	 * 设置乡镇为当前区县下的默认乡镇 如已存在默认乡镇，则覆盖。
	 * 
	 * @param town
	 */
	void addDistrictDefTown(Area town);

	/**
	 * 从地址中匹配获取乡镇信息 如果匹配失败，则返回区县下的默认乡镇 匹配规则:
	 * http://wiki.i.winxuan.com/index.php?
	 * title=%E5%88%9B%E5%BB%BA%E8%AE%A2%E5%8D%95
	 * 
	 * @param address
	 * @return
	 */
	Area matchTownByAddress(Area district, String address);

	/**
	 * 获取没有默认乡镇省市区信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> getNoDefTownDistrictList();
	
	/**
	 * 通过条件查询
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> find(String level,String path);
	
	
	/**
	 * 通过parentId查询所有的子类区域
	 *
	 * @param parentId
	 * @return
	 */
	List<Area> findChildrenByParentId(Long parentId);
	
}
