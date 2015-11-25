package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.dc.DcRuleArea;
import com.winxuan.framework.dynamicdao.annotation.Delete;
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
import com.winxuan.framework.dynamicdao.type.FlushMode;
import com.winxuan.framework.pagination.Pagination;

/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 上午11:28:41  --!
 * @description:
 ********************************
 */
public interface DcDao {

	@Get
	DcRule getDc(Long id);

	@Get
	DcRuleArea getDcArea(Long id);

	@Save
	void saveDcArea(DcRuleArea area);
	
	@Save
	void saveDcRule(DcRule dcRule);
	
	@Update
	void updateDcRule(DcRule dcRule);
	
	@Delete
	void removeDcRuleArea(DcRuleArea dcRuleArea);

	
	@Query(value="select distinct d from DcRule d left join d.dcAreaList da",flushMode=FlushMode.COMMIT)
	@Conditions({
		@Condition("d.available =:available"),
		@Condition("da.area =:area")
	})
	@OrderBys({
		@OrderBy("d.priority desc"),
		@OrderBy("d.priority asc")
		
	})
	List<DcRule> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order Short sort);
}
