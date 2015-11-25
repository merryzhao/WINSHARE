package com.winxuan.ec.service.version;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.VersionDao;
import com.winxuan.ec.model.version.ClientInfo;
import com.winxuan.ec.model.version.VersionInfo;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author zhousl
 *
 * 2013-4-3
 */
@Service(value="versionService")
@Transactional(rollbackFor = Exception.class)
public class VersionServiceImpl implements VersionService{

	@InjectDao
	private VersionDao versionDao;
	
	@Override
	public List<VersionInfo> find(Map<String, Object> parameters,
			Pagination pagination, short orderIndex) {
		return versionDao.find(parameters, pagination,orderIndex);
	}

	@Override
	public VersionInfo get(Long id) {
		return versionDao.get(id);
	}

	@Override
	public void update(VersionInfo versionInfo) {
		versionDao.update(versionInfo);
		
	}

	@Override
	public void save(VersionInfo versionInfo) {
		versionDao.save(versionInfo);
	}

	@Override
	public void save(ClientInfo clientInfo) {
		versionDao.save(clientInfo);
	}

}
