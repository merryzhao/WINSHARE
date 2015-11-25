package com.winxuan.ec.service.version;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.version.ClientInfo;
import com.winxuan.ec.model.version.VersionInfo;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author zhousl
 *
 * 2013-4-3
 */
public interface VersionService {

	/**
	 * 条件查询
	 */
	List<VersionInfo> find(Map<String, Object> parameters,Pagination pagination, short orderIndex);
	
	/**
	 * 根据id获取版本信息
	 */
	VersionInfo get(Long id);
	
	/**
	 * 更新
	 */
	void update(VersionInfo versionInfo);
	
	/**
	 * 保存版本信息
	 */
	void save(VersionInfo versionInfo);
	
	void save(ClientInfo clientInfo);
}
