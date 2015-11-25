package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.activity.ActivityShow;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author  zhoujun 
 * @version 1.0,2011-10-10
 */
public interface ActivityShowDao {
	@Query("from ActivityShow a where a.available=1")
	List<ActivityShow> find();
}
