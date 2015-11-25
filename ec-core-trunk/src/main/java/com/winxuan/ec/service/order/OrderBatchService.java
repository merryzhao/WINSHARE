package com.winxuan.ec.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.user.Employee;

/**
 * 订单批量操作service
 * @author juqkai(juqkai@gmail.com)
 */
@Service("orderBatchService")
@Transactional(rollbackFor = Exception.class)
public interface OrderBatchService {
    /**
     * 批量复制订单, 异步
     * @param employee
     * @param items
     * @param filename
     */
    void batchCopyOrder(Employee employee, List<String> items, String filename);
}
