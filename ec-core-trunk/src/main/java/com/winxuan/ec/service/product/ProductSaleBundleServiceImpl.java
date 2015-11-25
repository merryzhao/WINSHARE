package com.winxuan.ec.service.product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductSaleBundleDao;
import com.winxuan.ec.exception.ProductSaleBundleException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleBundle;
import com.winxuan.ec.model.product.ProductSaleBundleLog;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 
 * @author juqkai(juqkai@gmail.com)
 */
@Service("productSaleBundleService")
@Transactional(rollbackFor = Exception.class)
public class ProductSaleBundleServiceImpl implements ProductSaleBundleService{
    
    @InjectDao
    private ProductSaleBundleDao productSaleBundleDao; 
    
    @Autowired
    private ProductService productService;
    
    public ProductSaleBundle get(Long id) {
        return productSaleBundleDao.get(id);
    }
    public void save(ProductSaleBundle bundleSale) {
        productSaleBundleDao.save(bundleSale);
    }
    
    public void update(ProductSaleBundle bundleSale) {
        productSaleBundleDao.update(bundleSale);
    }
    
    public void saveLog(ProductSaleBundleLog log) {
        productSaleBundleDao.save(log);
    }
    /**
     * 保存
     */
    public void save(Employee user, List<ProductSale> masters, Map<ProductSale, BigDecimal> products){
        for (ProductSale master : masters) {
            for (Entry<ProductSale, BigDecimal> entry : products.entrySet()) {
                save(user, master, entry.getKey(), entry.getValue());
            }
        }
    }
    
    private void save(User user, ProductSale master, ProductSale productSale, BigDecimal saveMoney){
        if(master.getId().equals(productSale.getId())){
            return;
        }
        canSetSaveMoney(productSale, saveMoney);
        
        ProductSaleBundle bundle = new ProductSaleBundle();
        bundle.setMaster(master);
        bundle.setProductSale(productSale);
        bundle.setSaveMoney(saveMoney);
        bundle.setStatus(true);
        save(bundle);
        
        saveLog(master, productSale, user, Code.OPERATE_SAVE, saveMoney);
        
        if(!master.isHasBundle()){
            master.setHasBundle(true);
            productService.update(master);
        }
    }
    private void canSetSaveMoney(ProductSale productSale, BigDecimal saveMoney){
        if(saveMoney.compareTo(BigDecimal.ZERO) < 0 || productSale.getSalePrice().compareTo(saveMoney) <= 0){
            throw new ProductSaleBundleException(productSale.getId() + "价格不能比销售价格低!");
        }
    }
    /**
     * 修改
     */
    public void edit(ProductSale master, Map<ProductSale, BigDecimal> psbs,  User user){
        List<ProductSaleBundle> dels = new ArrayList<ProductSaleBundle>(); 
        for(ProductSaleBundle sb : master.getBundles()){
            ProductSale pstmp = sb.getProductSale();
            if(psbs.containsKey(pstmp)){
                //修改
                if(sb.getSaveMoney().compareTo(psbs.get(pstmp)) != 0){
                    canSetSaveMoney(sb.getProductSale(), psbs.get(pstmp));
                    sb.setSaveMoney(psbs.get(pstmp));
                    update(sb);
                    
                    saveLog(master, sb.getProductSale(), user, Code.OPERATE_UPDATE, psbs.get(pstmp));
                }
                psbs.remove(pstmp);
            } else {
                //删除
                dels.add(sb);
            }
        }
        //删除
        del(master, dels, user);
        //添加不存在的
        for(Entry<ProductSale, BigDecimal> en : psbs.entrySet()){
            save(user, master, en.getKey(), en.getValue());
        }
    }
    /**
     * 删除
     * @param master
     * @param dels
     * @param user
     */
    private void del(ProductSale master, List<ProductSaleBundle> dels, User user){
        for(ProductSaleBundle sb : dels){
            del(master, sb, user);
        }
    }
    
    private void del(ProductSale master, ProductSaleBundle sb, User user){
        saveLog(master, sb.getProductSale(), user, Code.OPERATE_DELETE, BigDecimal.ZERO);
        
        master.getBundles().remove(sb);
        sb.setMaster(null);
        productSaleBundleDao.del(sb);
        if(master.getBundles().size() <= 0){
            master.setHasBundle(false);
            productService.update(master);
        }
    }
    public void del(Long psbid, User user){
        ProductSaleBundle psb = productSaleBundleDao.get(psbid);
        del(psb.getMaster(), psb, user);
    }

    private void saveLog(ProductSale master, ProductSale productSale, User user, Long code, BigDecimal saveMoney){
        ProductSaleBundleLog log = new ProductSaleBundleLog();
        log.setMaster(master);
        log.setProductSale(productSale);
        log.setOperateDate(new Date());
        log.setOperateUser(user);
        log.setOperate(new Code(code));
        log.setSaveMoney(saveMoney);
        
        saveLog(log);
    }
    /**
     * 显示日志
     */
    public List<ProductSaleBundleLog> listLogForMaster(Long masterId) {
        return productSaleBundleDao.listLogForMaster(masterId);
    }
    
}
