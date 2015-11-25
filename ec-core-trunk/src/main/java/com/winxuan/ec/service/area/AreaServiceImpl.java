/*
 * @(#)AreaServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.area;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.AreaDao;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.area.AreaDefault;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.AcceptHashMap;

/**
 * 区域服务接口实现类
 * 
 * @author wumaojie
 * @version 1.0,2011-7-18
 */
@Service("areaService")
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl implements AreaService {

	@InjectDao
	private AreaDao areaDao;

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Area get(Long id) {
		return areaDao.get(id);
	}

	public void update(Area area) {
		areaDao.update(area);
	}

	@Override
	public List<Area> get(String name) {
		return this.areaDao.get(name);
	}

	@Override
	public Area getDefTownByDistrict(Area district) {
		AreaDefault areaDefault = areaDao
				.getDefTownByDistrict(district.getId());
		if (areaDefault != null) {
			return areaDefault.getArea();
		}
		return null;
	}

	@Override
	public long getNoDefTownDistrictCount() {
		return areaDao.getNoDefTownDistrictCount();
	}

	@Override
	public void addDistrictDefTown(Area town) {
		AreaDefault administrate = areaDao.getDefTownByDistrict(town
				.getParent().getId());
		if (administrate != null) {
			administrate.setArea(town);
			areaDao.update(administrate);
		} else {
			AreaDefault areaDefault = new AreaDefault();
			areaDefault.setArea(town);
			areaDefault.setParent(town.getParent().getId());
			areaDao.save(areaDefault);
		}
	}

	@Override
	public Area matchTownByAddress(Area district, String address) {
		Map<String, Object> parame = this.afferentByAddressReturnTown(address);
		String town = (String) parame.get("town");
		if (town != null && !"".equals(town)) {
			parame.put("district", district.getName());
			List<Area> areas = areaDao.getDefTownByName(parame);
			if (!areas.isEmpty()) {
				return areas.get(0);
			} else {
				return this.getDefTownByDistrict(district);
			}
		} else {
			return this.getDefTownByDistrict(district);
		}
	}

	public Map<String, Object> afferentByAddressReturnTown(String address) {
		Map<String, Object> parame = new AcceptHashMap<String, Object>();
		String village = "乡";
		String town = "镇";
		String[] add = address.split("区");
		if (add.length > 1) {
			String[] addres = add[1].split(village);
			if (addres.length > 1) {
				parame.put("town", addres[0] + village);
			}
			String[] towns = add[1].split(town);
			if (towns.length > 1) {
				parame.put("town", towns[0] + town);
			}
			return parame;
		}
		String[] county = address.split("县");
		if (county.length > 1) {
			String[] vill = county[1].split(village);
			if (vill.length > 1) {
				parame.put("town", vill[0] + village);
			}
			String[] towns = county[1].split(town);
			if (towns.length > 1) {
				parame.put("town", towns[0] + town);
			}
		}
		return parame;
	}

	@Override
	public List<Map<String, Object>> getNoDefTownDistrictList() {
		return areaDao.getNoDefTownDistrictList();
	}

	@Override
	public List<Map<String, Object>> find(String level,String path) {
		return areaDao.find(level,path);
	}

	@Override
	public List<Area> findChildrenByParentId(Long parentId) {
		return this.areaDao.findChildrenByParentId(parentId);
	}

}
