package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.manufacturer.Manufacturer;
import com.winxuan.ec.model.manufacturer.ManufacturerItem;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * ManufacturerDao
 * @author heyadong
 * @version 1.0, 2012-9-17 上午10:32:57
 */
public interface ManufacturerDao {
    
    @Get
    Manufacturer get(Integer id);
    
    @Query("select distinct m from Manufacturer m LEFT JOIN m.items mi")
    @Conditions({
        @Condition(" mi.name LIKE :nickname"),
        @Condition(" m.name LIKE :name")
    })
    @OrderBys({
        @OrderBy(" m.id")
    })
    List<Manufacturer> query(@ParameterMap Map<String, Object> params, @Page Pagination pagination, @Order Short index);
    @Save
    void save(Manufacturer m);
    @Delete
    void remove(Manufacturer m);
    
    @Save
    void saveItem(ManufacturerItem mi);
    
    @Delete
    void removeItem(ManufacturerItem mi);
    
    @Query(value="SELECT count(1) FROM manufacturer WHERE name = ? and id != ?", sqlQuery=true)
    boolean uniqueName(String name, Integer id);
    
    @Query(value="SELECT count(1) FROM manufacturer_item WHERE name = ? and id != ?", sqlQuery=true)
    boolean uniqueItemName(String name, Integer id);
    
    @Query(value="SELECT count(1) FROM manufacturer_item WHERE name = ? and manufacturer != ? ",sqlQuery=true)
    boolean existsOrtherItemName(String itemName, Integer manufacturerId);
}
