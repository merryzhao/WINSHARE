package com.winxuan.ec.service;

import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.BaseDao;



/*******************************************
* @ClassName: AbstractBaseService 
* @Description: 做个抽象吧,方便后来人
* @author:cast911
* @param <T> 泛型标识
* @date:2014年9月10日 下午2:46:48 
*********************************************/
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractBaseService<T> implements BaseService<T> {

	public abstract BaseDao<T> getDao();

	@Override
	public void save(T t) {
		this.getDao().save(t);
	}

	@Override
	public void update(T t) {
		this.getDao().update(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		this.getDao().saveOrUpdate(t);
	}

	@Override
	public void delete(T t) {
		this.getDao().delete(t);
	}

	@Override
	public void evict(T t) {
		this.getDao().evict(t);
	}

	@Override
	public void merge(T t) {
		this.getDao().merge(t);
	}

}
