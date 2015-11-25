/**
 * 
 */
package com.winxuan.ec.service.replenishment;

import com.winxuan.ec.model.replenishment.MrExaminer;

/**
 * @author john
 *
 */
public interface MrExaminerService {

	MrExaminer findExaminer(Long userId);
}
