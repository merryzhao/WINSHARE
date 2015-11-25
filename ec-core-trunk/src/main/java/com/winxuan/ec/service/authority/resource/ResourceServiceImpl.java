package com.winxuan.ec.service.authority.resource;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.EmployeeDao;
import com.winxuan.ec.dao.ResourceDao;
import com.winxuan.ec.dao.ResourceGroupDao;
import com.winxuan.ec.exception.ResourceException;
import com.winxuan.ec.exception.ResourceGroupException;
import com.winxuan.ec.model.authority.Resource;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;


/**
 * 资源实现类
 * @author sunflower
 *
 */
@Service("resourceService")
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {
	
	@InjectDao
	private ResourceDao resourceDao;
	
	@InjectDao
	private ResourceGroupDao resourceGroupDao;
	
	@InjectDao
	private EmployeeDao employeeDao;

	@Override
	public void addResource(Resource resource) throws ResourceException {

		if(isResourceExist(resource)){
			throw new ResourceException(resource,"该资源已经存在");
		}
		resource.setCreateTime(new Date());
		resourceDao.save(resource);
	}

	private boolean isResourceExist(Resource resource) {
		
		return  queryResourceByCode(resource.getCode()) != null;
	}

	@Override
	public void modifyResource(Resource resource) {
		
		resourceDao.update(resource);
	}

	@Override
	public void deleteResource(Resource resource) {
		
		resourceDao.delete(resource);
	}

	@Override
	public void addResourceGroup(ResourceGroup resourceGroup) throws ResourceGroupException {
		
		if(isResourceGroupExist(resourceGroup)){
			throw new ResourceGroupException(resourceGroup,"该资源组已经存在");
		}
		resourceGroup.setCreateTime(new Date());
		resourceGroupDao.save(resourceGroup);
	}

	private boolean isResourceGroupExist(ResourceGroup resourceGroup) {
		
		return  queryResourceGroupByCode(resourceGroup.getCode()) != null;
	}

	@Override
	public void modifyResourceGroup(ResourceGroup resourceGroup) {
		
		resourceGroupDao.update(resourceGroup);
	}

	@Override
	public void deleteResourceGroup(ResourceGroup resourceGroup) {
		Set<Employee> employees = resourceGroup.getEmployees();
		Iterator<Employee> it = employees.iterator();
		while(it.hasNext()){
			Employee employee = it.next();
			employee.removeResourceGroup(resourceGroup);
			employeeDao.update(employee);
		}
		//resourceGroupDao.evict(resourceGroup);
		resourceGroupDao.delete(resourceGroup);
	}

	@Override
	public void addResourcesToGroup(ResourceGroup resourceGroup,
			List<Resource> resources) {
		
		if(resources == null || resources.size() == 0){
			return;
		}
		for(Resource resource : resources){
			resourceGroup.addResource(resource);
		}
		resourceGroupDao.update(resourceGroup);
	}

	@Override
	public void addResourceGroupToEmployee(Employee employee,
			List<ResourceGroup> resourceGroups) {
		
		if(resourceGroups == null || resourceGroups.size() == 0){
			return;
		}
		for(ResourceGroup resourceGroup : resourceGroups){
			employee.addResourceGroup(resourceGroup);
		}
		employeeDao.update(employee);
	}

	@Override
	public List<ResourceGroup> queryResourceGroup() {
		
		return resourceGroupDao.findAll();
	}

	@Override
	public List<Resource> queryResources(Long groupId, Pagination pagination) {
		
		return resourceDao.find(groupId,pagination);
	}

	@Override
	public Resource queryResourceByCode(String code) {
		return resourceDao.find(code);
	}
	
	@Override
	public ResourceGroup queryResourceGroupByCode(String groupCode) {
		return resourceGroupDao.find(groupCode);
	}

	@Override
	public void addGroupsToResource(List<ResourceGroup> resourceGroups,
			Resource resource) {
		if(resourceGroups == null || resourceGroups.size() == 0){
			return;
		}
		resource.setResourceGroups(null);
		for(ResourceGroup resourceGroup : resourceGroups){
			resource.addResourceGroup(resourceGroup);
			
		}
		resourceDao.merge(resource);
	}

	@Override
	public Resource queryResourceById(Long id) {
		return resourceDao.get(id);
	}

	@Override
	public ResourceGroup queryResourceGroupById(Long id) {
		return resourceGroupDao.get(id);
	}

	@Override
	//@MethodCache(idleTime="09:00")
	public List<Resource> queryResources() {
		return resourceDao.findAll();
	}
	
	


}
