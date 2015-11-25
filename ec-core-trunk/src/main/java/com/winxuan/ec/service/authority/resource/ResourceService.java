package com.winxuan.ec.service.authority.resource;

import java.util.List;

import com.winxuan.ec.exception.ResourceException;
import com.winxuan.ec.exception.ResourceGroupException;
import com.winxuan.ec.model.authority.Resource;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * 权限资源处理类
 * @author sunflower
 *
 */
public interface ResourceService {

	/**
	 * 添加资源
	 * @param resource
	 * @throws ResourceException 
	 */
	void addResource(Resource resource) throws ResourceException;
	
	/**
	 * 修改资源
	 * @param resource
	 */
	void modifyResource(Resource resource);
	
	/**
	 * 删除资源
	 * @param resource
	 */
	void deleteResource(Resource resource);
	

	/**
	 * 通过编码查询资源
	 * @param code
	 * @return
	 */
	Resource queryResourceByCode(String code);
	
	/**
	 * 通过ID查询资源
	 * @param code
	 * @return
	 */
	Resource queryResourceById(Long id);
	
	/**
	 * 通过编码查询资源
	 * @param groupCode
	 * @return
	 */
	ResourceGroup queryResourceGroupByCode(String groupCode);
	
	/**
	 * 添加资源组
	 * @param resourceGroup
	 * @throws ResourceGroupException 
	 */
	void addResourceGroup(ResourceGroup resourceGroup) throws ResourceGroupException;
	
	/**
	 * 修改资源组
	 * @param resourceGroup
	 */
	void modifyResourceGroup(ResourceGroup resourceGroup);
	
	/**
	 * 删除资源组
	 * @param resourceGroup
	 */
	void deleteResourceGroup(ResourceGroup resourceGroup);
	
	/**
	 * 给资源增加组
	 * @param resourceGroup
	 * @param resources
	 */
	void addResourcesToGroup(ResourceGroup resourceGroup,List<Resource> resources);
	

	/**
	 * 给资源添加组
	 * @param resourceGroups
	 * @param resource
	 */
	void addGroupsToResource(List<ResourceGroup> resourceGroups,Resource resource);
	
	
	/**
	 * 给员工添加资源
	 * @param employee
	 * @param resourceGroups
	 */
	void addResourceGroupToEmployee(Employee employee,List<ResourceGroup> resourceGroups);
	
	
	/**
	 * 查询所有的资源组
	 * @return
	 */
	List<ResourceGroup> queryResourceGroup();
	
	/**
	 * 查询所有的资源
	 * @return
	 */
	List<Resource> queryResources();
	
	
	/**
	 * 根据ID查询资源组
	 * @param id
	 * @return
	 */
	ResourceGroup queryResourceGroupById(Long id);

	/**
	 * 根据资源组查找资源
	 * @param resourceGroup
	 * @param pagination
	 * @return
	 */
	List<Resource> queryResources(Long groupId, Pagination pagination);
	
}
