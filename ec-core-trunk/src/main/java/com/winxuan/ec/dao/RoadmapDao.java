package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.roadmap.Roadmap;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;
/**
 * description
 * @author renshiyong
 * @version 1.0,2011-8-23
 */

public interface RoadmapDao {
	@Save
	  void save(Roadmap roadmap);
	
	@Query("from Roadmap r")
		@OrderBys({
		@OrderBy("r.id desc"),
		@OrderBy("r.id asc")
	})
	  List<Roadmap> findRoadmaps(@Page Pagination pagination,@Order short orderIndex);
}
