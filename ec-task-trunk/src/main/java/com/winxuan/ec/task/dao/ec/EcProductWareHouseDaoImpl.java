package com.winxuan.ec.task.dao.ec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ec.EcProductWareHouse;
/**
 * description
 * @author  lean bian
 * @version 1.0,2013-09-26
 */
@Repository("ecProductWareHouseDao")
public class EcProductWareHouseDaoImpl implements EcProductWareHouseDao{
	private static final long serialVersionUID = 2505245255998376581L;
	private static Log log = LogFactory.getLog(EcProductWareHouseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	@Override
	public List<EcProductWareHouse> getChangedWarehouseList(int limit){
		String sql = "select * from product_warehouse p where p.ischanged = 1 limit " + limit;
		List<Map<String, Object>> queryResult = jdbcTemplateEcCore.queryForList(sql);
		
		return convertToEcProductWareHouseList(queryResult);
	}

	private List<EcProductWareHouse> convertToEcProductWareHouseList(
			List<Map<String, Object>> queryResult) {
		List<EcProductWareHouse> result = new ArrayList<EcProductWareHouse>(0);
		if(CollectionUtils.isEmpty(queryResult)){
			return result;
		}
		
		for(Map<String, Object> currentRow : queryResult){
			EcProductWareHouse newEcProductWareHouse = new EcProductWareHouse();
			newEcProductWareHouse.setId(Long.parseLong(currentRow.get("id").toString()));
			int isChanged = Boolean.valueOf(currentRow.get("ischanged").toString()) ? 1 : 0;
			newEcProductWareHouse.setIsChanged(isChanged);
			newEcProductWareHouse.setLocation(currentRow.get("location").toString());
			newEcProductWareHouse.setOuterId(currentRow.get("outerId").toString());
			newEcProductWareHouse.setStock(Integer.parseInt(currentRow.get("stock").toString()));
			result.add(newEcProductWareHouse);
		}
		return result;
	}
	
	@Override
	public void updateChanged(long id){
		String sql = "update product_warehouse p set p.ischanged = 0 where p.id = " + id;
		jdbcTemplateEcCore.update(sql);
	}
}
