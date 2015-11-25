package com.winxuan.ec.service.subject;

import java.util.List;

import com.winxuan.ec.model.subject.Subject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 *
 */
public interface SubjectService {

	/**
	 * 分页查询已经创建的专题
	 * @param pagination
	 * @return
	 */
	List<Subject> querySubjects(Pagination pagination);

	/**
	 * 添加专题
	 * @param subject
	 */
	void addSubject(Subject subject);

	Subject get(long id);

	/**
	 * 删除指定id的专题
	 * @param subject
	 */
	void delSubject(Subject subject);
	
	/**
	 * 是否属于专题活动商品并返回专题标签
	 * @param productId
	 * @return
	 */
	String isSubjectProduct(String productId);
	
	
}
