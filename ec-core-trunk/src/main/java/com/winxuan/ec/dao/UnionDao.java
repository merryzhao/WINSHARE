package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.union.Union;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
/**
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public interface UnionDao {

	@Save
	void save(Union union);
	
	@Update
	void update(Union union);
	
	@Get
	Union get(Long id); 
	
	@Query("from Union u where u.show =1 and u.available=1")
	@Conditions({
		@Condition("u.id in:unionIds ")
	})
	List<Union> find(@ParameterMap Map<String,Object> parameters);
}
