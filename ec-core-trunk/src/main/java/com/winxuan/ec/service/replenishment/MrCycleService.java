package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.winxuan.ec.model.replenishment.MrCycle;
import com.winxuan.framework.pagination.Pagination;

/**
 * 补货周期管理
 * @author yangxinyi
 *
 */

public interface MrCycleService {
	
	void create(MrCycle mrcycle);
	
	MrCycle get(Long id);
	
	void save(MrCycle mrCycle);

	void delete(MrCycle mrCycle);

	void update(MrCycle mrCycle);
	
	List<MrCycle> findByMC(String mc);

	List<MrCycle> getAll(Pagination pagination);

	void saveData(InputStream is) throws IOException;
	
}
