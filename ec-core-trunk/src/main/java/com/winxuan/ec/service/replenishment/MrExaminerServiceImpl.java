/**
 * 
 */
package com.winxuan.ec.service.replenishment;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrExaminerDao;
import com.winxuan.ec.model.replenishment.MrExaminer;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author john
 *
 */
@Service("mrExaminerService")
@Transactional(rollbackFor = Exception.class)
public class MrExaminerServiceImpl implements MrExaminerService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6582950160062907303L;
	
	@InjectDao
	private MrExaminerDao mrExaminerDao;
	
	public MrExaminer findExaminer(Long userId){
		return this.mrExaminerDao.findExaminer(userId);
	}
}
