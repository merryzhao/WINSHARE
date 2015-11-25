package com.winxuan.ec.service.authority.user;

import java.util.List;

import com.winxuan.ec.exception.AuthorityException;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * 权限用户处理类
 * @author sunflower
 *
 */
public interface UserService {

	
	/**
	 * 用户添加
	 * @param authorityUser
	 * @throws AuthorityException 
	 */
	void addUser(Employee employee,Employee operator) throws AuthorityException;
	
	/**
	 * 用户修改
	 * @param authorityUser
	 * @throws AuthorityException 
	 */
	void modifyUser(Employee employee,Employee operator) throws AuthorityException;
	
	/**
	 * 用户删除
	 * @param authorityUser
	 */
	void deleteUser(Employee employee);
	
	/**
	 * 通过ID查询用户
	 * @param authorityUser
	 * @return
	 */
	Employee queryUserById(Long employeeId); 
	
	/**
	 * 修改密码
	 * @param authorityUser
	 */
	void modifyPassword(Employee employee);
	
	/**
	 * 重置密码
	 * @param authorityUser
	 * @throws AuthorityException 
	 */
	void resetPassword(Employee employee,Employee operator) throws AuthorityException;
	
	/**
	 * 通过资源组查询用户
	 * @param pagination
	 * @param groupId
	 * @return
	 */
	List<Employee> queryUsers(Pagination pagination,Long groupId); 
	
	/**
	 * 给雇员设置权限
	 * @param resourceGroups
	 * @param employee
	 */
	void addGroupsToEmployee(List<ResourceGroup> resourceGroups,Employee employee);

	/**
	 * 启用禁用雇员
	 * @param employee
	 * @return 
	 */
	Employee enableOrDisable(Employee employee);
}
