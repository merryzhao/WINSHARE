package com.winxuan.ec.admin.controller.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.ProductSaleBundleException;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.product.ProductSaleBundleService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author juqkai(juqkai@gmail.com)
 */
@Controller
@RequestMapping("/bundle")
public class ProductSaleBundleController {
    private static final String PRODUCTSALES = "productSales";
    private static final String PAGINATION = "pagination";
    private static final String OUTERID = "outerId";
    private static final String PRODUCTSALEIDS = "productSaleIds";
    private static final String PRODUCTID = "productId";
    private static final String SUCCESS = "success";
    private static final String SUCCESS_MSG = "搭配推荐操作成功!";
    
    private static final String MSG = "msg";
    @Autowired
    private ProductSaleBundleService productSaleBundleService;
    @Autowired
    private ProductService productService;
    private Pattern pa = null;
    
    public ProductSaleBundleController(){
        pa = Pattern.compile("^\\d+(\\.\\d+)?$");
    }
    
    @RequestMapping(value="list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView view = new ModelAndView("/productbundle/list");
        view.addObject("form", new ProductSaleBundleForm());
        return view;
    }
    @RequestMapping(value="list", method = RequestMethod.POST)
    public ModelAndView list(ProductSaleBundleForm form, @MyInject Pagination page){
        ModelAndView view = new ModelAndView("/productbundle/list");
        Map<String, Object> param = new HashMap<String, Object>();
        
        if(form.getCodingType() != null && form.getCodingContent() != null){
            String[] theIds=form.getCodingContent().split("\n");
            List<String> outerIds=new ArrayList<String>();
            List<Long> proIds=new ArrayList<Long>();
            if(OUTERID.equals(form.getCodingType())){
                for(int i=MagicNumber.ZERO;i<theIds.length;i++){ 
                    if(!StringUtils.isBlank(theIds[i])){
                         outerIds.add(theIds[i].trim());
                    }
                }
                if(!outerIds.isEmpty()){
                    param.put("outerIds", outerIds);
                }
            }else if(PRODUCTID.equals(form.getCodingType())){
                for(int i=MagicNumber.ZERO;i<theIds.length;i++){ 
                    if(!StringUtils.isBlank(theIds[i])){
                        try{
                            proIds.add(Long.valueOf(theIds[i].trim()));
                        }catch (Exception e) {
                            continue;
                        }
                    }
                }
                if(proIds.size() > 0){
                     param.put(PRODUCTSALEIDS, proIds);
                }
            }
        }
        param.put("bundle", true);
        view.addObject(PRODUCTSALES, productService.findProductSale(param, page));
        view.addObject(PAGINATION, page);
        view.addObject("form", form);
        return view;
    }
    @RequestMapping(value="/new", method = RequestMethod.GET)
    public ModelAndView save(){
        ModelAndView view = new ModelAndView("/productbundle/new");
        return view;
    }
    @RequestMapping(value="/new", method = RequestMethod.POST)
    public ModelAndView save(Long[] masters, String[] products, @MyInject Employee user){
        ModelAndView view = new ModelAndView("/productbundle/new");
        try{
            productSaleBundleService.save(user, fetchMaster(masters), fetchProducts(products));
        } catch (ProductSaleBundleException be){
            view.addObject(MSG, "操作失败!" + be.getMessage());
            return view;
        }
        view.addObject(MSG, SUCCESS_MSG);
        return view;
    }
    
    private List<ProductSale> fetchMaster(Long... masters){
        List<ProductSale> list = new ArrayList<ProductSale>();
        for(Long id : masters){
            list.add(productService.getProductSale(id));
        }
        return list;
    }
    
    
    private Map<ProductSale, BigDecimal> fetchProducts(String[] products){
        Map<ProductSale, BigDecimal> ps = new HashMap<ProductSale, BigDecimal>();
        if(products == null){
            return ps;
        }
        for (String s : products) {
            String[] val = s.split(":");
            if(!pa.matcher(val[1]).matches()){
                throw new ProductSaleBundleException("优惠价必需是数字!");
            }
            Long productId = Long.valueOf(val[0]);
            BigDecimal saveMoney = new BigDecimal(val[1]);
            ProductSale productSale = productService.getProductSale(productId);
            if(productSale == null){
                throw new ProductSaleBundleException(productId + "不存在!");
            }
            ps.put(productSale, saveMoney);
        }
        return ps;
    }
    
    /**
     * 跳转到修改页面
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/edit", method= RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")Long id){
        ModelAndView view = new ModelAndView("/productbundle/edit");
        view.addObject("productSale", productService.getProductSale(id));
        return view;
    }
    /**
     * 修改
     * @param products
     * @return
     */
    @RequestMapping(value="/{id}/edit", method= RequestMethod.POST)
    public ModelAndView edit(@PathVariable Long id, String[] products, @MyInject Employee user){
        ModelAndView view = new ModelAndView("redirect:/bundle/" + id + "/edit");
        try{
            productSaleBundleService.edit(productService.getProductSale(id), fetchProducts(products), user);
        } catch(ProductSaleBundleException e){
            view.addObject(MSG, "搭配推荐操作失败!" + e.getMessage());
            view.addObject(SUCCESS, false);
            return view;
        }
        view.addObject(MSG, SUCCESS_MSG);
        return view;
    }
    @RequestMapping(value="/bundleitem/{id}", method = RequestMethod.DELETE)
    public ModelAndView del(@PathVariable Long id, @MyInject Employee user){
        ModelAndView view = new ModelAndView("/productbundle/delbundleitem");
        productSaleBundleService.del(id, user);
        view.addObject(SUCCESS, true);
        view.addObject(MSG, "删除成功!");
        return view;
    }
    
    @RequestMapping(value="/{id}/log", method = RequestMethod.GET)
    public ModelAndView showLog(@PathVariable("id") Long masterId){
        ModelAndView view = new ModelAndView("/productbundle/logList");
        view.addObject("logs", productSaleBundleService.listLogForMaster(masterId));
        view.addObject("productSale", productService.getProductSale(masterId));
        return view;
    }
    
    /**
     * 查找商品
     * @param ids
     * @param codingType
     * @return
     */
    @RequestMapping(value = "/productinfolist", method = RequestMethod.POST)
    public ModelAndView productInfoList(@RequestParam("codingValue") String ids,
                                  @RequestParam("codingType") String codingType){
        ModelAndView mav=new ModelAndView("/product/complex");
        Map<String,Object> parameters = new HashMap<String, Object>();
        String errorMsg="";
        String[] theIds=ids.split("\n");
        List<String> outerIds=new ArrayList<String>();
        List<Long> proIds=new ArrayList<Long>();
        if(OUTERID.equals(codingType)){
            for(int i=MagicNumber.ZERO;i<theIds.length;i++){ 
                if(!StringUtils.isBlank(theIds[i])){
                     outerIds.add(theIds[i].trim());
                }
            }
            if(!outerIds.isEmpty()){
                parameters.put("outerIds", outerIds);
            }
        }else if(PRODUCTID.equals(codingType)){
            proIds.add(Long.valueOf("-1"));
            for(int i=MagicNumber.ZERO;i<theIds.length;i++){ 
                if(!StringUtils.isBlank(theIds[i])){
                    try{
                        proIds.add(Long.valueOf(theIds[i].trim()));
                    }catch (Exception e) {
                        continue;
                    }
                }
            }
                 parameters.put(PRODUCTSALEIDS, proIds);
        }
        List<ProductSale> productSales =productService.findProductSale(parameters, null);
        boolean nothas=true;
        if(OUTERID.equals(codingType)){
            for(String o:outerIds){
                for(ProductSale ps:productSales){
                   if(ps.getOuterId().equals(o)){
                       nothas=false;
                       break;
                   }
                }
                if(nothas){
                    errorMsg=errorMsg+"自编码为："+o+"的商品不存在<br/>";
                }
             nothas=true;
            }
             
        }else if(PRODUCTID.equals(codingType)){
            for(Long l:proIds){
                for(ProductSale ps:productSales){
                   if(ps.getId().equals(l)){
                       nothas=false;
                       break;
                   }
                }
                if(nothas&&l>0){
                    errorMsg=errorMsg+"商品编码为："+l+"的商品不存在<br/>";
                }
             nothas=true;
            }
        }
        mav.addObject(PRODUCTSALES, productSales);
        mav.addObject("error", errorMsg);
        return mav;
    }
}
