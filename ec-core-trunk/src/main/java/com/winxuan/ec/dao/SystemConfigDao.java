package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.config.SystemConfig;
import com.winxuan.ec.model.config.SystemGroup;
import com.winxuan.ec.model.config.SystemSwitchConfig;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;


/**
 * 
 * @author cast911
 *
 */
public interface SystemConfigDao {

	@Get
	SystemGroup getSystemGroup(Long id);

	@Get
	SystemConfig get(Long id);

	@Save
	void save(SystemConfig systemConfig);

	@Delete
	void delete(SystemConfig systemConfig);

	@SaveOrUpdate
	void saveOrUpdate(SystemConfig systemConfig);
	
	@Update
	void update(SystemConfig systemConfig);

	@Query("from SystemConfig sc ")
	List<SystemConfig> find(@Page Pagination pagination);
	
	@Query("from SystemSwitchConfig ssc where ssc.type=?")
	SystemSwitchConfig getSystemSwitchConfig(short type);
	
	@Get
	SystemSwitchConfig getSwitchConfig(Long id);
	
	@Save
	void saveSwitchConfig(SystemSwitchConfig switchConfig);
	
	@Update
	void updateSwitchConfig(SystemSwitchConfig switchConfig);
	
	@Query("from SystemSwitchConfig ssc")
	List<SystemSwitchConfig> findSwitchConfig(@Page Pagination pagination);
	
	@Query("from SystemSwitchConfig ssc where ssc.type=?")
	boolean isSystemSwitchConfig(short type);
}
