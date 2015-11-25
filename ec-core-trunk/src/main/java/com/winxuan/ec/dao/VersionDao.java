package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.version.ClientInfo;
import com.winxuan.ec.model.version.VersionInfo;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author zhousl
 *
 * 2013-4-3
 */
public interface VersionDao {

	@Get
	VersionInfo get(Long id);
	
	@Save
	void save(VersionInfo versionInfo);
	
	@Save
	void save(ClientInfo clientInfo);
	
	@Update
	void update(VersionInfo versionInfo);
	
	@Query("from VersionInfo vi")
	@Conditions({ 
		@Condition("vi.version =:version"),
		@Condition("vi.system =:system"),
		@Condition("vi.latest =:latest")
		})
	@OrderBys({ 
		@OrderBy("vi.id desc")
	})
	List<VersionInfo> find(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination, @Order short orderIndex);
}
