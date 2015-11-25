package com.winxuan.ec.service.roadmap;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.RoadmapDao;
import com.winxuan.ec.model.roadmap.Roadmap;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author renshiyong
 * @version 1.0,2011-8-23
 */

@Service("roadmapService")
@Transactional(rollbackFor = Exception.class)
public class RoadmapServiceImpl implements RoadmapService {

	@InjectDao
	private RoadmapDao roadmapDao;
	public void save(Roadmap roadmap) {
		// TODO Auto-generated method stub
		roadmapDao.save(roadmap);

	}
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Roadmap> findRoadmaps(Pagination pagination, short orderIndex) {
        return roadmapDao.findRoadmaps(pagination, orderIndex);
	}

}
