package com.winxuan.ec.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.AttachService;

/**
 * 订单批量操作
 * @author juqkai(juqkai@gmail.com)
 */
@Service("orderBatchService")
@Transactional(rollbackFor = Exception.class)
public class OrderBatchServiceImpl implements OrderBatchService{
    @Autowired
    private AttachService attachService; 
    
    @Autowired
    private OrderService orderService;
    

    /**
     * 批量复制订单
     * 
     * @param employee
     * @param items
     */
    @Async
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void batchCopyOrder(Employee employee, List<String> items, String filename) {
        List<List<String>> data = new ArrayList<List<String>>();
        for(String orderid : items){
            List<String> col = new ArrayList<String>();
            data.add(col);
            col.add(orderid);
            try {
                Order newOrder = orderService.copyOrder(employee, orderid);
                col.add(newOrder.getId());
            }
            catch (Exception e) {
                col.add("");
                col.add(e.getMessage());
            }
        }
        makeFile(employee, data, filename);
    }
    
    
    /**
     * 生成导出数据
     * @param maps
     */
    private void makeFile(Employee employee, List<List<String>> data, String name){
        List<String> head = new ArrayList<String>();
        head.add("原订 单号");
        head.add("新订 单号");
        head.add("备注");
        
        attachService.makeAttach(employee, "复制订单_" + name, head, data);
    }
}
