package com.winxuan.ec.service.activity;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ActivityShowDao;
import com.winxuan.ec.model.activity.ActivityShow;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-10
 */
@Service("activityShowService")
@Transactional(rollbackFor = Exception.class)
public class ActivityShowServiceImpl implements ActivityShowService {

	@InjectDao
	ActivityShowDao activityShowDao;
	@Override
	@Read
	@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
	public List<ActivityShow> find() {
		return activityShowDao.find();
	}
 
}
