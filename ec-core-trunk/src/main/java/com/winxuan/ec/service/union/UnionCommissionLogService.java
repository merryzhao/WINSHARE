package com.winxuan.ec.service.union;

import java.util.List;

import com.winxuan.ec.model.union.UnionCommissionLog;

/**
 * @author zhoujun
 * @version 1.0,2011-9-24
 */
public interface UnionCommissionLogService {

	List<UnionCommissionLog> findByUnionCommission(Long id);
}
