package com.winxuan.ec.service.groupinfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.GroupInfoDao;
import com.winxuan.ec.model.groupinfo.GroupShoppingInfo;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 团购信息
 * @author cast911 玄玖东
 *
 */
@Service("groupInfoService")
@Transactional(rollbackFor = Exception.class)
public class GroupInfoServiceImpl implements GroupInfoService {

	@InjectDao
	private GroupInfoDao groupInfoDao;
	
	public GroupInfoDao getGroupInfoDao() {
		return groupInfoDao;
	}

	public void setGroupInfoDao(GroupInfoDao groupInfoDao) {
		this.groupInfoDao = groupInfoDao;
	}

	@Override
	public List<GroupShoppingInfo> find() {
		return null;
	}

	@Override
	public GroupShoppingInfo get(Long id) {
		return this.groupInfoDao.get(id);
	}

	@Override
	public void save(GroupShoppingInfo groupShoppingInfo) {
		this.groupInfoDao.save(groupShoppingInfo);
		
	}

	@Override
	public void update(GroupShoppingInfo groupShoppingInfo) {
		this.groupInfoDao.update(groupShoppingInfo);
		
	}

	@Override
	public void delete(GroupShoppingInfo groupShoppingInfo) {
		this.groupInfoDao.delete(groupShoppingInfo);
		
	}

	@Override
	public List<GroupShoppingInfo> find(Map<String, Object> parameters, Pagination pagination,Short sort) {
		return this.groupInfoDao.find(parameters, pagination, sort);
	}
}
