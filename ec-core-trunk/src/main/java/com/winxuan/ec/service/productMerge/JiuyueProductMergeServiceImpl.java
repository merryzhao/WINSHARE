package com.winxuan.ec.service.productMerge;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductMergeTaskDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductMerge;
import com.winxuan.ec.model.product.ProductMergeTask;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 合并9月过来的商品
 * User: juqkai
 * Date: 13-4-27
 * Time: 上午11:33
 */
@Service
public class JiuyueProductMergeServiceImpl implements ProductMergeService {
	private static final Short COMPLEX=0;//
    @InjectDao
    ProductMergeTaskDao productMergeTaskDao;

    @Autowired
    ProductJiuyueService productJiuyueService;

    @Autowired
    ProductService productService;

    //缓存列表
    LinkedList<ProductMergeTask> productMerges = new LinkedList<ProductMergeTask>();

    //最后一个任务
    ProductMergeTask lastTask;

    Lock lock = new ReentrantLock();

    @Override
    @Transactional(rollbackFor = Exception.class)
	public void cancelOperate(Employee employee,ProductSale jiuyueProductSale) {
    	  Map<String, Object> param = new HashMap<String, Object>();
          param.put("productsaleid",jiuyueProductSale.getId());
          ProductMergeTask task = productMergeTaskDao.fetchProductMergeTask(param);
          task.setStatus(new Code(Code.PRODUCT_MERTE_STATUS_NONE));
          task.setUser(employee);
          task.setLastTime(new Date());
          
          this.cancelEbookAttr(jiuyueProductSale);
          
          jiuyueProductSale.setProduct(task.getProduct());
          productService.update(jiuyueProductSale);
          productMergeTaskDao.update(task);
	}
    
    
    @Override
    public Map<ProductSale, List<ProductSale>> fetchMergeItem() {
        while(true){
            ProductMergeTask pm = fetchProductMergeTask();
            if (null == pm) {
                return null;
            }

            ProductSale ps = productService.getProductSale(pm.getProductSale().getId());
            appendImage(ps);

            Map<String, Object> psparam = new HashMap<String, Object>();
            psparam.put("productBarcode", pm.getProduct().getBarcode());
            psparam.put("notStorageType", Code.STORAGE_AND_DELIVERY_TYPE_EBOOK);
            psparam.put("complex", COMPLEX);
            List<ProductSale> productSales = productService.findProductSale(psparam, null);
            if (productSales.size() <= 0) {
                continue;
            }
            Map<ProductSale, List<ProductSale>> item = new HashMap<ProductSale, List<ProductSale>>();
            item.put(ps, productSales);
            return item;
        }
    }
    
    
    public Map<ProductSale, List<ProductSale>> fetchMergeItem(ProductSale productSale) {
    	return null;
    	
    }

    /**
     * 得到合并任务
     *
     * @return
     */
    private ProductMergeTask fetchProductMergeTask() {
        lock.lock();
        try {
            ProductMergeTask pmt = null;
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("status", Code.PRODUCT_MERTE_STATUS_NONE);
            param.put("salestatus", Code.PRODUCT_SALE_STATUS_ONSHELF);

            while (pmt == null) {
                if (productMerges.size() <= 0) {
                    Pagination page = new Pagination();
                    page.setPageSize(5);
                    if (null != lastTask) {
                        param.put("id", lastTask.getId());
                    }
                    List<ProductMergeTask> items = productMergeTaskDao.listProductMergeTask(param, page);
                    productMerges.addAll(items);
                    if (null == items || items.isEmpty()) {
                        //先查上架的, 然后查所有的
                        if (param.containsKey("salestatus") && Code.PRODUCT_SALE_STATUS_ONSHELF.equals(param.get("salestatus"))) {
                            param.remove("salestatus");
                            pmt = null;
                            lastTask = null;
                            continue;
                        }
                        //如果lastTask为空, 同时, 又没有任何一个任何, 那肯定是没有了, 直接返回空, 否则会死循环
                        if (null == lastTask) {
                            return null;
                        }
                        lastTask = null;
                    } else {
                        lastTask = items.get(items.size() - 1);
                    }
                }

                pmt = productMerges.poll();
                if (null != pmt && null == pmt.getProduct().getBarcode() || "".equals(pmt.getProduct().getBarcode())) {
                    mergetItem(pmt.getProductSale());
                    pmt = null;
                }
            }
            return pmt;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 忽略9月网的, 不合并
     * @param ps
     */
    private void mergetItem(ProductSale ps) {
        if (ps == null) {
            return;
        }
        ProductMerge pm = new ProductMerge();
        pm.setMergeProduct(new ProductSale(ps.getId()));
        pm.setMerge(false);
        mergeItem(new Employee(Employee.SYSTEM), pm);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void mergeItem(Employee employee, ProductMerge productMerge) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (null == productMerge || null == productMerge.getMergeProduct() || null == productMerge.getMergeProduct().getId()) {
            return;
        }
        param.put("productsaleid", productMerge.getMergeProduct().getId());
        ProductMergeTask task = productMergeTaskDao.fetchProductMergeTask(param);
        task.setUser(employee);
        task.setLastTime(new Date());
        if(productMerge.isIgnore()){
        	productMergeTaskDao.update(task);
        	return;
        }
        task.setStatus(new Code(Code.PRODUCT_MERTE_STATUS_NOT_MERGE));
        if (productMerge.isMerge()) {
            if (null == productMerge.getWxProduct() || null == productMerge.getWxProduct().getId()) {
                return;
            }
            ProductSale jiu = productService.getProductSale(productMerge.getMergeProduct().getId());
            ProductSale wx = productService.getProductSale(productMerge.getWxProduct().getId());
           
            updateProductExtend(jiu, wx);

            jiu.setProduct(wx.getProduct());
            productService.update(jiu);
            task.setStatus(new Code(Code.PRODUCT_MERTE_STATUS_MERGE));
        }
        productMergeTaskDao.update(task);

    }

    /**
     * 合并product, 主要合并9月独有的extend信息
     *
     * @param jiu
     * @param wx
     */
    private void updateProductExtend(ProductSale jiu, ProductSale wx) {
        Product p = wx.getProduct();
        Product pj = jiu.getProduct();
        Set<ProductExtend> pes = pj.getExtendList();
        for (ProductExtend pe : pes) {
            Code open = pe.getProductMeta().getOpen();
            if (open.getId().equals(Code.PRODUCT_META_OPEN_EBOOK)) {
            	pe.setProduct(p);
            	p.addExtend(pe);
            }
        }
        productService.update(p);
    }
    
    
    /**
     * 取消电子书西属性
     * @param jiuyueProductSale
     */
    private void cancelEbookAttr(ProductSale jiuyueProductSale){
        //取消电子书属性
        Product product = productService.get(jiuyueProductSale.getProduct().getId());
        Set<ProductExtend> extendList =  product.getExtendList();
        Set<ProductExtend> newExtendList = new HashSet<ProductExtend>();
        for (ProductExtend productExtend : extendList) {
      	  Code open = productExtend.getProductMeta().getOpen();
            if (!open.getId().equals(Code.PRODUCT_META_OPEN_EBOOK)) {
            	newExtendList.add(productExtend);
            }
	      }
        product.setExtendList(newExtendList);
        productService.update(product);
    }


    /**
     * 添加9月网的图片
     *
     * @param ps
     */
    private void appendImage(ProductSale ps) {
        if (null == ps) {
            return;
        }
        String url = productJiuyueService.fetchJiuyueMiddleImg(ps);
        ProductImage pi = new ProductImage();
        pi.setType((short) 10);
        pi.setUrl(url);
        Set<ProductImage> pis = new HashSet<ProductImage>();
        pis.add(pi);
        ps.getProduct().setImageList(pis);
    }

	

}
