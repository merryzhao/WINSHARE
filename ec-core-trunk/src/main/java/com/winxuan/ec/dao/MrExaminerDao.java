/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.replenishment.MrExaminer;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @author john
 *
 */
public interface MrExaminerDao {

	@Query("from MrExaminer me where me.user.id = ?")
	MrExaminer findExaminer(Long userId);
}
