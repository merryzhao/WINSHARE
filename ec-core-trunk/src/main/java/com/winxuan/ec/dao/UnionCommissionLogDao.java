package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.union.UnionCommissionLog;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
/**
 * 
 * @author zhoujun
 * @version 1.0,2011-9-25
 */
public interface UnionCommissionLogDao {
	@Query("from UnionCommissionLog u where u.unionCommission.id = ?")
	List<UnionCommissionLog> findByUnionCommission(Long commissionId);

}
