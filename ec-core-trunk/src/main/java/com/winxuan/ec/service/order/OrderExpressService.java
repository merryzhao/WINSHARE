package com.winxuan.ec.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.user.Employee;

/**
 * 快递
 * @author juqkai(juqkai@gmail.com)
 */
@Service("orderExpressService")
@Transactional(rollbackFor = Exception.class)
public interface OrderExpressService {
    /**
     * 快递对账
     */
    void expressToAccount(Employee employee, Map<String, List<String>> delivery, Map<String, Object> param, List<String> head, String name);
    /**
     * 是否能进行快递对账
     * @return
     */
    boolean canExpressToAccount();
    
    List<Object[]> findForDeliveryCompany(Map<String, Object> param);
    
}
