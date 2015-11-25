package com.winxuan.ec.service.union;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.union.UnionCommission;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author zhoujun
 * @version 1.0,2011-9-8
 */
public interface UnionCommissionService {
	
    void save(UnionCommission unionCommission);
    
    void update(UnionCommission unionCommission);
    
    UnionCommission get(Long id);
    
    UnionCommission get(Long id, String time); 
    
    List<UnionCommission> find(Map<String,Object> parameters,Short sort,Pagination pagination);
    
}
