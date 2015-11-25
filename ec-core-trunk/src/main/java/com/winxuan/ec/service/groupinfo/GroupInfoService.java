package com.winxuan.ec.service.groupinfo;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.groupinfo.GroupShoppingInfo;
import com.winxuan.framework.pagination.Pagination;
/**
 * 团购信息
 * @author cast911 玄玖东
 *
 */
public interface GroupInfoService {
	
	List<GroupShoppingInfo> find();
	
	GroupShoppingInfo get(Long id);
	
	void save(GroupShoppingInfo groupShoppingInfo);
	
	void update(GroupShoppingInfo groupShoppingInfo);
	
	void delete(GroupShoppingInfo groupShoppingInfo);
	
	List<GroupShoppingInfo> find(Map<String, Object> parameters, Pagination pagination,Short sort);
}
