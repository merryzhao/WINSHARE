package com.winxuan.ec.admin.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.BatchDeliveryException;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderPackage;
import com.winxuan.ec.model.product.DeliveryProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 订单发货Controller.
 * @author heyadong
 * @version 1.0, 2012-11-16 下午01:58:11
 */
@Controller
@RequestMapping("/orderDelivery")
public class OrderDeliveryController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryService deliveryService;
    
    @RequestMapping(value="/batchDelivery",method=RequestMethod.GET)
    public ModelAndView batchDelivery(){
        return new ModelAndView("/order/delivery/upload");
    }
    @RequestMapping(value="/batchDelivery/upload",method=RequestMethod.POST)
    public ModelAndView batchDeliveryUpload(@MyInject Employee employee,
            @RequestParam(value = "file") MultipartFile file)   {
        ModelAndView mav = new ModelAndView("/order/delivery/result");
        //批量发货.业务需要.物流号填.0 物流公司为 成都物流中心
        DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(DeliveryCompany.CHENGDU_CENTER);
        String deliverycode = "0";
        int cellOrder = MagicNumber.ZERO;
        int cellSap = MagicNumber.TWO;
        int cellDeliveryQuantity = MagicNumber.FIVE;
        
        Workbook wb = null;
        Sheet sheet = null;
        try {
            wb = Workbook.getWorkbook(file.getInputStream());
            sheet = wb.getSheet(0);
        } catch (Exception e) {
            mav.addObject(ControllerConstant.MESSAGE_KEY,"Excel格式 不正确,请确定Excel格式为2003");
            return mav;
        }
        //数据装载
        Map<String, OrderPackage> orderPackages = new HashMap<String, OrderPackage>();
        //Excel格式验证
        int rows = sheet.getRows();
        for (int i=1; i< rows; i++) {
            Cell[] cell = sheet.getRow(i);
            if (cell.length < MagicNumber.SIX || StringUtils.isBlank(cell[cellOrder].getContents())) {
                continue;
            }
            String orderId = cell[cellOrder].getContents();
            String sapCode = cell[cellSap].getContents();
            String deliveryQuantity = cell[cellDeliveryQuantity].getContents();
            if(StringUtils.isBlank(orderId) || !orderId.matches("\\d+")) {
                mav.addObject(ControllerConstant.MESSAGE_KEY, String.format("[%s]行,订单号:%s ,订单格式错误!",i, orderId));
                return mav;
            } else if (StringUtils.isBlank(deliveryQuantity) || !deliveryQuantity.matches("\\d+")){
                mav.addObject(ControllerConstant.MESSAGE_KEY, String.format("[%s]行,订单号:%s  发货数量格式错误!",i, orderId));
                return mav;
            } else if (StringUtils.isBlank(sapCode)) {
                mav.addObject(ControllerConstant.MESSAGE_KEY, String.format("[%s]行,订单号:%s  商品SAP编码格式错误!",i, orderId));
                return mav;
            }
            
            OrderPackage op = orderPackages.get(orderId);
            if (op == null) {
                Order order = orderService.get(orderId);
                if (order == null) {
                    mav.addObject(ControllerConstant.MESSAGE_KEY, String.format("订单号:%s 不存在", orderId));
                    return mav;
                }
                op = new OrderPackage();
                op.setOrder(order);
                op.setProductSaleSet(new HashSet<DeliveryProductSale>());
                op.setDeliveryCompany(deliveryCompany);
                op.setDeliveryCode(deliverycode);
                orderPackages.put(orderId, op);
            }
            
            if (op.hasProductSale(sapCode)) {
                mav.addObject(ControllerConstant.MESSAGE_KEY, String.format("订单号:%s ,SAP编码重复%s", orderId, sapCode));
                return mav;
            }
            DeliveryProductSale dp = new DeliveryProductSale();
            dp.setDeliveryQuantity(Integer.valueOf(deliveryQuantity));
            dp.setSapCode(sapCode);
            op.getProductSaleSet().add(dp);
        }
        try {
            orderService.batchDelivery(new ArrayList<OrderPackage>(orderPackages.values()), employee);
        } catch (BatchDeliveryException e) {
            mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
            return mav;
        }
        mav.addObject(ControllerConstant.MESSAGE_KEY, "上传发货数据成功!");
        return mav;
    }
}
