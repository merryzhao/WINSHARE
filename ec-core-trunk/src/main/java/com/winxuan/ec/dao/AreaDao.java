/*
 * @(#)AreaDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.area.AreaDefault;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 区域DAO
 * 
 * @author wumaojie XX
 * @version 1.0,2011-7-18
 */
public interface AreaDao {

	@Get
	Area get(Long id);

	@Update
	void update(Area area);

	@Save
	void save(Area area);

	@Query("from Area a where a.name = ?")
	List<Area> get(String name);

	@Query(sqlQuery = true, value = "SELECT count(district.id) FROM area country LEFT JOIN area direction ON country.id = direction.parent "
			+ "LEFT JOIN area province ON  direction.id= province.parent  LEFT JOIN area city ON  province.id= city.parent "
			+ "LEFT JOIN area district ON  city.id= district.parent  WHERE  country.id in(23,6126)  and district.id not in(select parent from area_default)")
	long getNoDefTownDistrictCount();

	@Query("from AreaDefault  aadmin where aadmin.parent = ?")
	AreaDefault getDefTownByDistrict(Long district);

	@Query("from AreaDefault aadmin where aadmin.area.id = ?")
	void getAdministrateById(Long town);

	@Update
	void update(AreaDefault areaDefault);

	@Save
	void save(AreaDefault areaDefault);

	@Query("from Area a")
	@Conditions({ @Condition("a.parent.name=:district"),
			@Condition("a.name=:town") })
	List<Area> getDefTownByName(@ParameterMap Map<String, Object> parame);

	@Query(sqlQuery = true, value = "SELECT direction.name as country,  province.name as provincea ,city.name as citya , district.name as districta,province.id as provinceid," +
			" direction.id as directionid,	city.id as cityid, district.id as districtid FROM area country  LEFT JOIN area direction ON country.id = direction.parent "
			+ "LEFT JOIN area province ON  direction.id= province.parent  LEFT JOIN area city ON  province.id= city.parent "
			+ "LEFT JOIN area district ON  city.id= district.parent  WHERE country.id in(23,6126) and district.id not in (select parent from area_default)")
	List<Map<String, Object>> getNoDefTownDistrictList();
	
	@Query(sqlQuery = true,value="select * from area where level  = ? and path like ?")
	List<Map<String, Object>> find(String level,String path);
	
	
	@Query("from Area  a where a.parent.id = ?")
	List<Area> findChildrenByParentId(Long parentId);
}
