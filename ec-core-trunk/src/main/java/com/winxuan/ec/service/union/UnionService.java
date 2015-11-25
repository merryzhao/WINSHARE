package com.winxuan.ec.service.union;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.union.Union;
/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public interface UnionService {
	
	void save(Union union);
	
	void update(Union union);
	
	Union get(Long id);
	
    List<Union> find(Map<String,Object> parameters);
    
}
