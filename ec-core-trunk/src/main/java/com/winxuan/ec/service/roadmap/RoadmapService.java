package com.winxuan.ec.service.roadmap;

import java.util.List;
import com.winxuan.ec.model.roadmap.Roadmap;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author renshiyong
 * @version 1.0,2011-8-23
 */

public interface RoadmapService {
	/**
	 * 保存路线图、版本发布历史信息
	 * @param roadmap
	 */
	void save(Roadmap roadmap);
	
	/**
	 * 查询版本记录
	 * @param pagination
	 * @param orderIndex
	 * @return
	 */
    List<Roadmap> findRoadmaps(Pagination pagination, short orderIndex);
}
