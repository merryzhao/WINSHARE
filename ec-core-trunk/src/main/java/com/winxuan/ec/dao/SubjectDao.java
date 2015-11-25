package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.subject.Subject;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
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
 * 
 * @author sunflower
 *
 */
public interface SubjectDao {

	@Query("from Subject s ")
	@OrderBys({ @OrderBy("s.updateTime desc") })
	List<Subject> querySubjects(@Page Pagination pagination, @Order Short sort);

	@Save
	void save(Subject subject);

	@Get
	Subject get(long id);

	@Delete
	void del(Subject subject);
	
	@Query("from Subject s ")
	@Conditions({
		@Condition("s.id in :ids"),
		@Condition("s.deploy = :deploy")
	})
	@OrderBys({ @OrderBy("s.updateTime desc") })
	List<Subject> findSubjects(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination, @Order Short sort);
	
	@Query(sqlQuery = true,value="select substring(page,9)as subject from cm_fragment where id in (select "+
			" fragment from cm_element where datatype = 'p' and content = ?) and jspfile LIKE '%subject%' ")
	List<Map<String,Object>> isSubjectProduct(String productId);

}
