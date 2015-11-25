package com.winxuan.ec.service.config;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.winxuan.ec.model.config.SystemConfig;
import com.winxuan.ec.model.config.SystemGroup;
import com.winxuan.ec.model.config.SystemSwitchConfig;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911
 *
 */
public interface SystemConfigService {

	SystemGroup getSystemGroup(Long id);

	SystemConfig get(Long id);

	void save(SystemConfig systemConfig);

	void delete(SystemConfig systemConfig);

	void saveOrUpdate(SystemConfig systemConfig);

	void update(SystemConfig systemConfig);

	List<SystemConfig> find(Pagination pagination);

	/**
	 * 返回properties
	 * 
	 * @param systemGroup
	 *            系统组id
	 * @return
	 */
	Properties getForProperties(SystemGroup systemGroup);

	/**
	 * 返回map
	 * 
	 * @param systemGroup
	 *            系统组id
	 * @return
	 */
	HashMap<String, String> getForHashMap(SystemGroup systemGroup);

	boolean lockStockSwitchOpen();
	
	SystemSwitchConfig getSystemSwitchConfig(short type);
	
	void saveSwitchConfig(SystemSwitchConfig switchConfig);
	
	void updateSwitchConfig(SystemSwitchConfig switchConfig);
	
	List<SystemSwitchConfig> findSwitchConfig(Pagination pagination);
	
	SystemSwitchConfig getSwitchConfig(Long id);
	
	boolean isSystemSwitchConfig(short type);
}
