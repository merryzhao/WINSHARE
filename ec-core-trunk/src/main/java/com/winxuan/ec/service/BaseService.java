package com.winxuan.ec.service;

/*******************************************
 * @ClassName: BaseService
 * @Description: TODO
 * @author:cast911
 * @date:2014年9月10日 下午2:44:29
 * @param <T> 泛型标识
 *********************************************/
public interface BaseService<T> {
	/**
	 * 
	 * @param t
	 *            泛型标识
	 * @Description: 保存操作
	 * @Date: 2014年5月26日
	 * @Time: 上午9:50:32
	 * @author LIPAN
	 */
	void save(T t);

	/**
	 * 
	 * @param t
	 *            泛型标识
	 * @Description: 更新操作
	 * @Date: 2014年5月26日
	 * @Time: 上午9:51:13
	 * @author LIPAN
	 */
	void update(T t);

	/**
	 * 
	 * @param t
	 *            泛型标识
	 * @Description: 保存或更新操作
	 * @Date: 2014年5月26日
	 * @Time: 上午9:51:38
	 * @author LIPAN
	 */
	void saveOrUpdate(T t);

	/**
	 * 
	 * @param t
	 *            泛型标识
	 * @Description: 删除操作
	 * @Date: 2014年5月26日
	 * @Time: 上午9:52:47
	 * @author LIPAN
	 */
	void delete(T t);

	/**
	 * 
	 * @param t
	 *            泛型标识
	 * @Description: 解除对象在Hibernation Session中的关联信息
	 * @Date: 2014年5月26日
	 * @Time: 上午9:54:03
	 * @author LIPAN
	 */
	void evict(T t);

	/**
	 * 
	 * @param t
	 *            泛型标识
	 * @Description: 如果session中存在两个相同标识的对象信息，则合并再做保存。
	 * @Date: 2014年5月26日
	 * @Time: 上午9:54:07
	 * @author LIPAN
	 */
	void merge(T t);
}
