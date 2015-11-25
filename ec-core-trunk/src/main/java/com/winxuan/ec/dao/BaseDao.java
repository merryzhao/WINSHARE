package com.winxuan.ec.dao;

import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Evict;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;



/*******************************************
* @ClassName: BaseDao 
* @Description: TODO
* @author:cast911
* @param <T> 泛型标识
* @date:2014年9月10日 下午2:43:03 
*********************************************/
public interface BaseDao<T> {

	/**
	 * 
	 * @param t
	 *            Description: 保存对象 Date: 2014年5月5日 Time: 上午10:32:13
	 * @author LIPAN
	 */
	@Save
	void save(T t);

	/**
	 * 
	 * @param t
	 *            Description: 更新对象 Date: 2014年5月5日 Time: 上午10:32:28
	 * @author LIPAN
	 */
	@Update
	void update(T t);

	/**
	 * 
	 * @param t
	 *            Description: 保存或更新对象 Date: 2014年5月5日 Time: 上午10:32:40
	 * @author LIPAN
	 */
	@SaveOrUpdate
	void saveOrUpdate(T t);

	/**
	 * 
	 * @param t
	 *            Description: 删除实体 Date: 2014年5月5日 Time: 上午10:33:42
	 * @author LIPAN
	 */
	@Delete
	void delete(T t);

	/**
	 * 
	 * @param t
	 * @Description: 通过@merge,处理对象的saveOrUpdate，如果session中存在两个相同标识的对象信息，则合并再做保存。
	 * @Date: 2014年5月21日
	 * @Time: 上午10:00:38
	 * @author LIPAN
	 */
	@Merge
	void merge(T t);

	/**
	 * 
	 * @param t
	 * @Description: 通过@evit可以强制的解除对象在Hibernation Session中的关联信息，变为游离状态的对象。
	 * @Date: 2014年5月21日
	 * @Time: 上午10:00:59
	 * @author LIPAN
	 */
	@Evict
	void evict(T t);

}
