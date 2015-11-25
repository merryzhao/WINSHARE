/*
 * @(#)DeliveryServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.delivery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DeliveryCompanyDao;
import com.winxuan.ec.dao.DeliveryInfoDao;
import com.winxuan.ec.dao.DeliveryTypeDao;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.cache.BusinessCacheService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.employee.EmployeeBusinessService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * DeliveryService实现类
 * 
 * @author HideHai
 * @version 1.0,2011-7-18
 */
@Service("deliveryService")
@Transactional(rollbackFor = Exception.class)
public class DeliveryServiceImpl implements DeliveryService, Serializable {

	private static final long serialVersionUID = 3259885716147648314L;

	private static final Long AREA_JINNIU = 1510L;

	@InjectDao
	private DeliveryTypeDao deliveryTypeDao;
	@InjectDao
	private DeliveryCompanyDao deliveryCompanyDao;
	@InjectDao
	private DeliveryInfoDao deliveryInfoDao;
	@Autowired
	private CodeService codeService;
	@Autowired
	private EmployeeBusinessService employeeBusinessService;
	@Autowired
	private BusinessCacheService businessCacheService;

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DeliveryType getDeliveryType(Long id) {
		return deliveryTypeDao.get(id);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DeliveryType> findDeliveryType(boolean onlyAvailable) {
		return deliveryTypeDao.find(onlyAvailable ? true : null);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DeliveryCompany getDeliveryCompany(Long id) {
		return deliveryCompanyDao.get(id);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DeliveryCompany> findDeliveryCompany(boolean onlyAvailable) {
		if (onlyAvailable) {
			return deliveryCompanyDao.find(onlyAvailable);
		} else {
			return deliveryCompanyDao.find(null);
		}
	}

	private DeliveryInfo generateDiyDeliveryInfo(Area area) {
		DeliveryType diyDeliveryType = deliveryTypeDao.get(DeliveryType.DIY);
		DeliveryInfo diyDeliveryInfo = new DeliveryInfo();
		diyDeliveryInfo.setId(-1L);
		diyDeliveryInfo.setArea(area);
		diyDeliveryInfo.setDeliveryFeeRule("0");
		diyDeliveryInfo.setDeliveryFeeType(codeService
				.get(Code.DELIVERY_FEE_FIXED));
		diyDeliveryInfo.setDeliveryType(diyDeliveryType);
		return diyDeliveryInfo;
	}

	@Read 
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DeliveryInfo> findDeliveryInfo(Area area,
			DeliveryType deliveryType, boolean hasDiy ) {
		//	List<DeliveryInfo> deliveryInfos = findDeliveryInfoForUser();

		//1510是金牛区  写死球了,地址判断放在了checkout.js,当地址中包含"文轩路6号"时,hasDiy=true;否则hasDiy=false
		//		if (hasDiy && AREA_JINNIU.equals(area.getParent().getId())) {
		//			DeliveryInfo diyDeliveryInfo = generateDiyDeliveryInfo(area);
		//			deliveryInfos.add(diyDeliveryInfo);
		//		}
		return findNoneDiyDeliveryInfo(area, deliveryType);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DeliveryInfo> findDeliveryInfoForEmployee(Area area, DeliveryType deliveryType, String address, Employee employee){
		List<DeliveryInfo> deliveryInfos = findNoneDiyDeliveryInfo(area, deliveryType);
		if(canDeliveryByDiy(area, address, employee)){
			deliveryInfos.add(generateDiyDeliveryInfo(area));
		}
		return deliveryInfos;
	}

	private boolean canDeliveryByDiy(Area area, String address, Employee employee) {
		Long parentId = area.getParent().getId();
		return employeeBusinessService.get(employee, codeService.get(Code.DELIVERY_DEMAND_PRIVILEGE)) != null 
				&& address != null && ("蓉北商贸大道文轩路6号").equals(address) && AREA_JINNIU.equals(parentId);
	}

	private List<DeliveryInfo> findNoneDiyDeliveryInfo(Area area, DeliveryType deliveryType){
		List<DeliveryInfo> deliveryInfos = getDeliveryInfoByArea(area, deliveryType);
		if (area == null && deliveryType != null) {
			deliveryInfos = deliveryInfoDao.find(area, deliveryType, Code.DC_8A17, (short) 0);
		}
		return deliveryInfos;
	}

	private List<DeliveryInfo> getDeliveryInfoByArea(Area area,
			DeliveryType deliveryType) {
		List<DeliveryInfo> deliveryInfos = new ArrayList<DeliveryInfo>();
		if (area != null) {
			setDeliveryInfosWithAreaAndDeliveryType(deliveryInfos, area, deliveryType);
		}
		return deliveryInfos;
	}

	@Read
	@Deprecated
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DeliveryInfo> findDeliveryInfo(Area area,
			DeliveryType deliveryType, String address ) {
		boolean hasDiy = false;
		if(address != null && address.indexOf("文轩路6号") != -1){
			hasDiy = true;
		}
		return findDeliveryInfo(area, deliveryType, hasDiy);
	}



	/**
	 * 设置deliveryInfo列表
	 * 
	 * @param deliveryInfos
	 * @param areas
	 * @param deliveryType
	 */
	private void setDeliveryInfosWithAreaAndDeliveryType(
			List<DeliveryInfo> deliveryInfos, Area area,
			DeliveryType deliveryType) {
		Set<Area> areas = null;
		if (area.hasChildren()) {
			areas = area.getChildren();
		} else {
			areas = new HashSet<Area>();
			areas.add(area);
		}
		for (Area a : areas) {
			List<DeliveryInfo> leafDeliveryInfos = deliveryInfoDao.find(a,
					deliveryType, Code.DC_8A17, (short) 0);
			if (leafDeliveryInfos != null && !leafDeliveryInfos.isEmpty()) {
				for (DeliveryInfo deliveryInfo : leafDeliveryInfos) {
					deliveryInfos.add(deliveryInfo);
				}
			}
		}
	}

	public void update(DeliveryType deliveryType) {
		deliveryTypeDao.update(deliveryType);
	}

	public void update(DeliveryCompany deliveryCompany) {
		deliveryCompanyDao.update(deliveryCompany);
	}

	public void update(DeliveryInfo deliveryInfo) {
		deliveryInfoDao.update(deliveryInfo);
	}

	public void delete(DeliveryInfo deliveryInfo) {
		deliveryInfoDao.delete(deliveryInfo);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DeliveryInfo getDeliveryInfo(Long id) {
		return deliveryInfoDao.get(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal getDeliveryFee(DeliveryType deliveryType, Area area,
			BigDecimal listPrice) {
		DeliveryInfo deliveryInfo = deliveryInfoDao.getByDeliveryTypeAndArea(
				deliveryType, area);
		return deliveryInfo == null ? BigDecimal.ZERO : deliveryInfo
				.getDeliveryFee(listPrice);
	}

	@Override
	public DeliveryType findDefaultDeliveryType(Channel channel, Area district) {
		String key = deliveryTypeCacheKey(channel, district);
		Object keyObject = businessCacheService.get(key);
		DeliveryType deliveryType =keyObject == null ? null : (DeliveryType) keyObject;
		if(deliveryType == null) {
			long id  = deliveryTypeDao.findDeliveryType(channel.getId(),district.getId());
			deliveryType = getDeliveryType(id);
			businessCacheService.put(key, deliveryType);
		}
		return deliveryType;
	}

	/**
	 * 渠道默认缓存key
	 * @param channel
	 * @param district
	 * @return
	 */
	private String deliveryTypeCacheKey(Channel channel, Area district) {
		StringBuilder sb = new StringBuilder();
		sb.append("DeliveryService");
		sb.append("_");
		sb.append(channel.getId());
		sb.append("_");
		sb.append(district.getId());
		return sb.toString();
	}

}
