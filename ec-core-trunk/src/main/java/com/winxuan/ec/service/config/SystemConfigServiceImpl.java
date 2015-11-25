package com.winxuan.ec.service.config;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SystemConfigDao;
import com.winxuan.ec.model.config.SystemConfig;
import com.winxuan.ec.model.config.SystemGroup;
import com.winxuan.ec.model.config.SystemSwitchConfig;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911
 *
 */
@Service("systemConfigService")
@Transactional(rollbackFor = Exception.class)
public class SystemConfigServiceImpl implements SystemConfigService {

	@InjectDao
	private SystemConfigDao systemConfigDao;
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public SystemGroup getSystemGroup(Long id) {
		// TODO Auto-generated method stub
		return systemConfigDao.getSystemGroup(id);
	}

	@Override
	public SystemConfig get(Long id) {
		// TODO Auto-generated method stub
		return systemConfigDao.get(id);
	}

	@Override
	public void save(SystemConfig systemConfig) {
		systemConfigDao.save(systemConfig);

	}

	@Override
	public void delete(SystemConfig systemConfig) {
		systemConfigDao.delete(systemConfig);

	}

	@Override
	public void saveOrUpdate(SystemConfig systemConfig) {
		systemConfigDao.saveOrUpdate(systemConfig);

	}

	@Override
	public void update(SystemConfig systemConfig) {
		systemConfigDao.update(systemConfig);

	}

	@Override
	public List<SystemConfig> find(Pagination pagination) {
		// TODO Auto-generated method stub
		return systemConfigDao.find(pagination);
	}

	@Override
	public Properties getForProperties(SystemGroup systemGroup) {
		HashMap<String, String> hm = this.getForHashMap(systemGroup);
		Properties properties = new Properties();
		properties.putAll(hm);
		return properties;
	}

	@Override
	public HashMap<String, String> getForHashMap(SystemGroup systemGroup) {
		SystemGroup sysGroup = this.getSystemGroup(systemGroup.getId());
		Set<SystemConfig> systemConfigs = sysGroup.getSystemConfigs();
		HashMap<String, String> result = new HashMap<String, String>();
		for (SystemConfig systemConfig : systemConfigs) {
			result.put(systemConfig.getKey(), systemConfig.getValue());
		}
		return result;
	}

	@Override
	public void saveSwitchConfig(SystemSwitchConfig switchConfig) {
		systemConfigDao.saveSwitchConfig(switchConfig);
	}

	@Override
	public void updateSwitchConfig(SystemSwitchConfig switchConfig) {
		systemConfigDao.updateSwitchConfig(switchConfig);
	}

	@Override
	public List<SystemSwitchConfig> findSwitchConfig(Pagination pagination) {
		return systemConfigDao.findSwitchConfig(pagination);
	}

	@Override
	public SystemSwitchConfig getSwitchConfig(Long id) {
		return systemConfigDao.getSwitchConfig(id);
	}

	@Override
	public SystemSwitchConfig getSystemSwitchConfig(short type) {
		return systemConfigDao.getSystemSwitchConfig(SystemSwitchConfig.TYPE_STOCK_TYPE);
	}

	@Override
	public boolean lockStockSwitchOpen() {
		String sql = "select if(count(1) > 0, s.isopen, 0) from system_switch_config s where s.type=1";
		int count = jdbcTemplate.queryForInt(sql);
		return 0 != count;
	}

	@Override
	public boolean isSystemSwitchConfig(short type) {
		return systemConfigDao.isSystemSwitchConfig(type);
	}

}
