package com.winxuan.ec.service.subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SubjectDao;
import com.winxuan.ec.model.subject.Subject;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 *
 */
@Service("subjectService")
@Transactional(rollbackFor = Exception.class)
public class SubjectServiceImpl implements SubjectService {

	@InjectDao
	private SubjectDao subjectDao;
	
	@Override
	public List<Subject> querySubjects(Pagination pagination) {
		return subjectDao.querySubjects(pagination,(short) 0);
	}

	@Override
	public void addSubject(Subject subject) {
		subjectDao.save(subject);
	}

	@Override
	public Subject get(long id) {
		return subjectDao.get(id);
	}


	@Override
	public void delSubject(Subject subject) {
		subjectDao.del(subject);
	}

	@Override
	public String isSubjectProduct(String productId) {
		List<Map<String,Object>> sujects = subjectDao.isSubjectProduct(productId);
		if(sujects.size()>0)
		{
		Long[] ids = new Long[sujects.size()];
		for (int i = 0; i < sujects.size(); i++) {
			ids[i] = Long.valueOf(sujects.get(i).get("subject").toString());
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		List<Subject> subjects = subjectDao.findSubjects(params, null, (short)0);
		for (Subject subject : subjects) {
			if(StringUtils.isNotBlank(subject.getTagurl())){
				return subject.getTagurl();
			}
		}
		}
		return "";
	}

}
