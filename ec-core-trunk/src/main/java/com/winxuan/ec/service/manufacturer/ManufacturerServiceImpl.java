package com.winxuan.ec.service.manufacturer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ManufacturerDao;
import com.winxuan.ec.exception.ManufacturerException;
import com.winxuan.ec.model.manufacturer.Manufacturer;
import com.winxuan.ec.model.manufacturer.ManufacturerItem;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 制造商实现 
 * @author heyadong
 * @version 1.0, 2012-9-17 上午11:00:20
 */
@Service("manufacturerService")
@Transactional(rollbackFor=Exception.class)
public class ManufacturerServiceImpl implements ManufacturerService {
    @InjectDao
    private ManufacturerDao manufacturerDao;

    @Transactional(readOnly=true)
    @Override
    public Manufacturer get(Integer id) {
        return manufacturerDao.get(id);
    }
    
    
    @Transactional(readOnly=true)
    @Override
    public List<Manufacturer> query(Map<String, Object> params,
            Pagination pagination) {
        Short index = 0;
        return manufacturerDao.query(params, pagination, index);
    }

    @Override
    public void save(Manufacturer m) throws ManufacturerException {
        if (manufacturerDao.uniqueName(m.getName(), MagicNumber.ZERO)) {
            throw new ManufacturerException(m, String.format("%s 供应商名字重复", m.getName()));
        } else if (manufacturerDao.uniqueItemName(m.getName(), MagicNumber.ZERO )) {
            throw new ManufacturerException(m, String.format("%s 子供应商名字重复", m.getName()));
        }
        
        manufacturerDao.save(m);
    }

    public void remove(Manufacturer m) {
        manufacturerDao.remove(m);
        
    }


    @Override
    public void updateName(Manufacturer manufacturer, String name) throws ManufacturerException {
        
        if (manufacturer != null && !manufacturer.getName().equals(name)) {
            if (manufacturerDao.uniqueName(name, manufacturer.getId())) {
                throw new ManufacturerException(manufacturer, String.format(",%s与供应商名字重复",name));
            } else if (manufacturerDao.existsOrtherItemName(name, manufacturer.getId())) {
                throw new ManufacturerException(manufacturer, String.format(",%s与其他其子供应商名字重复",name));
            }
            
            //如果子供应没有  原有供应商的名字,添加到子供应商
            if (!manufacturerDao.uniqueItemName(manufacturer.getName(), MagicNumber.ZERO)) {
                ManufacturerItem item = new ManufacturerItem();
                item.setName(manufacturer.getName());
                item.setManufacturer(manufacturer);
                manufacturerDao.saveItem(item);
            }
            manufacturer.setName(name);
            manufacturerDao.save(manufacturer);
        }
        
    }


    @Override
    public void appendItem(Manufacturer manufacturer, String itemName)
            throws ManufacturerException {
        if (manufacturerDao.uniqueName(itemName, MagicNumber.ZERO)) {
            throw new ManufacturerException(manufacturer, String.format("%s与应商名字重复",itemName));
        }else if(manufacturerDao.uniqueItemName(itemName,  MagicNumber.ZERO)) {
            throw new ManufacturerException(manufacturer, String.format("%s与子供应商名字重复",itemName));
        }
        
        ManufacturerItem item = new ManufacturerItem();
        item.setName(itemName);
        item.setManufacturer(manufacturer);
        manufacturerDao.saveItem(item);
        
    }


  
}
