package com.winxuan.ec.task.service.ec;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.task.model.ec.EcProductWareHouse;
/**
 * description
 * @author  lean bian
 * @version 1.0,2013-09-26
 */
public interface EcProductWareHouseService extends Serializable{
	void changeStockAndStateOneByOne(EcProductWareHouse changedEcProductWareHouseItem);
	
	List<EcProductWareHouse> getChangedWarehouseList(int limit);
}
