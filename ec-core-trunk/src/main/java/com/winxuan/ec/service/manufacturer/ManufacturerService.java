package com.winxuan.ec.service.manufacturer;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.ManufacturerException;
import com.winxuan.ec.model.manufacturer.Manufacturer;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * 制造商,服务接口
 * @author heyadong
 * @version 1.0, 2012-9-17 上午11:00:12
 */
public interface ManufacturerService {
    
    Manufacturer get(Integer id);
   
    List<Manufacturer> query(@ParameterMap Map<String, Object> params, @Page Pagination pagination);
   
    void updateName(Manufacturer m , String name) throws ManufacturerException;
    
    void appendItem(Manufacturer m, String itemName) throws ManufacturerException;
    
    void save(Manufacturer m) throws ManufacturerException;
    
    
}
